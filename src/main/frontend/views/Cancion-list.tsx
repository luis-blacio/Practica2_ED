import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, NumberField, Select, SelectItem, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService,GeneroService,TaskService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import Task from 'Frontend/generated/org/northpole/workshop/taskmanagement/domain/Task';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect, useState } from 'react';
import { data } from 'react-router';
import { Cancion } from 'Frontend/generated/org/northpole/workshop/base/models/Cancion';
import { deleteCancion , search , updateCancion , createCancion , listCanciones } from 'Frontend/generated/CancionService';


export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:music',
    order: 3,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormUpdateProps = {
  onCancionUpdated?: () => void;
};

type CancionEntryFormDeleteProps = {
  onCancionDelete?: () => void;
};

type CancionEntryFormSearchProps = {
  onCancionSearch?: (atributo: string, valor: string) => void;
}

function CancionEntryFormSearch (props : CancionEntryFormSearchProps) {
    const atributo = useSignal('');
    const valor = useSignal('');
        console.log("atributo >>>>",atributo.value, "Valor >>>>>", valor.value);

        const atributosOpciones = [
  { label: 'Nombre', value: 'nombre' },
  { label: 'Genero', value: 'id_genero' },
  { label: 'Duracion', value: 'duracion' },
  { label: 'URL', value: 'url' },
  { label: 'Tipo de archivo', value: 'tipo' },
  { label: 'Album', value: 'id_album' },
  ];
    
    const searchCancion = async () => {
      try {
        console.log("atributo >>>>",atributo.value, "Valos >>>>>", valor.value);
        if (atributo.value.trim().length > 0  && valor.value.trim().length > 0 ) {
          const dat = await CancionService.search(atributo.value , valor.value );
          if (props.onCancionSearch) {
          props.onCancionSearch(atributo.value, valor.value);
          }
          console.log(dat);
          Notification.show('Busqueda exitosa', { duration: 5000, position: 'top-center', theme: 'success' });
        } else {
        Notification.show('No se pudo realizar la busqueda', { duration: 5000, position: 'top-center', theme: 'error' });
        }
      } catch (error) {
      console.log(error);
      Notification.show('Lo que busca no existe', { duration: 5000, position: 'top-center', theme: 'error' });
      //handleError(error);
    }
    }
    return (

      <HorizontalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <Select
            label="Atributo"
            placeholder="Seleccione un atributo"
            items={atributosOpciones}
            value={atributo.value}
            onValueChanged={(e) => (atributo.value = e.detail.value)}
          />
          <TextField label="Valor"
            placeholder="Ingrese el valor"
            aria-label="Ingrese el valor"
            value={valor.value}
            onValueChanged={(evt) => (valor.value = evt.detail.value)}
          />
          <Button onClick={searchCancion} theme="secondary">
              Buscar
          </Button>
        </HorizontalLayout>

    )
}


function CancionEntryFormDelete (props : CancionEntryFormDeleteProps) {
  const dialogOpened = useSignal(false);
  const posicion = useSignal(props.arguments.id);

  const deleteCancion = async () =>{
    try {
      console.log("posicion A ELIMINAR >>>>>>>>>>>>>>",posicion.value);
      if (posicion.value != null) {
        await CancionService.deleteCancion(posicion.value);
        if (props.onCancionDelete) {
          props.onCancionDelete();
        }
        Notification.show('Cancion eliminada', { duration: 5000, position: 'top-center', theme: 'success' });
      } else {
        Notification.show('No elimino o no existe', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  }

  return (
    <>
      <Dialog
        modeless
        headerTitle="Eliminar Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={deleteCancion} theme="primary">
              Eliminar
            </Button>

          </>
        }
      >
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Eliminar
      </Button>
    </>
  )

}


function CancionEntryForm(props: CancionEntryFormProps) {
  const dialogOpened = useSignal(false);

  const nombre = useSignal('');
  const genero = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const album = useSignal('');

  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value != null &&
        duracion.value != null && url.value.trim().length > 0 &&
        tipo.value.trim().length > 0 && album.value != null ) {
        const id_genero = parseInt(genero.value);
        const id_album = parseInt(album.value);
        await CancionService.createCancion(nombre.value, id_genero, parseInt(duracion.value),
          url.value, tipo.value, id_album
        )
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        genero.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        album.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada', { duration: 5000, position: 'top-center', theme: 'success' });
      } else {
        Notification.show('No se pudo crear la cancion', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listGenders().then(data => listGenero.value = data);
    console.log(data);
  }, []);

  let listAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbum().then(data => listAlbum.value = data);
        console.log(data);
  }, []);

  let listTipos = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipos().then(data => listTipos.value = data);
  }, []);

  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={createCancion} theme="primary">
              Registrar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre de la cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listGenero.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          <NumberField label="Escriba la duracion"
            placeholder="Ingrese la duracion"
            aria-label="Ingrese la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="URL"
            placeholder="Ingrese el URL"
            aria-label="Ingrese el enlace"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listTipos.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un genero de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <ComboBox label="Album"
            items={listAlbum.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione el album '
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Agregar
      </Button>
    </>
  );
}

  function indexIndex({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }



function CancionEntryFormUpdate (props: CancionEntryFormUpdateProps) {

  const dialogOpened = useSignal(false);
  const ident = useSignal(props.arguments.id);
  const nombre = useSignal(props.arguments.nombre);
  const genero = useSignal(props.arguments.genero);
  const duracion = useSignal(props.arguments.duracion);
  const url = useSignal(props.arguments.url);
  const tipo = useSignal(props.arguments.tipo);
  const album = useSignal(props.arguments.album);
  console.log("id actual"+ident.value+"NEW name >>>> " + nombre.value + "NEW gender >>>>" + genero.value + "NEW duracion >>>>" + duracion.value + "NEW URL >>>>" + url.value + "NEW tipo >>>>" + tipo.value+"NEW album>>>>>" + album.value)

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value != null &&
        duracion.value != null && url.value.trim().length > 0 &&
        tipo.value.trim().length > 0 && album.value != null) {
        const id_genero = parseInt(genero.value);
        const id_album = parseInt(album.value);
        //console.log("id actual"+ident.value+"NEW name >>>> " + nombre.value + "NEW gender >>>>" + genero.value + "NEW duracion >>>>" + duracion.value + "NEW URL >>>>" + url.value + "NEW tipo >>>>" + tipo.value+"NEW album>>>>>" + album.value)
        await CancionService.updateCancion(ident.value,nombre.value, id_genero, parseInt(duracion.value),
          url.value, tipo.value, id_album
        )
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }
        nombre.value = '';
        genero.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        album.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion actualizada', { duration: 5000, position: 'top-center', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar la cancion, llene todo los datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listGenero = useSignal<String[]>([]);
  useEffect(() => {
    
    CancionService.listGenders().then(data => listGenero.value = data);
  }, []);

  let listAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbum().then(data => listAlbum.value = data);
    console.log(data);
  }, []);

  let listTipos = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipos().then(data => listTipos.value = data);
  }, []);

  return (
    <>
      <Dialog
        modeless
        headerTitle="Editar Cancion"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button
              onClick={() => {
                dialogOpened.value = false;
              }}
            >
              Cancelar
            </Button>
            <Button onClick={updateCancion} theme="primary">
              Editar
            </Button>

          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField label="Nombre de la cancion"
            placeholder="Ingrese el nombre de la cancion"
            aria-label="Nombre de la cancion"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox label="Genero"
            items={listGenero.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione un genero'
            aria-label='Seleccione un genero de la lista'
            value={genero.value}
            onValueChanged={(evt) => (genero.value = evt.detail.value)}
          />
          <NumberField label="Escriba la duracion"
            placeholder="Ingrese la duracion"
            aria-label="Ingrese la cancion"
            value={duracion.value}
            onValueChanged={(evt) => (duracion.value = evt.detail.value)}
          />
          <TextField label="URL"
            placeholder="Ingrese el URL"
            aria-label="Ingrese el enlace"
            value={url.value}
            onValueChanged={(evt) => (url.value = evt.detail.value)}
          />
          <ComboBox label="Tipo"
            items={listTipos.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione un tipo de archivo'
            aria-label='Seleccione un genero de la lista'
            value={tipo.value}
            onValueChanged={(evt) => (tipo.value = evt.detail.value)}
          />
          <ComboBox label="Album"
            items={listAlbum.value}
            itemLabelPath="nombre" 
            itemValuePath="id"
            placeholder='Seleccione el album '
            aria-label='Seleccione un album de la lista'
            value={album.value}
            onValueChanged={(evt) => (album.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button
        onClick={() => {
          dialogOpened.value = true;
        }}
      >
        Editar
      </Button>
    </>
  );
}



export default function CancionView() {
  const[items , setItems] = useState([]);

  
  const callData = () => {
    CancionService.listCanciones().then(function(data){
      setItems(data);
    });
  };

  useEffect (()=> {
      callData();
  },[]);

  const search = (atributo, valor) => {
    CancionService.search(atributo,valor).then((data) =>{
      setItems(data);
    });
  }

  const order = (event, columId) =>{
    const direction = event.detail.value;
    if(!direction){
      callData();
      return;
    }
    var dir = direction === 'asc' ? 1 : 2;
    CancionService.listOrder(columId, dir).then((data)=>{
    console.log("Recibido:", data.map(c => c.columId)); 
      setItems(data);
    });
  }

  function indexLink({ item, model }: { item: Cancion , model: GridItemModel<Cancion> }) {
    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdated={callData}/>
        <CancionEntryFormDelete arguments={item} onCancionDelete={callData}/>
      </span>
    );
  }

  return (

    <main className="w-full h-full flex flex-col box-border gap-s p-m">

      <ViewToolbar title="Lista de Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={callData} />
        </Group>
      </ViewToolbar>
      <Group>
          <CancionEntryFormSearch onCancionSearch={search} />
        </Group>
      <Grid items = {items}>
        <GridColumn  renderer={indexIndex} header="Nro"/>
        <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e)=> order(e,"nombre")} />
        <GridSortColumn path="genero" header="Genero" onDirectionChanged={(e)=> order(e,"genero")} />
        <GridSortColumn path="duracion" header="Duracion" onDirectionChanged={(e)=> order(e,"duracion")} />
        <GridColumn path="url" header="Enlace" />
        <GridSortColumn path="tipo" header="Archivo" onDirectionChanged={(e)=> order(e,"tipo")} />
        <GridSortColumn path="album" header="Album" onDirectionChanged={(e)=> order(e,"album")} />
        <GridColumn header="Acciones" renderer={indexLink} />
      </Grid>
    </main>

  );
}