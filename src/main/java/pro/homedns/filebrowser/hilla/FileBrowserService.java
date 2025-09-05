package pro.homedns.filebrowser.hilla;

import java.util.List;

import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import pro.homedns.filebrowser.model.FileItem;
import pro.homedns.filebrowser.service.FileService;

import com.vaadin.hilla.BrowserCallable;

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
}
