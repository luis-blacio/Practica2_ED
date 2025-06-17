import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Genero_1 from "./org/northpole/workshop/base/models/Genero.js";
async function createGenero_1(nombre: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("GeneroService", "createGenero", { nombre }, init); }
async function listAllGenders_1(init?: EndpointRequestInit_1): Promise<Array<Genero_1 | undefined> | undefined> { return client_1.call("GeneroService", "listAllGenders", {}, init); }
async function updateGenero_1(id: number | undefined, nombre: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("GeneroService", "updateGenero", { id, nombre }, init); }
export { createGenero_1 as createGenero, listAllGenders_1 as listAllGenders, updateGenero_1 as updateGenero };
