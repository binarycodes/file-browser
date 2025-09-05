import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {FileBrowserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";
import {Grid, GridColumn} from "@vaadin/react-components";

export const config: ViewConfig = {
    title: 'File Browser',
    loginRequired: true,
};

export default function FileBrowserView() {
    const fileItems = useSignal<FileItem[]>([]);

    FileBrowserService.getRootLevelItems()
        .then(items => fileItems.value = items);

    return <>
        <Grid items={fileItems.value}>
            <GridColumn path="firstName"/>
            <GridColumn path="lastName"/>
            <GridColumn path="email"/>
            <GridColumn path="profession"/>
        </Grid>
    </>;
}