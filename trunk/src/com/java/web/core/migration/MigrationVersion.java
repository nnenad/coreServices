
package com.java.web.core.migration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MigrationVersion implements Comparable<MigrationVersion> {

    protected ArrayList<Integer> list;

    public MigrationVersion(String txt) throws Exception {
        Pattern ptrn = Pattern.compile("(v\\d+)(_\\d+)?(_\\d+)?(_\\d+)?(_\\d+)?");
        Matcher matcher = ptrn.matcher(txt);
        this.list = new ArrayList<Integer>();
        if (matcher.find()) {
            int gc = matcher.groupCount();
            for (int i=1; i<gc; i++){
                String g = matcher.group(i);
                if (g == null)
                    break;
                g = g.replace("_", "").replace("v", "");
                Integer x = Integer.decode(g);
                this.list.add(x);
            }
        }
        if (this.list.isEmpty())
            throw new Exception("Invalid version " + txt);
    }

    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("v");
        Iterator iter = this.list.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(".");
            }
        }
        return buffer.toString();
    }

    public int compareTo(MigrationVersion o) {
        int cnt = Math.max(this.list.size(), o.list.size());
        int result = 0;
        for(int i=0; i<cnt; i++) {
            int a = this.list.size() > i ? this.list.get(i) : 0;
            int b = o.list.size() > i ? o.list.get(i) : 0;
            if (a<b) result = -1;
            else if (a>b) result = 1;
            if (result != 0)
                break;
        }
        return result;
    }

    
}
