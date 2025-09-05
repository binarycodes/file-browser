package pro.homedns.filebrowser.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.homedns.filebrowser.config.ApplicationProperties;
import pro.homedns.filebrowser.model.FileDownloadData;
import pro.homedns.filebrowser.model.FileItem;

@Log4j2
@Service
public class FileService {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public FileService(final ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public List<FileItem> getRootLevelItems() {
        return getItems(StringUtils.EMPTY);
    }

    public List<FileItem> getItems(final String pathString) {
        try {
            final var rootPath = applicationProperties.root();
            final var normalizedPath = rootPath.resolve(pathString).normalize();

            if (!normalizedPath.startsWith(rootPath)) {
                throw new SecurityException("Invalid file path: path traversal attempt detected.");
            }

            return Files.walk(normalizedPath, 1)
                    .filter(Predicate.not(normalizedPath::equals))
                    .map(itemPath -> {
                        try {
                            final var isDirectory = Files.isDirectory(itemPath);
                            final var lastModifiedOn = Files.getLastModifiedTime(itemPath);
                            final var fileSize = FileUtils.byteCountToDisplaySize(Files.size(itemPath));

                            final var displayName = itemPath.getFileName().toString();
                            var relativePath = rootPath.relativize(itemPath).toString();

                            return new FileItem(relativePath, displayName, isDirectory, lastModifiedOn.toInstant(), fileSize);
                        } catch (final IOException ex) {
                            log.fatal(ex.getMessage(), ex);
                        }
                        return null;
                    })
                    .toList();
        } catch (final IOException ex) {
            log.fatal(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    public FileDownloadData downloadFile(String pathString) throws IOException {
        final var rootPath = applicationProperties.root();
        final var normalizedPath = rootPath.resolve(pathString).normalize();

        if (!normalizedPath.startsWith(rootPath)) {
            throw new SecurityException("Invalid file path: path traversal attempt detected.");
        }

        if (normalizedPath.toFile().isDirectory()) {
            throw new IllegalArgumentException("Invalid file path: directory download not supported.");
        }

        final var content = Files.readAllBytes(normalizedPath);
        final var name = normalizedPath.getFileName().toString();

        return new FileDownloadData(name, content);
    }
}
