import matplotlib.pyplot as plt
import numpy as np
import timeit
from functools import partial

def quickSort(arr):
    return sorted(arr)

def minHeapPerformance(arr):
    minHeap = []
    for num in arr:
        minHeap.append(num)
        minHeap.sort()

def maxHeapPerformance(arr):
    maxHeap = []
    for num in arr:
        maxHeap.append(num)
        maxHeap.sort(reverse=True)

def generateArray(size):
    return np.random.randint(0, 1000, size)

def measure_time(func, *args):
    start_time = timeit.default_timer()
    func(*args)
    end_time = timeit.default_timer()
    return end_time - start_time

# Tailles de tableau à tester
sizes = [10**i for i in range(1, 6)] # TODO: Remplacez ceci par une liste de tailles de tableau à tester

# Stockage des temps d'exécution pour chaque méthode
times_quickSort = []
times_minHeap = []
times_maxHeap = []

for size in sizes:
    array = generateArray(size)

    time_quickSort = measure_time(quickSort, array.copy())
    time_minHeap = measure_time(minHeapPerformance, array.copy())
    time_maxHeap = measure_time(maxHeapPerformance, array.copy())

    times_quickSort.append(time_quickSort)
    times_minHeap.append(time_minHeap)
    times_maxHeap.append(time_maxHeap)

# Tracé de la courbe
plt.plot(sizes, times_quickSort, label='QuickSort')
plt.plot(sizes, times_minHeap, label='PriorityQueue (min-heap)')
plt.plot(sizes, times_maxHeap, label='PriorityQueue (max-heap)')

plt.xscale('log')  # Utilisez une échelle logarithmique sur l'axe des x pour mieux visualiser
plt.yscale('log')  # Utilisez une échelle logarithmique sur l'axe des y pour mieux visualiser

plt.xlabel('Taille du tableau')
plt.ylabel('Temps d\'exécution (s)')
plt.title('Comparaison de performances entre QuickSort et PriorityQueue')
plt.legend()
plt.show()
