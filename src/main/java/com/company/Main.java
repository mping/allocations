package com.company;

public class Main {

    public static void main(String[] args) {
        exampleSolve1();
        //exampleSolve2();
        //exampleSolve3();
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
        Fleet f4 = new Fleet("f4");
        Fleet f5 = new Fleet("f5");
        bs.allocate(f1, new Booking(1, 4));
        bs.allocate(f2, new Booking(0, 0), new Booking(3, 4));
        bs.allocate(f3, new Booking(0, 0), new Booking(2, 4));
        bs.allocate(f4, new Booking(0, 4));
        bs.allocate(f5, new Booking(0, 2), new Booking(4, 4));
        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);

        Booking tentative = new Booking(1, 3);
        System.out.print("Trying to put booking: ");
        cs.draw(tentative);
        System.out.printf(" %s\n", tentative);

        BookingSheet.Rearrangement rr = bs.accept(tentative);
        cs.draw(bs);

        rr.swaps.stream().forEach(s -> System.out.printf("Swapped Booking %s from %s to %s\n", s.booking, s.old, s.now));

    }

    public static void exampleSolve2() {
        BookingSheet bs = new BookingSheet();
        Fleet f1 = new Fleet("f1");
        Fleet f2 = new Fleet("f2");
        Fleet f3 = new Fleet("f3");
        Fleet f4 = new Fleet("f4");
        bs.allocate(f1, new Booking(0, 2), new Booking(4, 6));
        bs.allocate(f2, new Booking(1, 3), new Booking(5, 7));
        bs.allocate(f3, new Booking(2, 4), new Booking(6, 9));
        bs.allocate(f4, new Booking(3, 5), new Booking(7, 10));

        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);

        Booking tentative = new Booking(0, 3);
        System.out.print("Trying to put booking: ");
        cs.draw(tentative);
        System.out.printf(" %s\n", tentative);

        BookingSheet.Rearrangement rr = bs.accept(tentative);
        cs.draw(bs);

        rr.swaps.stream().forEach(s -> System.out.printf("Swapped Booking %s from %s to %s\n", s.booking, s.old, s.now));
    }

    public static void exampleSolve3() {
        BookingSheet bs = new BookingSheet();
        Fleet f1 = new Fleet("f1");
        Fleet f2 = new Fleet("f2");
        bs.allocate(f1, new Booking(0, 0), new Booking(2, 2));
        bs.allocate(f2, new Booking(1, 1), new Booking(3, 3));

        ConsolePrinter cs = new ConsolePrinter();
        cs.draw(bs);

        Booking tentative = new Booking(0, 3);
        System.out.print("Trying to put booking: ");
        cs.draw(tentative);
        System.out.printf(" %s\n", tentative);

        BookingSheet.Rearrangement rr = bs.accept(tentative);
        cs.draw(bs);

        rr.swaps.stream().forEach(s -> System.out.printf("Swapped Booking %s from %s to %s\n", s.booking, s.old, s.now));
    }
}
