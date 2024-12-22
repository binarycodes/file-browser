package pro.homedns.filebrowser.callback;

import java.nio.file.Path;

@FunctionalInterface
public interface BreadCrumbNavigation {

    void navigate(Path path);
}
