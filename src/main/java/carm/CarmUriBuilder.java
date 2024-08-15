package carm;

import carm.linktype.CarmType;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class CarmUriBuilder {

    public static final String CARM_HOST = "www.carm.es";
    public static final String CARM_BASE_URL = "https://" + CARM_HOST;
    private static final String CARM_PAGE_PATH = "/web/pagina";
    private static final String CARM_DOWNLOAD_PATH = "/web/descarga";
    private static final String CARM_CONTENT_ID_QUERY_PARAM = "IDCONTENIDO";
    private static final String CARM_TYPE_ID_QUERY_PARAM = "IDTIPO";
    private static final String CARM_LOWER_BOUND_QUERY_PARAM = "RESULTADO_INFERIOR";
    private static final String CARM_UPPER_BOUND_QUERY_PARAM = "RESULTADO_SUPERIOR";
    private static final String CARM_TRACE_QUERY_PARAM = "RASTRO";

    private final URIBuilder uriBuilder;
    private final UriType uriType;
    private String contentId;
    private CarmType carmType;
    private Integer lowerBound;
    private Integer upperBound;
    private String trace;

    public CarmUriBuilder(UriType uriType) throws URISyntaxException {
        this.uriType = uriType;
        uriBuilder = new URIBuilder(CARM_BASE_URL).appendPath(uriType.getPath());
    }

    public CarmUriBuilder addContentId(String contentId) {
        this.contentId = contentId;
        uriBuilder.addParameter(CARM_CONTENT_ID_QUERY_PARAM, contentId);
        return this;
    }

    public CarmUriBuilder addType(CarmType carmType) {
        this.carmType = carmType;
        uriBuilder.addParameter(CARM_TYPE_ID_QUERY_PARAM, carmType.getId());
        return this;
    }

    public CarmUriBuilder addLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
        uriBuilder.addParameter(CARM_LOWER_BOUND_QUERY_PARAM, String.valueOf(lowerBound));
        return this;
    }

    public CarmUriBuilder addUpperBound(int upperBound) {
        this.upperBound = upperBound;
        uriBuilder.addParameter(CARM_UPPER_BOUND_QUERY_PARAM, String.valueOf(upperBound));
        return this;
    }

    public CarmUriBuilder addTrace(String trace) {
        this.trace = trace;
        uriBuilder.addParameter(CARM_TRACE_QUERY_PARAM, trace);
        return this;
    }

    public URI build() throws URISyntaxException {
        return uriBuilder.build();
    }

    public enum UriType {
        PAGE(CARM_PAGE_PATH),
        DOWNLOAD(CARM_DOWNLOAD_PATH),
        ;

        private final String path;

        UriType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
