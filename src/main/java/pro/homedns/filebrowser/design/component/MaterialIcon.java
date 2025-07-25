package pro.homedns.filebrowser.design.component;

import com.vaadin.flow.component.html.Span;

/*
 * only adding the icons being used in the application
 * add the required icons from here - https://fonts.google.com/icons
 */
public enum MaterialIcon {
    FILE_DOWNLOAD, FOLDER, DESCRIPTION, CHEVRON_RIGHT, CLEAR;

    public static final String MATERIAL_ICONS = "material-icons";

    public Span create(final String... classNames) {
        final Span icon = new Span(
                (this.name().startsWith("_") ? this.name().substring(1) : this.name())
                        .toLowerCase()
        );
        icon.addClassName(MATERIAL_ICONS);
        icon.addClassNames(classNames);
        return icon;
    }
}