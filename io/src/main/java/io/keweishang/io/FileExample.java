package io.keweishang.io;

import java.io.File;
import java.io.IOException;

public class FileExample {

    public static void main(String[] args) throws IOException {
//        filePathExamples();
//        rootDirectoriesExample();
        fileCreationModificationExamples();
    }

    private static void fileCreationModificationExamples() throws IOException {
        createEmptyFile("myfile");
        deleteFile("myfile");

        File tmpFile = createTmpFile("prefix");
        // the tmpFile is still there even after JVM exits, need to be deleted here manually
        deleteFile(tmpFile.getCanonicalPath());

        File tmpFile2 = createTmpFile("prefix");
        // the tmpFile is deleted after JVM exits
        tmpFile2.deleteOnExit();

        mkdirs("dir1/dir2/dir3");
        deleteFile("dir1/dir2/dir3");
    }

    private static void mkdirs(String path) {
        boolean created = new File(path).mkdirs();
        System.out.printf("Directory and its intermediate directories created: %s\n", created);
    }

    private static File createTmpFile(String prefix) throws IOException {
        File file = File.createTempFile(prefix, null);
        System.out.printf("Created tmp file: %s\n", file);
        return file;
    }

    private static void deleteFile(String path) {
        boolean deleted = new File(path).delete();
        System.out.printf("File %s is deleted: %s\n", path, deleted);
    }

    private static void createEmptyFile(String path) throws IOException {
        File f = new File(path);
        // OS will atomically check + create an empty file
        boolean created = f.createNewFile();
        System.out.printf("File %s is created: %s\n", path, created);
    }

    private static void rootDirectoriesExample() {
        File[] roots = File.listRoots();
        for (File root : roots) {
            System.out.println("Partition: " + root);
            System.out.println("Free space on this partition = " +
                    root.getFreeSpace());
            // usable is more accurate, it's the disk space usable by JVM, checks for write permissions
            System.out.println("Usable space on this partition = " +
                    root.getUsableSpace());
            System.out.println("Total space on this partition = " +
                    root.getTotalSpace());
        }
    }

    private static void filePathExamples() throws IOException {
        // absolute path
        printFilePath("/foo/bar/my_absolute_file");
        // case where canonical != absolute
        printFilePath("/foo/bar/../baz/my_absolute_file");

        // relative path
        printFilePath("my_relative_file");
        // cases where canonical != absolute
        printFilePath(".");
        printFilePath("..");
        // case of empty path
        printFilePath("");
    }

    private static void printFilePath(String path) throws IOException {
        File file = new File(path);
        System.out.println("Absolute path = " + file.getAbsolutePath());
        System.out.println("Canonical path = " + file.getCanonicalPath());
        System.out.println("Name = " + file.getName());
        System.out.println("Parent = " + file.getParent());
        System.out.println("Path = " + file.getPath());
        System.out.println("Is absolute = " + file.isAbsolute());
        System.out.println("---");
    }


}
