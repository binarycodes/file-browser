import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import FileBrowserView from "Frontend/views/browser/@index";

export const config: ViewConfig = {
    title: 'Home',
    loginRequired: true,
};

export default function HomeView() {
    return <FileBrowserView/>;
}