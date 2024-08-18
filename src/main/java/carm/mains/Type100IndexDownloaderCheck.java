package carm.mains;

import carm.Carm;
import carm.storage.LinkStore;
import carm.storage.type100incrementalindex.Type100IncrementalIndexStore;
import carm.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Type100IndexDownloaderCheck {

    private static List<String> files;

    public static void run(String indexFile) throws IOException {

        final Type100IncrementalIndexStore index = Carm.gson.fromJson(Files.readString(Path.of(indexFile)), Type100IncrementalIndexStore.class);

        final Path dataPath = FileUtils.getFolder(indexFile);

        files = Files.list(dataPath).map(Path::toString).toList();

        index.links().forEach(link -> check(link, dataPath, files));

    }

    private static void check(LinkStore link, Path dataPath, List<String> files) {
        final String filePath = FileUtils.getFilePath(dataPath, link.name(), ".pdf").toString();

        if (!files.contains(filePath)) {
            System.out.println("Not found: " + filePath);
        }
    }

    public static void main(String[] args) throws IOException {
        run("/tmp/Type100IncrementalIndex/2024-08-15_18-32-38.json");
    }
}
