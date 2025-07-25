package pro.homedns.filebrowser.presenter;

import java.nio.file.Path;

import pro.homedns.filebrowser.config.ApplicationProperties;
import pro.homedns.filebrowser.service.FileService;
import pro.homedns.filebrowser.service.UserService;
import pro.homedns.filebrowser.view.DocumentView;

@Presenter
public class DocumentPresenter extends AbstractViewPresenter<DocumentView, DocumentPresenter> {

    private final ApplicationProperties properties;
    private final UserService userService;
    private final FileService fileService;

    public DocumentPresenter(final ApplicationProperties properties,
                             final UserService userService,
                             final FileService fileService) {
        this.properties = properties;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public void viewNavigated(final DocumentView view) {
        super.viewNavigated(view);

        doInView(v -> {
            v.createGrid(userService.zoneId());
            v.init();
        });

        enterPath(getRootPath());
    }

    public void enterPath(final Path path) {
        doInView(v -> {
            v.populateGrid(getRootPath(), path, fileService.getItems(path));
        });
    }

    public Path getRootPath() {
        return properties.root();
    }
}
