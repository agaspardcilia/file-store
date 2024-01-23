package fr.agaspardcilia.filestore.store;

import fr.agaspardcilia.filestore.common.exception.ApiBadRequestException;
import fr.agaspardcilia.filestore.common.exception.ApiInternalServerErrorException;
import fr.agaspardcilia.filestore.common.exception.ApiNotFoundException;
import fr.agaspardcilia.filestore.store.dto.FileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * A controller exposing all the features related to store operations.
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
    private final StoreService service;

    public StoreController(StoreService service) {
        this.service = service;
    }

    /**
     * File upload endpoint, will store the given {@link MultipartFile} to be retrieved later on.
     *
     * @param file the file to store.
     * @return a description of the newly stored file.
     */
    @PostMapping("/upload")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            throw new ApiBadRequestException("No input file");
        }

        try {
            return service.storeFile(file);
        } catch (StoreException e) {
            throw new ApiBadRequestException(e.getMessage());
        }
    }

    /**
     * File download endpoint. Retrieves a file present in the store by its name.
     *
     * @param fileName the name of the file to download.
     * @return the file to download as a {@link FileSystemResource}.
     */
    @GetMapping("/download/{fileName}")
    public FileSystemResource downloadFile(@PathVariable String fileName) {
        try {
            File file = service.getFile(fileName)
                    .orElseThrow(() -> new ApiNotFoundException("No file to be found for this name"));
            return new FileSystemResource(file);
        } catch (StoreException e) {
            // Log as it's not supposed to happen.
            LOGGER.warn("Error during file read", e);
            throw new ApiInternalServerErrorException("Error during file read");
        }
    }

    /**
     * File list endpoint. Returns all the description of all the files present in the store.
     *
     * @return the {@link List} of all the file descriptions as {@link FileResponse}s.
     */
    @GetMapping
    public List<FileResponse> getFileList() {
        return service.listFiles();
    }
}
