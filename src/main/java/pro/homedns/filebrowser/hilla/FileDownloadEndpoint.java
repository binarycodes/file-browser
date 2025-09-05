package pro.homedns.filebrowser.hilla;

import java.io.IOException;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pro.homedns.filebrowser.service.FileService;

@Log4j2
@RestController
public class FileDownloadEndpoint {

    private final FileService fileService;

    @Autowired
    public FileDownloadEndpoint(final FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/download/{*filePath}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String filePath) {
        final var normalizedFilePath = filePath.replaceFirst("^/", "");

        try {
            final var fileDownloadData = fileService.downloadFile(normalizedFilePath);
            final var file = new ByteArrayResource(fileDownloadData.content());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownloadData.name() + "\"")
                    .body(file);
        } catch (IOException | IllegalArgumentException | SecurityException ex) {
            log.error(ex.getMessage(), ex);
        }

        return ResponseEntity.notFound().build();
    }
}
