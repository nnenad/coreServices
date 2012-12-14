
package com.java.web.core.config.build;

import com.yahoo.platform.yui.compressor.YUICompressor;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class BuildJSCompressor {

    public class Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return name.endsWith(".js");
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        if (args.length < 1) {
            System.out.println("Two arguments required: ");
            System.out.println("1 - source folder to scan for js files");
            System.out.println("2 - destination folder to save minified js files");
            return;
        }
        File root = new File(args[0]);
        File destFile = new File(args[1]);
        String dest = destFile.getAbsolutePath();
        if (!dest.endsWith("\\"))
            dest += "\\";
        doFolder(root.listFiles(), dest);
    }

    private static void doFolder(File[] files, String dest) throws IOException, Exception {
        File destFile = new File(dest);
        if (!destFile.exists()) {
            destFile.mkdir();
            destFile = new File(dest);
        }
        if (!destFile.isDirectory())
            throw new Exception("Not directory: " + dest);
        for (int i=0; i<files.length; i++) {
            if (files[i].isDirectory()) {
                doFolder(files[i].listFiles(), dest + files[i].getName() + "\\");
            } else if (files[i].isFile()) {
                String name = files[i].getAbsolutePath();
                if (name.endsWith(".min.js") || name.endsWith(".min.css"))
                    continue;
                String[] arr = null;
                if (name.endsWith(".js")) {
                    String minName = files[i].getName();
                    minName = minName.substring(0, minName.length() - 2) + "min.js";
                    minName = dest + minName;
                    String[] arr2 = {
                        "--type",
                        "js",
                        "--charset",
                        "utf-8",
                        "-o",
                        minName,
                        name
                    };
                    arr = arr2;
                } else if (files[i].getName().endsWith(".css")) {
                    String minName = files[i].getName();
                    if ("ext-touch.css".equalsIgnoreCase(minName) ||
                        "ext-touch.min.css".equalsIgnoreCase(minName) ||
                        "ext-touch-debug.css".equalsIgnoreCase(minName) ||
                        "ext-touch-debug.min.css".equalsIgnoreCase(minName)
                    ) {
                        continue;
                    }
                    minName = minName.substring(0, minName.length() - 3) + "min.css";
                    minName = dest + minName;
                    String[] arr2 = {
                        "--type",
                        "css",
                        "--charset",
                        "utf-8",
                        "-o",
                        minName,
                        name
                    };
                    arr = arr2;
                }

                if (arr != null) {
                    for (int j=0; j<arr.length; j++) {
                        System.out.print(arr[j] + " ");
                    }
                    System.out.println();
                    YUICompressor.main(arr);
                }
            }
        }
    }

    

}
