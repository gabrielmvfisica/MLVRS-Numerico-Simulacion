/*Programa Stochastic Lotka-Volterra Model Simulator. Este programa simula la interacción entre dos especies (Depredador-Presa)
 * siguiendo la dinámica de agentes inspirado en el modelo propuesto por Mobilia et al. (2007), donde los depredadores
 * pueden moverse libremente según una distribución de Ley de potencias con exponente $\beta$. En este programa de simulación,
 * los depredadores retornan al parche a una tasa $\r$, mueren con probabilidad $\mu$ y se reproducen con probabilidad $\lambda$,
 * sólo si han conseguido encontrar una presa. Las presas están contenidas en un parche circular de radio $\R$ y se reproducen
 * a una tasa $\sigma$.
 */

package lotkaresetting;
//Importamos las librerías necesarias
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.Math;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.security.SecureRandom;
import java.text.NumberFormat;

//Definimos la clase SimulationModel
public class SimulationModel extends JPanel {
		
		// Establecemos las variables de movilidad y demográficas
		private double r, beta, mu, lambda, sigma;
		//Establecemos dos variables auxiliares para la posición de los depredadores
		private int radio = 0; 
		private double theta = 0,radio2=0;
		//Definimos posiciones y tamaño del parche, así como el zoom que tendremos en la pantalla al visualizar el sistema
		private int rpatch, xpatch, ypatch,xcpatch,ycpatch,zoom;
		// xd[i],yd[i]= posiciones del i-esimo depredador
		private int[] xd = new int[100000];
		private int[] yd = new int[100000];
		// xp[i],yp[i]= posiciones de la i-esima presa
		private int[] xp = new int[100000];
		private int[] yp = new int[100000];
		//variable aleatoria auxiliar como semilla para todos los valores aleatorios que utilicemos
		SecureRandom rand = new SecureRandom();
		// depin=numero de depredadores inicial, prein=numero de presas inicial y Kcapacity=capacidad de carga y una variable
		//auxiliar llamada preyseed
		private int depin,prein,Kcapacity,preyseed;
		//variable de tiempo
		private int time=0;
		
		private final double pi = Math.PI;
	    
		//Definimos el objeto SimulationModel
		public SimulationModel(double beta, double r, double lambda, double mu, double sigma, int prein, 
				int depin, int alturaPantalla, int anchoPantalla,int patch, int zoom){
	        super(new BorderLayout());
			this.beta=beta;
			this.r=r;
			this.lambda=lambda;
			this.mu=mu;
			this.sigma=sigma;
			this.prein=prein;
			this.depin=depin;
			rpatch=patch;
			this.zoom=zoom;
			
			Kcapacity=(int) (pi*(rpatch)*(rpatch));
			
			if(this.prein >= Kcapacity){
				this.prein=Kcapacity;
			}
			
			//Definimos el tamaño del parche y su posición (mostrar siempre en el centro de la pantalla)
			xpatch=anchoPantalla/(2*zoom)-rpatch;
			ypatch=alturaPantalla/(2*zoom)-rpatch;
			xcpatch=xpatch+rpatch;
			ycpatch=ypatch+rpatch;
			
			/*Establecemos las posiciones aleatorias (con distribución uniforme) iniciales de las presas.
			Dado que en este modelo tomamos en cuenta la competencia espacial entre las presas, checamos que no ocupen
			el mismo lugar. Para esto, plantamos 10 presas "semilla" que se reproducen instantaneamente hasta llegar 
			a la población de presas inicial*/
			preyseed=10;
			
			for (int i = 1; i <= preyseed; i++) {
				radio= rand.nextInt(rpatch);
				theta = rand.nextDouble()*2*pi;
				
				xp[i] = (int) (xcpatch + radio*Math.cos(theta));
				yp[i] = (int) (ycpatch + radio*Math.sin(theta));
			}
			
			while(preyseed<=this.prein){
				int k=0;
				for (int i=1; i<=preyseed;i++){
				 	int[][] flat = new int[3][3];
					int nrep=0;
						flat=neighbour(i,preyseed+k);
					int xprad=0, yprad=0;
					double radpre=0;
					
				for(int j=0;j<=2;j++){
					for(int l=0;l<=2;l++){
						if(flat[j][l]==0 && nrep==0){
							
							xprad=xp[i]+l-1-xcpatch;
							yprad=yp[i]+j-1-ycpatch;
							radpre=Math.sqrt(xprad*xprad+yprad*yprad);
							
							if(radpre<=rpatch){
								k++;
								if(preyseed+k>this.prein){
									break;
								}{
								xp[preyseed+k]=xp[i]+l-1;
								yp[preyseed+k]=yp[i]+j-1;
								nrep++;}
								}
							}
						}
					}
				}
				preyseed=preyseed+k;
			}
			
			//Establecemos las posiciones aleatorias (con distribución uniforme) iniciales de los depredadores
			for (int i = 1; i <= this.depin; i++) {
				radio= rand.nextInt(rpatch);
				theta = rand.nextDouble()*2*pi;
				
				xd[i] = (int) (xcpatch + radio*Math.cos(theta));
				yd[i] = (int) (ycpatch + radio*Math.sin(theta));
			}

		}

		//Definimos el método para mover al depredador
		public void movePredator() {
			time++;
			for (int i = 1; i <= depin; i++) {
				
				if(rand.nextDouble()<=r){
					radio= rand.nextInt(rpatch);
					theta = rand.nextDouble()*2*pi;
					
					xd[i] = (int) (xcpatch + radio*Math.cos(theta));
					yd[i] = (int) (ycpatch + radio*Math.sin(theta));
					//xd[i] = xcpatch;
					//yd[i] = ycpatch;
				}
				else{
					double al=0;
					while(al==0){
						al=rand.nextDouble();
					}
				radio2 =1*Math.pow(al, -1/(beta - 1));
				theta = rand.nextDouble()*2*pi;
				
				xd[i] = xd[i] + (int) (radio2*Math.cos(theta));
				yd[i] = yd[i] + (int) (radio2*Math.sin(theta));
				}
			}
			
		}

		//Método para reproducir a los depredadores
		public void reproduceDep() {
			int k = 0;

			for (int i = 1; i <= depin; i++) {
					for (int j = 1; j <= prein; j++) {
						int xrad=xd[i]-xpatch;
						int yrad=yd[i]-ypatch;
						radio=(int) (Math.sqrt(xrad*xrad+yrad*yrad));
						if(radio > rpatch+2){

						if (xd[i] == xp[j] && yd[i] == yp[j]) {
							
							if (rand.nextDouble()< lambda) {
								k++;
								xd[depin + k] = xd[i];
								yd[depin + k] = yd[i];
							}
							for (int l = j; l < prein; l++) {
								xp[l]=xp[l+1];
								yp[l]=yp[l+1];
							}
							prein--;
						}
					}
				}
			}
			depin = depin + k;
		}

		//Método para la mortandad de depredadores
		public void mortalityPredator(){
			int k=0;
			for (int i = 1; i <= depin; i++) {

				if (rand.nextDouble() < mu) {
					for (int l = i-k; l <= depin; l++) {
						xd[l] = xd[l + 1];
						yd[l] = yd[l + 1];
						
					}
					k++;
				}
			}
			depin=depin-k;
		}
		
		//Método para reproducir a las presas
		public void reproducePrey(){
		int k=0;

	for (int i=1; i<=prein;i++){
	 	int[][] flat = new int[3][3];
		int nrep=0;
	 	
		if (rand.nextDouble() < sigma){
			flat=neighbour(i,prein+k);
		int xprad=0, yprad=0;
		double radpre=0;
		
	for(int j=0;j<=2;j++){
		for(int l=0;l<=2;l++){
			if(flat[j][l]==0 && nrep==0){
				
				xprad=xp[i]+l-1-xcpatch;
				yprad=yp[i]+j-1-ycpatch;
				radpre=Math.sqrt(xprad*xprad+yprad*yprad);
				
				if(radpre<=rpatch){
					k++;
					xp[prein+k]=xp[i]+l-1;
					yp[prein+k]=yp[i]+j-1;
					nrep++;
					}
				
				}
			}
		}


		}
	  }


	prein=prein+k;


	}
		
		//Método para saber si hay vecinos alrededor de las presas 
		public int[][] neighbour(int i,int prey){
		 	int[][] flat = new int[3][3];
			
			for(int j=1; j<=prey; j++){
				
			int dif=xp[j]-xp[i];
			int dif2=yp[j]-yp[i];
			
			switch(dif2){
			 case -1:
				switch(dif){	
			 	case -1: 
			 		flat[0][0]=1;
					break;
			 	case 0: 
			 		flat[0][1]=1;
					break;
				case 1: 
					flat[0][2]=1;
					break;
			  	}
				break;
			 case 0:
				switch(dif){	
			 	case -1: 
			 		flat[1][0]=1;
					break;
			 	case 0: 
			 		flat[1][1]=1;
					break;
				case 1: 
					flat[1][2]=1;
					break;
			  	}
				break;
			 case 1:
				switch(dif){	
			 	case -1: 
			 		flat[2][0]=1;
					break;
			 	case 0: 
			 		flat[2][1]=1;
					break;
				case 1: 
					flat[2][2]=1;
					break;
			  	}
				break;
				
			 default:
				 flat[1][1]=1;
			  }
			 }	
			
			for(int j=1; j<=depin; j++){
				
				int dif=xp[j]-xp[i];
				int dif2=yp[j]-yp[i];
				
				switch(dif2){
				 case -1:
					switch(dif){	
				 	case -1: 
				 		flat[0][0]=1;
						break;
				 	case 0: 
				 		flat[0][1]=1;
						break;
					case 1: 
						flat[0][2]=1;
						break;
				  	}
					break;
				 case 0:
					switch(dif){	
				 	case -1: 
				 		flat[1][0]=1;
						break;
				 	case 0: 
				 		flat[1][1]=1;
						break;
					case 1: 
						flat[1][2]=1;
						break;
				  	}
					break;
				 case 1:
					switch(dif){	
				 	case -1: 
				 		flat[2][0]=1;
						break;
				 	case 0: 
				 		flat[2][1]=1;
						break;
					case 1: 
						flat[2][2]=1;
						break;
				  	}
					break;
					
				 default:
					 flat[1][1]=1;
				  }
				 }	
			
			return flat;
		}

		//Método para obtener la población total de depredadores y presas después de un tiempo de montecarlo
		public int[] getPrePop(){
			int[] pobtime=new int[3];
			pobtime[0]=time;
			pobtime[1]=depin;
			pobtime[2]=prein;
			
			return pobtime;
		}
		
		//Método para graficar la dinámica en tiempo real
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d=(Graphics2D) g;
			g2d.drawString("beta= "+String.format("%1$,.2f",beta)+"   r= "+String.format("%1$,.2f",r)+"  lambda= "+
					String.format("%1$,.4f",lambda)+"   mu= "+String.format("%1$,.4f",mu)+"   sigma= "+String.format("%1$,.2f",sigma)+"   Patch radio= "+rpatch,70,20);
			g2d.drawString("Time: "+time+"  Predators: "+depin+"   Preys: "+prein,70,40);
			g2d.scale(zoom,zoom);
			g2d.setColor(Color.GREEN);
			g2d.fill(new Ellipse2D.Double(xpatch+.35, ypatch+.35, 2*rpatch, 2*rpatch));
			g2d.setColor(Color.BLACK);
			
			for (int i = 1; i <= prein; i++) {
				g2d.setColor(Color.BLUE);
				g2d.fill(new Ellipse2D.Double(xp[i], yp[i], .7, .7));
			}
			for (int i = 1; i <= depin; i++) {
				g2d.setColor(Color.RED);
				g2d.fill(new Ellipse2D.Double(xd[i], yd[i], .7, .7));
			}
		 }
		
	}
