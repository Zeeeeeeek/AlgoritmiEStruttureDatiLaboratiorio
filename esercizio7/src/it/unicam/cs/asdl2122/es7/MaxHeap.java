package it.unicam.cs.asdl2122.es7;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Classe che implementa uno heap binario che può contenere elementi non nulli
 * possibilmente ripetuti.
 *
 * @param <E> il tipo degli elementi dello heap, che devono avere un
 *            ordinamento naturale.
 * @author Template: Luca Tesei, Implementation: collettiva
 */
public class MaxHeap<E extends Comparable<E>> {

    /*
     * L'array che serve come base per lo heap
     */
    private ArrayList<E> heap;

    /**
     * Costruisce uno heap vuoto.
     */
    public MaxHeap() {
        this.heap = new ArrayList<E>();
    }

    /**
     * Restituisce il numero di elementi nello heap.
     *
     * @return il numero di elementi nello heap
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Determina se lo heap è vuoto.
     *
     * @return true se lo heap è vuoto.
     */
    public boolean isEmpty() {
        return this.heap.isEmpty();
    }

    /**
     * Costruisce uno heap a partire da una lista di elementi.
     *
     * @param list lista di elementi
     * @throws NullPointerException se la lista è nulla
     */
    public MaxHeap(List<E> list) {
        if (list == null) throw new NullPointerException("La lista è null");
        heap = new ArrayList<>();
        for (E e : list) {
            insert(e);
        }
    }

    /**
     * Inserisce un elemento nello heap
     *
     * @param el l'elemento da inserire
     * @throws NullPointerException se l'elemento è null
     */
    public void insert(E el) {
        if (el == null) throw new NullPointerException("L'elemento è null");
        if(heap.size() == 0) {
            heap.add(el);
            return;
        }

        if(heap.size()== 1) {
            heap.add(el);
            if(el.compareTo(heap.get(0)) > 0) {
                swap(0, 1);
            }
            return;
        }


        heap.add(el);
        int elPos = size() - 1;
        while (elPos > 0 && el.compareTo(heap.get(parentIndex(elPos))) > 0) {
            swap(elPos, parentIndex(elPos));
            elPos = parentIndex(elPos);
            if(elPos <=0) break;
        }
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio sinistro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int leftIndex(int i) {
        return (2 * i) + 1;
    }

    /*
     * Funzione di comodo per calcolare l'indice del figlio destro del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int rightIndex(int i) {
        return (2 * i) + 2;
    }

    /*
     * Funzione di comodo per calcolare l'indice del genitore del nodo in
     * posizione i. Si noti che la posizione 0 è significativa e contiene sempre
     * la radice dello heap.
     */
    private int parentIndex(int i) {
        return (i / 2) - 1;
    }

    /**
     * Ritorna l'elemento massimo senza toglierlo.
     *
     * @return l'elemento massimo dello heap oppure null se lo heap è vuoto
     */
    public E getMax() {
        if (heap.size() == 0) return null;
        return heap.get(0);
    }

    /**
     * Estrae l'elemento massimo dallo heap. Dopo la chiamata tale elemento non
     * è più presente nello heap.
     *
     * @return l'elemento massimo di questo heap oppure null se lo heap è vuoto
     */
    public E extractMax() {
        if (heap.size() == 0) return null;
        E max = heap.get(0);
        heap.set(0, heap.get(size() - 1));
        heap.remove(size() - 1);
        heapify(0);
        return max;
    }

    /*
     * Ricostituisce uno heap a partire dal nodo in posizione i assumendo che i
     * suoi sottoalberi sinistro e destro (se esistono) siano heap.
     */
    private void heapify(int i) {
        //Controllo se il nodo passato è una foglia
        if (i >= heap.size() / 2) return;
        //Salvo gli indici dei figli in due variabili
        int leftPos = leftIndex(i);
        int rightPos = rightIndex(i);

        //Nel caso in cui il nodo destro non esista lo pongo uguale al sinistro così da evitare l'eccezione
        //IndexOutOFBoundsException
        if (rightPos >= size()) rightPos = leftPos;

        //Controllo se sono necessari scambi
        if (heap.get(i).compareTo(heap.get(leftPos)) < 0 || heap.get(i).compareTo(heap.get(rightPos)) < 0) {

            //Controllo quale dei due figli sia più grande
            if (heap.get(leftPos).compareTo(heap.get(rightPos)) >= 0) {
                swap(leftPos, i);
                heapify(leftPos);
            } else {
                swap(rightPos, i);
                heapify(rightPos);
            }
        }

    }

    //Metodo per scambiare due valori
    private void swap(int pos1, int pos2) {
        E temp = heap.get(pos1);
        heap.set(pos1, heap.get(pos2));
        heap.set(pos2, temp);
    }

    /**
     * Only for JUnit testing purposes.
     *
     * @return the arraylist representing this max heap
     */
    protected ArrayList<E> getHeap() {
        return this.heap;
    }
}
