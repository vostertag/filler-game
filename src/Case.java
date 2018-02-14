//Cases pr�sentes sur le plateau de jeu.

public class Case {
	
	private String couleur;	//Couleur (rouge, bleu, jaune, orange, vert, violet, gris)
	private int joueur;		//Num�ro du joueur qui poss�de cette case
	private int i;			//Emplacement sur le plateau[i][j]
	private int j;			//Emplacement sur le plateau[i][j]
		
	//Constructeur "normal"
	public Case(int i, int j){
		this.joueur = 0;		//De base, la case appartient � personne
		this.i=i;				//On doit indiquer son emplacement
		this.j=j;				//avec ses coordonn�es sur le plateau de la grille
		int alea = (int) (Math.random() * 6);	//Choix de la couleur al�atoire
		String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};
		this.couleur = listeCouleurs[alea];
	}
	
	//Constructeur sp�ciale pour charger une sauvegarde
	public Case(String str){
		String[] Case = new String[]{"couleur=","joueur=","i=","j="};
		Case = remplaceParValeur(Case,str,',',0);
		this.joueur = Integer.parseInt(Case[1]);
		this.i = Integer.parseInt(Case[2]);	//Infos utiles stock�es dans str que
		this.j = Integer.parseInt(Case[3]);	//l'on r�cup�re ici.
		this.couleur = Case[0];
	}
	
	//Getters et setters pour Case	
	public String getCouleur(){
		return this.couleur;	
	}

	public int getJoueur(){
		return this.joueur;
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public void setJoueur(int joueur) {
		this.joueur = joueur;
	}

	public void setI(int i) {
		this.i = i;
	}
	
	public void changeJoueur(Entity joueur){
		this.joueur = joueur.numero;
	}
	
	//M�thode toString pour effectuer une sauvegarde
	@Override
	public String toString() {
		return "Case [couleur=" + couleur + ", joueur=" + joueur + ", i=" + i + ", j=" + j + ",]";
	}
	
	//Fonction permettant de remplacer une variable par sa valeur � partir du texte de la sauvegarde
	public static String[] remplaceParValeur(String[] str, String texte, char separateur, int aPartir){
		for(int k=0 ; k<str.length ; k++){									//On parcourt les variables voulues
			int index = texte.indexOf(str[k],aPartir-1)+str[k].length();	//On rep�re o� elles sont dans le texte de sauvegarde
			int i = 0;														//On r�cup�re tout jusqu'au s�parateur
			String mot="";								
			while(texte.charAt(index+i)!=separateur){
				mot = mot + texte.charAt(index+i);
				i++;
			}
			str[k]=mot;
		}
		return str;															//Et on renvoit ce qui est obtenu
	}
}
