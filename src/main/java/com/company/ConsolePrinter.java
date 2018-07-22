package com.company;

import java.util.TreeSet;
import java.util.stream.IntStream;

public class ConsolePrinter {

    private final BookingSheet bs;

    ConsolePrinter(BookingSheet bs) {
        this.bs = bs;
    }

    void draw() {
        //grid
        System.out.print("    ");
        for (int i = bs.min; i <= bs.max; i++) {
            System.out.printf("|%d", i);
        }
        System.out.println("");

        //line printer
        for (Fleet f : bs.allocated.keySet()) {
            System.out.printf("%s: ", f.id);

            TreeSet<Booking> copy = new TreeSet(bs.allocated.get(f));
            for (int i = bs.min; i <= bs.max && !copy.isEmpty(); ) {
                Booking next = copy.first();

                if (next.start == i) {
                    printBooking(next);
                    copy.remove(next);
                    i += next.size;
                } else {
                    System.out.print("  ");
                    i++;
                }
            }
            System.out.println("");
        }

        System.out.print("Unallocated:");
        for(Booking u : bs.unallocated) {
            System.out.printf("%s, ", u);
        }
        System.out.println("\n---\n");
    }

    static void printBooking(Booking b) {
        IntStream.range(0, b.size).forEach(i -> {
            String ch = i == 0 ? " <" : (i == b.size - 1) ? "|>" : "|=";
            System.out.print(ch);
        });
    }
}
