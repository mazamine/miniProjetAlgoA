# Guide installation Eclipse (il est  recommandé de regarder cette vidéo pour VSCode "https://youtu.be/BB0gZFpukJU?si=kmeFlOC7ogxSvbQ_" sur votre ordinateur)



# Guide de la structuration des fichiers et d'exécution de code

Ce fichier en format .zip est structuré en plusieurs répertoires pour une gestion plus efficace des codes et une meilleure organisation des différents fichiers appartenant aux différents projets (les comptes-rendus dans ce contexte). 
Cette structuration comprend les éléments suivants: 


1. Les répertoires `projet1 TP7PartieB TP9`  :
   - Dans ces répertoires, les codes sont organisés dans des fichiers : *.java *.py

2. Pour exécuter les projets Java, Veuillez suivre les indications dans la vidéo fournie ou bien executer les commandes suivantes dans un terminal: (Cherchez le fichier contenant la méthode main()):
	* depuis le dossier contenant le fichier à exzcuter lancez les 2 commandes terminal suivantes:
		javac NomDuFichier.java
		java NomDuFichier

3. Pour exécuter les fichiers Python, depuis un terminal commençant par le dossier ou se trouve le fichier cible, lancez:
	chmod +x NomDuFichier.py
	./NomDuFichier.py

4. concernant le TP9 il y a une classe qui dépend d'une librairie d'affichage graphique "JavaFX" (nom du fichier = PerformancesComparaisonsGraphics.java), pour exécuter ce fichier il faut préciser:
	javac --module-path lib --add-modules javafx.controls,javafx.fxml -d bin src/Main.java
	java --module-path lib --add-modules javafx.controls,javafx.fxml -cp bin Main

		Remplacez "Main" par le nom de votre classe principale.

Ou bien suivez les indications dans cette vidéo d'une durée de 3 minutes:
	"https://youtu.be/AubJaosfI-0?si=yfCkVMHmkAJaUsqS"
	
5, Deux fichiers sous format PDF contenant des commentaires, évaluations et constatations sur les différents projets et fichiers

#FIN