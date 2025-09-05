package pro.homedns.filebrowser.model;

import java.time.Instant;

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
    private String path;
    private String displayName;
    private boolean isDirectory;
    private Instant lastModifiedOn;
    private String fileSize;
}
