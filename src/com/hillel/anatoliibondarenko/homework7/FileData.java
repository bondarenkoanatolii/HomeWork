package com.hillel.anatoliibondarenko.homework7;

import java.nio.file.Path;

public class FileData {
    private final String name;
    private final long size;
    private final Path path;

    public FileData(String name, long size, Path path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", path=" + path.toString() +
                "}\n";
    }
}
