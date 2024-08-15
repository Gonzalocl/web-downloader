package carm;

import carm.exception.ConnectException;
import carm.linktype.CarmType;
import com.google.gson.Gson;
import org.apache.hc.core5.net.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

public class Carm {

    public static final String CARM_BASE_URL = "https://www.carm.es";
    public static final String CARM_URL_PATH = "web/pagina";
    public static final String CARM_CONTENT_ID_QUERY_PARAM = "IDCONTENIDO";
    public static final String CARM_TYPE_ID_QUERY_PARAM = "IDTIPO";

    public static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral('-')
            .appendValue(ChronoField.MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.DAY_OF_MONTH, 2)
            .appendLiteral('_')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral('-')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .toFormatter();

    public static final Gson gson = new Gson();

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
