package fr.agaspardcilia.filestore.configuration.properties;

import fr.agaspardcilia.filestore.store.StoreService;

import java.nio.file.Path;

/**
 * {@link StoreService} related properties.
 */
public class Store {
    private Path directory;
    private long sizeLimit;

    public Path getDirectory() {
        return directory;
    }

    public void setDirectory(Path directory) {
        this.directory = directory;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }
}
