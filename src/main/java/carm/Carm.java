package carm;

import carm.exception.ConnectException;
import carm.linktype.CarmType;
import org.apache.hc.core5.net.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public class Carm {

    public static final String CARM_BASE_URL = "https://www.carm.es";
    public static final String CARM_URL_PATH = "web/pagina";
    public static final String CARM_CONTENT_ID_QUERY_PARAM = "IDCONTENIDO";
    public static final String CARM_TYPE_ID_QUERY_PARAM = "IDTIPO";

    public static List<CarmItem> fetchCarm(String contentId, CarmType type) throws ConnectException {

        final String url = buildUrl(contentId, type);

        final Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ConnectException();
        }

        return type.getParser().parse(doc);
    }

    private static String buildUrl(String contentId, CarmType type) {
        try {
            return new URIBuilder(CARM_BASE_URL)
                    .appendPath(CARM_URL_PATH)
                    .addParameter(CARM_CONTENT_ID_QUERY_PARAM, contentId)
                    .addParameter(CARM_TYPE_ID_QUERY_PARAM, type.getId())
                    .build().toURL().toString();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws ConnectException {
        fetchCarm("72846", CarmType.MENU);
    }
}
