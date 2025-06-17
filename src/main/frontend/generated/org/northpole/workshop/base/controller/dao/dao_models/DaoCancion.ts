import type Cancion_1 from "../../../models/Cancion.js";
import type AdapterDao_1 from "../AdapterDao.js";
interface DaoCancion extends AdapterDao_1 {
    cancion?: Cancion_1;
}
export default DaoCancion;
