package pro.homedns.filebrowser.view;


import java.util.Collection;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import pro.homedns.filebrowser.design.component.FlexedColDiv;
import pro.homedns.filebrowser.design.component.NotifiableComposite;
import pro.homedns.filebrowser.presenter.AbstractViewPresenter;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

@Getter
@Log4j2
public abstract class AbstractBaseView<V extends AbstractBaseView<V, P>, P extends AbstractViewPresenter<V, P>> extends NotifiableComposite<FlexedColDiv.Small> implements AfterNavigationObserver {

    private final P presenter;

    protected AbstractBaseView(final P presenter) {
        this.presenter = presenter;
        getContent().setSizeFull();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onAttach(final AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        presenter.viewEntered((V) this);

        if (attachEvent.isInitialAttach()) {
            presenter.init((V) this);
            attachEvent.getUI().addDetachListener(e -> {
                try {
                    e.unregisterListener();
                } catch (final Exception ex) {
                    log.fatal(ex.getMessage(), ex);
                } finally {
                    destroy();
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterNavigation(final AfterNavigationEvent event) {
        presenter.afterNavigation((V) this);
    }

    protected void destroy() {
        presenter.viewExited();
        presenter.destroy();
    }

    @Override
    protected void onDetach(final DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        presenter.viewExited();
    }

    public void downloadFile(final String fileName, final byte[] result) {
        FileDownloader.downloadFile(this, fileName, result);
    }

    public void cleanup() {
        this.getContent().removeAll();
    }

    protected void add(final Component... components) {
        getContent().add(components);
    }

    protected void add(final Collection<Component> components) {
        getContent().add(components);
    }
}