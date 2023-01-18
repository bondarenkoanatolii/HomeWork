package com.hillel.anatoliibondarenko.homework7;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* For Stream API
import java.util.stream.Collectors;
 */

public class FileNavigator {
    private final HashMap<Path, List<FileData>> fileNavigator = new HashMap<>();

    public void add(Path path, FileData file) {
        if (!path.equals(file.getPath())) {
            System.out.printf("Error: key-path and file path do not match.\n" +
                    "Path: %s - FileData: %s.\n", path, file);
            return;
        }

        List<FileData> listFiles = fileNavigator.get(path);
        if (listFiles == null) {
            listFiles = new ArrayList<>();
            listFiles.add(file);
            fileNavigator.put(path, listFiles);
        } else { //If "file" already present, will do not add.
            if (!listFiles.contains(file)) listFiles.add(file);
        }
    }

    public List<FileData> find(Path path) {
        return fileNavigator.get(path);
    }

    public List<FileData> filterBySize(int size) {
        List<FileData> listFiltered = new ArrayList<>();
        for (List<FileData> list1 : fileNavigator.values())
            for (FileData file : list1)
                if (file.getSize() <= size)
                    listFiltered.add(file);
        return listFiltered;

        /* If allowed to use the Stream API.
        return fileNavigator.values().stream()
                .flatMap(listFile -> listFile.stream().
                        filter(file -> file.getSize() <= size))
                .collect(Collectors.toList());
         */
    }

    public void remove(Path path) {
        fileNavigator.remove(path);
    }

    public List<FileData> sortBySize() {
        List<FileData> listSorted = new ArrayList<>();
        for (List<FileData> list1 : fileNavigator.values())
            listSorted.addAll(list1);
        listSorted.sort(Comparator.comparingLong(FileData::getSize));
        return listSorted;

        /* If allowed to use the Stream API.
        return fileNavigator.values().stream()
                .flatMap(listFile -> listFile.stream())
                .sorted(Comparator.comparingLong(FileData::getSize))
                .collect(Collectors.toList());
         */
    }

    @Override
    public String toString() {
        return "FileNavigator{" +
                "fileNavigator=" + fileNavigator +
                '}';
    }
}

