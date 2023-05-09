package mam3.ipa.projet;	



/**
 * Imports qui vont nous être utilse : 
 * @see {@link java.awt.Color} pour avoir accès au constructeur Color
 * @see {@link java.awt.image.BufferdImage} pour manipuler l'image
 * @see {@link java.io.BufferedWriter} pour écrire dans le fichier .txt
 * @see {@link java.io.FileWriter} pour écrire un fichier sur le disque
 * @see {@link java.io.IOException} au cas où la lecture de l'image ne fonctionnerait pas
 */
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TraitementImage {
	/**
	 * Classe qui contient différentes méthodes de traitement d'image.
	 * @author Lélio Astruc
	 * @author Théva Gérard
	 * @version 0.2.5
	 */
	/**
	 * Applique une transformation qui convertit l'image en niveaux de gris en utilisant la formule suivante:
	 * gris = $0.21 * rouge + 0.72 * vert + 0.07 * bleu$
	 * 
	 * @param bi l'image à transformer
	 */
	public static void appliquerTransformationNiveauxDeGris(BufferedImage bi) {
		for(int i=0;i<bi.getHeight(); i++) {
			for(int j=0; j<bi.getWidth(); j++) {
				Color c = new Color(bi.getRGB(j, i));
				int red = (int) (c.getRed() * 0.21);
				int green = (int) (c.getGreen() * 0.72);
				int blue = (int) (c.getBlue() * 0.07);
				Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
				bi.setRGB(j, i, newColor.getRGB());
			}
		}
	}
	/**
	 * Applique un contraste maximal à l'image en utilisant la formule suivante:
	 * contraste = $255 - couleur$
	 * Remarque : il nous semble que cette formule donne plutot le négatif de l'image.
	 * 
	 * @param bi l'image à transformer
	 */
	public static void appliquerContraste(BufferedImage bi) {
		for(int i=0 ; i<bi.getWidth() ; i++) {
			for(int j=0 ; j<bi.getHeight() ; j++) {
				Color c = new Color(bi.getRGB(i, j));
				int red = (int) (c.getRed());
				int green = (int) (c.getGreen());
				int blue = (int) (c.getBlue());
				Color colorContraste = new Color(255-red, 255-green, 255-blue);
				bi.setRGB(i, j, colorContraste.getRGB());
			}
		}
	}
	/**
	 * Applique un assombrissement à l'image en utilisant la formule suivante:
	 * assombrissement = $\frac{couleur^2}{255}$
	 * 
	 * @param bi L'image à transformer
	 */
	public static void appliquerAssombrissement(BufferedImage bi) {
		for(int i=0 ; i<bi.getWidth() ; i++) {
			for(int j=0 ; j<bi.getHeight() ; j++) {
				Color c = new Color(bi.getRGB(i, j));
				int red = (int) (c.getRed());
				int green = (int) (c.getGreen());
				int blue = (int) (c.getBlue());
				Color colorAssombrissement = new Color((int)((red*red)/255), (int)((green*green)/255), (int)((blue*blue)/255));
				bi.setRGB(i, j, colorAssombrissement.getRGB());
			}
		}
	}
	/**
	 * Applique un éclairage à l'image en utilisant la formule suivante:
	 * éclairage = $\sqrt{couleur}*16$
	 * 
	 * @param bi L'image à transformer
	 */
	public static void appliquerEclairage(BufferedImage bi) {
		for(int i=0 ; i<bi.getWidth() ; i++) {
			for(int j=0 ; j<bi.getHeight() ; j++) {
				Color c = new Color(bi.getRGB(i, j));
				int red = (int) (c.getRed());
				int green = (int) (c.getGreen());
				int blue = (int) (c.getBlue());
				Color colorEclairage = new Color((int)(Math.sqrt((double) red)*16), (int)(Math.sqrt((double) green)*16), (int)(Math.sqrt((double) blue)*16));
				bi.setRGB(i, j, colorEclairage.getRGB());

			}
		}
	}
	/**
	 * Applique un filtre de convolution à l'image en utilisant la matrice fournie.
	 * 
	 * @param bi l'image à transformer
	 * @param matrice la matrice de convolution à utiliser
	 */
	public static void appliquerConvolution(BufferedImage bi, double[][] matrice){
		double longueurMatrice = matrice[0].length;
		double hauteurMatrice = matrice.length;
		double moitieLongueurMatrice = longueurMatrice / 2;
		double moitieHauteurMatrice = hauteurMatrice / 2;
		BufferedImage imageTemporaire = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		for (double y = moitieHauteurMatrice; y < bi.getHeight() - moitieHauteurMatrice; y++) {
			for (double x = moitieLongueurMatrice; x < bi.getWidth() - moitieLongueurMatrice; x++) {
				double red = 0;
				double green = 0;
				double blue = 0;
				for (int matriceY = 0; matriceY < hauteurMatrice; matriceY++) {
					for (int matriceX = 0; matriceX < longueurMatrice; matriceX++) {
						double pixelX = x + matriceX - moitieLongueurMatrice;
						double pixelY = y + matriceY - moitieHauteurMatrice;
						Color couleurPixel = new Color(bi.getRGB((int) pixelX, (int) pixelY));
						red += couleurPixel.getRed() * matrice[(int) matriceY][(int) matriceX];
						green += couleurPixel.getGreen() * matrice[(int) matriceY][(int) matriceX];
						blue += couleurPixel.getBlue() * matrice[(int) matriceY][(int) matriceX];
					}
				}
				red = Math.min(Math.max(red, 0), 255);
				green = Math.min(Math.max(green, 0), 255);
				blue = Math.min(Math.max(blue, 0), 255);
				Color colorConvolution = new Color((int) red, (int) green, (int) blue);
				imageTemporaire.setRGB((int) x, (int) y, colorConvolution.getRGB());
			}
		}
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				bi.setRGB(x, y, imageTemporaire.getRGB(x, y));
			}
		}
	}
	/**
	 * Crée un histogramme de l'image 
	 * @param bi l'image source pour créer l'histogramme
	 * 
	 */
	public static int[][] creerHistogramme(BufferedImage bi) {
		int[][] histogram = new int[3][256];

		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				Color couleurPixel = new Color(bi.getRGB(x, y));
				int red = couleurPixel.getRed();
				int green = couleurPixel.getGreen();
				int blue = couleurPixel.getBlue();
				histogram[0][red]++;
				histogram[1][green]++;
				histogram[2][blue]++;
			}
		}
		return histogram;
	}
	/**
	 * Ajoute la première colonne qui va de 0 à 255 et enregistre l'histogramme dans 
	 * un fichier .txt
	 * @param bi l'image source pour créer l'histogramme
	 * @param fileName le nom sous lequel le fichier .txt sera crée
	 * 
	 */
	static void appliquerHistogramme(BufferedImage bi, String fileName) {
		int[][] histogram = creerHistogramme(bi);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (int i = 0; i < 256; i++) {
				String line = String.format("%d,%d,%d,%d", i, histogram[0][i], histogram[1][i], histogram[2][i]);
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
