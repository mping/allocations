package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class ConsolePrinter {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static int nextColor = 0;

    private final Map<Booking, String> known = new HashMap<>();

    ConsolePrinter() {
    }

    void draw(BookingSheet bs) {
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
                    draw(next);
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
        for (Booking u : bs.unallocated) {
            System.out.printf("%s, ", u);
        }
        System.out.println("\n---\n");
    }

    void draw(Booking b) {
        String color = nextColor(b);
        String reset = ANSI_RESET;

        IntStream.range(0, b.size).forEach(i -> {
            String ch = i == 0 ? " <" : (i == b.size - 1) ? "|>" : "|=";
            System.out.print(color + ch + reset);
        });
    }


    String nextColor(Booking b) {
        return known.computeIfAbsent(b, booking -> rotateColor());
    }

    String rotateColor() {
        String[] colors = new String[]{ANSI_BLACK, ANSI_BLUE, ANSI_CYAN, ANSI_GREEN, ANSI_PURPLE, ANSI_YELLOW, ANSI_RED};
        return colors[nextColor++ % colors.length];
    }
}
