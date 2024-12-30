
# MiniProjet TP7PartieB

Ce projet implémente les algorithmes A* et Dijkstra pour trouver le chemin le plus court dans un graphe pondéré. Le graphe est représenté sous forme de grille, et le projet inclut un affichage graphique du processus de recherche de chemin.

## Prérequis

- Java Development Kit (JDK) installé
- Un terminal ou une invite de commande

## Fichiers

- `MainApp/App.java`: Fichier principal de l'application
- `MainApp/WeightedGraph.java`: Implémentation du graphe
- `MainApp/graph.txt`: Fichier d'entrée décrivant le graphe
- `out.txt`: Fichier de sortie contenant le chemin

## Compilation

Pour compiler les fichiers Java, naviguez vers le répertoire `TP7PartieB` et exécutez :

```sh
javac -d . MainApp/*.java
```

Cette commande compile les fichiers Java et place les classes compilées dans la structure de répertoires correcte.

## Exécution de l'application

Pour exécuter l'application, utilisez la commande suivante :

```sh
java -cp . MainApp.App <chemin_vers_fichier_graph> <algorithme>
```

- `<chemin_vers_fichier_graph>`: Chemin vers le fichier `graph.txt`.
- `<algorithme>`: L'algorithme à utiliser pour la recherche de chemin. Utilisez `astar` pour l'algorithme A* et `dijkstra` pour l'algorithme de Dijkstra.

### Exemples

Exécuter l'application en utilisant l'algorithme de Dijkstra :

```sh
java -cp . MainApp.App C:/Users/amine/Desktop/MiniProjet/TP7PartieB/MainApp/graph.txt dijkstra
```

Exécuter l'application en utilisant l'algorithme A* :

```sh
java -cp . MainApp.App C:/Users/amine/Desktop/MiniProjet/TP7PartieB/MainApp/graph.txt astar
```

## Sortie

L'application affichera le processus de recherche de chemin dans une fenêtre graphique et écrira le chemin résultant dans `out.txt`.

### Exemple de sortie

```
Done! Using Dijkstra:
    Number of nodes explored: 4155
    Total time of the path: 273.0
Done!

Done! Using A*:
    Number of nodes explored: 4080
    Total time of the path: 273.0
Done!
```

## Auteur

- [Mohamed El Amine MAZOUZ](mailto:mohamed-el-amine.mazouz@etu.u-paris.fr)
