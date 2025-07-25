package pro.homedns.filebrowser.design.component;

import pro.homedns.filebrowser.design.style.CustomCss;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;

public sealed class FlexedRowDiv extends Div
        permits FlexedRowDiv.Large, FlexedRowDiv.Medium, FlexedRowDiv.Small, FlexedRowDiv.None {

    public static non-sealed class Large extends FlexedRowDiv {
        public Large() {
            super();
            withClassNames(LumoUtility.Gap.LARGE);
        }

        public Large(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.LARGE);
        }
    }

    public static non-sealed class Medium extends FlexedRowDiv {
        public Medium() {
            super();
            withClassNames(LumoUtility.Gap.MEDIUM);
        }

        public Medium(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.MEDIUM);
        }
    }

    public static non-sealed class Small extends FlexedRowDiv {
        public Small() {
            super();
            withClassNames(LumoUtility.Gap.SMALL);
        }

        public Small(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.SMALL);
        }
    }

    public static non-sealed class None extends FlexedRowDiv {
        public None() {
            super();
            withClassNames(CustomCss.Gap.None);
        }

        public None(final Component... components) {
            super(components);
            withClassNames(CustomCss.Gap.None);
        }
    }

    private FlexedRowDiv() {
        addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.ROW,
                CustomCss.HideEmpty);
    }

    private FlexedRowDiv(final Component... components) {
        this();
        add(components);
    }

    public FlexedRowDiv withClassNames(final String... classNames) {
        addClassNames(classNames);
        return this;
    }

    public FlexedRowDiv withBaseBackGround() {
        addClassNames(LumoUtility.Background.BASE);
        return this;
    }
}
