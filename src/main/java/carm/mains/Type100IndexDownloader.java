package carm.mains;

import carm.Carm;
import carm.CarmUriBuilder;
import carm.storage.LinkStore;
import carm.storage.type100incrementalindex.Type100IncrementalIndexStore;
import carm.utils.FileUtils;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Type100IndexDownloader {

    private static final HttpClient client = HttpClient.newHttpClient();

    public static void run(String indexFile) throws IOException {

        Type100IncrementalIndexStore index = Carm.gson.fromJson(Files.readString(Path.of(indexFile)), Type100IncrementalIndexStore.class);

        final Path dataPath = FileUtils.getFolder(indexFile);

        Files.createDirectories(dataPath);

        final List<LinkStore> failedDownloads = new LinkedList<>();
        final int[] count = {0};
        final int total = index.links().size();
        index.links().forEach(link -> download(link, ++count[0], total, dataPath, failedDownloads));

        if (!failedDownloads.isEmpty()) {
            System.out.printf("%d downloads failed%n", failedDownloads.size());

            failedDownloads.forEach(d -> System.out.printf("Failed: %s%n", d));

        }
    }

    private static void download(LinkStore link, int count, int total, Path dataPath, List<LinkStore> failedDownloads) {
        final String url = link.url();
        final URIBuilder uriBuilder;
        final URI uri;

        System.out.printf("%d of %d: %s%n", count, total, url);

        try {
            uriBuilder = new URIBuilder(url);
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            failedDownloads.add(link);
            System.out.printf("Failed to download: %s%n", url);
            e.printStackTrace();
            return;
        }

        final String archive = uriBuilder.getFirstQueryParam(CarmUriBuilder.CARM_ARCHIVE_QUERY_PARAM).getValue();
        final String fileNameExtension = archive.substring(archive.lastIndexOf("."));
        final Path filePath = FileUtils.getFilePath(dataPath, link.name(), fileNameExtension);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();

        try {
            client.send(request, HttpResponse.BodyHandlers.ofFile(filePath));
        } catch (IOException | InterruptedException e) {
            failedDownloads.add(link);
            System.out.printf("Failed to download: %s%n", url);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        run("/tmp/Type100IncrementalIndex/2024-08-15_18-32-38.json");
    }

}
