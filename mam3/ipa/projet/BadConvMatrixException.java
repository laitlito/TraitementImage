package mam3.ipa.projet;
/**
 * Exception à lever lorsque la matrice de convolution donnée dans la méthode @see {@link TraitementImage.appliquerConvolution} 
 * n'a pas la bonne taille.
 * La taille correcte est de 3x3.
 * 
 * @author Lélio Astruc
 * @author Théva Gérard
 * @version 0.1
 * 
 */
public class BadConvMatrixException extends Exception {
    /**
     * Constructeur qui affiche le message d'erreur
     */
    public BadConvMatrixException() {
        System.err.println("Les dimensions de la matrice de convolution n'ont pas été respectées (3x3)");;
    }
}