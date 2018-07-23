package com.company;


import com.google.common.base.Preconditions;

import java.util.*;
import java.util.stream.IntStream;

class BookingSheet {
    int min = 0;
    int max = 0;

    SortedSet<Booking> unallocated = new TreeSet<>();
    Map<Fleet, SortedSet<Booking>> allocated = new TreeMap<>();

    boolean hasBookingConflict(Fleet f, Booking b) {
        return allocated
                .getOrDefault(f, Collections.emptySortedSet())
                .stream()
                .anyMatch(e -> e.intersects(b));
    }

    boolean hasBookingOnDay(Fleet f, int day) {
        return allocated
                .getOrDefault(f, Collections.emptySortedSet())
                .stream()
                .anyMatch(e -> e.intersects(day));
    }

    void allocate(Fleet f, Collection<Booking> bs) {
        allocated.computeIfAbsent(f, x -> new TreeSet<>());

        for (Booking b : bs) {
            if (hasBookingConflict(f, b)) {
                throw new IllegalArgumentException("Can't allocate to fleet because of overbooking: " + b);
            }

            min = Math.min(min, b.start);
            max = Math.max(max, b.end);

            allocated
                    .get(f)
                    .add(b);
        }
    }

    void allocate(Fleet f, Booking... bs) {
        if (bs.length > 0) allocate(f, Arrays.asList(bs));
    }

    long totalAllocations(int day) {
        return allocated
                .values()
                .stream()
                .flatMap(ss -> new ArrayList<>(ss).stream())
                .filter(b -> b.intersects(day))
                .count();
    }

    public boolean canAccept(Booking booking) {
        //if max of overlaps <= num of fleets, we can accept the booking
        return IntStream
                .rangeClosed(booking.start, booking.end)
                .mapToLong(i -> totalAllocations(i))
                .allMatch(i -> i < allocated.keySet().size());
    }


    public void xchg(Fleet src, Fleet dst, int day) {
        Preconditions.checkArgument(allocated.containsKey(src));
        Preconditions.checkArgument(allocated.containsKey(dst));

        List<Booking> toGo = new ArrayList<>();
        List<Booking> toRcv = new ArrayList<>();

        Iterator<Booking> srcBookings = allocated.get(src).iterator();
        Iterator<Booking> dstBookings = allocated.get(dst).iterator();

        while (srcBookings.hasNext()) {
            Booking b = srcBookings.next();
            if (b.start <= day && day <= b.end) {
                throw new IllegalArgumentException("src contains a booking with specified day: " + b);
            }
            if (day < b.start) {
                toGo.add(b);
                srcBookings.remove();
            }
        }

        while (dstBookings.hasNext()) {
            Booking b = dstBookings.next();
            if (b.start < day && day <= b.end) {
                throw new IllegalArgumentException("dst contains a booking with specified day: " + b);
            }
            if (day <= b.start) {
                toRcv.add(b);
                dstBookings.remove();
            }
        }

        allocate(src, toRcv);
        allocate(dst, toGo);
    }

    public boolean accept(Booking booking) {
        if (canAccept(booking)) {
            Set<Fleet> fleets = allocated.keySet();

            Fleet src = fleets.stream().filter(f -> !hasBookingOnDay(f, booking.start)).findFirst().get();
            for (int i = booking.start + 1; i <= booking.end; i++) {
                int next = i;
                Fleet freeOnNextDay = fleets.stream().filter(f -> !hasBookingOnDay(f, next)).findFirst().get();
                xchg(freeOnNextDay, src, next);
            }

            allocate(src, booking);
            return true;
        } else {
            unallocated.add(booking);
            return false;
        }
    }

}
