package com.company;

import java.util.Arrays;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
        exampleSolve2();
    }

    public static void examplePrint() {
        BookingSheet bs = new BookingSheet();
        bs.allocate(new Fleet("f1"), new Booking(0, 3), new Booking(4, 5), new Booking(8, 10));
        bs.allocate(new Fleet("f2"), new Booking(1, 2), new Booking(4, 6));
        bs.allocate(new Fleet("f3"), new Booking(1, 10));

        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);
    }

    public static void exampleSolve1() {
        BookingSheet bs = new BookingSheet();
        Fleet f1 = new Fleet("f1");
        Fleet f2 = new Fleet("f2");
        Fleet f3 = new Fleet("f3");
        bs.allocate(f1, new Booking(0, 2), new Booking(4, 6), new Booking(7, 11));
        bs.allocate(f2, new Booking(0, 1), new Booking(3, 5), new Booking(6, 10));
        bs.allocate(f3, new Booking(0, 3), new Booking(5, 6));
        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);

        bs.accept(new Booking(2, 4));
        cs.draw(bs);
    }

    public static void exampleSolve2() {
        BookingSheet bs = new BookingSheet();
        Fleet f1 = new Fleet("f1");
        Fleet f2 = new Fleet("f2");
        Fleet f3 = new Fleet("f3");
        Fleet f4 = new Fleet("f4");
        bs.allocate(f1, new Booking(0, 2), new Booking(4, 6));
        bs.allocate(f2, new Booking(1, 3), new Booking(4, 6));
        bs.allocate(f3, new Booking(2, 4), new Booking(5, 6));
        bs.allocate(f4, new Booking(3, 5), new Booking(6, 7));

        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);

        Booking tentative = new Booking(0, 3);
        System.out.print("Trying to put booking: ");
        cs.draw(tentative);
        System.out.printf(" %s\n", tentative);

        bs.accept(tentative);
        cs.draw(bs);
    }
}
