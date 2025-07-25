package pro.homedns.filebrowser.design.component;

import java.util.List;

import lombok.extern.log4j.Log4j2;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Log4j2
public abstract class NotifiableComposite<T extends Component> extends Composite<T> implements HasSize {
    private static final int NOTIFICATION_DURATION = 5000;

    public void onError(final String displayMessage) {
        onError(displayMessage, false);
    }

    public void onSuccess(final String displayMessage) {
        onSuccess(displayMessage, false);
    }

    public void onWarning(final String displayMessage) {
        onWarning(displayMessage, false);
    }

    public void onError(final String displayMessage, final boolean translate) {
        log.error(translate ? I18NProvider.translate(displayMessage) : displayMessage);
        showErrorNotification(displayMessage, translate);
    }

    public void onError(final String logMessage, final String displayMessage, final boolean translate) {
        log.error(logMessage);
        showErrorNotification(displayMessage, translate);
    }

    public void onError(final String logMessage, final Throwable throwable, final String displayMessage, final boolean translate) {
        log.error(logMessage, throwable);
        showErrorNotification(displayMessage, translate);
    }

    private void showErrorNotification(final String displayMessage, final boolean translate) {
        final var notification = createNotification(displayMessage, translate);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }

    public void onSuccess(final String displayMessage, final boolean translate) {
        final var notification = createNotification(displayMessage, translate);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    public void onWarning(final String displayMessage, final boolean translate) {
        final var notification = createNotification(displayMessage, translate);
        notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
    }

    public void showMultipleErrors(final List<String> displayMessages, final boolean translate) {
        displayMessages.forEach(displayMessage -> showErrorNotification(displayMessage, translate));
    }

    private Notification createNotification(final String displayMessage, final boolean translate) {
        final var notificationMessage = translate ? I18NProvider.translate(displayMessage) : displayMessage;
        final var notification = new Notification();

        final var notificationComponent = new FlexedRowDiv.Medium(
                new Span(notificationMessage),
                new MaterialButton(MaterialIcon.CLEAR, notification::close)
                        .withThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_SMALL)
                        .withClassNames(LumoUtility.Margin.Start.AUTO)
        ).withClassNames(LumoUtility.AlignItems.CENTER);

        notification.add(notificationComponent);
        notification.setDuration(NOTIFICATION_DURATION);
        notification.open();
        return notification;
    }
}
