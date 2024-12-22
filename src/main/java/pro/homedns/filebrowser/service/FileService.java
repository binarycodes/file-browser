package pro.homedns.filebrowser.service;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;
import pro.homedns.filebrowser.model.FileItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Service
public class FileService {

    public List<FileItem> getItems(Path path) {
        try {
            return Files.walk(path, 1)
                    .filter(Predicate.not(path::equals))
                    .map(itemPath -> {
                        try {
                            var isDirectory = Files.isDirectory(itemPath);
                            var owner = Files.getOwner(itemPath);
                            var permission = SystemUtils.IS_OS_WINDOWS ? Collections.<PosixFilePermission>emptySet() : Files.getPosixFilePermissions(itemPath);
                            var lastModifiedOn = Files.getLastModifiedTime(itemPath);
                            var fileSize = Files.size(itemPath);

                            return new FileItem(itemPath, isDirectory, owner, permission, lastModifiedOn, fileSize);
                        } catch (IOException e) {
                            //TODO: add logging;
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .toList();
        } catch (IOException e) {
            //TODO: add logging;
            System.out.println(e.getMessage());
        }
        return Collections.emptyList();
    }
}
