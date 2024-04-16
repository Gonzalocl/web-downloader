package carm;

import carm.linktype.LinkType;

public record CarmItem(String contentId, LinkType type, String text, String link) {
}
