 
public class IA extends Entity{

	int coupsJoués= 0;
	int [][] coups= {{0,0,0},{0,0,0}};
	int [] coupsAnalytique= {0,0,0,0,0,0};
	String [] coupsGagnants={couleur,couleur};
	String [] mesCoupsGagnants={couleur,couleur};
	int mesCoupsGagnantsJoués =0;
	int coupsGagnantsJoués =0;
	boolean possible = false;
	boolean sonPossible = false;
	String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
	
	public IA(Jeu jeu, String pseudo){
		super(jeu, pseudo);
	}
	
	public IA(String[] infos, String[] cases, Grille grille){
		super(infos, cases, grille);
	}
	
	
	public void joue(Grille grille, Jeu jeu, FenetreJeu fen, int niveauIA){
		int Z=0;
		boolean joue = true;
		while (joue){

			if (niveauIA==1){//Checked
				this.joueAléatoire(grille, jeu, fen);
				joue = false;
			}
			
			if (niveauIA==4){//Checked
				this.joue2(grille, jeu, fen);
				joue = false;
			}

			else if (niveauIA ==2){//Checked
				this.joueMeilleureSolution(grille, jeu, fen);
				joue= false;
			}

			else if (niveauIA == 3){

				if(mesCoupsGagnantsJoués==0){
				this.deuxCoupsGagnants(grille, jeu, fen);}
				
				/*for(int z=0 ; z<jeu.listeJoueurs.length ;z++)
				{
					if(jeu.listeJoueurs[z].numero != this.numero)
					{	
						if(jeu.listeJoueurs[z].coupsGagnantsJoués==0){ 
							this.sesDeuxCoupsGagnants(grille, jeu, fen, z);
							if (jeu.listeJoueurs[z].sonPossible==true){
								Z=z;
								System.out.println("GAGNANT");
								
							}
						}
					}
				}*/
				
				if((possible == true) && (mesCoupsGagnantsJoués==0)){
					System.out.println("Je peux gagner en deux coups");
					boolean jeJoueCa=true;
					for(int k=0 ; k<jeu.listeJoueurs.length ; k++){
						if(jeu.listeJoueurs[k].couleur == mesCoupsGagnants[mesCoupsGagnantsJoués]){
							jeJoueCa=false;
						}
					}
					if(jeJoueCa){
						grille.changeCase(jeu.listeJoueurs[this.numero-1], mesCoupsGagnants[mesCoupsGagnantsJoués], jeu);
						mesCoupsGagnantsJoués++;
					}
					else{
						this.joueMeilleureSolution(grille, jeu, fen);
					}
					joue = false;
				}
				else if((possible == true) && (mesCoupsGagnantsJoués==1)){
					System.out.println("Je peux gagner en un coups");
					boolean jeJoueCa=true;
					for(int k=0 ; k<jeu.listeJoueurs.length ; k++){
						if(jeu.listeJoueurs[k].couleur == mesCoupsGagnants[mesCoupsGagnantsJoués]){
							jeJoueCa=false;
						}
					}
					if(jeJoueCa){
						grille.changeCase(jeu.listeJoueurs[this.numero-1], mesCoupsGagnants[mesCoupsGagnantsJoués], jeu);
						mesCoupsGagnantsJoués++;
					}
					else{
						this.joueMeilleureSolution(grille, jeu, fen);
					}
					joue = false;
				}
				

				/*else if (jeu.listeJoueurs[Z].sonPossible==true && (coupsGagnantsJoués==0)){
					System.out.println("Il peut gagner en deux coups");
					grille.changeCase(jeu.listeJoueurs[this.numero-1], jeu.listeJoueurs[Z].coupsGagnants[coupsGagnantsJoués], jeu);
					jeu.listeJoueurs[Z].coupsGagnantsJoués++;
					joue = false;
				}
				
				else if (jeu.listeJoueurs[Z].sonPossible==true && (coupsGagnantsJoués==1)){
					System.out.println("Il peut gagner en un coups");
					grille.changeCase(jeu.listeJoueurs[this.numero-1], mesCoupsGagnants[coupsGagnantsJoués], jeu);
					jeu.listeJoueurs[Z].coupsGagnantsJoués++;
					joue = false;				
				}*/
				
				else{
					this.joue2(grille, jeu, fen);
					joue=false;
				}
				
				
				
				
			}
		}
	}



	//Calcule la "surface de contact" pour jouer l'extension et pas la prise de case
	public int NombreCasesBordure(IA joueur, Grille grille){
		Case[] CasesBordure = new Case[0];
		int nombreCasesBordures =0;
		for(int i=0 ; i<grille.hauteur ; i++){
			for (int j=0 ; j<grille.largeur; j++){
				if(i!=0 && j!=0 && i!=grille.hauteur-1 && j!=grille.largeur-1){				
					int n=0;
					if(grille.plateau[i+1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i-1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i][j+1].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j-1].getJoueur()==joueur.numero){
						n++;
					}
					if(n==1||n==2||n==3){
						nombreCasesBordures++;
					}
				}	

				else if (i==0 && j!=0 && j!=grille.largeur-1){
					int n=0;
					if(grille.plateau[i+1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i][j+1].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j-1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1||n==2){
						nombreCasesBordures++;}
				}

				else if (i==grille.hauteur-1 && j!=0 && j!=grille.largeur-1){
					int n=0;
					if(grille.plateau[i-1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i][j+1].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j-1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1||n==2){
						nombreCasesBordures++;}
				}

				else if (j==0 && i!=0 && i!=grille.hauteur-1){
					int n=0;
					if(grille.plateau[i+1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i-1][j].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j+1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1||n==2){
						nombreCasesBordures++;}
				}

				else if (j==grille.largeur-1 && i!=0 && i!=grille.hauteur-1){
					int n=0;
					if(grille.plateau[i+1][j].getJoueur()==joueur.numero){
						n++;
					}
					if(grille.plateau[i-1][j].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j-1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1||n==2){
						nombreCasesBordures++;}
				}

				else if (i==grille.hauteur-1 && j==0){
					int n=0;
					if(grille.plateau[i-1][j].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j+1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1){
						nombreCasesBordures++;}
				}

				else if (i==0 && j==grille.largeur-1){
					int n=0;
					if(grille.plateau[i+1][j].getJoueur()==joueur.numero){
						n++;				
					}
					if(grille.plateau[i][j-1].getJoueur()==joueur.numero){
						n++;				
					}
					if(n==1){
						nombreCasesBordures++;}
				}

			}
		}
		return nombreCasesBordures;
	}	

	//Méthode utile pour l'IA joue2
	static int [] coordonnéesMaximumAnalytique( int matrice[][][][][][] )
	{
		int max = matrice[0][0][0][0][0][0];
		int[] coordonnées = {0,0,0,0,0,0};
		for (int a=0;a<6;a++){
			for (int b=0;b<6;b++){					
				for (int c=0;c<6;c++){
					for (int d=0;d<6;d++){
						for (int e=0;e<6;e++){
							for (int f=0;f<6;f++){
								if ( max < matrice[a][b][c][d][e][f] ){
									max = matrice[a][b][c][d][e][f];
									coordonnées[0] = a;
									coordonnées[1] = b;
									coordonnées[2] = c;
									coordonnées[3] = d;
									coordonnées[4] = e;
									coordonnées[5] = f;

								}
							}
						}
					}
				}
			}
		}
		return coordonnées;
	}


	//Méthode utile pour l'IA joue2
	static int [] coordonnéesMaximum( int matrice[][][] )
	{
		int max = matrice[0][0][0];
		int[] coordonnées = {0,0,0};
		for ( int a = 0; a < 6; a++ ){
			for ( int b = 0; b < 6; b++ ){
				for ( int c = 0; c < 6; c++ ){
					if ( max < matrice[a][b][c] ){
						max = matrice[a][b][c];
						coordonnées[0] = a;
						coordonnées[1] = b;
						coordonnées[2] = c;

					}}}}

		return coordonnées;
	}


	//Permet de déterminer si l'on peut gagner en deux coups
	public void deuxCoupsGagnants(Grille grille, Jeu jeu, FenetreJeu fen){		

		Case[] copyListeCases = new Case[this.listeCases.length];

		for(int w=0; w < this.listeCases.length; w++){
			copyListeCases[w]=this.listeCases[w];
		}

		Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
		for (int i = 0; i < grille.largeur; i++){
			for (int j = 0; j < grille.hauteur; j++){
				copyPlateau[i][j] = new Case(i,j);
			}
		}

		for(int L=0 ; L<grille.hauteur ; L++){
			for (int H=0; H<grille.largeur;H++){
				copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
				copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
			}
		}

		int copyScore = this.score;

		String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
		String[][][] listeCoupsPossibles = new String [6][6][2];

		int[][] gains= new int [6][6];

		for(int g=0;g<2;g++){
			for (int a=0;a<6;a++){
				for (int b=0;b<6;b++){
					if(g==0){
						listeCoupsPossibles[a][b][g]=listeCouleurs[a];							
					}
					else if (g==1){
						listeCoupsPossibles[a][b][g]=listeCouleurs[b];
					}
				}
			}
		}
		
		
		for (int a=0;a<6;a++){
			for (int b=0;b<6;b++){	
				for(int g=0;g<2;g++){
					String choixProvisoire = listeCoupsPossibles[a][b][g];
					grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
				}
				int gain = this.score - copyScore;
				gains[a][b]= gain;
				for(int L=0 ; L<grille.hauteur ; L++){
					for (int H=0; H<grille.largeur;H++){
						grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
						grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
					}
				}
				jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
				jeu.listeJoueurs[this.numero-1].score = copyScore;
			}
		}
		
		int scoreGagnant=(grille.hauteur*grille.largeur/2);	
		
		for (int a=0;a<6;a++){
			for (int b=0;b<6;b++){
				if (jeu.listeJoueurs[this.numero-1].score + gains[a][b] > scoreGagnant){
					scoreGagnant=jeu.listeJoueurs[this.numero-1].score + gains[a][b];
					mesCoupsGagnants[0]=listeCoupsPossibles[a][b][0];
					mesCoupsGagnants[1]=listeCoupsPossibles[a][b][1];
					//System.out.println(mesCoupsGagnants[0]);
					//System.out.println(mesCoupsGagnants[1]);
					possible = true;
				}
			}
		}
	}

	//Permet de déterminer si un ou joueur peut gagner en deux coups
		public void sesDeuxCoupsGagnants(Grille grille, Jeu jeu, FenetreJeu fen, int numeroJoueur){		

			Case[] copyListeCases = new Case[jeu.listeJoueurs[numeroJoueur].listeCases.length];

			for(int w=0; w < this.listeCases.length; w++){
				copyListeCases[w]=jeu.listeJoueurs[numeroJoueur].listeCases[w];
			}

			Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
			for (int i = 0; i < grille.largeur; i++){
				for (int j = 0; j < grille.hauteur; j++){
					copyPlateau[i][j] = new Case(i,j);
				}
			}

			for(int L=0 ; L<grille.hauteur ; L++){
				for (int H=0; H<grille.largeur;H++){
					copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
					copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
				}
			}

			int copyScore = jeu.listeJoueurs[numeroJoueur].score;

			String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
			String[][][] listeCoupsPossibles = new String [6][6][2];

			int[][] gains= new int [6][6];

			for(int g=0;g<2;g++){
				for (int a=0;a<6;a++){
					for (int b=0;b<6;b++){
						if(g==0){
							listeCoupsPossibles[a][b][g]=listeCouleurs[a];							
						}
						else if (g==1){
							listeCoupsPossibles[a][b][g]=listeCouleurs[b];
						}
					}
				}
			}
			
			System.out.println(numeroJoueur);
			for (int a=0;a<6;a++){
				for (int b=0;b<6;b++){	
					for(int g=0;g<2;g++){
						String choixProvisoire = listeCoupsPossibles[a][b][g];
						grille.changeCase(jeu.listeJoueurs[numeroJoueur], choixProvisoire, jeu);	
					}
					int gain = jeu.listeJoueurs[numeroJoueur].score - copyScore;
					gains[a][b]= gain;
					for(int L=0 ; L<grille.hauteur ; L++){
						for (int H=0; H<grille.largeur;H++){
							grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
							grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
						}
					}
					jeu.listeJoueurs[numeroJoueur].listeCases=copyListeCases;
					jeu.listeJoueurs[numeroJoueur].score = copyScore;
				}
			}
			
			int scoreGagnant=(grille.hauteur*grille.largeur/2);	
			
			for (int a=0;a<6;a++){
				for (int b=0;b<6;b++){
					if (jeu.listeJoueurs[numeroJoueur].score + gains[a][b] > scoreGagnant){
						scoreGagnant=jeu.listeJoueurs[jeu.listeJoueurs[numeroJoueur].numero-1].score + gains[a][b];
						jeu.listeJoueurs[numeroJoueur].coupsGagnants[0]=listeCoupsPossibles[a][b][0];
						jeu.listeJoueurs[numeroJoueur].coupsGagnants[1]=listeCoupsPossibles[a][b][1];
						System.out.println(mesCoupsGagnants[0]);
						System.out.println(mesCoupsGagnants[1]);
						jeu.listeJoueurs[numeroJoueur].sonPossible = true;
					}
				}
			}
		}



	//Joue la meilleure série possible en 7 tours
	public void joueAnalytique(Grille grille, Jeu jeu, int x, int y, FenetreJeu fen){
		boolean joue = true;

		while (joue){
			int casesAccessibles = this.NombreCasesBordure(this, grille);

			Case[] copyListeCases = new Case[this.listeCases.length];

			for(int w=0; w < this.listeCases.length; w++){
				copyListeCases[w]=this.listeCases[w];
			}

			Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
			for (int i = 0; i < grille.largeur; i++){
				for (int j = 0; j < grille.hauteur; j++){
					copyPlateau[i][j] = new Case(i,j);
				}
			}

			for(int L=0 ; L<grille.hauteur ; L++){
				for (int H=0; H<grille.largeur;H++){
					copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
					copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
				}
			}

			int copyScore = this.score;

			String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
			String[][][][][][][] listeCoupsPossibles = new String [6][6][6][6][6][6][6];

			int[][][][][][] gains= new int [6][6][6][6][6][6];

			for(int g=0;g<6;g++){
				for (int a=0;a<6;a++){
					for (int b=0;b<6;b++){					
						for (int c=0;c<6;c++){
							for (int d=0;d<6;d++){
								for (int e=0;e<6;e++){
									for (int f=0;f<6;f++){
										if(g==0){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[a];							
										}
										else if (g==1){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[b];
										}
										else if (g==2){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[c];
										}
										else if(g==3){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[d];							
										}
										else if (g==4){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[e];
										}
										else if (g==5){
											listeCoupsPossibles[a][b][c][d][e][f][g]=listeCouleurs[f];
										}
									}
								}
							}
						}
					}
				}
			}


			//le tableau est construit comme suit:
			//coordonnées dans "l'espace" c est la premiere couleur choisie, d la seconde
			//et e la troisième.
			// ce sont des triplets de couleurs donc f permet de choisir la f-ième couleur du triplet
			//on a ici tous les triplets possibles


			if ((coupsJoués%6)==0){
				for (int a=0;a<6;a++){
					for (int b=0;b<6;b++){					
						for (int c=0;c<6;c++){
							for (int d=0;d<6;d++){
								for (int e=0;e<6;e++){
									for (int f=0;f<6;f++){
										for(int g=0;g<6;g++){
											String choixProvisoire = listeCoupsPossibles[a][b][c][d][e][f][g];
											if(g==0){
												grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);
											}
											else{
												grille.changeCase2(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);
											}
										}
										int nouveauCasesAccessibles = this.NombreCasesBordure(this, grille);
										int gain = nouveauCasesAccessibles - casesAccessibles;
										gains[a][b][c][d][e][f]= gain;
										for(int L=0 ; L<grille.hauteur ; L++){
											for (int H=0; H<grille.largeur;H++){
												grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
												grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
											}
										}

										jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
										jeu.listeJoueurs[this.numero-1].score = copyScore;
									}
								}

							}
						}

					}
					//on obtient la meilleure série de coups à jouer				
					coupsAnalytique=coordonnéesMaximumAnalytique(gains); //{2,5,3,2,5,3} par exemple
				}
			}

			System.out.println("Combinaison à jouée :");
			System.out.println("Coup 1=" +coupsAnalytique[0]+ " Coup 2= "+coupsAnalytique[1]+ " Coup 3= "+coupsAnalytique[2] + "Coup 4=" +coupsAnalytique[3]+ " Coup 5= "+coupsAnalytique[4]+ " Coup 6= "+coupsAnalytique[5]);	

			System.out.println(jeu.listeJoueurs[this.numero-1].pseudo + " joue la couleur");
			System.out.println(listeCouleurs[coupsAnalytique[coupsJoués%6]]);
			boolean bonneIdee = true;
			for(int k=0; k<jeu.listeJoueurs.length ; k++){
				if(listeCouleurs[coupsAnalytique[coupsJoués%6]]==jeu.listeJoueurs[k].couleur){
					bonneIdee=false;
				}
			}
			if(bonneIdee){
				grille.changeCase(jeu.listeJoueurs[this.numero-1], listeCouleurs[coupsAnalytique[coupsJoués%6]], jeu);
			}
			else{
				joueMeilleureSolution(grille,jeu,fen);
				coupsJoués-=1;
				System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			}
			coupsJoués++;

			joue = false;
		}

			/*
			else {
				System.out.println("///////////////////////////////////////////////////");
				for (int c=0;c<6;c++){
					for (int d=0;d<6;d++){
						for (int e=0;e<6;e++){
							for (int f=0;f<3;f++){
								String choixProvisoire = listeCoupsPossibles[c][d][e][f];
								grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
							}

							int gain = listeCases.length - copyListeCases.length;
							gains[c][d][e]= gain;
							for(int L=0 ; L<grille.hauteur ; L++){
								for (int H=0; H<grille.largeur;H++){
									grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
									grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
								}
							}

							jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
							jeu.listeJoueurs[this.numero-1].score = copyScore;
						}
					}
				}

				//on garde les coups qu'on devait jouer au tour d'avant et on prend aussi les nouveaux
				int M[]=coordonnéesMaximum(gains); //{2,5,3} par exemple
				for (int u=0; u<3; u++){					
					this.coups[0][u]=this.coups[1][u];
					this.coups[1][u]=M[u];
				}

				joue = false;
				/*


				grille.changeCase(jeu.listeJoueurs[this.numero-1], listeCouleurs[this.coups[1][0]], jeu);
				joue = false;*/
		

		joue = false;
	}


	//Joue le premier coup des meilleurs trois tours à venir
	public void joue2(Grille grille, Jeu jeu, FenetreJeu fen){
		boolean joue = true;

		while (joue){
			int casesAccessibles = this.NombreCasesBordure(this, grille);

			Case[] copyListeCases = new Case[this.listeCases.length];

			for(int w=0; w < this.listeCases.length; w++){
				copyListeCases[w]=this.listeCases[w];
			}

			Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
			for (int i = 0; i < grille.largeur; i++){
				for (int j = 0; j < grille.hauteur; j++){
					copyPlateau[i][j] = new Case(i,j);
				}
			}

			for(int L=0 ; L<grille.hauteur ; L++){
				for (int H=0; H<grille.largeur;H++){
					copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
					copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
				}
			}

			int copyScore = this.score;

			String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
			String[][][][] listeCoupsPossibles = new String [6][6][6][3];

			int[][][] gains= new int [6][6][6];


			for (int c=0;c<6;c++){
				for (int d=0;d<6;d++){
					for (int e=0;e<6;e++){
						for (int f=0;f<3;f++){
							if(f==0){
								listeCoupsPossibles[c][d][e][f]=listeCouleurs[c];							
							}
							else if (f==1){
								listeCoupsPossibles[c][d][e][f]=listeCouleurs[d];
							}
							else if (f==2){
								listeCoupsPossibles[c][d][e][f]=listeCouleurs[e];
							}
						}					
					}
				}
			}

			//le tableau est construit comme suit:
			//coordonnées dans "l'espace" c est la premiere couleur choisie, d la seconde
			//et e la troisième.
			// ce sont des triplets de couleurs donc f permet de choisir la f-ième couleur du triplet
			//on a ici tous les triplets possibles

			if (this.score < (grille.hauteur*grille.largeur/3)){
				for (int c=0;c<6;c++){
					for (int d=0;d<6;d++){
						for (int e=0;e<6;e++){
							for (int f=0;f<3;f++){
								String choixProvisoire = listeCoupsPossibles[c][d][e][f];
								if(f==0){
									grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
								}
								else{
									grille.changeCase2(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
								}
							}
							int nouveauCasesAccessibles = this.NombreCasesBordure(this, grille);
							int gain = nouveauCasesAccessibles - casesAccessibles;
							gains[c][d][e]= gain;
							for(int L=0 ; L<grille.hauteur ; L++){
								for (int H=0; H<grille.largeur;H++){
									grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
									grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
								}
							}

							jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
							jeu.listeJoueurs[this.numero-1].score = copyScore;
						}
					}
				}

				//on garde les coups qu'on devait jouer au tour d'avant et on prend aussi les nouveaux
				int M[]=coordonnéesMaximum(gains); //{2,5,3} par exemple
				for (int u=0; u<3; u++){					
					this.coups[0][u]=this.coups[1][u];
					this.coups[1][u]=M[u];
				}

				

				System.out.println(jeu.listeJoueurs[this.numero-1].pseudo + " joue la couleur");
				System.out.println(listeCouleurs[this.coups[1][0]]);
				grille.changeCase(jeu.listeJoueurs[this.numero-1], listeCouleurs[this.coups[1][0]], jeu);
				joue = false;
			}

			else {
				for (int c=0;c<6;c++){
					for (int d=0;d<6;d++){
						for (int e=0;e<6;e++){
							for (int f=0;f<3;f++){
								String choixProvisoire = listeCoupsPossibles[c][d][e][f];
								if(f==0){
									grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
								}
								else{
									grille.changeCase2(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
								}
							}
							int gain = listeCases.length - copyListeCases.length;
							gains[c][d][e]= gain;
							for(int L=0 ; L<grille.hauteur ; L++){
								for (int H=0; H<grille.largeur;H++){
									grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
									grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
								}
							}

							jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
							jeu.listeJoueurs[this.numero-1].score = copyScore;
						}
					}
				}

				//on garde les coups qu'on devait jouer au tour d'avant et on prend aussi les nouveaux
				int M[]=coordonnéesMaximum(gains); //{2,5,3} par exemple
				for (int u=0; u<3; u++){					
					this.coups[0][u]=this.coups[1][u];
					this.coups[1][u]=M[u];
				}

				joue = false;
				/*if (this.coups[1][0]==this.coups[0][1] && this.coups[1][1]==this.coups[0][2]){
					System.out.println("Je reste fidèle à moi même");
				}
				else {System.out.println("Je suis une girouette");
				}



				System.out.println("Ancienne Combinaison:/n");
				System.out.println("Coup 1=" +this.coups[0][0]+ " Coup 2= "+this.coups[0][1]+ " Coup 3= "+this.coups[0][2]);
				System.out.println("Nouvelle Combinaison:/n");
				System.out.println("Coup 1=" +this.coups[1][0]+ " Coup 2= "+this.coups[1][1]+ " Coup 3= "+this.coups[1][2]);
				 */
				System.out.println(jeu.listeJoueurs[this.numero-1].pseudo + " joue la couleur");
				System.out.println(listeCouleurs[this.coups[1][0]]);
				grille.changeCase(jeu.listeJoueurs[this.numero-1], listeCouleurs[this.coups[1][0]], jeu);
				joue = false;
			}

			joue = false;
		}
	}
	
	
	//Joue aléatoirement une couleur
	public void joueAléatoire(Grille grille, Jeu jeu, FenetreJeu fen){boolean joue = true;
		while (joue){
			Case[] copyListeCases = new Case[this.listeCases.length];
			for(int w=0; w < this.listeCases.length; w++){
				copyListeCases[w]=this.listeCases[w];
			}
		
			int casesAccessibles = this.NombreCasesBordure(this, grille);
		
			Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
			for (int i = 0; i < grille.largeur; i++){
				for (int j = 0; j < grille.hauteur; j++){
					copyPlateau[i][j] = new Case(i,j);
				}
			}	
		
			for(int L=0 ; L<grille.hauteur ; L++){
				for (int H=0; H<grille.largeur;H++){
					copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
					copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
				}
			}
		
			int copyScore = this.score;
		
			int[] gains= {0,0,0,0,0};
			String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
			String[] listeCouleursPossibles = new String [7-jeu.listeJoueurs.length];
			int j=0;												
			for(int l=0 ; l<6 ; l++){
				for(int z=0 ; z<jeu.listeJoueurs.length ;z++)
				{
					if(jeu.listeJoueurs[z].numero != this.numero)
					{	
						if(!listeCouleurs[l].equals(jeu.listeJoueurs[z].couleur))
						{
							if(j<7-jeu.listeJoueurs.length){
								listeCouleursPossibles[j]=(listeCouleurs[l]);
								j++;
							}
						}
					}
				}
			}
		
		
		int nbAleatoire = (int) (Math.random()*5);
		String couleur =listeCouleursPossibles[nbAleatoire];
		System.out.println(couleur);
		grille.changeCase(this, couleur, jeu);
		joue=false;
		}
	}

	//Joue tour par tour la meilleure solution en 1 coup
	public void joueMeilleureSolution(Grille grille, Jeu jeu, FenetreJeu fen){

		boolean joue = true;
		while (joue){Case[] copyListeCases = new Case[this.listeCases.length];
		for(int w=0; w < this.listeCases.length; w++){
			copyListeCases[w]=this.listeCases[w];
		}

		int casesAccessibles = this.NombreCasesBordure(this, grille);

		Case[][] copyPlateau = new Case[grille.hauteur][grille.largeur];
		for (int i = 0; i < grille.largeur; i++){
			for (int j = 0; j < grille.hauteur; j++){
				copyPlateau[i][j] = new Case(i,j);
			}
		}	

		for(int L=0 ; L<grille.hauteur ; L++){
			for (int H=0; H<grille.largeur;H++){
				copyPlateau[L][H].setCouleur(grille.plateau[L][H].getCouleur());
				copyPlateau[L][H].setJoueur(grille.plateau[L][H].getJoueur());
			}
		}

		int copyScore = this.score;

		int[] gains= {0,0,0,0,0};
		String[] listeCouleurs = {"rouge","orange","vert","bleu","violet","jaune"};	
		String[] listeCouleursPossibles = new String [7-jeu.listeJoueurs.length];
		int j=0;												
		for(int l=0 ; l<6 ; l++){
			for(int z=0 ; z<jeu.listeJoueurs.length ;z++)
			{
				if(jeu.listeJoueurs[z].numero != this.numero)
				{	
					if(!listeCouleurs[l].equals(jeu.listeJoueurs[z].couleur))
					{
						if(j<7-jeu.listeJoueurs.length){
							listeCouleursPossibles[j]=(listeCouleurs[l]);
							j++;
						}
					}
				}
			}
		}
		
		if (this.score < grille.hauteur*grille.largeur/3){
			for(int l=0 ; l<listeCouleursPossibles.length ; l++){
				String choixProvisoire = listeCouleursPossibles[l];
				grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
				int nouveauCasesAccessibles = this.NombreCasesBordure(this, grille);
				int gain = nouveauCasesAccessibles - casesAccessibles;
				gains[l]=gain;	;

				for(int L=0 ; L<grille.hauteur ; L++){
					for (int H=0; H<grille.largeur;H++){
						grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
						grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
					}
				}

				jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
				jeu.listeJoueurs[this.numero-1].score = copyScore;
			}
		}

		else{
			for(int l=0 ; l<5 ; l++){
				String choixProvisoire = listeCouleursPossibles[l];
				grille.changeCase(jeu.listeJoueurs[this.numero-1], choixProvisoire, jeu);	
				int gain = this.score - copyScore;
				gains[l]=gain;	

				for(int L=0 ; L<grille.hauteur ; L++){
					for (int H=0; H<grille.largeur;H++){
						grille.plateau[L][H].setCouleur(copyPlateau[L][H].getCouleur());
						grille.plateau[L][H].setJoueur(copyPlateau[L][H].getJoueur());
					}
				}

				jeu.listeJoueurs[this.numero-1].listeCases=copyListeCases;
				jeu.listeJoueurs[this.numero-1].score = copyScore;
			}
		}
		
		int max= 0;
		int maxIndex=0;
		for (int i = 0; i < gains.length; i++){
			int newmax = gains[i];
			if (newmax > max){
				max=newmax;
				maxIndex = i;
			}}
		
		String couleur =listeCouleursPossibles[maxIndex];
		System.out.println("A ce tour il etait preferable de jouer");
		System.out.println(couleur);		
		grille.changeCase(this, couleur, jeu);
		joue = false;
		}
	}	
}


