package pro.homedns.filebrowser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "file-browser")
public record ApplicationProperties(Path root) {
}
