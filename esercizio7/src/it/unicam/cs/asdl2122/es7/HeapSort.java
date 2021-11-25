/**
 *
 */
package it.unicam.cs.asdl2122.es7;

import java.util.ArrayList;

import java.util.List;

/**
 * Classe che implementa un algoritmo di ordinamento basato su heap.
 *
 * @author Template: Luca Tesei, Implementation: collettiva
 *
 */
public class HeapSort<E extends Comparable<E>> implements SortingAlgorithm<E> {

    List<E> list = new ArrayList<>();
    int countCompare = 0;

    @Override
    public SortingAlgorithmResult<E> sort(List<E> l) {
        //Nota: usare una variante dei metodi della classe
        // MaxHeap in modo da implementare l'algoritmo utilizzando solo un array
        // (arraylist) e alcune variabili locali di appoggio (implementazione
        // cosiddetta "in loco" o "in place", si veda
        // https://it.wikipedia.org/wiki/Algoritmo_in_loco)
        if (l == null) throw new NullPointerException("La lista è null");
        list = l;
        for (int i = 0; i < l.size(); i++) {
            order(i);
        }
        return new SortingAlgorithmResult<>(l, countCompare);
    }

    private void order(int index) {
        if (index == 0) return;
        int previousIndex = index - 1;
        countCompare++;
        if (list.get(previousIndex).compareTo(list.get(index)) > 0) {
            //Swap
            E temp = list.get(previousIndex);
            list.set(previousIndex, list.get(index));
            list.set(index, temp);
            //Continuo ad ordinare
            order(previousIndex);
        }
    }

    @Override
    public String getName() {
        return "HeapSort";
    }

}