package it.unicam.cs.asdl2122.es8;

import java.util.List;

/**
 * Implementazione dell'algoritmo di Insertion Sort integrata nel framework di
 * valutazione numerica. L'implementazione è in loco.
 *
 * @author Template: Luca Tesei, Implementazione: Collettiva
 *
 * @param <E>
 *                Una classe su cui sia definito un ordinamento naturale.
 */
public class InsertionSort<E extends Comparable<E>>
        implements SortingAlgorithm<E> {

    public SortingAlgorithmResult<E> sort(List<E> l) {
        int numComparisons = 0;
        for (int i = 1; i < l.size(); i++) {
            E e = l.get(i);
            int j = i - 1;
            while (j >= 0 && l.get(j).compareTo(e) > 0) {
                numComparisons++;
                l.set(j + 1, l.get(j));
                j--;
            }
            if (j >= 0 && l.get(j).compareTo(e) <= 0) {
                numComparisons++;
            }
            l.set(j + 1, e);
        }
        return new SortingAlgorithmResult<>(l, numComparisons);
    }

    public String getName() {
        return "InsertionSort";
    }
}
