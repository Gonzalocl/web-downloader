package carm.storage.interinosincrementalindex;

import carm.storage.LinkStore;

import java.util.List;

public record InterinosIncrementalIndexStore(
        Long timestamp,
        Integer lowerBound,
        Integer upperBound,
        List<LinkStore> links,
        List<LinkStore> newLinks,
        List<LinkStore> deletedLinks
) {
}
