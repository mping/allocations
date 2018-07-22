package com.company;


import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import java.util.*;

class BookingSheet {
    int min = 0;
    int max = 0;

    SortedSet<Booking> unallocated = new TreeSet<>();
    Map<Fleet, SortedSet<Booking>> allocated = new TreeMap<>();

    boolean afterLast(Fleet f, Booking b) {
        SortedSet<Booking> f_bookings = allocated
                .getOrDefault(f, Collections.emptySortedSet());
        return f_bookings.isEmpty() || f_bookings.last().end < b.start;
    }

    boolean intersectsAny(Fleet f, Booking b) {
        return allocated.getOrDefault(f, Collections.emptySortedSet())
                .stream()
                .anyMatch(e -> e.intersects(b));
    }

    void allocate(Fleet f, Booking... bs) {
        for (Booking b : bs) {
            assert !intersectsAny(f, b);

            min = Math.min(min, b.start);
            max = Math.max(max, b.end);

            allocated
                    .computeIfAbsent(f, x -> new TreeSet<>())
                    .add(b);
        }
    }

    void add(Booking b) {
        unallocated.add(b);
    }

    void add(Fleet f) {
        assert !allocated.containsKey(f);
        allocated.put(f, new TreeSet<>());
    }

    boolean equivalent(BookingSheet other) {
        if (unallocated.size() != other.unallocated.size()) return false;

        Set<Fleet> thisFleets = allocated.keySet();
        Set<Fleet> otherFleets = other.allocated.keySet();
        if (thisFleets.size() != otherFleets.size()) return false;
        if (Sets.symmetricDifference(thisFleets, otherFleets).size() > 0) return false;

        for (Iterator<Fleet> t = thisFleets.iterator(); t.hasNext(); ) {
            for (Iterator<Fleet> o = otherFleets.iterator(); o.hasNext(); ) {
                Fleet tf = t.next();
                Fleet of = o.next();
                if (!tf.id.equals(of.id)) return false;

                SortedSet<Booking> tbookings = allocated.get(tf);
                SortedSet<Booking> obookings = other.allocated.get(of);

                if (Sets.symmetricDifference(tbookings, obookings).size() > 0) return false;
            }
        }

        return true;
    }


    static BookingSheet tryOrderedAllocation(List<Fleet> fleets, List<Booking> bookings) {
        BookingSheet copy = new BookingSheet();
        List<Booking> toBook = new ArrayList<>(bookings);

        for (Fleet f : fleets) {
            copy.add(f);

            for (Iterator<Booking> it = toBook.iterator(); it.hasNext(); ) {
                Booking b = it.next();
                // because the sheet keeps bookings sorted we want to make sure that:
                // - for a given booking sheet f1: [4,5], f2: <empty>
                //- if we try to book [1,2] at 'f1' it will reach the end and go to f2
                //- otherwise some orderedPermutations won't be possible
                if (copy.afterLast(f, b) && !copy.intersectsAny(f, b)) {
                    copy.allocate(f, b);
                    it.remove();
                }
            }
        }
        copy.unallocated.addAll(toBook);

        return copy;
    }

    //TODO stop on 0 unallocations
    static List<BookingSheet> possibleCombinations(List<Fleet> fleets, List<Booking> bookings) {
        Collection<Possibility> possibilities = orderedPermutations(fleets, bookings);

        List<BookingSheet> res = new ArrayList<>();
        for (Possibility p : possibilities) {
            BookingSheet alt = tryOrderedAllocation(p.fleets, p.bookings);
            if (res.stream().noneMatch(b -> b.equivalent(alt))) res.add(alt);
        }
        res.sort(Comparator.comparingInt(o -> o.unallocated.size()));
        return res;
    }

    //TODO convert to lazy iterator
    static Collection<Possibility> orderedPermutations  (Collection<Fleet> fleets, Collection<Booking> bookings) {
        Collection<Possibility> res = new ArrayList<>();

        for (List<Fleet> fleetPerms : Collections2.orderedPermutations(fleets)) {
            for (List<Booking> bookingPerms : Collections2.orderedPermutations(bookings)) {
                res.add(new Possibility(fleetPerms, bookingPerms));
            }
        }
        return res;
    }

    static class Possibility {
        final List<Fleet> fleets;
        final List<Booking> bookings;

        Possibility(List<Fleet> fleets, List<Booking> bookings) {
            this.fleets = fleets;
            this.bookings = bookings;
        }
    }

    public static void main(String[] args) {
        for (List<String> perm : Collections2.orderedPermutations(Arrays.asList("b", "c", "a"))) {
            System.out.println(perm);
        }
    }

}
