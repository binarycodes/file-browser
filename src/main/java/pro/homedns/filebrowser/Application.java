package pro.homedns.filebrowser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;


@SpringBootApplication
@ConfigurationPropertiesScan
@Theme("my-theme")
public class Application implements AppShellConfigurator {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
