package carm;

import carm.linktype.CarmType;
import org.apache.hc.core5.net.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CarmUriBuilder {

    public static final String CARM_HOST = "www.carm.es";
    public static final String CARM_BASE_URL = "https://" + CARM_HOST;
    private static final String CARM_CONTENT_ID_QUERY_PARAM = "IDCONTENIDO";
    private static final String CARM_TYPE_ID_QUERY_PARAM = "IDTIPO";
    private static final String CARM_LOWER_BOUND_QUERY_PARAM = "RESULTADO_INFERIOR";
    private static final String CARM_UPPER_BOUND_QUERY_PARAM = "RESULTADO_SUPERIOR";
    private static final String CARM_TRACE_QUERY_PARAM = "RASTRO";

    private final URIBuilder uriBuilder;
    private final UriPath uriPath;
    private String contentId;
    private CarmType carmType;
    private Integer lowerBound;
    private Integer upperBound;
    private String trace;

    public CarmUriBuilder(UriPath uriPath) throws URISyntaxException {
        this.uriPath = uriPath;
        uriBuilder = new URIBuilder(CARM_BASE_URL).appendPath(uriPath.getPath());
    }

    public CarmUriBuilder(String uri) throws URISyntaxException {
        if (!uri.startsWith(CARM_BASE_URL)) {
            throw new IllegalArgumentException();
        }

        uriBuilder = new URIBuilder(uri);

        final URI parsedUri = uriBuilder.build();

        uriPath = UriPath.getByPath(parsedUri.getPath());

        if (uriPath == null) {
            throw new IllegalArgumentException();
        }
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

    public enum UriPath {
        PAGE("/web/pagina"),
        DOWNLOAD("/web/descarga"),
        ;

        private static final Map<String, UriPath> byPath = new HashMap<>();
        private final String path;

        static {
            for (final UriPath value : UriPath.values()) {
                byPath.put(value.getPath(), value);
            }
        }

        UriPath(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

        public static UriPath getByPath(String path) {
            return byPath.get(path);
        }
    }
}
