package pro.homedns.filebrowser.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.io.FileUtils;
import pro.homedns.filebrowser.config.ApplicationProperties;
import pro.homedns.filebrowser.model.FileItem;
import pro.homedns.filebrowser.service.FileService;
import pro.homedns.filebrowser.service.UserService;
import pro.homedns.filebrowser.view.component.AppIcon;
import pro.homedns.filebrowser.view.component.BreadCrumbs;

import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TimeZone;


@Route("")
@PermitAll
public class DocumentView extends VerticalLayout {

    private final ApplicationProperties properties;

    private final UserService userService;
    private final FileService fileService;

    private final Div breadCrumbContainer;
    private final Grid<FileItem> grid;

    public DocumentView(ApplicationProperties properties,
                        AuthenticationContext authContext,
                        UserService userService,
                        FileService fileService) {
        this.properties = properties;
        this.userService = userService;
        this.fileService = fileService;

        breadCrumbContainer = new Div();
        grid = createGrid();

        populateGrid(properties.root());
        add(breadCrumbContainer, grid);
    }

    private Grid<FileItem> createGrid() {
        var grid = new Grid<FileItem>();
        grid.setAllRowsVisible(true);

        grid.addComponentColumn(item -> {
            var label = new Span(item.path().getFileName().toString());
            var icon = new AppIcon(item.isDirectory() ? VaadinIcon.FOLDER : VaadinIcon.FILE_O).withClassNames(LumoUtility.Flex.SHRINK_NONE);
            var wrap = new Div(icon, label);
            wrap.addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.Gap.XSMALL);
            return wrap;
        });
        grid.addColumn(item -> item.owner().getName());
        grid.addColumn(item -> {
            var formatter = DateTimeFormatter
                    .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)
                    .withLocale(getLocale())
                    .withZone(TimeZone.getTimeZone(userService.zoneInfo()).toZoneId());
            return formatter.format(item.lastModifiedOn().toInstant());
        });
        grid.addColumn(item -> FileUtils.byteCountToDisplaySize(item.fileSize()));
        grid.addComponentColumn(item -> {
            if (item.isDirectory()) {
                return new Span("-");
            }

            var downloadIcon = new AppIcon(VaadinIcon.DOWNLOAD_ALT);
            downloadIcon.addClickListener(event -> {
                FileDownloader.downloadFile(this, item.path());
            });
            return downloadIcon;
        });


        grid.addItemDoubleClickListener(event -> {
            if (event.getItem().isDirectory()) {
                populateGrid(event.getItem().path());
            }
        });

        return grid;
    }

    private void populateGrid(Path path) {
        var items = fileService.getItems(path);
        grid.setItems(items);

        this.breadCrumbContainer.removeAll();
        this.breadCrumbContainer.add(new BreadCrumbs(properties.root(), path, this::populateGrid));
    }

}
