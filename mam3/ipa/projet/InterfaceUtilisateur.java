/**
 * Ce projet permet l'analyse d'images avec une interface graphique
 * très intuitive. Les différentes méthodes d'analyse sont : le niveaux de gris de l'image,
 * appliquer un assombrissement et un éclairage à l'image, le contraste, un filtre de convolution 
 * grâce à une matrice "convmatrix.csv" que l'utilisateur devra éditer. Et enfin, il est possible de 
 * générer l'histogramme d'une image en créant un fichier texte.
 */
package mam3.ipa.projet;
/**
 * Import des java.awt qui vont nous être utiles
 * @see {@link java.awt.BorderLayout} pour l'agencement de la fenêtre en général
 * @see {@link java.awt.FlowLayout} pour l'agencement des boutons
 * @see {@link java.awt.ColorSapce} pour savoir si l'image est en couleur ou en niveaux de gris
 * @see {@link java.awt.ActionEvent} pour gérer les actions lors des clics
 * @see {@link java.awt.ActionListener} pour gérer les actions lors des clics
 * @see {@link java.awt.BufferdImage} pour manipuler l'image
 * @see {@link java.awt.ColorModel} pour savoir si l'image est en couleur ou en niveaux de gris
 * 
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
/**
 * Import des java.io qui vont nous être utiles
 * @see {@link java.io.File} pour ouvrir et lire l'image
 * @see {@link java.io.FileNotFoundException} au cas où l'image n'est pas trouvable
 * @see {@link java.io.IOException} au cas où la lecture de l'image ne fonctionnerait pas
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Import de ImageIO
 * @see {@link javax.imageio.ImageIO} pour ouvrir et lire l'image
 */
import javax.imageio.ImageIO;
/**
 * Import des javax.swing qui vont nous être utiles
 * @see {@link javax.swing.JButton} pour créer des boutons cliquables
 * @see {@link javax.swing.JComboBox} pour créer le menu déroulant pour le choix des images
 * @see {@link javax.swing.JFrame} pour créer la fenêtre générale
 * @see {@link javax.swing.JPanel} pour créer l'endroit où mettre les boutons et le menu déroulant
 */
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfaceUtilisateur extends JFrame{
	/**
	 * Cette classe sert d'interface graphique, le main est également dedans donc c'est
	 * cette classe que l'on va executer. 
	 *
	 * @author Lélio Astruc
	 * @author Théva Gérard
	 * @version 0.2.1
	 *
	 **/
	/*
	 *comboBox est une sorte de menu déroulant qui va permettre à l'utilisateur de
	 * sélectionner l'image sur laquelle il souhaite appliquer des opérations.
	 */
	private JComboBox<String> comboBoxPhoto = new JComboBox<>();
	/*
	 * Les différents boutons sont des boutons qui portent le nom de la 
	 * chaîne de caractère donnée dans le constructeur de JButton.
	 * Ils servent à indiquer à l'utilisateur quelle transformations
	 * il peut appliquer.
	 */
	private JButton boutonGris = new JButton("Niveaux de gris");
	private JButton boutonContraste = new JButton("Contraste");
	private JButton boutonAssombrissement = new JButton("Assombrissement");
	private JButton boutonEclairage = new JButton("Éclairage");
	private JButton boutonSauvegarder = new JButton("Sauvegarder");
	private JButton boutonConvolution = new JButton("Appliquer la convolution");
	private JButton boutonHistogramme = new JButton("Histogramme");
	/*
	 * Le pannelPhoto est la fenêtre dans laquelle l'image sera affichée.
	 * On la place au centre de l'interface.
	 */
	private PannelPhoto pannelPhoto = new PannelPhoto();

	/*
	 * Ces entiers seront utilisés dans le bouton de sauvegarde.
	 */
	private int grisCounter = 0;
	private int contrasteCounter = 0;
	private int assombrissementCounter = 0;
	private int eclairageCounter = 0;
	private int convolutionCounter = 0;

	/**
	 * Constructeur de la classe, il va permettre la création
	 * de l'interface graphique.
	 */
	public InterfaceUtilisateur() {
		try {
			//On précise que l'opération fermer "avec la croix" permet de quitter completement le programme.
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			//Le "Layout" est l'agencement qu'aura la fenêtre.
			//On utilise BorderLayout pour placer les boutons en haut (NORTH) et l'image au centre (CENTER).
			this.setLayout(new BorderLayout());
			//On charge l'image depuis le dossier puis on crée un tableau composé des noms des images pour pouvoir les afficher dans
			//la comboBox et que l'utilisateur comprenne mieux.
			File dossierPhotos = new File("photos");
			String[] images = dossierPhotos.list();
			comboBoxPhoto = new JComboBox<>(images);
			String nomPhoto = (String) comboBoxPhoto.getSelectedItem();
			//On lit l'image depuis le dossier photos et on crée une BufferedImage bi.
			BufferedImage bi = ImageIO.read(new File("photos/"+nomPhoto));
			//On ajoute l'image bi à pannelPhoto.
			pannelPhoto.setBi(bi);
			//Pour l'agencement des boutons, on a choisi
			//FlowLayout qui permet de mettre les boutons "à la suite".
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			//On ajoute les boutons a notre panel
			panel.add(comboBoxPhoto);
			panel.add(boutonGris);
			panel.add(boutonContraste);
			panel.add(boutonAssombrissement);
			panel.add(boutonEclairage);
			panel.add(boutonConvolution);
			panel.add(boutonHistogramme);
			panel.add(boutonSauvegarder);
			//On ajoute les boutons à la fenetre, en haut.
			this.add(panel, BorderLayout.NORTH);
			//On ajoute la photo à la fenetre, au centre.
			this.add(pannelPhoto, BorderLayout.CENTER);
			//On définit les coordonées de la fenetre ainsi que les dimensions initiales.
			this.setBounds(10, 10, 1200, 600);
			//Bien évidemment, on rend la fenetre visible.
			this.setVisible(true);
			/*
			 * On ajoute un effet au bouton. En effet, sans les addActionListener qui suivent, si 
			 * l'utilisateur appuie sur les boutons, rien ne se passera.
			 * On commence par l'action à effectuer pour la comboBox de photos
			 */
			comboBoxPhoto.addActionListener(new ActionListener(){
				/**
			     * Méthode appelée lorsqu'un événement est détecté sur la comboBox.
			     * Charge l'image sélectionnée dans la comboBox et la affiche dans le PannelPhoto.
			     * 
			     * @param e l'événement détecté sur la comboBox
			     */
				public void actionPerformed(ActionEvent e) {
					try {
			            // Récupère le nom de l'image sélectionnée dans la comboBox
						String nomPhoto = (String) comboBoxPhoto.getSelectedItem();
			            // Charge l'image à partir du fichier
						BufferedImage bi = ImageIO.read(new File("photos/"+nomPhoto));
			            // Modifie l'image affichée dans le PannelPhoto
						pannelPhoto.setBi(bi);
			            // Met à jour l'affichage du PannelPhoto
						pannelPhoto.repaint();
			            // Affiche un message de confirmation dans la console
						System.out.println("Photo changée. Photo choisie : " + nomPhoto);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			boutonGris.addActionListener(new ActionListener() {
				/**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Applique la transformation de gris à l'image et met à jour l'affichage.
			     * 
			     * @param e l'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
			        // Récupère l'image affichée dans le PannelPhoto
					BufferedImage bi = pannelPhoto.getBi();
			        // Récupère le modèle de couleur de l'image
					ColorModel cm = bi.getColorModel();
			        // Vérifie si l'image est déjà en niveaux de gris
					if (bi.getType() == BufferedImage.TYPE_BYTE_GRAY && cm.getColorSpace().getType() == ColorSpace.TYPE_GRAY) {
			            // Affiche un message de confirmation dans la console
						System.out.println("L'image est en niveaux de gris, aucun effet.");
						return;
					}
			        // Vérifie si l'image est en couleur
					if (cm.getColorSpace().getType() == ColorSpace.TYPE_RGB) {
			            // Transforme l'image en niveaux de gris
						TraitementImage.appliquerTransformationNiveauxDeGris(bi);
			            // Met à jour l'affichage du PannelPhoto
						pannelPhoto.repaint();
			            // Incrémente un compteur
						grisCounter += 1;
			            // Affiche un message de confirmation dans la console
						System.out.println("L'image a été transformée en niveaux de gris.");
					}
				}
			});

			boutonContraste.addActionListener(new ActionListener() {
				 /**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Applique un contraste à l'image et met à jour l'affichage.
			     * 
			     * @param e l'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
			        // Récupère l'image affichée dans le PannelPhoto
					BufferedImage bi = pannelPhoto.getBi();
			        // Applique un contraste à l'image
					TraitementImage.appliquerContraste(bi);
			        // Met à jour l'affichage du PannelPhoto
					pannelPhoto.repaint();
			        // Incrémente un compteur
					contrasteCounter += 1;
			        // Affiche un message de confirmation dans la console
					System.out.println("Contraste appliqué");
				}
			});

			boutonAssombrissement.addActionListener(new ActionListener() {
				/**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Applique un assombrissement à l'image et met à jour l'affichage.
			     * 
			     * @param e l'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
			        // Récupère l'image affichée dans le PannelPhoto
					BufferedImage bi = pannelPhoto.getBi();
			        // Applique un assombrissement à l'image
					TraitementImage.appliquerAssombrissement(bi);
			        // Met à jour l'affichage du PannelPhoto
					pannelPhoto.repaint();
			        // Incrémente un compteur
					assombrissementCounter += 1;
					System.out.println("Assombrissement appliqué");
				}
			});

			boutonEclairage.addActionListener(new ActionListener() {
				 /**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Applique un éclairage à l'image et met à jour l'affichage.
			     * 
			     * @param e L'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
			        // Récupère l'image affichée dans le PannelPhoto
					BufferedImage bi = pannelPhoto.getBi();
			        // Applique un éclairage à l'image
					TraitementImage.appliquerEclairage(bi);
			        // Met à jour l'affichage du PannelPhoto
					pannelPhoto.repaint();
			        // Incrémente un compteur
					eclairageCounter += 1;
			        // Affiche un message de confirmation dans la console
					System.out.println("Éclairage appliqué");
				}
			});

			boutonConvolution.addActionListener(new ActionListener() {
				 /**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Applique une convolution à l'image à l'aide de la matrice de convolution donnée
			     * dans le fichier CSV et met à jour l'affichage.
			     * 
			     * @param e L'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
					try {
			            // Récupère l'image affichée dans le PannelPhoto
						BufferedImage bi = pannelPhoto.getBi();
			            // Récupère la matrice de convolution à partir du fichier CSV
						double[][] matrice = ConvolutionCSV.CSVtoMatrix();
			            // Met à jour l'affichage du PannelPhoto
						TraitementImage.appliquerConvolution(bi, matrice);
			            // Met à jour l'affichage du PannelPhoto
						pannelPhoto.repaint();
			            // Affiche un message de confirmation dans la console
						System.out.println("Convolution appliquée");
				        // Incrémente un compteur
						convolutionCounter+= 1;
					} catch (FileNotFoundException e1) {
			            // Affiche un message d'erreur si le fichier CSV est introuvable
						e1.printStackTrace();
					} catch (BadConvMatrixException e1) {
			            // Affiche un message d'erreur si la matrice de convolution ne respecte pas les bonnes dimensions
						e1.printStackTrace();
					}
				}
			});

			boutonHistogramme.addActionListener(new ActionListener() {
				/**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Crée le fichier txt qui est un histogramme de l'image.
			     * 
			     * @param e l'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
		            // Récupère l'image affichée dans le PannelPhoto
					BufferedImage bi = pannelPhoto.getBi();
					// Récupère le nom de l'image sélectionnée dans la combobox
					String nomPhoto = (String) comboBoxPhoto.getSelectedItem();
					// Trouve l'index du point dans le nom de l'image
					int indexDuPoint = nomPhoto.lastIndexOf(".");
					// Crée une chaîne de caractères qui est le nom de l'image sans son extension
					String nomSansExtension = nomPhoto.substring(0,indexDuPoint);
					// Crée une chaîne de caractères qui est le nom de l'histogramme de l'image
					String nomPhotoHistogramme = nomSansExtension + "-h.txt";
					// Applique l'histogramme à l'image et enregistre le résultat dans un fichier texte
					TraitementImage.appliquerHistogramme(bi, nomPhotoHistogramme);
					// Affiche un message de confirmation dans la console
					System.out.println("Histogramme crée");
					// Rafraîchit l'image affichée dans le programme
					pannelPhoto.repaint();
				}
			});

			boutonSauvegarder.addActionListener(new ActionListener() {
				/**
			     * Méthode appelée lorsqu'un événement est détecté sur le bouton.
			     * Permet de sauvegarder l'image avec les modifications effectuées
			     * 
			     * @param e l'événement détecté sur le bouton
			     */
				public void actionPerformed(ActionEvent e) {
					// Récupère l'image à sauvegarder
					BufferedImage bi = pannelPhoto.getBi();
					// Récupère le nom de l'image sélectionnée dans la combo box
					String nomPhoto = (String) comboBoxPhoto.getSelectedItem();
					// Récupère l'index du point dans le nom de l'image (pour enlever l'extension)
					int indexDuPoint = nomPhoto.lastIndexOf(".");
					// Crée un nouveau nom pour l'image en enlevant l'extension
					String nomSansExtension = nomPhoto.substring(0,indexDuPoint);
					try {
						// Enregistre l'image dans le dossier "photosSauvegardees" avec un nom comprenant le nombre de fois où chaque traitement a été appliqué
						ImageIO.write(bi, "PNG", new File("photosSauvegardees/"+nomSansExtension+"-"+Integer.toString(grisCounter)+"*niveaux de gris-"+Integer.toString(contrasteCounter)+"*contraste-"+Integer.toString(assombrissementCounter)+"*assombrissement-"+Integer.toString(eclairageCounter)+"*eclairage"+Integer.toString(convolutionCounter)+"*convolution"));
						System.out.println("Image sauvegardée");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fonction main pour executer le programme.	
	 *
	 **/
	public static void main(String[] args) {
		// Cette méthode est le point d'entrée de l'application. Elle crée une nouvelle instance de la classe InterfaceUtilisateur
		// qui correspond à l'interface graphique utilisateur (GUI) de l'application.
		new InterfaceUtilisateur();
	}
}



