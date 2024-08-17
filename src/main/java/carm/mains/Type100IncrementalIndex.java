package carm.mains;

import carm.Carm;
import carm.CarmUriBuilder;
import carm.linktype.CarmType;
import carm.parser.impl.Type100Item;
import carm.parser.impl.Type100Parser;
import carm.parser.types.Link;
import carm.storage.LinkStore;
import carm.storage.type100incrementalindex.Type100IncrementalIndexStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public class Type100IncrementalIndex {

    public static void run(String dataFolder, String contentId, int lowerBound, int upperBound, String trace) throws IOException, URISyntaxException {

        final String url = buildUrl(contentId, lowerBound, upperBound, trace);

        final Document document = Jsoup.connect(url).get();

        final Type100Item item = new Type100Parser().parse(document);

        final List<Link> newLinks = storeAndGetNewLinks(dataFolder, item, url);

        printNewLinks(newLinks);

    }

    private static void printNewLinks(List<Link> newLinks) {

        if (newLinks.isEmpty()) {
            System.out.println("No new links");
        }
    }

    private static List<Link> storeAndGetNewLinks(String dataFolder, Type100Item item, String url) throws IOException {

        final ZonedDateTime now = ZonedDateTime.now();
        final Path dataPath = Paths.get(dataFolder, "Type100IncrementalIndex");
        final Path filePath = dataPath.resolve(Carm.formatter.format(now) + ".json");

        Files.createDirectories(dataPath);

        List<LinkStore> links = item.links().stream()
                .map(l -> new LinkStore(l.name(), l.url()))
                .toList();

        Type100IncrementalIndexStore indexStore = new Type100IncrementalIndexStore(now.toEpochSecond(), url, links, null, null);

        Files.writeString(filePath, Carm.gson.toJson(indexStore));

        return Collections.emptyList();
    }

    private static String buildUrl(String contentId, int lowerBound, int upperBound, String trace) throws URISyntaxException, MalformedURLException {
        return new CarmUriBuilder(CarmUriBuilder.UriPath.PAGE)
                .addContentId(contentId)
                .addType(CarmType.MENU)
                .addLowerBound(lowerBound)
                .addUpperBound(upperBound)
                .addTrace(trace)
                .build().toURL().toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        run("/tmp", "", 0, 3, "");
    }

}
