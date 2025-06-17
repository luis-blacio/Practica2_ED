import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Album_1 from "./org/northpole/workshop/base/models/Album.js";
async function createAlbum_1(nombre: string | undefined, fecha: string | undefined, banda: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "createAlbum", { nombre, fecha, banda }, init); }
async function listAllAlbums_1(init?: EndpointRequestInit_1): Promise<Array<Album_1 | undefined> | undefined> { return client_1.call("AlbumService", "listAllAlbums", {}, init); }
async function updateAlbum_1(id: number | undefined, nombre: string | undefined, fecha: string | undefined, banda: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "updateAlbum", { id, nombre, fecha, banda }, init); }
export { createAlbum_1 as createAlbum, listAllAlbums_1 as listAllAlbums, updateAlbum_1 as updateAlbum };
