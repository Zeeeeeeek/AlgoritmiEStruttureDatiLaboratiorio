import java.util.List;

public class Quicksort<E extends Comparable<E>> {
    private List<E> list;
    private int countCompare;

    public Quicksort(List<E> a) {
        if (a == null) throw new NullPointerException();
        if (a.size() <= 1) throw new IllegalArgumentException("Lista già ordinata");
        list = a;
        countCompare = 0;
    }

    public void sort(int begin, int end) {
        if (begin < end) {
            int pivot = partition(begin, end);

            sort(begin, pivot - 1);
            sort(pivot + 1, end);
        }

    }


    private int partition(int begin, int end) {
        //Elemento pivot per semplicità quello più a destra
        E pivot = list.get(end);
        //Inizializzo un indice i
        int i = begin - 1;

        for(int j = begin; j <= end -1; j ++) {

            int compare = list.get(j).compareTo(pivot);
            countCompare++;

            if(compare <= 0) {
                //Se l'elemento in posizione j <= pivot
                i++;
                //Scambio l'elemento in posizione i con j
                E temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        //Uscito dal ciclo l'elemento in posizione i+1 è il primo elemento più grande del pivot
        //quindi scambio il pivot(in posizione end) con l'elemento in posizione i+1
        E temp = list.get(end);
        list.set(end, list.get(i+1));
        list.set(i+1, temp);

        //In fine restituisco la nuova posizione del pivot
        return i+1;
    }

    public int getCountCompare() {
        return countCompare;
    }
}
