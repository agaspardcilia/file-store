package fr.agaspardcilia.filestore.store.dto;

import java.time.Instant;

/**
 * A response containing everything that describes a stored file.
 *
 * @param name the name of the file.
 * @param modificationDate the last modification date of the file.
 * @param size the size of the file in bytes.
 */
public record FileResponse(
        String name,
        Instant modificationDate,
        long size
) { }
