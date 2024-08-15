package carm.parser.types;

import carm.CarmUriBuilder;

public record Link(String name, String url, CarmUriBuilder carmUriBuilder) {
}
