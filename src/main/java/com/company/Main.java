package com.company;

import java.util.Arrays;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        exampleSolve();
    }

    public static void combinationsPrint() {
        Collection<BookingSheet> alts = BookingSheet.possibleCombinations(
                Arrays.asList(new Fleet("f1"), new Fleet("f2"), new Fleet("f3"), new Fleet("f4")),
                Arrays.asList(
                        new Booking(0, 1),
                        new Booking(3, 4),
                        new Booking(0, 6),
                        new Booking(4, 6),
                        new Booking(2, 7),
                        new Booking(5, 7),
                        new Booking(8, 10),
                        new Booking(9, 10),
                        new Booking(1, 2)));

        alts.forEach(b -> new ConsolePrinter(b).draw());
    }

    public static void examplePrint() {
        BookingSheet bs = new BookingSheet();
        bs.allocate(new Fleet("f1"), new Booking(0, 3), new Booking(4, 5), new Booking(8, 10));
        bs.allocate(new Fleet("f2"), new Booking(1, 2), new Booking(4, 6));
        bs.allocate(new Fleet("f3"), new Booking(1, 10));

        new ConsolePrinter(bs).draw();
    }

    public static void exampleSolve() {
        BookingSheet bs = new BookingSheet();
        bs.allocate(new Fleet("f1"), new Booking(0, 2), new Booking(4, 6));
        bs.allocate(new Fleet("f2"), new Booking(0, 1), new Booking(3, 6));
        bs.allocate(new Fleet("f3"), new Booking(0, 3), new Booking(5, 6));


        System.out.println(bs.canAccept(new Booking(2,4)));

        bs.accept(new Booking(2,4));

        new ConsolePrinter(bs).draw();
    }
}
