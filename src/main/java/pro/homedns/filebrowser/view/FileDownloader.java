package pro.homedns.filebrowser.view;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.FileDownloadHandler;

public final class FileDownloader {

    private FileDownloader() {
        /* do not instantiate */
    }

    public static void downloadFile(Component component, Path path) {
        downloadFile(component, createDownloadHandler(path.toFile()));
    }

    public static void downloadFile(Component component, DownloadHandler downloadHandler) {
        Objects.requireNonNull(component);
        Objects.requireNonNull(downloadHandler);

        component.getUI()
                .map(UI::getSession)
                .map(VaadinSession::getResourceRegistry)
                .ifPresent(rr -> {
                    final var streamRegistration = rr.registerResource(downloadHandler);
                    component.addDetachListener(e -> Optional.ofNullable(streamRegistration).ifPresent(StreamRegistration::unregister));
                    component.getElement().executeJs("window.location.href = $0", streamRegistration.getResourceUri().toString());
                });
    }

    public static DownloadHandler createDownloadHandler(File file) {
        return new FileDownloadHandler(file) {
            @Override
            public boolean isAllowInert() {
                return true;
            }
        };
    }

}
