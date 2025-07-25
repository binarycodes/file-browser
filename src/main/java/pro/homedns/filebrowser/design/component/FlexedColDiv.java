package pro.homedns.filebrowser.design.component;

import pro.homedns.filebrowser.design.style.CustomCss;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;

public sealed class FlexedColDiv extends Div
        permits FlexedColDiv.Large, FlexedColDiv.Medium, FlexedColDiv.Small, FlexedColDiv.None {

    public static non-sealed class Large extends FlexedColDiv {
        public Large() {
            super();
            withClassNames(LumoUtility.Gap.LARGE);
        }

        public Large(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.LARGE);
        }
    }

    public static non-sealed class Medium extends FlexedColDiv {
        public Medium() {
            super();
            withClassNames(LumoUtility.Gap.MEDIUM);
        }

        public Medium(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.MEDIUM);
        }
    }

    public static non-sealed class Small extends FlexedColDiv {
        public Small() {
            super();
            withClassNames(LumoUtility.Gap.SMALL);
        }

        public Small(final Component... components) {
            super(components);
            withClassNames(LumoUtility.Gap.SMALL);
        }
    }

    public static non-sealed class None extends FlexedColDiv {
        public None() {
            super();
            withClassNames(CustomCss.Gap.None);
        }

        public None(final Component... components) {
            super(components);
            withClassNames(CustomCss.Gap.None);
        }
    }

    private FlexedColDiv() {
        addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                CustomCss.HideEmpty);
    }

    private FlexedColDiv(final Component... components) {
        this();
        add(components);
    }

    public FlexedColDiv withClassNames(final String... classNames) {
        addClassNames(classNames);
        return this;
    }

    public FlexedColDiv withBaseBackGround() {
        addClassNames(LumoUtility.Background.BASE);
        return this;
    }
}
