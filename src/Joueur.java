public class Joueur extends Entity{
	
	public Joueur(Jeu jeu, String pseudo)
	//Fonction permettant de cr�er un nouveau joueur
	{
		super(jeu, pseudo);
	}
	
	public Joueur(String[] infos, String[] cases, Grille grille){
		super(infos, cases, grille);
	}
	
	public void joue(Grille grille, Jeu jeu, FenetreJeu fen, int niveauIA){
		
	}
}