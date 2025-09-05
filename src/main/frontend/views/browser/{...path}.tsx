import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {FileBrowserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";
import {Button, Grid, GridColumn, GridSortColumn, Icon} from "@vaadin/react-components";
import {useEffect} from "react";

import folderIcon from "@icons/icons8-folder.svg?url";
import fileIcon from "@icons/icons8-file.svg?url";
import downloadImage from "@images/icons8-download-from-the-cloud.svg?url";
import renameImage from "@images/icons8-rename-50.png?url";

import {useNavigate, useParams} from "react-router";
import BreadCrumbs from "Frontend/components/BreadCrumbs";
import {formatDate} from "Frontend/util/dateFormat";
import RenameFileDialog, {itemToRename, renameFileDialogOpenSignal} from "Frontend/components/RenameFileDialog";


export const config: ViewConfig = {
    title: 'File Browser',
    loginRequired: true,
};

export default function FileBrowserView() {
    const navigate = useNavigate();
    const wildcardPath = useParams()['*'];

    const fileItems = useSignal<FileItem[]>([]);

    useEffect(() => {
        FileBrowserService.getPathItems(wildcardPath)
            .then(items => fileItems.value = items);
    }, [wildcardPath]);

    const visitItem = (item: FileItem) => {
        if (!item.directory) return;
        navigate("/browser/" + item.path);
    };

    const onRenameFileClick = (item: FileItem) => {
        renameFileDialogOpenSignal.value = true;
        itemToRename.value = item;
    };

    return <>
        <BreadCrumbs path={wildcardPath}/>
        <RenameFileDialog/>

        <Grid items={fileItems.value}>
            <GridSortColumn resizable path="displayName" header="Name" renderer={
                ({item}) => (
                    <div className="flex gap-s items-center"
                         onClick={() => visitItem(item)}
                         title={item.path}>
                        <Icon src={item.directory ? folderIcon : fileIcon}/>
                        <span className="text-s text-secondary">{item.displayName}</span>
                    </div>
                )}
            />
            <GridSortColumn resizable path="fileSize" header="Size" renderer={
                ({item}) => (
                    <span className="text-s text-secondary">{item.fileSize}</span>
                )}
            />
            <GridSortColumn resizable path="lastModifiedOn" header="Last Modified On" renderer={
                ({item}) => (
                    <span className="text-s text-secondary">{formatDate(item.lastModifiedOn)}</span>
                )}
            />
            <GridColumn resizable frozenToEnd renderer={
                ({item}) => (
                    <>
                        <a href={item.directory ? "" : `/download/${item.path}`}
                           download={!item.directory}
                           aria-disabled={item.directory}
                           className={item.directory ? 'item-disabled' : ""}>
                            <Button theme="icon tertiary" disabled={item.directory}
                                    className={item.directory ? 'item-disabled' : ""}>
                                <img src={downloadImage} width="32"/>
                            </Button>
                        </a>
                        <Button theme="icon tertiary" onClick={() => onRenameFileClick(item)}>
                            <img src={renameImage} width="32"/>
                        </Button>
                    </>
                )}
            />
        </Grid>
    </>;
}