package carm.parser.impl;

import carm.parser.Parser;
import carm.parser.types.Link;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class Type100Parser implements Parser {

    private static Link elementToLink(Element element) {
        final String text = element.text();
        final String link = element.absUrl("href");

        return new Link(text, link, null);
    }

    @Override
    public Type100Item parse(Document document) {
        final Elements elements = document.select("#margenZonaPrincipal > ul > li > a");

        List<Link> links = elements.stream()
                .map(Type100Parser::elementToLink)
                .toList();

        return new Type100Item(links);
    }
}
