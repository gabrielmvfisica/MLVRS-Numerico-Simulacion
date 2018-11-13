/*Clase para establecer el marco de la pantalla necesario para que Java pueda abrir un frame
 */

package lotkaresetting;

import javax.swing.*;

import java.awt.*;
import java.net.*;

public class Marco extends JFrame{

	String title="SLVM Simulator";
	public Marco(){
		
		//Toolkit mipantalla =Toolkit.getDefaultToolkit();
		URL rutaMono=getClass().getResource("monkey.jpg");
		ImageIcon miIcono=new ImageIcon(rutaMono);
		
		//Image miIcono=mipantalla.getImage("src/graficos/monkey.jpg");
		setIconImage(miIcono.getImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setSize(500,500);
		//setLocation(anchoPantalla/2,alturaPantalla/2);
		
	}
}

