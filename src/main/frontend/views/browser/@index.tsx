import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import FileBrowserView from "Frontend/views/browser/{...path}";

export const config: ViewConfig = {
    title: 'File Browser',
    loginRequired: true,
};

export default function FileBrowserIndexView() {
    return <FileBrowserView/>;
}