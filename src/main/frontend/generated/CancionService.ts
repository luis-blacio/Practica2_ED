import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type DaoCancion_1 from "./org/northpole/workshop/base/controller/dao/dao_models/DaoCancion.js";
import type Cancion_1 from "./org/northpole/workshop/base/models/Cancion.js";
async function createCancion_1(nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: string | undefined, id_album: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "createCancion", { nombre, id_genero, duracion, url, tipo, id_album }, init); }
async function deleteCancion_1(id: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "deleteCancion", { id }, init); }
async function getAtributs_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("CancionService", "getAtributs", {}, init); }
async function getDc_1(init?: EndpointRequestInit_1): Promise<DaoCancion_1 | undefined> { return client_1.call("CancionService", "getDc", {}, init); }
async function list_1(init?: EndpointRequestInit_1): Promise<Array<Cancion_1 | undefined> | undefined> { return client_1.call("CancionService", "list", {}, init); }
async function listAlbum_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listAlbum", {}, init); }
async function listCanciones_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listCanciones", {}, init); }
async function listGenders_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listGenders", {}, init); }
async function listOrder_1(atributo: string | undefined, orden: number | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "listOrder", { atributo, orden }, init); }
async function listTipos_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("CancionService", "listTipos", {}, init); }
async function main_1(args: Array<string | undefined> | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "main", { args }, init); }
async function search_1(atributo: string | undefined, valor: string | undefined, init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("CancionService", "search", { atributo, valor }, init); }
async function setDc_1(dc: DaoCancion_1 | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "setDc", { dc }, init); }
async function updateCancion_1(id: number | undefined, nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: string | undefined, id_album: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "updateCancion", { id, nombre, id_genero, duracion, url, tipo, id_album }, init); }
export { createCancion_1 as createCancion, deleteCancion_1 as deleteCancion, getAtributs_1 as getAtributs, getDc_1 as getDc, list_1 as list, listAlbum_1 as listAlbum, listCanciones_1 as listCanciones, listGenders_1 as listGenders, listOrder_1 as listOrder, listTipos_1 as listTipos, main_1 as main, search_1 as search, setDc_1 as setDc, updateCancion_1 as updateCancion };
