package pro.homedns.filebrowser.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.homedns.filebrowser.config.ApplicationProperties;
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
        return getItems(applicationProperties.root());
    }

    public List<FileItem> getItems(final Path path) {
        try {
            return Files.walk(path, 1)
                    .filter(Predicate.not(path::equals))
                    .map(itemPath -> {
                        try {
                            final var isDirectory = Files.isDirectory(itemPath);
                            final var owner = Files.getOwner(itemPath);
                            final var permission = SystemUtils.IS_OS_WINDOWS ? Collections.<PosixFilePermission>emptySet() : Files.getPosixFilePermissions(itemPath);
                            final var lastModifiedOn = Files.getLastModifiedTime(itemPath);
                            final var fileSize = Files.size(itemPath);

                            return new FileItem(itemPath.toString(), isDirectory, owner, permission, lastModifiedOn, fileSize);
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
}
