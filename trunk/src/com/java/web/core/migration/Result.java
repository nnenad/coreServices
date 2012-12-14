
package com.java.web.core.migration;

import java.util.ArrayList;
import java.util.Iterator;

public class Result extends ArrayList<MigrationResult> {

    public MigrationResult getError() {
        MigrationResult result = null;
        for (Iterator<MigrationResult> i = this.iterator(); i.hasNext(); ) {
            MigrationResult r = i.next();
            if (!r.isOK()) {
                result = r;
                break;
            }
        }
        return result;
    }

    

}
