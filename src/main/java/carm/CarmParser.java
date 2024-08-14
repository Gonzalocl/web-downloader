package carm;

import carm.linktype.CarmType;
import carm.linktype.LinkType;
import carm.linktype.OtherType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.hc.core5.net.URIBuilder;
import org.apache.hc.core5.net.URLEncodedUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class CarmParser {

    public static List<CarmItem> parse60(Document document) {
        return Collections.emptyList();
    }

    public static List<CarmItem> parse100(Document doc) {
        final Elements elements = doc.select("#margenZonaPrincipal > ul > li > a");
        return elements.stream()
                .map(CarmParser::parse100Item)
                .toList();
    }

    private static CarmItem parse100Item(Element e) {
        final String contentId;
        final LinkType type;
        final String text = e.text();
        final String link = e.absUrl("href");

//        new URIBuilder(link).build().getqu

        if (link.startsWith(Carm.CARM_BASE_URL + "/" + Carm.CARM_URL_PATH)) {
            ImmutablePair<String, CarmType> contentIdAndType = extractContentIdAndTypeFromCarmUrl(link);
            contentId = contentIdAndType.getLeft();
            type = contentIdAndType.getRight();
        } else {
            contentId = "";
            type = OtherType.EXTERNAL;
        }

        return new CarmItem(contentId, type, text, link);
    }

    private static ImmutablePair<String, CarmType> extractContentIdAndTypeFromCarmUrl(String url) {
        try {
            final String query = new URL(url).getQuery();
            return ImmutablePair.nullPair();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ImmutablePair.nullPair();
    }

    public interface Parser {
        List<CarmItem> parse(Document doc);
    }
}
