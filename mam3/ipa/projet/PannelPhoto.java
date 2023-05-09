package mam3.ipa.projet;
/**
 * Imports nécessaires :
 * @see {@link java.awt.Graphics} pour dessiner l'image sur la fenêtre
 * @see {@link java.awt.image.BufferedImage} pour manipuler l'image
 * @see {@link java.swing.JPanel} pour avoir accès à getHeight() et getWidth()
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class PannelPhoto extends JPanel{
	/**
	 * Cette classe sert à "repeindre" le pannelPhoto à chaque méthode pour voir les changements effetctués sur l'image.
	 * @author Lélio Astruc
	 * @author Théva Gérard
	 * @version 0.1
	 */
	private BufferedImage bi;
	/**
     * Dessine l'image BufferedImage sur le JPanel.
     * 
     * @param g l'objet Graphics à utiliser pour dessiner sur le JPanel
     */
	protected void paintComponent(Graphics g) {
		g.drawImage(bi, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	/**
     * Retourne l'image BufferedImage affichée sur le JPanel.
     * 
     * @return L'image BufferedImage affichée sur le JPanel
     */
	public BufferedImage getBi() {
		return bi;
	}
	 /**
     * Modifie l'image BufferedImage affichée sur le JPanel.
     * 
     * @param bi La nouvelle image BufferedImage à afficher sur le JPanel
     */
	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}


}
