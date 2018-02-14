import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private int width=500, height=500;
	private Grille grille;
	private FenetreJeu fen;
	private Jeu jeu;
	private int sizeBloc;
	
	//Constructeur d'une fenêtre
	public Fenetre(String title, int width, int height, int sizeBloc){
		super();
		this.title=title;
		this.sizeBloc=sizeBloc;
		this.width=width;
		this.height=height;
		Jeu jeu = new Jeu();
		Grille grille = new Grille(width,height);
		this.grille=grille;
		this.jeu=jeu;
		FenetreJeu fenetre = new FenetreJeu(grille, sizeBloc,jeu);
		this.fen=fenetre;
		setTitle(title);
		setSize(width*sizeBloc+7,height*sizeBloc+30+50);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(fen);
		setVisible(true);
		play(false);
	}
	
	//Création d'une fenêtre pour une partie chargée
	public Fenetre(String title, int width, int height, int sizeBloc, String[] grille, String[] jeu){
		this.title=title;
		this.sizeBloc=sizeBloc;
		this.width=width;
		this.height=height;
		this.grille = new Grille(grille);
		this.jeu = new Jeu(jeu, this.grille);
		FenetreJeu fenetre = new FenetreJeu(this.grille, this.sizeBloc,this.jeu);
		this.fen=fenetre;
		setTitle(title);
		setSize(width*sizeBloc+7,height*sizeBloc+30+50);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(fen);
		setVisible(true);
		play(true);
	}
	
	//Deuxième toString de cette classe
	public String toString2() {
		return "Fenetre [title=" + title + ":width=" + width + ": height=" + height + ": sizeBloc=" + sizeBloc + ": grille=" + grille.toString() + ": fen="
				+ fen.toString() + ": jeu=" + jeu.toString() + ":]";
	}

	//Permet de déterminer la couleur de départ (vérifie qu'aucun joueur n'a la même couleur au début d'une partie)
	public void couleurDepart(Grille grille, Jeu jeu, int i, int j, int nbJoueursLa){
		boolean aPersonne = false;
		for(int k=0 ; k<nbJoueursLa ; k++){
			if(grille.plateau[i][j].getCouleur().equals(jeu.listeJoueurs[k].couleur)){
				aPersonne = true;
			}
		}
		while(aPersonne){
			int alea = (int) (Math.random() * 6);
			String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};
			grille.plateau[i][j].setCouleur(listeCouleurs[alea]);
			aPersonne=false;
			for(int k=0 ; k<nbJoueursLa ; k++){
				if(grille.plateau[i][j].getCouleur().equals(jeu.listeJoueurs[k].couleur)){
					aPersonne = true;
				}
			}
		}
	}
	
	//Fonction permettant de jouer
	public void play(boolean save){
		if(!save){
			int nbJoueur = jeu.addPlayer(fen);
			jeu.setFogOfWar(JOptionPane.showConfirmDialog(fen, "Souhaitez-vous du brouillard de guerre?"));
			if (jeu.getFogOfWar()== 0){
				jeu.setViewDistance(Integer.parseInt(JOptionPane.showInputDialog(fen, "Combien de cases de distance ?")));
			}
			jeu.setBlocs(JOptionPane.showConfirmDialog(fen, "Souhaitez-vous des obstacles?"));
			if(jeu.getBlocs() == 0){
				for (int i = 1; i < grille.largeur-1; i++){
					for (int j = 1; j < grille.hauteur-1; j++){
						int alea = (int) (Math.random() * 7);
						if (alea == 6){
							grille.plateau[i][j].setCouleur("gris");
						}
					}
				}
				repaint();
			}
			for(int k=0 ; k<nbJoueur ; k++){
				if(k<2){
					grille.plateau[k*(width-1)][k*(height-1)].changeJoueur(jeu.listeJoueurs[k]);
					couleurDepart(grille, jeu,k*(width-1),k*(height-1),k);
					grille.plateau[k*(width-1)][k*(height-1)].changeJoueur(jeu.listeJoueurs[k]);
					jeu.listeJoueurs[k].ajouterCase(grille.plateau[k*(width-1)][k*(height-1)]);
					jeu.listeJoueurs[k].couleur = grille.plateau[k*(width-1)][k*(height-1)].getCouleur();
				}
				else if(k==2){
					grille.plateau[0][height-1].changeJoueur(jeu.listeJoueurs[k]);
					couleurDepart(grille, jeu, 0, height-1,k);
					grille.plateau[0][(height-1)].changeJoueur(jeu.listeJoueurs[k]);
					jeu.listeJoueurs[k].ajouterCase(grille.plateau[0][(height-1)]);
					jeu.listeJoueurs[k].couleur = grille.plateau[0][(height-1)].getCouleur();
				}
				else{
					grille.plateau[width-1][0].changeJoueur(jeu.listeJoueurs[k]);
					couleurDepart(grille, jeu,width-1,0,k);
					grille.plateau[width-1][0].changeJoueur(jeu.listeJoueurs[k]);
					jeu.listeJoueurs[k].ajouterCase(grille.plateau[width-1][0]);
					jeu.listeJoueurs[k].couleur = grille.plateau[width-1][0].getCouleur();
				}
			}
		}
		fen.repaint();
		Mouse clic = new Mouse(jeu,grille,fen);
		fen.addMouseListener(clic);
		this.addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent ev)
		   {
				int exit = JOptionPane.showConfirmDialog(fen, "Souhaitez-vous enregistrer cette partie ?");
				if(exit==0){
					try {
						Date date = new Date();
						File fichier =  new File("C:/Users/victo/Documents/Filler/filler" + date.toString().replaceAll(" ", "").replaceAll(":", "") + ".txt");
						fichier.createNewFile();
						FileWriter ffw=new FileWriter(fichier);
						String laFen = toString2();
						ffw.write(laFen);  
						ffw.close(); // fermer le fichier à la fin des traitements
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
				else if (exit==1){
					System.exit(0);
				}
				else{
					setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				}
		   }});
	}
}
