package com.company;

import java.util.Arrays;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        combinationsPrint();
    }

    public static void combinationsPrint() {
        Collection<BookingSheet> alts = BookingSheet.possibleCombinations(
                Arrays.asList(new Fleet("f1"), new Fleet("f2"), new Fleet("f3")),
                Arrays.asList(
                        new Booking(0, 1), new Booking(3, 4), new Booking(0, 6),
                        new Booking(4, 6), new Booking(1, 2)));

        alts.forEach(b -> new ConsolePrinter(b).draw());
    }

    public static void examplePrint() {
        BookingSheet bs = new BookingSheet();
        bs.allocate(new Fleet("f1"), new Booking(0, 3), new Booking(4, 5), new Booking(8, 10));
        bs.allocate(new Fleet("f2"), new Booking(1, 2), new Booking(4, 6));
        bs.allocate(new Fleet("f3"), new Booking(1, 10));

        new ConsolePrinter(bs).draw();
    }
}
