
public abstract class Entity {
	protected int numero; 		//Numéro attribué au joueur (1 à 4)
	protected String pseudo; 	//Comment le joueur souhaite être appelé
	protected int score;		//Score du joueur
	protected String couleur;	//Couleur du joueur
	protected Case[] listeCases;//Cases du joueur
	boolean aMonTour;			//Est-ce au tour du joueur ?
	public String[] coupsGagnants={couleur, couleur};	//Coups qui permettent de gagner en 2 coups (utile pour l'IA)
	public int coupsGagnantsJoués=0;					//Combien de coups avant la victoire si c'est possible (utile pour l'IA)
	public boolean sonPossible= false;					//Si oui ou non c'est possible de gagner en deux coups (utile pour l'IA)
	
	//Constructeur
	public Entity(Jeu jeu, String pseudo){
		this.pseudo = pseudo;						
		this.numero = jeu.listeJoueurs.length + 1;
		this.score = 0;
		this.listeCases = new Case[0];
	}
	
	public abstract void joue(Grille grille, Jeu jeu, FenetreJeu fen, int niveauIA);
	
	//Construction pour récupérer les valeurs d'une sauvegarde
	public Entity(String[] infos, String[] cases,Grille grille){  
		this.numero = Integer.parseInt(infos[0]);				  
		this.pseudo = infos[1];									  
		this.score = Integer.parseInt(infos[2]);
		this.couleur = infos[3];
		this.aMonTour = Boolean.valueOf(infos[4]);
		boolean construction = true;
		int aPartir = 0;
		this.listeCases = new Case[nbOcc(cases[0],"Case")];
		int numero = 0;
		while(construction){
			String[] uneCase = new String[]{"Case"};
			uneCase = remplaceParValeur(uneCase,cases[0],';',aPartir);
			aPartir = cases[0].indexOf("Case",aPartir+4);
			String[] Case = new String[]{"couleur=","joueur=","i=","j="};
			Case = remplaceParValeur(Case,uneCase[0],',',0);
			grille.plateau[Integer.parseInt(Case[2])][Integer.parseInt(Case[3])].setJoueur(Integer.parseInt(Case[1]));
			this.listeCases[numero]=grille.plateau[Integer.parseInt(Case[2])][Integer.parseInt(Case[3])];
			if(aPartir==-1){
				construction=false;
			}
			numero += 1;
		}	
	}
	
	//Compte le nombre d'occurences dans un texte
	public static int nbOcc(String str, String mot){
		int compteur=0;
		int occurence=0;
		for(int k = 0; k<str.length()-mot.length() ; k++){
			if(mot.charAt(compteur)==(str.charAt(k))){
				compteur += 1;
				if(compteur==mot.length()){
					occurence+=1;
					compteur=0;
				}
			}
			else{
				compteur=0;
			}
		}
		return occurence;
	}
	
	//Fonction déjà défini (voir case)
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
		for(int i=0 ; i<listeCases.length ; i++){
			array = array + listeCases[i].toString();
			array = array + ";";
		}
		return "Entity [numero=" + numero + ", pseudo=" + pseudo + ", score=" + score + ", couleur=" + couleur
				+ ", aMonTour=" + aMonTour + ", listeCases=" + array + "!]";
	}


	//Getters et Setters
	public boolean getTour(){
		return aMonTour;
	}
	
	public void setTour(boolean bool){
		aMonTour=bool;
	}
	
	//Permet d'ajouter cette case à la liste du joueur
	public void ajouterCase(Case uneCase)
	{
		Case[] Cases = new Case[listeCases.length+1];
		boolean dejaPris = false;
		for(int k=0 ; k<Cases.length ; k++)
		{
			if(k<listeCases.length)
			{
				if(uneCase.getJ()==listeCases[k].getJ() && uneCase.getI()==listeCases[k].getI())
				{
					dejaPris = true; 
				}
				Cases[k] = listeCases[k];
			}
			else
			{				
				Cases[k] = uneCase;
			}
		}
		if(!dejaPris)
		{
			this.listeCases = Cases;
			score = score + 1;
		}
	}


}
