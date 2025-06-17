package org.northpole.workshop.base.controller.datastruct.list;

import org.northpole.workshop.base.Utiles;
import org.northpole.workshop.base.models.Banda;

public class LinkedList<E> {
    private Node<E> header;
    private Node<E> last;
    private Integer size;
    public LinkedList() {
        header = null;
        last = null;
        size = 0;
    }

    public LinkedList(Integer tamanio) {
        header = null;
        last = null;
        size = tamanio;
    }

    public Boolean isEmpty() {
        return header == null || size == 0;
    }

    public void deleteFirst() {
        if (isEmpty()) {
            System.out.println("La lista esta vacia");
            return;
        }
        Node<E> aux = header.getNext();
        header = aux;
        size--;
        if (header == null) {
            last = null;
        }
    }

public E get(int index) {
    Node<E> node = getNode(index);
    if (node == null) {
        throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
    }
    return node.getData();
}


    public void deleteLast() {
        if (isEmpty()) {
            System.out.println("La lista esta vacia");
            return;
        } else if (size == 1) {
            deleteFirst();
            return;
        }

        Node<E> aux = getNode(size - 2);
        last = aux;
        last.setNext(null);
        size--;
    }



    
    public void delete(Integer pos) {
        if (pos == 0) {
            deleteFirst();
        } else if (pos == size - 1) {
            deleteLast();
        } else if (pos < 0 || pos >= size) {
            System.out.println("Fuera de rango");
        } else {
            Node<E> previo = getNode(pos - 1);
            previo.setNext(getNode(pos).getNext());
            size--;
        }
    }

    public void deleteById(Integer id) throws Exception {
        Utiles util = new Utiles();

        for (int i = 0; i < size; i++) {

            if (util.getClazz(getNode(i).getData(), "id").equals(id)) {
                if (i == 0) {
                    header = header.getNext(); // eliminar el primero
                } else {
                    Node<E> previo = getNode(i - 1);
                    previo.setNext(getNode(i).getNext());
                }
                size--;
                break;
            }
        }
    }

    public Node<E> getNode(Integer pos) {
        if (isEmpty()) {
            System.out.println("La lista esta vacia");
            return null;
        } else if (pos < 0 || pos >= size) {
            System.out.println("Fuera de rango");
            return null;
        } else if (pos == 0) {
            return header;
        } else if (pos.intValue() == size.intValue()) {
            return last;
        } else if (pos >= size) {
            System.out.println("MUY ADELANTE OE");
            return null;
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < pos) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public Node<E> getNodeById(Integer pos) throws Exception {
        Utiles util = new Utiles();
        if (isEmpty()) {
            System.out.println("La lista esta vacia");
            return null;
        }
        Node<E> search = header;
        while (search != null) {
            if (util.getClazz(search.getData(), "id") != null && util.getClazz(search.getData(), "id").equals(pos)) {
                return search;
            }
            search = search.getNext();
        }
        System.out.println("NODO NO ECNONTRADO");
        return null;
    }

    private void addFirst(E data) {
        if (isEmpty()) {
            Node<E> aux = new Node<E>(data);
            header = aux;
            last = aux;
        } else {
            Node<E> head_old = header;
            Node<E> aux = new Node<>(data, head_old);
            header = aux;
        }
        size++;
    }

    private void addLast(E data) {
        if (isEmpty()) {
            addFirst(data);
        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            size++;
        }
    }

    public void add(E data, Integer pos) throws Exception {
        if (pos == 0) {
            addFirst(data);
        } else if (size.intValue() == pos.intValue()) {
            addLast(data);
        } else if (pos > size || pos < 0) {
            System.out.println("Fuera de rango");
        } else {
            Node<E> serach_preview = getNode(pos - 1);
            Node<E> search = getNode(pos);
            Node<E> aux = new Node<>(data, search);
            serach_preview.setNext(aux);
            size++;
        }
    }

    public void add(E data) {
        addLast(data);
    }

    public E getData(Integer pos) {
        return getNode(pos).getData();
    }

    public E getDataId(Integer id) throws Exception {
        return getNodeById(id).getData();
    }

    public void update(E data, Integer pos) throws Exception {
        Node<E> node = getNode(pos);
        if (node == null) {
            throw new Exception("EL NODO ESTA NULO");
        }
        node.setData(data);
    }

    public void updateById(E data, Integer id) throws Exception {
        Node<E> node = getNodeById(id);
        if (node == null) {
            throw new Exception("EL NODO ESTA NULO");
        }
        node.setData(data);
    }

    public void clear() {
        header = null;
        last = null;
        size = 0;
    }

    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if (this.size > 0) {
            clazz = header.getData().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, this.size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    public LinkedList<E> toList(E[] matriz) {
        clear();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);
        }
        return this;
    }

    public Integer getUbicacion(String attribute, Object x) throws Exception {
        if (isEmpty()) {
            System.out.println("VACIO");
            return -1;
        }
        this.quickSort(attribute, 1);
        E[] arr = this.toArray();
        return binarySearch(arr, x, attribute);
    }
   

    ///// Metodo de busqueda Binario
    public E binarySearch(String attribute, Object x) throws Exception {
        if (isEmpty())
            return null;
        E[] arr = this.quickSort(attribute, 1).toArray();

        int i = binarySearch(arr, x, attribute);
        if (i == -1)
            return null;
        try {
            return arr[i];
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Objeto no encontrado, intente mas despues");
        }

    }

    private int binarySearch(E arr[], Object buscado, String attribute) throws Exception {
        Utiles util = new Utiles();
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (util.getClazz(arr[mid], attribute).equals(buscado))
                return mid;
            if (util.comparar(util.getClazz(arr[mid], attribute), buscado, 1)) {
                low = mid + 1;
            } else
                high = mid - 1;
            Object midValue = util.getClazz(arr[mid], attribute);
            System.out.println(" MID = " + mid + ", valor: " + midValue + ", buscado: " + buscado);
        }
        return -1;
    }

    //Metodo de busqueda  Lineal Binario
    public LinkedList<E> binaryLinearSearch(String attribute, Object x) {
        Utiles util = new Utiles();
        if (this.isEmpty()) {
            return new LinkedList<>();
        }
        try {
            this.quickSort(attribute, 1);

            Integer index = getUbicacion(attribute, x);
            Integer i = index.intValue();

            E obj = getData(index);
            System.out.println("Lo que se quiere buscar " + obj.toString());
            E[] arr = this.toArray();
            LinkedList<E> list = new LinkedList<>();
            while (index >= 0
                    && util.comparar(util.getClazz(arr[index], attribute), util.getClazz(obj, attribute), 3)) {
                list.add(arr[index]);
                index--;
            }

            index = i + 1;
            while (index < this.size
                    && util.comparar(util.getClazz(arr[index], attribute), util.getClazz(obj, attribute), 3)) {
                list.add(arr[index]);
                index++;
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new LinkedList<>();
        }
    }

    // SHELLSORT
    public LinkedList<E> shellSort(String attribute, Integer orden) throws Exception {
        if (isEmpty())
            return this;

        E[] array = this.toArray();
        shellSort(attribute, array, orden);
        return this.toList(array);
    }

    private int shellSort(String attribute, E[] arr, Integer orden) throws Exception {
        Utiles util = new Utiles();
        int n = arr.length;

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {

                E temp = arr[i];
                int j;
                for (j = i; j >= gap && !util.compararAtributos(attribute, arr[j - gap], temp, orden); j -= gap)
                    arr[j] = arr[j - gap];
                arr[j] = temp;
            }
        }
        return 0;
    }

    // QUICKSORT
    public LinkedList<E> quickSort(String atributo, Integer orden) throws Exception {
        if (isEmpty()) {
            return this;
        }

        E[] array = this.toArray();
        final Integer high = array.length - 1;
        final Integer low = 0;

        quickSort(atributo, array, low, high, orden);

        clear();
        for (E e : array) {
            add(e);
        }

        return this;
    }

    private void quickSort(String atributo, E[] array, Integer low, Integer high, Integer orden) throws Exception {
        if (low < high) {
            int pivoteIndex = partir(atributo, array, low, high, orden);

            quickSort(atributo, array, low, pivoteIndex - 1, orden);
            quickSort(atributo, array, pivoteIndex + 1, high, orden);
        }
    }

    private Integer partir(String atributo, E[] array, Integer low, Integer high, Integer orden) throws Exception {
        Utiles util = new Utiles();
        E pivote = array[high];

        Integer j = low - 1;

        for (int i = low; i <= high - 1; i++) {
            if (util.compararAtributos(atributo, array[i], pivote, orden)) {
                j++;
                intercambio(array, j, i);
            }
        }

        intercambio(array, j + 1, high);

        return j + 1;
    }

    private void intercambio(E[] array, Integer i, Integer j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        LinkedList<String> text = new LinkedList<>();
        try {
            text.add("Andresito");
            text.addFirst("primero");
            text.addLast("ultimo");
            text.add("gato");
            text.add("ddd");
            text.add("zzz");
            text.add("212", 6);
            System.out.println(text.print());
            text.deleteLast();
            System.out.println(text.print());
            text.delete(4);
            System.out.println(text.print());
            text.update("1212", 1);
            System.out.println(text.print());
            text.delete(1);
            System.out.println(text.print());

        } catch (Exception e) {
            System.out.println("ERROR :" + e.getMessage());
        }

    }

    public String print() {
        if (isEmpty()) {
            return "Esta vacio";
        } else {
            StringBuilder txt = new StringBuilder();
            Node<E> help = header;
            while (help != null) {
                txt.append(help.getData().toString()).append(" -- ");
                help = help.getNext();
            }
            txt.append("\n");
            return txt.toString();
        }
    }

    public Node<E> getHeader() {
        return this.header;
    }

    public void setHeader(Node<E> header) {
        this.header = header;
    }

    public Node<E> getLast() {
        return this.last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
