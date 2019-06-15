package io.keweishang.io;

import java.io.File;
import java.io.IOException;

public class FileExample {

    public static void main(String[] args) throws IOException {
        // absolute path
        printFile("/foo/bar/my_absolute_file");
        // case where canonical != absolute
        printFile("/foo/bar/../baz/my_absolute_file");

        // relative path
        printFile("my_relative_file");
        // cases where canonical != absolute
        printFile(".");
        printFile("..");
    }

    private static void printFile(String path) throws IOException {
        File file = new File(path);
        System.out.println("Absolute path = " + file.getAbsolutePath());
        System.out.println("Canonical path = " + file.getCanonicalPath());
        System.out.println("Name = " + file.getName());
        System.out.println("Parent = " + file.getParent());
        System.out.println("Path = " + file.getPath());
        System.out.println("Is absolute = " + file.isAbsolute());
        System.out.println();
    }

}
