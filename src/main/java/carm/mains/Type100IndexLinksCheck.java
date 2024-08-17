package carm.mains;

import carm.Carm;
import carm.CarmUriBuilder;
import carm.storage.type100incrementalindex.Type100IncrementalIndexStore;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Type100IndexLinksCheck {

    public static void run(String indexFile) throws IOException {

        Type100IncrementalIndexStore index = Carm.gson.fromJson(Files.readString(Path.of(indexFile)), Type100IncrementalIndexStore.class);

        final List<URIBuilder> uris = index.links().stream()
                .map(l -> {
                    try {
                        return new URIBuilder(l.url());
                    } catch (URISyntaxException e) {
                        throw new IllegalArgumentException();
                    }
                })
                .toList();

        if (uris.stream().allMatch(uri -> CarmUriBuilder.CARM_HOST.equals(uri.getHost()))) {
            System.out.println("All CARM");
        }

        if (uris.stream().allMatch(uri -> CarmUriBuilder.CARM_DOWNLOAD_PATH.equals(uri.getPath()))) {
            System.out.println("All downloads");
        }

        if (uris.stream().allMatch(uri -> uri.getFirstQueryParam(CarmUriBuilder.CARM_ARCHIVE_QUERY_PARAM).getValue().endsWith(".pdf"))) {
            System.out.println("All .pdf");
        }

    }

    public static void main(String[] args) throws IOException {
        run("/tmp/Type100IncrementalIndex/2024-08-15_18-32-38.json");
    }
}
