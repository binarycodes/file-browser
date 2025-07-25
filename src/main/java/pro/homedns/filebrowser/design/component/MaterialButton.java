package pro.homedns.filebrowser.design.component;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MaterialButton extends Composite<Button> {
    private static final ButtonVariant DEFAULT_THEME_VARIANT = ButtonVariant.LUMO_TERTIARY;

    private MaterialButton() {
        getContent().addThemeVariants(DEFAULT_THEME_VARIANT);
    }

    public MaterialButton(final MaterialIcon icon) {
        this();
        getContent().setIcon(icon.create());
    }

    public MaterialButton(final String text) {
        this();
        getContent().setText(text);
    }

    public MaterialButton(final MaterialIcon icon, final String text) {
        this(text);

        final var iconComponent = icon.create();
        iconComponent.addClassNames(LumoUtility.Padding.Right.SMALL);

        getContent().setPrefixComponent(iconComponent);
    }

    public MaterialButton(final String text, final MaterialIcon icon) {
        this(text);

        final var iconComponent = icon.create();
        iconComponent.addClassNames(LumoUtility.Padding.Left.SMALL);

        getContent().setSuffixComponent(iconComponent);
    }

    public MaterialButton(final String text, final MaterialIcon icon, final Command command) {
        this(text, icon);
        addClickListener(command);
    }

    public MaterialButton(final MaterialIcon icon, final String text, final Command command) {
        this(icon, text);
        addClickListener(command);
    }

    public MaterialButton(final MaterialIcon icon, final Command command) {
        this(icon);
        addClickListener(command);
    }

    public MaterialButton(final String text, final Command command) {
        this(text);
        addClickListener(command);
    }

    @Override
    protected Button initContent() {
        return new Button();
    }

    public MaterialButton addClickListener(final Command command) {
        getContent().addClickListener(event -> command.execute());
        return this;
    }

    public MaterialButton setIcon(final MaterialIcon icon) {
        getContent().setIcon(icon.create());
        return this;
    }

    public MaterialButton setEnabled(final boolean enabled) {
        getContent().setEnabled(enabled);
        return this;
    }

    public MaterialButton setAriaLabel(final String label) {
        getContent().setAriaLabel(label);
        return this;
    }

    public MaterialButton setTooltipText(final String text) {
        getContent().setTooltipText(text);
        return this;
    }

    public MaterialButton withThemeVariants(final ButtonVariant... variants) {
        getContent().removeThemeVariants(DEFAULT_THEME_VARIANT);
        getContent().addThemeVariants(variants);
        return this;
    }

    public MaterialButton withClassNames(final String... classNames) {
        getContent().addClassNames(classNames);
        return this;
    }
}
