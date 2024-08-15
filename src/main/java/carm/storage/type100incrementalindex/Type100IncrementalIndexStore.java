package carm.storage.type100incrementalindex;

import carm.storage.LinkStore;

import java.util.List;

public record Type100IncrementalIndexStore(
        Long timestamp,
        String url,
        List<LinkStore> links,
        List<LinkStore> newLinks,
        List<LinkStore> deletedLinks
) {
}
