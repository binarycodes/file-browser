package pro.homedns.filebrowser.design.component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pro.homedns.filebrowser.callback.BreadCrumbNavigation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class BreadCrumbs extends FlexedRowDiv.Small {

    private final List<Path> crumbs;

    public BreadCrumbs(final Path root, final Path itemPath, final BreadCrumbNavigation breadCrumbNavigation) {
        crumbs = new ArrayList<>();

        addClassNames(LumoUtility.AlignItems.CENTER);

        findPaths(root, itemPath);

        Collections.reverse(crumbs);

        final var itr = crumbs.iterator();
        while (itr.hasNext()) {
            final var path = itr.next();
            add(new Button(path.getFileName().toString(), event -> breadCrumbNavigation.navigate(path)));
            if (itr.hasNext()) {
                final var icon = MaterialIcon.CHEVRON_RIGHT.create(LumoUtility.TextColor.SECONDARY);
                add(icon);
            }
        }
    }

    private void findPaths(final Path root, final Path itemPath) {
        crumbs.add(itemPath);

        if (root.equals(itemPath)) {
            return;
        }

        findPaths(root, itemPath.getParent());
    }
}
