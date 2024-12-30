# Projet Mini TP7 et Évasion du Labyrinthe

Ce projet contient deux parties : l'implémentation des algorithmes A* et Dijkstra pour trouver le chemin le plus court dans un graphe pondéré (Évasion du Labyrinthe) et un projet pour déterminer si un prisonnier peut s'échapper d'un labyrinthe tout en évitant des obstacles dynamiques représentés par des mouvements de feu (Prisoner Pardon Maze).

## Structure du projet

```
prisoner-pardon-maze
├── src
│   ├── MiniProjet_MAZOUZ
│   │   ├── App.java
│   │   ├── AStarSearch.java
│   │   ├── FireMovement.java
│   │   ├── Maze.java
│   │   └── Node.java
├── MainApp
│   ├── App.java
│   ├── WeightedGraph.java
│   └── graph.txt
├── README.txt
└── README.md
```

## Prérequis

- Java Development Kit (JDK) installé
- Un terminal ou une invite de commande

## Partie 1 : Prisoner Pardon Maze

### Fichiers

- `MiniProjet_MAZOUZ/App.java`: Fichier principal de l'application
- `MiniProjet_MAZOUZ/AStarSearch.java`: Implémentation de l'algorithme A*
- `MiniProjet_MAZOUZ/FireMovement.java`: Gestion des mouvements de feu
- `MiniProjet_MAZOUZ/Maze.java`: Gestion du labyrinthe
- `MiniProjet_MAZOUZ/Node.java`: Représentation d'un nœud dans le labyrinthe

### Compilation

Pour compiler les fichiers Java exécutez :

```sh
PS C:\Users\amine\Desktop\MiniProjet> javac -d bin .\src\MiniProjet_MAZOUZ\*.java
```

Cette commande compile les fichiers Java et place les classes compilées dans le répertoire `bin`.

### Exécution de l'application

Pour exécuter l'application, utilisez la commande suivante :

```sh
PS C:\Users\amine\Desktop\MiniProjet> java -cp bin MiniProjet_MAZOUZ.App
```

## Partie 2 : Évasion du Labyrinthe

### Fichiers

- `MainApp/App.java`: Fichier principal de l'application
- `MainApp/WeightedGraph.java`: Implémentation du graphe
- `MainApp/graph.txt`: Fichier d'entrée décrivant le graphe
- `out.txt`: Fichier de sortie contenant le chemin

### Compilation

Pour compiler les fichiers Java, naviguez vers le répertoire `src` et exécutez :

```sh
PS C:\Users\amine\Desktop\MiniProjet> javac -d bin .\src\MainApp\*.java
```

Cette commande compile les fichiers Java et place les classes compilées dans la structure de répertoires correcte.

### Exécution de l'application

Pour exécuter l'application, utilisez la commande suivante :

```sh
PS C:\Users\amine\Desktop\MiniProjet> java -cp bin MainApp.App C:\Users\amine\Desktop\MiniProjet\graph.txt djikstra
```

- `<chemin_vers_fichier_graph>`: Chemin vers le fichier `graph.txt`.
- `<algorithme>`: L'algorithme à utiliser pour la recherche de chemin. Utilisez `astar` pour l'algorithme A* et `dijkstra` pour l'algorithme de Dijkstra.

### Exemples

Exécuter l'application en utilisant l'algorithme de Dijkstra :

```sh
PS C:\Users\amine\Desktop\MiniProjet> java -cp bin MainApp.App C:\Users\amine\Desktop\MiniProjet\graph.txt dijkstra
```

Exécuter l'application en utilisant l'algorithme A* :

```sh
PS C:\Users\amine\Desktop\MiniProjet> java -cp bin MainApp.App C:\Users\amine\Desktop\MiniProjet\graph.txt astar
```

### Sortie

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