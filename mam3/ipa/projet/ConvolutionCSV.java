package mam3.ipa.projet;
/**
 * Classe qui permet de convertir une matrice de convolution enregistrée 
 * dans un fichier .csv en un tableau à deux dimensions.
 *
 * @author Lélio Astruc
 * @author Théva Gérard
 * @version 0.1.2
 *
 **/
/**
 * Imports nécessaires :
 * @see {@link java.io.File} pour ouvrir convmatrix.csv
 * @see {@link java.io.FileNotFountException} au cas où le fichier convmatrix.csv n'existe pas
 * @see {@link java.util.Scanner} pour la lecture des lignes du csv.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConvolutionCSV {
	/**
	 * Lit le fichier "convmatrix.csv" et convertit les valeurs enregistrées en un tableau à deux dimensions.
	 * 
	 * @return La matrice de convolution en tant que tableau à deux dimensions
	 * @throws BadConvMatrixException si la matrice de convolution n'a pas la bonne taille (doit être 3x3)
	 * @throws FileNotFoundException si le fichier "convmatrix.csv" n'a pas été trouvé
	 */
	public static double[][] CSVtoMatrix() throws BadConvMatrixException, FileNotFoundException {
		// Ouvre le fichier "convmatrix.csv" en utilisant un Scanner
		Scanner scanner = new Scanner(new File("convmatrix.csv"));
		// Crée un tableau à deux dimensions pour stocker la matrice de convolution
		double[][] matrice = new double[3][3];
		// Initialise un compteur pour suivre la ligne actuelle dans le fichier CSV
		int ligne = 0;
		// Tant qu'il y a encore des lignes à lire dans le fichier CSV, on lit la ligne suivante et la 
		//stocke dans une chaîne de caractères
		while (scanner.hasNextLine()) {
			String rangee = scanner.nextLine();
			// Sépare chaque valeur de la ligne par des virgules et les stocke dans un tableau de chaînes de caractères
			String[] values = rangee.split(",");
			// Pour chaque valeur dans le tableau
			for (int colonne = 0; colonne < values.length; colonne++) {
				//On vérifie que les dimensions ne sont pas dépassées
				if (values.length > 3) {
					//Sinon on lève une erreur
					throw new BadConvMatrixException();
				}
				// Convertit la valeur en entier et l'ajoute à la matrice de convolution
				matrice[ligne][colonne] = Double.parseDouble(values[colonne]);
			}
			// Incrémente le compteur de ligne
			ligne++;
			//On vérifie que les dimensions ne sont pas dépassées
			if (ligne > 3) {
				//Sinon on lève une erreur
				throw new BadConvMatrixException();
			}
		}
		// Ferme le Scanner une fois que toutes les lignes ont été lues
		scanner.close();
		// Retourne la matrice de convolution
		return matrice;
	}
}
