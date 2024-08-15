package carm.storage.type100incrementalindex;

import carm.storage.LinkStore;

import java.util.List;

public record Type100IncrementalIndexStore(
        Long timestamp,
        Integer lowerBound,
        Integer upperBound,
        List<LinkStore> links,
        List<LinkStore> newLinks,
        List<LinkStore> deletedLinks
) {
}
