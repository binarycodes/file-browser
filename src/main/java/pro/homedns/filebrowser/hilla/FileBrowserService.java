package pro.homedns.filebrowser.hilla;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.security.PermitAll;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import pro.homedns.filebrowser.model.FileItem;
import pro.homedns.filebrowser.service.FileService;

import com.vaadin.hilla.BrowserCallable;

@Log4j2
@BrowserCallable
@PermitAll
public class FileBrowserService {

    private final FileService fileService;

    @Autowired
    public FileBrowserService(final FileService fileService) {
        this.fileService = fileService;
    }

    public @NonNull List<@NonNull FileItem> getRootLevelItems() {
        return this.fileService.getRootLevelItems();
    }

    public @NonNull List<@NonNull FileItem> getPathItems(String pathString) {
        try {
            var path = Path.of(pathString);
            return this.fileService.getItems(path);
        } catch (InvalidPathException ex) {
            log.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }
}
