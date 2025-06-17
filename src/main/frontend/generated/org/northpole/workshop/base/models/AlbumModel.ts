import { _getPropertyModel as _getPropertyModel_1, makeObjectEmptyValueCreator as makeObjectEmptyValueCreator_1, NumberModel as NumberModel_1, ObjectModel as ObjectModel_1, StringModel as StringModel_1 } from "@vaadin/hilla-lit-form";
import type Album_1 from "./Album.js";
class AlbumModel<T extends Album_1 = Album_1> extends ObjectModel_1<T> {
    static override createEmptyValue = makeObjectEmptyValueCreator_1(AlbumModel);
    get id(): NumberModel_1 {
        return this[_getPropertyModel_1]("id", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
    get nombre(): StringModel_1 {
        return this[_getPropertyModel_1]("nombre", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.lang.String" } }));
    }
    get fechaLanzamiento(): StringModel_1 {
        return this[_getPropertyModel_1]("fechaLanzamiento", (parent, key) => new StringModel_1(parent, key, true, { meta: { javaType: "java.util.Date" } }));
    }
    get id_banda(): NumberModel_1 {
        return this[_getPropertyModel_1]("id_banda", (parent, key) => new NumberModel_1(parent, key, true, { meta: { javaType: "java.lang.Integer" } }));
    }
}
export default AlbumModel;
