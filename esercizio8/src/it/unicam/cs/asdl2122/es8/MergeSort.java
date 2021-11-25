/**
 *
 */
package it.unicam.cs.asdl2122.es8;

import java.util.List;

/**
 * Implementazione dell'algoritmo di Merge Sort integrata nel framework di
 * valutazione numerica. Non è richiesta l'implementazione in loco.
 *
 * @author Template: Luca Tesei, Implementazione: collettiva
 *
 */
public class MergeSort<E extends Comparable<E>> implements SortingAlgorithm<E> {

    private int numComparisons;

    public SortingAlgorithmResult<E> sort(List<E> l) {
        numComparisons = 0;
        mergeSort(l, 0, l.size() - 1);
        return new SortingAlgorithmResult<>(l, numComparisons);
    }

    private void mergeSort(List<E> l, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(l, left, middle);
            mergeSort(l, middle + 1, right);
            merge(l, left, middle, right);
        }
    }

    private void merge(List<E> l, int left, int middle, int right) {
        List<E> leftList = l.subList(left, middle + 1);
        List<E> rightList = l.subList(middle + 1, right + 1);
        int i = 0;
        int j = 0;
        int k = left;
        while (i < leftList.size() && j < rightList.size()) {
            numComparisons++;
            if (leftList.get(i).compareTo(rightList.get(j)) < 0) {
                l.set(k, leftList.get(i));
                i++;
            } else {
                l.set(k, rightList.get(j));
                j++;
            }
            k++;
        }
        while (i < leftList.size()) {
            l.set(k, leftList.get(i));
            i++;
            k++;
        }
        while (j < rightList.size()) {
            l.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    public String getName() {
        return "MergeSort";
    }
}
