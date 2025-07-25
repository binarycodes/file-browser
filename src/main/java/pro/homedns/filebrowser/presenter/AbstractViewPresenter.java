package pro.homedns.filebrowser.presenter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import lombok.Getter;
import pro.homedns.filebrowser.view.AbstractBaseView;

public abstract class AbstractViewPresenter<V extends AbstractBaseView<V, T>, T extends AbstractViewPresenter<V, T>> {

    @Getter
    private V view;

    private final Collection<Consumer<V>> viewCommands = new ArrayList<>();

    public void doInView(final Consumer<V> viewCommand) {
        if (view != null) {
            viewCommand.accept(view);
        } else {
            viewCommands.add(viewCommand);
        }
    }

    public void viewEntered(final V view) {
        this.view = view;
        
        doInView(AbstractBaseView::cleanup);

        Optional.ofNullable(view).ifPresent(v -> {
            final var commands = new ArrayList<>(viewCommands);
            viewCommands.clear();
            commands.forEach(cmd -> cmd.accept(v));
        });
    }

    public void afterNavigation(final V view) {
        viewNavigated(view);
    }

    public void viewNavigated(final V view) {
        doInView(AbstractBaseView::cleanup);
    }

    public void viewExited() {
        view = null;
    }

    public void init(final V view) {
        // do nothing
    }

    public void destroy() {
        viewCommands.clear();
    }
}

