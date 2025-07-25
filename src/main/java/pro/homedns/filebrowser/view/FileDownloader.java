package pro.homedns.filebrowser.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.streams.DownloadHandler;
import com.vaadin.flow.server.streams.DownloadResponse;
import com.vaadin.flow.server.streams.FileDownloadHandler;
import com.vaadin.flow.server.streams.InputStreamDownloadHandler;

public final class FileDownloader {

    private FileDownloader() {
        /* do not instantiate */
    }

    public static void downloadFile(final Component component, final Path path) {
        downloadFile(component, createDownloadHandler(path.toFile()));
    }

    public static void downloadFile(final Component component, final DownloadHandler downloadHandler) {
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

    public static DownloadHandler createDownloadHandler(final File file) {
        return new FileDownloadHandler(file) {
            @Override
            public boolean isAllowInert() {
                return false;
            }
        };
    }

    public static DownloadHandler createDownloadHandler(final String fileName, final byte[] data) {
        return new InputStreamDownloadHandler(downloadEvent -> {
            return new DownloadResponse(
                    new ByteArrayInputStream(data), fileName, null, data.length);
        }) {
            @Override
            public boolean isAllowInert() {
                return false;
            }
        };
    }

    public static void downloadFile(final Component component, final String fileName, final byte[] result) {
        downloadFile(component, createDownloadHandler(fileName, result));
    }
}
