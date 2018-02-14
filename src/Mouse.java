import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
	
public class Mouse implements MouseListener{
	
	public int x;
	public int y;
	public Jeu jeu;
	public Grille grille;
	public FenetreJeu fen;
	
	public Mouse(Jeu jeu, Grille grille,FenetreJeu fen){
		this.jeu = jeu;
		this.grille=grille;
		this.fen=fen;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.x=((int)e.getX())/fen.getSizeBloc();
		this.y=((int)e.getY())/fen.getSizeBloc();
		if(jeu.listeJoueurs[jeu.numeroJoueur] instanceof Joueur){
			if ((x>=0 && x<grille.largeur) && (y>=0 && y<grille.hauteur)){
				boolean Joue = grille.changeCase(jeu.listeJoueurs[jeu.numeroJoueur], grille.plateau[grille.largeur - 1 - y][x].getCouleur(), jeu);
				fen.repaint();
				if(Joue){
					if(jeu.victoire(grille)[0]!=404){
						Entity gagnant = jeu.listeJoueurs[jeu.victoire(grille)[0]-1];
						JOptionPane.showMessageDialog(fen, "Bravo " + gagnant.pseudo + " ! Vous avez gagné !");
						System.exit(0);
					}
					jeu.numeroJoueur = jeu.numeroJoueur+1;
					if(jeu.numeroJoueur>jeu.listeJoueurs.length-1){
						jeu.numeroJoueur = 0;
					}
					if(jeu.listeJoueurs[jeu.numeroJoueur] instanceof IA){
						Robot r;
						try {
							r = new Robot();
							PointerInfo a = MouseInfo.getPointerInfo();
							Point b = a.getLocation();
							int x1 = (int) b.getX();
							int y1 = (int) b.getY();
							r.mouseMove(x1, y1);  
							r.mousePress(InputEvent.BUTTON1_MASK);  
							r.mouseRelease(InputEvent.BUTTON1_MASK); 
						} catch (AWTException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}   
					}
				}
			}
		}
		else{
			jeu.listeJoueurs[jeu.numeroJoueur].joue(grille, jeu, fen,2);
			fen.repaint();
			jeu.numeroJoueur = jeu.numeroJoueur+1;
			if(jeu.numeroJoueur>jeu.listeJoueurs.length-1){
				jeu.numeroJoueur = 0;
			}
			if(jeu.victoire(grille)[0]!=404){
				Entity gagnant = jeu.listeJoueurs[jeu.victoire(grille)[0]-1];
				JOptionPane.showMessageDialog(fen, "Bravo " + gagnant.pseudo + " ! Vous avez gagné !");
				System.exit(0);
			}
			if(jeu.listeJoueurs[jeu.numeroJoueur] instanceof IA){
				Robot r;
				try {
					r = new Robot();
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					int x1 = (int) b.getX();
					int y1 = (int) b.getY();
					r.mouseMove(x1, y1);  
					r.mousePress(InputEvent.BUTTON1_MASK);  
					r.mouseRelease(InputEvent.BUTTON1_MASK); 
				} catch (AWTException e1) {
					
					e1.printStackTrace();
				}   
			}
		}

	}
	@Override
	public void mousePressed(MouseEvent e) {}
		//Invoked when a mouse button has been pressed on a component.
	@Override
	public void mouseReleased(MouseEvent e) {}
		//Invoked when a mouse button has been released on a component.
	@Override
	public void mouseEntered(MouseEvent e) {}
		//Invoked when the mouse enters a component.
	@Override
	public void mouseExited(MouseEvent e) {}
		//Invoked when the mouse exits a component.
	
	

}
