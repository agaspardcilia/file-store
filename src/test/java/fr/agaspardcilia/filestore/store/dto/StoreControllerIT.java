package fr.agaspardcilia.filestore.store.dto;

import fr.agaspardcilia.filestore.FileStoreApplication;
import fr.agaspardcilia.filestore.filemanager.DirectoryManager;
import fr.agaspardcilia.filestore.filemanager.DirectoryManagerException;
import fr.agaspardcilia.filestore.store.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = FileStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StoreControllerIT {

    @Autowired
    private MockMvc restUserMockMvc;

    @Autowired
    private StoreService service;

    private Path tmpDir;

    @BeforeEach
    void setUp(@TempDir Path dir) throws DirectoryManagerException {
        this.tmpDir = dir;
        // Injects a directory manager with the correct temp directory.
        ReflectionTestUtils.setField(service, "directoryManager", new DirectoryManager(dir, 42));
    }

    @Test
    void testListEmpty() throws Exception {
        restUserMockMvc.perform(get("/api/store"))
                .andExpect(status().is(200))
                .andExpect(content().json("[]"));
    }

    @Test
    void testList() throws Exception {
        File f1 = createTestFile("foo", new byte[0]);
        File f2 = createTestFile("bar", new byte[1]);
        restUserMockMvc.perform(get("/api/store"))
                .andExpect(status().is(200))
                .andExpect(
                        content()
                                .json(
                                        "[{\"name\":\"foo\",\"modificationDate\":\"%s\",\"size\":0},{\"name\":\"bar\",\"modificationDate\":\"%s\",\"size\":1}]"
                                                .formatted(toJsonDate(f1.lastModified()), toJsonDate(f2.lastModified()))
                                )
                );
    }

    @Test
    void testDownloadNotFound() throws Exception {
        restUserMockMvc.perform(get("/api/store/download/foo"))
                .andExpect(status().is(404))
                // Do a simple direct check as the message contains a timestamp.
                .andDo(result ->
                        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"No file to be found for this name\""))
                );
    }

    @Test
    void testDownload() throws Exception {
        byte[] fileData = new byte[]{0, 1, 2};
        createTestFile("foo", new byte[5]);
        createTestFile("bar", fileData);
        restUserMockMvc.perform(get("/api/store/download/bar"))
                .andExpect(status().is(200))
                .andExpect(content().bytes(fileData));
    }

    @Test
    void testUploadMissingPart() throws Exception {
        restUserMockMvc.perform(multipart("/api/store/upload"))
                .andExpect(status().is(400))
                // Do a simple direct check as the message contains a timestamp.
                .andDo(result ->
                        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"Required part 'file' is not present.\""))
                );
    }

    @Test
    void testUploadFileWithBadName() throws Exception {
        byte[] fileData = new byte[1000];
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "/not/good/at/all", "Content-Type: text", fileData
        );
        restUserMockMvc.perform(
                        multipart("/api/store/upload")
                                .file(mockFile)
                ).andExpect(status().is(400))
                // Do a simple direct check as the message contains a timestamp.
                .andDo(result ->
                        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"Name is not valid\""))
                );
    }

    @Test
    void testUploadOverSizedFile() throws Exception {
        byte[] fileData = new byte[1000];
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "foo", "Content-Type: text", fileData
        );
        restUserMockMvc.perform(
                multipart("/api/store/upload")
                        .file(mockFile)
                ).andExpect(status().is(400))
                // Do a simple direct check as the message contains a timestamp.
                .andDo(result ->
                        assertTrue(result.getResponse().getContentAsString().contains("\"message\":\"Not enough available space to write this file\""))
                );
    }

    @Test
    void testUpload() throws Exception {
        byte[] fileData = new byte[]{3, 4, 5};
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "foo", "Content-Type: text", fileData
        );
        restUserMockMvc.perform(
                multipart("/api/store/upload")
                        .file(mockFile)
        ).andExpect(status().is(200));
        Path actual = tmpDir.resolve("foo");
        assertTrue(actual.toFile().exists());
        assertEquals(new String(fileData), new String(Files.readAllBytes(actual)));
    }

    private File createTestFile(String name, byte[] data) throws IOException {
        return Files.write(tmpDir.resolve(name), data, StandardOpenOption.CREATE_NEW).toFile();
    }

    private String toJsonDate(long epochMillis) {
        return Instant.ofEpochMilli(epochMillis).toString();
    }
}
