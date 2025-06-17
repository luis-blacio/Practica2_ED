import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, ObjectModel as ObjectModel_1 } from "@vaadin/hilla-lit-form";
import type AdapterDao_1 from "./AdapterDao.js";
class AdapterDaoModel<T extends AdapterDao_1 = AdapterDao_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(AdapterDaoModel);
}
export default AdapterDaoModel;
