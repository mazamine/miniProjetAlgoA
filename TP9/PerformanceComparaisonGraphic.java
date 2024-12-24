import java.util.Arrays;
import java.util.PriorityQueue;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PerformanceComparaisonGraphic extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // TODO: Changez les valeurs de sizes selon vos besoins
        int[] sizes = {(int) Math.pow(10, 1), (int) Math.pow(10, 2), (int) Math.pow(10, 3), (int) Math.pow(10, 4), (int) Math.pow(10, 5), (int) Math.pow(10, 6), 
            (int) Math.pow(10, 7), (int) Math.pow(10, 8), (int) Math.pow(10, 9), (int) Math.pow(10, 10),
            (int) Math.pow(10, 11), (int) Math.pow(10, 12), (int) Math.pow(10, 13), (int) Math.pow(10, 14), (int) Math.pow(10, 15), (int) Math.pow(10, 16)};

        LineChart<Number, Number> lineChart = createChart("Performance Comparison", sizes);

        addDataSeries(lineChart, "QuickSort");
        addDataSeries(lineChart, "PriorityQueue (min-heap)");
        addDataSeries(lineChart, "PriorityQueue (max-heap)");

        Scene scene = new Scene(lineChart, 800, 600);
        stage.setTitle("Performance Comparison");
        stage.setScene(scene);
        stage.show();
    }

    private LineChart<Number, Number> createChart(String title, int[] sizes) {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);

        xAxis.setLabel("Array Size");
        yAxis.setLabel("Execution Time (ms)");

        return lineChart;
    }

    private void addDataSeries(LineChart<Number, Number> lineChart, String algorithm) {
        int[] sizes = {(int) Math.pow(10, 1), (int) Math.pow(10, 2), (int) Math.pow(10, 3), (int) Math.pow(10, 4)};
        ObservableList<XYChart.Series<Number, Number>> data = lineChart.getData();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(algorithm);

        for (int i = 0; i < sizes.length; i++) {
            int[] array = generateArray(sizes[i]);

            long startTime = System.currentTimeMillis();

            if (algorithm.equals("QuickSort")) {
                quickSort(array.clone());
            } else if (algorithm.equals("PriorityQueue (min-heap)")) {
                minHeapPerformance(array.clone());
            } else if (algorithm.equals("PriorityQueue (max-heap)")) {
                maxHeapPerformance(array.clone());
            }

            long endTime = System.currentTimeMillis();
            series.getData().add(new XYChart.Data<>(sizes[i], endTime - startTime));
        }

        data.add(series);
    }

    public static void quickSort(int[] array) {
        Arrays.sort(array);
    }

    public static void minHeapPerformance(int[] array) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int num : array) {
            minHeap.offer(num);
        }
        while (!minHeap.isEmpty()) {
            minHeap.poll();
        }
    }

    public static void maxHeapPerformance(int[] array) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(java.util.Collections.reverseOrder());
        for (int num : array) {
            maxHeap.offer(num);
        }
        while (!maxHeap.isEmpty()) {
            maxHeap.poll();
        }
    }

    public static int[] generateArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        return array;
    }
}
