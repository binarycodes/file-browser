package pro.homedns.filebrowser.view;

import java.nio.file.Path;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import jakarta.annotation.security.PermitAll;
import org.apache.commons.io.FileUtils;
import pro.homedns.filebrowser.design.component.BreadCrumbs;
import pro.homedns.filebrowser.design.component.FlexedRowDiv;
import pro.homedns.filebrowser.design.component.MaterialButton;
import pro.homedns.filebrowser.design.component.MaterialIcon;
import pro.homedns.filebrowser.model.FileItem;
import pro.homedns.filebrowser.presenter.DocumentPresenter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;


@Route("")
@PermitAll
public class DocumentView extends AbstractBaseView<DocumentView, DocumentPresenter> {

    private final Div breadCrumbContainer;
    private Grid<FileItem> grid;

    public DocumentView(final DocumentPresenter documentPresenter) {
        super(documentPresenter);
        breadCrumbContainer = new Div();
    }

    public void init() {
        add(breadCrumbContainer, grid);
    }

    public void createGrid(final ZoneId zoneId) {
        grid = new Grid<>();
        grid.setAllRowsVisible(true);

        grid.addComponentColumn(item -> {
            final var label = new Span(item.path().getFileName().toString());
            final var icon = (item.isDirectory() ? MaterialIcon.FOLDER : MaterialIcon.DESCRIPTION).create(LumoUtility.Flex.SHRINK_NONE);

            return new FlexedRowDiv.Small(icon, label);
        });
        grid.addColumn(item -> item.owner().getName());
        grid.addColumn(item -> {
            final var formatter = DateTimeFormatter
                    .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM)
                    .withLocale(getLocale())
                    .withZone(zoneId);
            return formatter.format(item.lastModifiedOn().toInstant());
        });
        grid.addColumn(item -> FileUtils.byteCountToDisplaySize(item.fileSize()));
        grid.addComponentColumn(item -> {
            if (item.isDirectory()) {
                return new MaterialButton(MaterialIcon.CHEVRON_RIGHT, () -> getPresenter().enterPath(item.path()));
            }

            return new MaterialButton(MaterialIcon.FILE_DOWNLOAD, () -> FileDownloader.downloadFile(this, item.path()));
        });

        grid.addItemDoubleClickListener(event -> {
            if (event.getItem().isDirectory()) {
                getPresenter().enterPath(event.getItem().path());
            }
        });
    }

    public void populateGrid(final Path rootPath, final Path path, final List<FileItem> fileItems) {
        grid.setItems(fileItems);

        this.breadCrumbContainer.removeAll();
        this.breadCrumbContainer.add(new BreadCrumbs(rootPath, path, getPresenter()::enterPath));
    }

}
