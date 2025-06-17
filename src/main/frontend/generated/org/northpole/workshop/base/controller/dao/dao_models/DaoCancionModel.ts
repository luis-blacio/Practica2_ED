import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1 } from "@vaadin/hilla-lit-form";
import CancionModel_1 from "../../../models/CancionModel.js";
import AdapterDaoModel_1 from "../AdapterDaoModel.js";
import type DaoCancion_1 from "./DaoCancion.js";
class DaoCancionModel<T extends DaoCancion_1 = DaoCancion_1> extends AdapterDaoModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(DaoCancionModel);
    get cancion(): CancionModel_1 {
        return this[_getPropertyModel_1]("cancion", (parent, key) => new CancionModel_1(parent, key, true));
    }
}
export default DaoCancionModel;
