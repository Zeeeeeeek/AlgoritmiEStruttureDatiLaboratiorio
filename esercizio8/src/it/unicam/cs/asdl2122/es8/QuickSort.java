/**
 *
 */
package it.unicam.cs.asdl2122.es8;

import java.util.List;


/**
 * Implementazione del QuickSort con scelta della posizione del pivot fissa.
 * L'implementazione è in loco.
 *
 * @author Template: Luca Tesei, Implementazione: collettiva
 * @param <E>
 *                il tipo degli elementi della sequenza da ordinare.
 *
 */
public class QuickSort<E extends Comparable<E>> implements SortingAlgorithm<E> {

    private int numComparisons;

    @Override
    public SortingAlgorithmResult<E> sort(List<E> l) {
        numComparisons = 0;
        quickSort(l, 0, l.size() - 1);
        return new SortingAlgorithmResult<>(l, numComparisons);
    }

    private void quickSort(List<E> list, int p, int q) {
        int l = partition(list, p, q);
        if (l - p < q - l) {
            if (p < l - 1) quickSort(list, p, l - 1);
            if (l + 1 < q) quickSort(list, l + 1, q);
        } else {
            if (l + 1 < q) quickSort(list, l + 1, q);
            if (p < l - 1) quickSort(list, p, l - 1);
        }
    }

    private int partition(List<E> list, int p, int q) {
        int i = p;
        int j = q;
        while (i <= j) {
            while (list.get(j).compareTo(list.get(p)) > 0) {
                numComparisons++;
                j--;
            }
            if (list.get(j).compareTo(list.get(p)) <= 0) {
                numComparisons++;
            }
            while (i <= j && list.get(i).compareTo(list.get(p)) <= 0) {
                i++;
                numComparisons++;
            }
            if (i <= j && list.get(i).compareTo(list.get(p)) > 0) {
                numComparisons++;
            }
            if (i < j) {
                E tmp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, tmp);
                i++;
                j--;
            }
        }
        E tmp = list.get(p);
        list.set(p, list.get(j));
        list.set(j, tmp);

        return j;
    }

    @Override
    public String getName() {
        return "QuickSort";
    }

}