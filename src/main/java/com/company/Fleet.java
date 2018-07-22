package com.company;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

class Fleet implements Comparable<Fleet> {
    final String id;

    Fleet(String id, Booking... bs) {
        assert id.length() == 2;
        this.id = id;

    }
    @Override
    public int compareTo(Fleet o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
