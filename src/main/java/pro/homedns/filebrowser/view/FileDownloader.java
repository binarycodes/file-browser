package pro.homedns.filebrowser.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileDownloader {

    public static void downloadFile(Component component, Path path) {
        var resource = new StreamResource(path.getFileName().toString(), () -> {
            try {
                return Files.newInputStream(path);
            } catch (IOException e) {
                //TODO: add logging
                throw new RuntimeException(e);
            }
        });
        downloadFile(component, resource);
    }

    public static void downloadFile(Component component, StreamResource streamResource) {
        streamResource.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment");
        component.getUI()
                .map(UI::getSession)
                .map(VaadinSession::getResourceRegistry)
                .ifPresent(registry -> {
                    var registration = registry.registerResource(streamResource);
                    component.addDetachListener(event -> {
                        if (registration != null) {
                            registration.unregister();
                        }
                    });

                    component.getElement().executeJs("window.location.href = $0", registration.getResourceUri().toString());
                });
    }
}
