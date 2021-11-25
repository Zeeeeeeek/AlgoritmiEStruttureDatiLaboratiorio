package it.unicam.cs.asdl2122.es6;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Lista concatenata singola che non accetta valori null, ma permette elementi
 * duplicati. Le seguenti operazioni non sono supportate:
 *
 * <ul>
 * <li>ListIterator<E> listIterator()</li>
 * <li>ListIterator<E> listIterator(int index)</li>
 * <li>List<E> subList(int fromIndex, int toIndex)</li>
 * <li>T[] toArray(T[] a)</li>
 * <li>boolean containsAll(Collection<?> c)</li>
 * <li>addAll(Collection<? extends E> c)</li>
 * <li>boolean addAll(int index, Collection<? extends E> c)</li>
 * <li>boolean removeAll(Collection<?> c)</li>
 * <li>boolean retainAll(Collection<?> c)</li>
 * </ul>
 * <p>
 * L'iteratore restituito dal metodo {@code Iterator<E> iterator()} è fail-fast,
 * cioè se c'è una modifica strutturale alla lista durante l'uso dell'iteratore
 * allora lancia una {@code ConcurrentMopdificationException} appena possibile,
 * cioè alla prima chiamata del metodo {@code next()}.
 *
 * @param <E> il tipo degli elementi della lista
 * @author Luca Tesei
 */
public class ASDL2122SingleLinkedList<E> implements List<E> {

    private int size;

    private Node<E> head;

    private Node<E> tail;

    private int numeroModifiche;

    /**
     * Crea una lista vuota.
     */
    public ASDL2122SingleLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
        this.numeroModifiche = 0;
    }

    /*
     * Classe per i nodi della lista concatenata. E' dichiarata static perché
     * gli oggetti della classe Node<E> non hanno bisogno di accedere ai campi
     * della classe principale per funzionare.
     */
    private static class Node<E> {
        private E item;
        private Node<E> next;

        /*
         * Crea un nodo "singolo" equivalente a una lista con un solo elemento.
         */
        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

    }

    /*
     * Classe che realizza un iteratore per ASDL2122SingleLinkedList.
     * L'iteratore deve essere fail-fast, cioè deve lanciare una eccezione
     * ConcurrentModificationException se a una chiamata di next() si "accorge"
     * che la lista è stata cambiata rispetto a quando l'iteratore è stato
     * creato.
     *
     * La classe è non-static perché l'oggetto iteratore, per funzionare
     * correttamente, ha bisogno di accedere ai campi dell'oggetto della classe
     * principale presso cui è stato creato.
     */
    private class Itr implements Iterator<E> {

        private Node<E> lastReturned;

        private int numeroModificheAtteso;

        private Itr() {
            // All'inizio non è stato fatto nessun next
            this.lastReturned = null;

        }

        @Override
        public boolean hasNext() {
            if (this.lastReturned == null)
                // sono all'inizio dell'iterazione
                return ASDL2122SingleLinkedList.this.head != null;
            else
                // almeno un next è stato fatto
                return lastReturned.next != null;

        }

        @Override
        public E next() {
            // controllo concorrenza
            if (this.numeroModificheAtteso != ASDL2122SingleLinkedList.this.numeroModifiche) {
                throw new ConcurrentModificationException(
                        "Lista modificata durante l'iterazione");
            }
            // controllo hasNext()
            if (!hasNext())
                throw new NoSuchElementException(
                        "Richiesta di next quando hasNext è falso");
            // c'è sicuramente un elemento di cui fare next
            // aggiorno lastReturned e restituisco l'elemento next
            if (this.lastReturned == null) {
                // sono all’inizio e la lista non è vuota
                this.lastReturned = ASDL2122SingleLinkedList.this.head;
                return ASDL2122SingleLinkedList.this.head.item;
            } else {
                // non sono all’inizio, ma c’è ancora qualcuno
                lastReturned = lastReturned.next;
                return lastReturned.item;
            }

        }

    }

    /*
     * Una lista concatenata è uguale a un'altra lista se questa è una lista
     * concatenata e contiene gli stessi elementi nello stesso ordine.
     *
     * Si noti che si poteva anche ridefinire il metodo equals in modo da
     * accettare qualsiasi oggetto che implementi List<E> senza richiedere che
     * sia un oggetto di questa classe:
     *
     * obj instanceof List
     *
     * In quel caso si può fare il cast a List<?>:
     *
     * List<?> other = (List<?>) obj;
     *
     * e usando l'iteratore si possono tranquillamente controllare tutti gli
     * elementi (come è stato fatto anche qui):
     *
     * Iterator<E> thisIterator = this.iterator();
     *
     * Iterator<?> otherIterator = other.iterator();
     *
     * ...
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (!(obj instanceof ASDL2122SingleLinkedList))
            return false;
        ASDL2122SingleLinkedList<?> other = (ASDL2122SingleLinkedList<?>) obj;
        // Controllo se entrambe liste vuote
        if (head == null) {
            if (other.head != null)
                return false;
            else
                return true;
        }
        // Liste non vuote, scorro gli elementi di entrambe
        Iterator<E> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            E o1 = thisIterator.next();
            // uso il polimorfismo di Object perché non conosco il tipo ?
            Object o2 = otherIterator.next();
            // il metodo equals che si usa è quello della classe E
            if (!o1.equals(o2))
                return false;
        }
        // Controllo che entrambe le liste siano terminate
        return !(thisIterator.hasNext() || otherIterator.hasNext());
    }

    /*
     * L'hashcode è calcolato usando gli hashcode di tutti gli elementi della
     * lista.
     */
    @Override
    public int hashCode() {
        int hashCode = 1;
        // implicitamente, col for-each, uso l'iterator di questa classe
        for (E e : this)
            hashCode = 31 * hashCode + e.hashCode();
        return hashCode;
    }

    @Override
    public int size() {
        return this.size;
    }

    //Utilizzato nei test
    protected int getNumeroModifiche() {
        return numeroModifiche;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) throw new NullPointerException("La lista non ammette elementi nulli");
        Itr iterator = new Itr();
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (o.equals(item)) return true;
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        if (e == null) throw new NullPointerException("La lista non può contenere valori nulli");
        Node<E> node = new Node<>(e, null);
        if (size == 0) {
            //Se la lista non ha elementi la testa è l'oggetto che aggiungo
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        numeroModifiche++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Itr iterator = new Itr();
        Node<E> previous = null;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (item.equals(o)) {
                numeroModifiche++;
                size--;
                Node<E> current = iterator.lastReturned;
                //Se previous è null allora o è la testa
                if (previous == null) {
                    //La testa della lista diventa il successivo, se la lista contiene un solo elemento current.next è
                    //nullo
                    head = current.next;
                    if (head == null) tail = null;
                } else {
                    if (current.next == null) tail = previous;
                    previous.next = current.next;
                }
                return true;
            }
            previous = iterator.lastReturned;
        }
        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
        numeroModifiche++;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Indice non valido");
        int i = 0;
        Itr iterator = new Itr();
        E item = null;
        while (iterator.hasNext()) {
            item = iterator.next();
            if (index == i) break;
            i++;
        }
        return item;
    }

    @Override
    public E set(int index, E element) {
        if (element == null) throw new NullPointerException("Nella lista non si accettano elementi nulli");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Indice non valido");
        Itr iterator = new Itr();
        int i = 0;
        while (iterator.hasNext()) {
            iterator.next();
            if (index == i) {
                E previousItem = iterator.lastReturned.item;
                iterator.lastReturned.item = element;
                numeroModifiche++;
                return previousItem;
            }
            i++;
        }
        return null;
    }

    @Override
    public void add(int index, E element) {
        if (element == null) throw new NullPointerException("Nella lista non si accettano elementi nulli");
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Indice non valido");
        Itr iterator = new Itr();
        Node<E> previousNode = null;
        int i = 0;
        if (index == size) {
            add(element);
            return;
        }
        while (iterator.hasNext()) {
            iterator.next();
            if (index == i) {
                if (previousNode == null) {
                    head = new Node<>(element, iterator.lastReturned);
                } else {
                    previousNode.next = new Node<>(element, iterator.lastReturned);
                }
                numeroModifiche++;
                size++;
                return;
            }
            i++;
            previousNode = iterator.lastReturned;
        }

    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Indece non valido");
        E itemRemoved = get(index);
        Itr iterator = new Itr();
        Node<E> previousNode = null;
        int i = 0;

        while (iterator.hasNext()) {
            iterator.next();
            if (index == i) {
                if (previousNode == null) {
                    head = iterator.lastReturned.next;
                } else {
                    previousNode.next = iterator.lastReturned.next;
                }
                numeroModifiche++;
                size--;
                break;
            }
            i++;
            previousNode = iterator.lastReturned;
        }
        return itemRemoved;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) throw new NullPointerException("La lista non ammette elementi nulli");
        Itr iterator = new Itr();
        int index = 0;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (item.equals(o)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) throw new NullPointerException("La lista non ammette elementi nulli");
        Itr iterator = new Itr();
        int i = 0;
        int lastOccurrenceIndex = -1;
        while (iterator.hasNext()) {
            E item = iterator.next();
            if (item.equals(o)) {
                lastOccurrenceIndex = i;
            }
            i++;
        }
        return lastOccurrenceIndex;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Itr iterator = new Itr();
        int i = 0;
        while (iterator.hasNext()) {
            array[i++] = iterator.next();
        }
        return array;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Operazione non supportata.");
    }
}
