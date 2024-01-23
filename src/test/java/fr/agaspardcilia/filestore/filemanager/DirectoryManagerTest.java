package fr.agaspardcilia.filestore.filemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryManagerTest {

    @Test
    void testGetFile(@TempDir Path tempDir) throws DirectoryManagerException, IOException {
        File file = createTestFile(tempDir, "foo");
        DirectoryManager manager = new DirectoryManager(tempDir, 42666);
        try {
            manager.getFile("does not exist");
            fail();
        } catch (DirectoryManagerException e) {
            assertEquals("File does not exist", e.getMessage());
        }

        assertEquals(file, manager.getFile("foo"));
    }

    @Test
    void testList(@TempDir Path tempDir) throws DirectoryManagerException, IOException {
        DirectoryManager manager = new DirectoryManager(tempDir, 42666);

        // Should work with empty directories.
        assertEquals(List.of(), manager.listFiles());

        File f1 = createTestFile(tempDir, "foo");
        File f2 = createTestFile(tempDir, "bar");
        File f3 = createTestFile(tempDir, "fizz");

        assertEquals(List.of(f1, f2, f3), manager.listFiles());
    }

    @Test
    void testWrite(@TempDir Path tempDir) throws DirectoryManagerException {
        byte[] emptyData = new byte[0];
        DirectoryManager manager = new DirectoryManager(tempDir, 10);
        testError(
                manager,
                emptyData,
                "Loooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooong name",
                "Name is not valid"
        );
        testError(
                manager,
                emptyData,
                "/path/to/your/password/he/he/he",
                "Name is not valid"
        );
        testError(
                manager,
                new byte[20],
                "valid.name",
                "Not enough available space to write this file"
        );
        File file = manager.write(new byte[2], "awesome.name");
        assertEquals(2, file.length());
        assertEquals("awesome.name", file.getName());

        File file2 = manager.write(new byte[4], "awesome.name");
        assertEquals(4, file2.length());
        assertEquals("awesome.name", file2.getName());
    }

    private File createTestFile(Path dir, String name) throws IOException {
        File file = dir.resolve(name).toFile();
        if (!file.createNewFile()) {
            fail("test file creation failed");
        }
        return file;
    }

    private void testError(DirectoryManager manager, byte[] data, String name, String expectedExceptionMessage) {
        try {
            manager.write(data, name);
            fail();
        } catch (DirectoryManagerException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }

    }
}
