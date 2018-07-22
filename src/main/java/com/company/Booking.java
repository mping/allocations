package com.company;

class Booking implements Comparable<Booking> {
    final int start;
    final int end;
    final int size;

    public Booking(int start, int end) {
        assert start >= 0;
        assert start <= end;

        this.start = start;
        this.end = end;
        this.size = end - start + 1;
    }

    boolean intersects(Booking other) {
        return (start <= other.start && other.start <= end) || (start <= other.end && other.end <= end);
    }


    @Override
    public int compareTo(Booking o) {
        int s = Integer.compare(start, o.start);
        if (s != 0) {
            return s;
        }

        s = Integer.compare(end, o.end);
        if (s != 0) {
            return s;
        }
        //same start and end -> sort by hashcode to ensure sorting is consistent
        return Integer.compare(hashCode(), o.hashCode());
    }

    @Override
    public String toString() {
        return String.format("[%d,%d]", start, end);
    }
}
