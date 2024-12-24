
import java.util.Arrays;
import java.util.PriorityQueue;

/*
public class MinMaxAlgos { // Implémentation de QuickSort et PriorityQueue (min-heap et max-heap) pour trouver le min et le max d'un tableau
    public static void main(String[] args) {
        int[] array = {5, 2, 4, 10, 7, 1, 3, 6, 9, 8};
        System.out.println("Tableau avant le tri : " + Arrays.toString(array));

        // Utilisation de QuickSort pour trier le tableau
        Arrays.sort(array);
        System.out.println("Tableau après le tri avec QuickSort : " + Arrays.toString(array));

        // Utilisation de PriorityQueue pour extraire le min et le max
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Par défaut, c'est un min-heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(java.util.Collections.reverseOrder()); // Pour obtenir un max-heap

        for (int num : array) {
            minHeap.offer(num);
            maxHeap.offer(num);
        }

        System.out.println("Le min extrait avec PriorityQueue (min-heap) : " + minHeap.poll());
        System.out.println("Le max extrait avec PriorityQueue (max-heap) : " + maxHeap.poll());
    }

    public static int[] minMax(int[] array) {
        int min = array[0];
        int max = array[array.length - 1];
        int[] minMax = { min, max };
        return minMax;
    }

}
 */



class PerformanceComparison { // Comparaison de performance entre QuickSort et PriorityQueue (min-heap et max-heap)
    public static void main(String[] args) {
        // Générer un tableau de taille n
        int[] array = generateArray((int)Math.pow(10, 8)); // TODO: Choisissez la taille du tableau selon vos besoins

        // Mesurer le temps d'exécution de QuickSort
        long startTime = System.currentTimeMillis();
        quickSort(array.clone()); // Clone pour éviter de trier le tableau d'origine (on teste sur une copie)
        long endTime = System.currentTimeMillis();
        System.out.println("QuickSort a pris " + (endTime - startTime) + " millisecondes");

        // Mesurer le temps d'exécution avec PriorityQueue (min-heap)
        startTime = System.currentTimeMillis();
        minHeapPerformance(array.clone());
        endTime = System.currentTimeMillis();
        System.out.println("PriorityQueue (min-heap) a pris " + (endTime - startTime) + " millisecondes");

        // Mesurer le temps d'exécution avec PriorityQueue (max-heap)
        startTime = System.currentTimeMillis();
        maxHeapPerformance(array.clone());
        endTime = System.currentTimeMillis();
        System.out.println("PriorityQueue (max-heap) a pris " + (endTime - startTime) + " millisecondes");       
    }

    public static void quickSort(int[] array) { // Implémentation de QuickSort
        Arrays.sort(array);
    }

    public static void minHeapPerformance(int[] array) { // Implémentation de PriorityQueue (min-heap)
        // minHeap est un PriorityQueue qui trie les éléments dans l'ordre croissant puis on extrait le min
        // puisque le min doit être en premier, cela côute O(1) pour l'extraire
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : array) {
            minHeap.offer(num);
        }
        while (!minHeap.isEmpty()) {
            minHeap.poll();
        }
    }

    public static void maxHeapPerformance(int[] array) { // Implémentation de PriorityQueue (max-heap)
        // maxHeap est un PriorityQueue qui trie les éléments dans l'ordre décroissant puis on extrait le max
        // puisque le max doit être en premier, cela côute O(1) pour l'extraire
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(java.util.Collections.reverseOrder());
        for (int num : array) {
            maxHeap.offer(num);
        }
        while (!maxHeap.isEmpty()) {
            maxHeap.poll();
        }
    }

    public static int[] generateArray(int size) { // Générer un tableau de taille n
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 1000); // Remplissez le tableau avec des valeurs aléatoires
        }
        return array;
    }
}