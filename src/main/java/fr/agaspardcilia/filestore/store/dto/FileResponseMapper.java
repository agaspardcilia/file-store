package fr.agaspardcilia.filestore.store.dto;

import java.io.File;
import java.time.Instant;

/**
 * A mapper meat to map everything related to {@link FileResponse}.
 */
public class FileResponseMapper {
    private FileResponseMapper() {
        // Do not instantiate.
    }

    /**
     * Maps a {@link File} to a {@link FileResponse}.
     *
     * @param file the {@link File} to map.
     * @return the mapped {@link FileResponse}.
     */
    public static FileResponse toResponse(File file) {
        return new FileResponse(
                file.getName(),
                Instant.ofEpochMilli(file.lastModified()),
                file.length()
        );
    }
}
