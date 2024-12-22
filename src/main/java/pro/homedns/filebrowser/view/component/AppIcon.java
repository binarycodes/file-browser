package pro.homedns.filebrowser.view.component;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AppIcon extends Icon {
    public AppIcon(VaadinIcon vaadinIcon) {
        super(vaadinIcon);
        addClassNames(LumoUtility.FontSize.XXSMALL);
    }

    public AppIcon withClassNames(String... classNames) {
        addClassNames(classNames);
        return this;
    }
}
