import {UserConfigFn} from 'vite';
import {overrideVaadinConfig} from './vite.generated';
import tsconfigPaths from "vite-tsconfig-paths";

const customConfig: UserConfigFn = (env) => ({
    // Here you can add custom Vite parameters
    // https://vitejs.dev/config/
    plugins: [tsconfigPaths()]
});

export default overrideVaadinConfig(customConfig);
