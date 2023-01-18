package com.hillel.anatoliibondarenko.homework7;

import java.nio.file.Path;

public class Test {
    public static void main(String[] args) {
        FileNavigator fileNavigator = new FileNavigator();

        // Adding a file to the specified path.
        fileNavigator.add(Path.of("/path/to/file1"), new FileData("file1.txt", 1000, Path.of("/path/to/file1")));
        fileNavigator.add(Path.of("/path/to/file1"), new FileData("file2.txt", 2000, Path.of("/path/to/file1")));
        fileNavigator.add(Path.of("/path/to/file1"), new FileData("file3.txt", 3000, Path.of("/path/to/file1")));
        fileNavigator.add(Path.of("/path/to/file1"), new FileData("file4.txt", 4000, Path.of("/path/to/file1")));
        fileNavigator.add(Path.of("/path/to/file2"), new FileData("file1.txt", 1500, Path.of("/path/to/file2")));
        fileNavigator.add(Path.of("/path/to/file2"), new FileData("file2.txt", 2500, Path.of("/path/to/file2")));
        fileNavigator.add(Path.of("/path/to/file2"), new FileData("file3.txt", 3500, Path.of("/path/to/file2")));
        fileNavigator.add(Path.of("/path/to/file3"), new FileData("file1.txt", 1300, Path.of("/path/to/file3")));
        fileNavigator.add(Path.of("/path/to/file3"), new FileData("file2.txt", 2300, Path.of("/path/to/file3")));
        fileNavigator.add(Path.of("/path/to/file4"), new FileData("file1.txt", 2600, Path.of("/path/to/file4")));
        fileNavigator.add(Path.of("/path/to/file5"), new FileData("file1.txt", 2700, Path.of("/path/to/file4")));

        System.out.println("Adding a file to the specified path.");
        System.out.println(fileNavigator);
        System.out.println();

        System.out.println("List of files associated with the path passed as a parameter.");
        System.out.println(fileNavigator.find(Path.of("/path/to/file2")));
        System.out.println();

        System.out.println("List of files whose size (in bytes) does not exceed the value passed as a parameter.");
        System.out.println(fileNavigator.filterBySize(2000));
        System.out.println();

        System.out.println("Delete the path and its associated files, based on the path value passed as a parameter.");
        fileNavigator.remove(Path.of("/path/to/file4"));
        System.out.println(fileNavigator);
        System.out.println();

        System.out.println("Sort all available files by their size (ascending)");
        System.out.println(fileNavigator.sortBySize());
    }
}
