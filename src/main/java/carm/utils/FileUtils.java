package carm.utils;

import java.nio.file.Path;

public class FileUtils {

    public static Path getFolder(String file) {
        return Path.of(file.substring(0, file.lastIndexOf(".")));
    }

    public static Path getFilePath(Path dirname, String fileName, String fileNameExtension) {
        return dirname.resolve(fileName.replace("/", "-") + fileNameExtension);
    }
}
