package carm.mains;

import carm.Carm;
import carm.CarmUriBuilder;
import carm.linktype.CarmType;
import carm.parser.impl.Type100Item;
import carm.parser.impl.Type100Parser;
import carm.parser.types.Link;
import carm.storage.LinkStore;
import carm.storage.interinosincrementalindex.InterinosIncrementalIndexStore;
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

public class InterinosIncrementalIndex {

    public static void fetchCarm(String dataFolder, int lowerBound, int upperBound) throws IOException, URISyntaxException {

        final String url = buildUrl(lowerBound, upperBound);

        final Document document = Jsoup.connect(url).get();

        final Type100Item item = new Type100Parser().parse(document);

        final List<Link> newLinks = storeAndGetNewLinks(dataFolder, item, lowerBound, upperBound);

        printNewLinks(newLinks);

    }

    private static void printNewLinks(List<Link> newLinks) {

        if (newLinks.isEmpty()) {
            System.out.println("No new links");
        }
    }

    private static List<Link> storeAndGetNewLinks(String dataFolder, Type100Item item, int lowerBound, int upperBound) throws IOException {

        final ZonedDateTime now = ZonedDateTime.now();
        final Path dataPath = Paths.get(dataFolder, "InterinosIncrementalIndex");
        final Path filePath = dataPath.resolve(Carm.formatter.format(now) + ".json");

        Files.createDirectories(dataPath);

        List<LinkStore> links = item.links().stream()
                .map(l -> new LinkStore(l.name(), l.url()))
                .toList();

        InterinosIncrementalIndexStore indexStore = new InterinosIncrementalIndexStore(now.toEpochSecond(), lowerBound, upperBound, links, null, null);

        Files.writeString(filePath, Carm.gson.toJson(indexStore));

        return Collections.emptyList();
    }

    private static String buildUrl(int lowerBound, int upperBound) throws URISyntaxException, MalformedURLException {
        return new CarmUriBuilder(CarmUriBuilder.UriType.PAGE)
                .addContentId("24183")
                .addType(CarmType.MENU)
                .addLowerBound(lowerBound)
                .addUpperBound(upperBound)
                .addTrace("c$m4034")
                .build().toURL().toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        fetchCarm("/tmp", 0, 3);
    }

}
