import {Outlet} from 'react-router';
import {useEffect} from "react";
import {useRouteMetadata} from "Frontend/util/routing";
import ApplicationHeader from "Frontend/components/ApplicationHeader";

export default function MainLayout() {
    const currentTitle = useRouteMetadata()?.title ?? 'File Browser';

    useEffect(() => {
        document.title = currentTitle;
    }, [currentTitle]);

    return (
        <div className="flex flex-col w-full h-full flex-grow gap-s">
            <header className="flex flex-col w-full flex-grow-0 p-m bg-shade-20">
                <ApplicationHeader/>
            </header>

            <main className="flex flex-col w-full flex-grow py-l px-m">
                <Outlet/>
            </main>

            <footer className="flex flex-col w-full flex-grow-0 p-m bg-shade-30 text-center text-xs">
                <span>built with <a href="https://vaadin.com/hilla">Hilla</a>, Icons by <a
                    href="https://icons8.com/">Icons8</a></span>
            </footer>
        </div>
    );
}