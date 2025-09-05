package pro.homedns.filebrowser.model;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FileItem {
    @EqualsAndHashCode.Include
    @NotNull
    private String path;
    @NotNull
    private String displayName;
    private boolean isDirectory;
    private Instant lastModifiedOn;
    private String fileSize;
}
