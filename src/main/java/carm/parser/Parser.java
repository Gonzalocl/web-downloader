package carm.parser;

import org.jsoup.nodes.Document;

public interface Parser {
    Item parse(Document document);
}
