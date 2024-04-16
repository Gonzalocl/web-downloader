package carm.linktype;

import carm.CarmParser;
import carm.exception.TypeNotRegisteredException;

import java.util.HashMap;
import java.util.Map;

public enum CarmType implements LinkType {

    DOWNLOAD("60", CarmParser::parse60),
    MENU("100", CarmParser::parse100),
    ;

    private final String id;
    private final CarmParser.Parser parser;

    private static final Map<String, CarmType> byId = new HashMap<>();

    static {
        for (CarmType value : CarmType.values()) {
            byId.put(value.getId(), value);
        }
    }

    CarmType(String id, CarmParser.Parser parser) {
        this.id = id;
        this.parser = parser;
    }

    public String getId() {
        return id;
    }

    public CarmParser.Parser getParser() {
        return parser;
    }

    public static CarmType getById(String id) {
        if (isTypeidRegistered(id)) {
            return byId.get(id);
        }

        throw new TypeNotRegisteredException();
    }

    public static boolean isTypeidRegistered(String id) {
        return byId.containsKey(id);
    }
}
