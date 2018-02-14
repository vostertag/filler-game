import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
public class Jeu {
	
	public Entity[] listeJoueurs;
	public int numeroJoueur=0;
	private int fogOfWar;	
	private int viewDistance;
	private int blocs;
	
	public int getFogOfWar() {
		return fogOfWar;
	}

	public void setFogOfWar(int fogOfWar) {
		this.fogOfWar = fogOfWar;
	}

	public int getViewDistance() {
		return viewDistance;
	}

	public void setViewDistance(int viewDistance) {
		this.viewDistance = viewDistance;
	}

	public int getBlocs() {
		return blocs;
	}

	public void setBlocs(int blocs) {
		this.blocs = blocs;
	}

	public Jeu()
	{
		this.listeJoueurs = new Entity[0];
		this.fogOfWar = 2;
		this.viewDistance = 1;
		this.blocs = 1;
	}
	
	public Jeu(String[] jeu, Grille grille){
		System.out.println(jeu[1]);
		this.numeroJoueur=Integer.parseInt(jeu[0]);
		this.fogOfWar=Integer.parseInt(jeu[1]);
		this.viewDistance=Integer.parseInt(jeu[2]);
		int occurence = nbOcc(jeu[3],"numero=");
		int nbIA = nbOcc(jeu[3],"IAn°");
		this.listeJoueurs = new Entity[occurence];
		int aPartir=0;
		for(int k=0 ; k<occurence ; k++){	
			String[] rechercheEntity = new String[]{"numero=","pseudo=","score=","couleur=","aMonTour="};
			String[] Entity = remplaceParValeur(rechercheEntity,jeu[3],',',aPartir);
			String[] Cases = remplaceParValeur(new String[]{"listeCases="}, jeu[3],'!',aPartir);
			if(k<occurence-nbIA){
				listeJoueurs[k] = new Joueur(Entity,Cases, grille);
			}
			else{
				listeJoueurs[k] = new IA(Entity,Cases, grille);
			}
			aPartir=jeu[3].indexOf("Entity",aPartir+6);
		}
	}
	
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
	
	public static String[] remplaceParValeur(String[] str, String texte, char separateur){
		for(int k=0 ; k<str.length ; k++){
			int index = texte.indexOf(str[k])+str[k].length();
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
		for (int i=0 ; i<listeJoueurs.length ; i++){
			array = array + listeJoueurs[i].toString();
			array = array + ";";
		}
		return "Jeu [numeroJoueur=" + numeroJoueur + ": fogOfWar=" + fogOfWar + ": viewDistance=" + viewDistance + ": listeJoueurs=" + array + ":]";
	}



	public int addPlayer(FenetreJeu fen){
		int nbJoueurs = 2;
		int nbIA = 0;
		boolean ok = false;
		while(!ok){
			try{
				String input = JOptionPane.showInputDialog(fen, "Combien de joueurs souhaitez-vous avoir ?");
				if(input == null){
					System.exit(0);
				}
				String input2 = JOptionPane.showInputDialog(fen, "Combien d'IA ?");
				if(input2 == null){
					System.exit(0);
				}
				nbJoueurs = Integer.parseInt(input);
				nbIA = Integer.parseInt(input2);
				if(nbJoueurs > 4){
					JOptionPane.showMessageDialog(fen,  "Il ne peut y avoir plus de 4 joueurs.");
				}
				else if(nbIA>nbJoueurs){
					JOptionPane.showMessageDialog(fen,  "Il ne peut y avoir plus de joueurs que d'IA.");
				}
				else if(nbJoueurs<2){
					JOptionPane.showMessageDialog(fen,  "Il doit au moins y avoir deux joueurs.");					
				}
				else{
					ok = true;
				}
			}
			catch (NumberFormatException e) {
				  JOptionPane.showMessageDialog(fen,  "Veuillez saisir un nombre !");
				}
		}
		for(int k=0 ; k<nbJoueurs-nbIA ; k++){
			String pseudo = JOptionPane.showInputDialog(fen, "Pseudo du joueur " + (k+1));
			Entity joueur = new Joueur(this, pseudo);
			ajouterJoueur(joueur);			
		}	
		for(int k=0 ; k<nbIA ; k++){
			Entity IA = new IA(this, "IA n°" + k);
			ajouterJoueur(IA);
		}
		return nbJoueurs;
	}
	
	
	public void ajouterJoueur(Entity joueur)
	{
		Entity[] joueurs = new Entity[listeJoueurs.length+1];
		for(int k=0 ; k<joueurs.length ; k++)
		{
			if(k<listeJoueurs.length)
			{
				joueurs[k] = listeJoueurs[k];
			}
			else
			{
				joueurs[k] = joueur;
			}
		}
		this.listeJoueurs = joueurs;
	}
	
	public int[] victoire(Grille grille)
	{
		if(grille.estLibre()==false)
		{
			
			return scoreMax(listeJoueurs);
		}
		int meilleurJoueur = scoreMax(listeJoueurs)[0];
		Entity meilleurJo = listeJoueurs[meilleurJoueur-1];
		if(meilleurJo.score>grille.largeur*grille.hauteur/2)
		{
			return new int[]{meilleurJo.numero, meilleurJo.score};
		}
		return new int[]{404,404};
	}
	
	public static int[] scoreMax(Entity[] joueurs)
	{
		int maximum = 0;
		Entity joueur = joueurs[0];
		for(int k=0 ; k<joueurs.length ; k++)
		{
			if(joueurs[k].score>maximum)
			{
				maximum = joueurs[k].score;
				joueur = joueurs[k];
			}
		}		
		return new int[]{joueur.numero,maximum};
	}
	
	public static void main(String[] args) {		
			try {
				JFileChooser dialogue = new JFileChooser("C:/Users/victo/Documents/Filler");
		        dialogue.showOpenDialog(null);   
				try{
					Scanner fichier = new Scanner(dialogue.getSelectedFile());
					String save = "";
					String[] rechercheGrille = new String[]{""};
					String[] rechercheJeu = new String[]{""};
					boolean lecture = true;
					while(lecture){
						if(!fichier.hasNext()){
							lecture = false;
						}
						else{
							save = save + fichier.next();
						}
					}
					fichier.close();
					String[] recherche = new String[]{"title=","width=","height=","sizeBloc=","grille=","jeu="};
					for(int k=0 ; k<recherche.length ; k++){
						if(k<4){
							int index = save.indexOf(recherche[k])+recherche[k].length();
							int i = 0;
							String mot = "";
							while(save.charAt(index+i)!=':'){
								mot = mot + save.charAt(index+i);
								i++;
							}
							recherche[k]=mot;
						}
						else if(k==4){
							rechercheGrille = new String[]{"hauteur=","largeur=","plateau=", "jeu="};
							for(int j=0 ; j<rechercheGrille.length ; j++){
								if(j!=2){
									int index = save.indexOf(rechercheGrille[j])+rechercheGrille[j].length();
									int i = 0;
									String mot = "";
									while(save.charAt(index+i)!=','){
										mot = mot + save.charAt(index+i);
										i++;
									}
									rechercheGrille[j] = mot;
								}
								else if(j==2){
									int index = save.indexOf(rechercheGrille[j])+rechercheGrille[j].length();
									int i = 0;
									String mot = "";
									while(save.charAt(index+i)!=':'){
										mot = mot + save.charAt(index+i);
										i++;
									}
									rechercheGrille[j] = mot;
								}
							}
						}
						else{
							rechercheJeu = new String[]{"numeroJoueur=","fogOfWar=", "viewDistance=", "Entity"};
							rechercheJeu = remplaceParValeur(rechercheJeu, save, ':');
						}
					}
					new Fenetre(recherche[0], Integer.parseInt(recherche[1]), Integer.parseInt(recherche[2]),Integer.parseInt(recherche[3]),rechercheGrille,rechercheJeu);
				}
				catch(Exception e){
					new Fenetre("Jeu Filler - HADRATOR Team", 13,13,20);
				}
		}
		catch(Exception e){
			System.exit(0);
		}
	}
}