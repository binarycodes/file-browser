package pro.homedns.filebrowser.config;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file-browser")
public record ApplicationProperties(Path root) {
}
