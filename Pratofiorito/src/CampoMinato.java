//progetto di cristian cosentino corso ing info anno 2018 matricola numero 190047


import java.util.Scanner;

public class CampoMinato {
	
	private int[][]campo;
	private int[][]stato;
	private int mineTotali;
	private int bandierine;
	private int scoperte;
	
	public CampoMinato(int r, int mine) {
		stato = new int[r][r];
		campo = new int[r][r];
		bandierine=0;
		mineTotali=mine;
		generaCampo();
	}
	
	
	public CampoMinato() {
		this(9,10);
	}
	
	
	
	
	private void generaCampo() {
		int mine=mineTotali ; 
		
		for(;mine>0;mine-- ) {
			int caselle= (int)Math.pow(campo.length,2);//cosi prendiamo la quantita di caselle che ci sono all'interno pero ci dobbiamo ricordare che le caselle partono da 0
			int pos=(int)(Math.random()*(caselle-1));
			int posC=posizioneC(pos);
			int posR=posizioneR(pos);
			if (campo[posR][posC]!= -1) {
				campo[posR][posC]=-1;
				valoriVicini(posR,posC);
				
			   }
			else mine++;
			}
		}
	
	
	private void valoriVicini(int r, int c) {
		for(int i=r-1;i<r+2;i++) {
			for(int j=c-1; j<c+2;j++) {
				if(dentroM(i,j)) {
					if(campo[i][j] != -1)
						campo[i][j]+=1;
					
				}
			}
		}
	}
	
	private boolean dentroM(int i,int j) {//sto verificando che la cella è nella matrice 
		if(i>=0 && i<campo.length && j>=0 && j<campo.length) {
			return true;
		}
		return false;
	}
		
	public void fissaBandiera(int r, int c) {
		stato[r][c]=2;
		bandierine++;
		
	}
	
	public void togliBandiera(int r, int c) {
		stato[r][c]=0;
		bandierine--;
		
	}
	
   private int posizioneC(int c) {
		
		int pos=(int)(c/campo.length);
		
		return pos;
	}
	
	private int posizioneR(int r) {
		
		int pos=(int)(r%campo.length);
		
		return pos;
	}
	
	
	

public boolean scopri(int r, int c) {
		
		if(stato[r][c]==1) {
			return true;
		}
		
		if(campo[r][c]== -1) {
			stato[r][c]=1;
			return false; 
			
		}
		
		if(campo[r][c]==0) {
			
			Pila p = new Pila();
			p.push(progressivo(r,c));
			while(!p.empty()) {
				int pos = p.pop();
				int posR = posizioneR(pos);
				int posC = posizioneC(pos);
				
				if(stato[posR][posC]==0 && campo[posR][posC]==0) {
					stato[posR][posC]=1;
					scoperte++;
				}
				
				
				
				
				
				for(int i=posR-1; i<=posR+1; i++) {
					for(int j=posC-1;j<=posC+1; j++) {
						if(dentroM(i,j)) {
							if(stato[i][j]==0 && campo[i][j]==0) {
							int prog = progressivo(i,j);
							p.push(prog);
							}
							
							if(stato[i][j]==0 && campo[i][j]!=-1) {
								stato[i][j]=1;
								scoperte++;
							}
						}
						
					}
				}
				
			} 
			
		}
		else {
			stato[r][c]=1;
			scoperte++;
		}
		return true;
	}
	
	
	
	
	
	private int progressivo(int r, int c) {
		
	    return r+c*campo.length;
	    
	}
	
	public boolean vittoria() {
		int vit=(int)((Math.pow(campo.length, 2))- mineTotali);
		if (vit== scoperte) {
			return true;
			
		}
		return false ;
	}
	
	public void disegnaCampo() {
			System.out.print(" ");
			for(int j=0; j<stato.length-1; j++) {
				System.out.print(" _ ");
			}
			System.out.println(" _ ");
			for(int i=0; i<stato.length; i++) {
				System.out.print("|");
				for(int j=0; j<stato.length;j++) {
					if(stato[i][j]==1) { 
						
						if(campo[i][j]==-1) {
							System.out.print(" x ");
						}
						else{
							int a = campo[i][j];
							System.out.print(" "+a+" ");
						}
						
					}
					if(stato[i][j]==0) {
						System.out.print(" . ");
					}
					
					if(stato[i][j]==2) { 
						System.out.print(" P ");
					}
					
					
				}
				System.out.println("|");
			}
			System.out.print(" ");
			for(int j=0; j<stato.length-1; j++) {
				System.out.print(" - ");
			}
			System.out.println(" -");
			System.out.println("");
		} 
	
	public static void gioca() {
		Scanner sc= new Scanner(System.in);
		
		System.out.println("salve ora potrai provare una nuova esperienza di campo minato inseriscimi una dimenzione e creero un campo con dimesioni a tua scelta: ");
		int dimensione= sc.nextInt();
		
		System.out.println("inserisci un numero di mine:");
		int mine=sc.nextInt();
		
		CampoMinato tabella= new CampoMinato(dimensione,mine);
		
		boolean perso=false,vinto=false;
		
		while(!perso && !vinto) {
			tabella.disegnaCampo();
			
			System.out.println("scegli una riga che vuoi designiare con un oggetto ricorda che partono da 0");
			int riga=sc.nextInt();
			System.out.println("scegli una  colonna che  vuoi designiare con un oggetto ricorda che partono da 0");
			int colonna=sc.nextInt();
			
			System.out.println("se vuoi liberare una casella clicca 0,se vuoi inserire una bandierina clicca 1, se vuoi toglirla 2");
			int comando=sc.nextInt();
			
			if(comando==0) {
				boolean controllo=tabella.scopri(riga, colonna);
				if(controllo==false) {
					perso=true;
					
					
				}
				if(tabella.vittoria()) {
					vinto=true;
				}
				
				
			}
			if(comando==1) {
				tabella.fissaBandiera(riga,colonna);
			}
			if(comando==2) {
				tabella.togliBandiera(riga,colonna);
			}
			if(comando!=0 && comando!=1 && comando!=2) {
				System.out.println("scelta sbagliata");
			}
			
			if(vinto) {
				
				tabella.disegnaCampo();
				System.out.println("Hai vinto!");
				
			}
			
			if(perso) {
				tabella.disegnaCampo();
				System.out.println("Hai perso.");
			}
			
		}
			
			sc.close();

		
			
			
	
		
		
		}
		
		public static void main(String[]args) {
			gioca();
		}
		
		
		
		
	}
	
		

	
	

