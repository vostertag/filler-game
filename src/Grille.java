public class Grille {
	
	int largeur, hauteur;
	Case[][] plateau;
	
	//Constructeur
	public Grille(int largeur, int hauteur){
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.plateau = new Case[hauteur][largeur];
		for (int i = 0; i < hauteur; i++){
			for (int j = 0; j < largeur; j++){
				plateau[i][j] = new Case(i,j);
			}
		}
	}
	
	public Grille(String[] grille){
		this.largeur=Integer.parseInt(grille[0]);
		this.hauteur=Integer.parseInt(grille[1]);
		this.plateau = new Case[largeur][hauteur];
		int aPartir = 0;
		for (int i = 0; i < largeur; i++){
			for (int j = 0; j < hauteur; j++){
				String[] uneCase = new String[]{"Case"};
				uneCase = remplaceParValeur(uneCase,grille[2],';',aPartir);
				aPartir = grille[2].indexOf("Case",aPartir+4);
				plateau[i][j] = new Case(uneCase[0]);
			}
		}
	}
	
	public static String[] remplaceParValeur(String[] str, String texte, char separateur, int aPartir){
		for(int k=0 ; k<str.length ; k++){
			int index = texte.indexOf(str[k],aPartir-1)+str[k].length();
			int i = 0;
			String mot="";
			while(texte.charAt(index+i)!=separateur){
				mot = mot + texte.charAt(index+i);
				i++;
			}
			str[k]=mot;
		}
		return str;
	}
	
	@Override
	public String toString() {
		String array = "";
		for(int i=0 ; i<hauteur ; i++){
			for(int j=0 ; j<largeur ; j++){
				array = array + plateau[i][j].toString();
				array = array + ";";
			}
		}
		return "Grille [largeur=" + largeur + ", hauteur=" + hauteur + ", plateau=" + array + "]";
	}



	public boolean estLibre(){
		for (int i = 0; i < largeur; i++){
			for (int j = 0; j < hauteur; j++){
				if (plateau[i][j].getJoueur() == 0){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean changeCase(Entity joueur, String couleur, Jeu jeu){
		boolean Joue = true;
		if (couleur.equals("gris")){
			Joue = false;
		}
		for(int k=0 ; k<jeu.listeJoueurs.length ;k++)
		{
			if(jeu.listeJoueurs[k].numero != joueur.numero)
			{
				if(jeu.listeJoueurs[k].couleur.equals(couleur))
				{
					Joue = false;
				}
			}
		}
		if(Joue){
			joueur.couleur = couleur;
			for (int k=0 ; k<joueur.listeCases.length ; k++){
				{
					joueur.listeCases[k].setCouleur(couleur);
					testCase(couleur, joueur.listeCases[k].getI() + 1, joueur.listeCases[k].getJ(), joueur,jeu);
					testCase(couleur, joueur.listeCases[k].getI() - 1, joueur.listeCases[k].getJ(), joueur,jeu);
					testCase(couleur, joueur.listeCases[k].getI(), joueur.listeCases[k].getJ() + 1, joueur,jeu);
					testCase(couleur, joueur.listeCases[k].getI(), joueur.listeCases[k].getJ() - 1, joueur,jeu);
				}
			}
		}
		return Joue;
	}
	
	public boolean changeCase2(Entity joueur, String couleur, Jeu jeu){
		
		
		joueur.couleur = couleur;
		for (int k=0 ; k<joueur.listeCases.length ; k++){
			{
				joueur.listeCases[k].setCouleur(couleur);
				testCase(couleur, joueur.listeCases[k].getI() + 1, joueur.listeCases[k].getJ(), joueur,jeu);
				testCase(couleur, joueur.listeCases[k].getI() - 1, joueur.listeCases[k].getJ(), joueur,jeu);
				testCase(couleur, joueur.listeCases[k].getI(), joueur.listeCases[k].getJ() + 1, joueur,jeu);
				testCase(couleur, joueur.listeCases[k].getI(), joueur.listeCases[k].getJ() - 1, joueur,jeu);
			}
		}
	
	return true;
	}
				
	public void testCase(String couleur, int i , int j, Entity joueur, Jeu jeu){
		if(i>=0 && j>=0 && i<largeur && j<hauteur){			
			if (plateau[i][j].getCouleur().equals(couleur)){
				{
					plateau[i][j].setCouleur(couleur);
					plateau[i][j].changeJoueur(joueur);
					joueur.ajouterCase(plateau[i][j]);
				}
			}
		}
			
	}
}
