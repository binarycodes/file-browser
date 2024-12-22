package pro.homedns.filebrowser.view.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import pro.homedns.filebrowser.callback.BreadCrumbNavigation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BreadCrumbs extends Div {

    private List<Path> crumbs;

    public BreadCrumbs(Path root, Path itemPath, BreadCrumbNavigation breadCrumbNavigation) {
        crumbs = new ArrayList<>();

        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.ROW, LumoUtility.Gap.SMALL, LumoUtility.AlignItems.CENTER);

        findPaths(root, itemPath);

        Collections.reverse(crumbs);

        var itr = crumbs.iterator();
        while (itr.hasNext()) {
            var path = itr.next();
            add(new Button(path.getFileName().toString(), event -> breadCrumbNavigation.navigate(path)));
            if (itr.hasNext()) {
                var icon = new AppIcon(VaadinIcon.CHEVRON_RIGHT).withClassNames(LumoUtility.TextColor.SECONDARY);
                add(icon);
            }
        }
    }


    private void findPaths(Path root, Path itemPath) {
        crumbs.add(itemPath);

        if (root.equals(itemPath)) {
            return;
        }

        findPaths(root, itemPath.getParent());
    }
}
