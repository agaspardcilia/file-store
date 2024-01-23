package fr.agaspardcilia.filestore.store.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileResponseMapperTest {

    @Test
    void testMap(@TempDir Path dir) throws IOException {
        Path path = dir.resolve("test.txt");
        Files.write(path, new byte[42], StandardOpenOption.CREATE_NEW);

        assertEquals(response("test.txt", 42, path.toFile().lastModified()), FileResponseMapper.toResponse(path.toFile()));
    }

    private FileResponse response(String name, long size, long lastModified) {
        return new FileResponse(name, Instant.ofEpochMilli(lastModified), size);
    }
}
