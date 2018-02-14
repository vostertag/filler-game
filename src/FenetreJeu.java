import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class FenetreJeu extends JPanel{

	private static final long serialVersionUID = 1L;
	private Grille grille;
	private int sizeBloc;
	private Jeu jeu;
	
	public FenetreJeu(Grille grille, int size,Jeu jeu){
		this.grille = grille;
		this.sizeBloc = size;
		this.jeu = jeu;
	}
	
	
	
	@Override
	public String toString() {
		return "FenetreJeu [sizeBloc=" + sizeBloc + "]";
	}



	public int getSizeBloc(){
		return sizeBloc;
	}
	
	//Fonction d'affichage du jeu
	public void paintComponent(Graphics g){
		for(int k=0 ; k<=1 ; k++){
		for (int i = 0; i < grille.hauteur; i++){
			for (int j = 0; j < grille.largeur; j++){
				String couleur = grille.plateau[i][j].getCouleur();
				if(couleur.equals("rouge"))
				{
					g.setColor(Color.red);
					if(k==0){
					if(grille.plateau[i][j].getJoueur() != 0){	
						g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
					}
					}
					if(k==1){
					if(grille.plateau[i][j].getJoueur() == 0){
						g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						g.setColor(Color.black);
						g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
					}
					}
					
				}
				if(couleur.equals("orange"))
				{
					g.setColor(Color.orange);
					if(k==0){
						if(grille.plateau[i][j].getJoueur() != 0){	
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
						if(k==1){
						if(grille.plateau[i][j].getJoueur() == 0){
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
							g.setColor(Color.black);
							g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
				}
				if(couleur.equals("bleu"))
				{
					g.setColor(Color.blue);
					if(k==0){
						if(grille.plateau[i][j].getJoueur() != 0){	
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
						if(k==1){
						if(grille.plateau[i][j].getJoueur() == 0){
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
							g.setColor(Color.black);
							g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
				}
				if(couleur.equals("vert"))
				{
					g.setColor(Color.green);
					if(k==0){
						if(grille.plateau[i][j].getJoueur() != 0){	
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
						if(k==1){
						if(grille.plateau[i][j].getJoueur() == 0){
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
							g.setColor(Color.black);
							g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
				}
				if(couleur.equals("violet"))
				{
					g.setColor(Color.magenta);
					if(k==0){
						if(grille.plateau[i][j].getJoueur() != 0){	
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
						if(k==1){
						if(grille.plateau[i][j].getJoueur() == 0){
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
							g.setColor(Color.black);
							g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
				}
				if(couleur.equals("jaune"))
				{
					g.setColor(Color.yellow);
					if(k==0){
						if(grille.plateau[i][j].getJoueur() != 0){	
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
						if(k==1){
						if(grille.plateau[i][j].getJoueur() == 0){
							g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
							g.setColor(Color.black);
							g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						}
						}
				}
				if(couleur.equals("gris"))
				{
					g.setColor(Color.DARK_GRAY);
					if(k==0){
					if(grille.plateau[i][j].getJoueur() != 0){	
						g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
					}
					}
					if(k==1){
					if(grille.plateau[i][j].getJoueur() == 0){
						g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
						g.setColor(Color.black);
						g.drawRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
					}
					}
					
				}
				if (jeu.getFogOfWar() == 0){
					boolean visible = false;
					for (int t=0; t<=jeu.getViewDistance();t++){
						if(i+t>=0 && j>=0 && i+t<grille.largeur && j<grille.hauteur){	
							if(grille.plateau[i+t][j].getJoueur() != 0){
								visible = true;
							}
						}
						if(i-t>=0 && j>=0 && i-t<grille.largeur && j<grille.hauteur){	
							if(grille.plateau[i-t][j].getJoueur() != 0){
								visible = true;
							}
						}
						if(i>=0 && j+t>=0 && i<grille.largeur && j+t<grille.hauteur){	
							if(grille.plateau[i][j+t].getJoueur() != 0){
								visible = true;
							}
						}
						if(i>=0 && j-t>=0 && i<grille.largeur && j-t<grille.hauteur){	
							if(grille.plateau[i][j-t].getJoueur() != 0){
								visible = true;
							}
						}
					}
					if (!visible){
						g.setColor(Color.black);
						g.fillRect(j*sizeBloc, (grille.hauteur-1-i)*sizeBloc, sizeBloc, sizeBloc);
					}
				}
			}
			}
		}
		g.setColor(Color.black);
		g.drawRect(0, 0, grille.hauteur*sizeBloc, grille.largeur*sizeBloc);
		g.setColor(Color.white);
		g.fillRect(0, grille.hauteur*sizeBloc+2,grille.largeur*sizeBloc , 50);
		g.setColor(Color.black);
		for(int k = 0 ; k<jeu.listeJoueurs.length ; k++){
			g.setFont(new Font("Arial", Font.PLAIN, 12));
			g.drawString("Score de " + jeu.listeJoueurs[k].pseudo + " : " + jeu.listeJoueurs[k].score, 2, grille.hauteur*sizeBloc+12*(k+1));
		}
	}
}
