import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {Navigate} from "react-router";

export const config: ViewConfig = {
    title: 'Home',
    loginRequired: true,
};


export default function RootRedirect() {
    return <Navigate to="/browser" replace/>;
}