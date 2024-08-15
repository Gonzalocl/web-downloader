package carm.parser.impl;

import carm.parser.Item;
import carm.parser.types.Link;

import java.util.List;

public record Type100Item(List<Link> links) implements Item {
}
