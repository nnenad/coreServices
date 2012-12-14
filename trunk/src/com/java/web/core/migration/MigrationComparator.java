
package com.java.web.core.migration;

import java.util.Comparator;

public class MigrationComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        MigrationWrapper a = (MigrationWrapper)o1;
        MigrationWrapper b = (MigrationWrapper)o2;
        return a.compareTo(b);
    }

}
