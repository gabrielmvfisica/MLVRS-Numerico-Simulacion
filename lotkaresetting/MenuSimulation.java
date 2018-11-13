/*Esta clase se utiliza únicamente para visualizar el menú que se muestra en pantalla al abrir el programa SLVM.
 */
package lotkaresetting;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.net.URL;
/*
 * @author José Gabriel Mercado Vásquez
 * @version v1.0
 */

public class MenuSimulation {
	static Marco marco1;
	//static JSplitPane splitPane;
	Marco marco2;
    ValuesModel ap;
    double[] parameters;
    private JButton boton;
    private static JButton boton2;
    static String title= "SLVM Simulator";
    static File file;
    private boolean wfile;
    private static boolean stop;

    Toolkit mipantalla =Toolkit.getDefaultToolkit();
	Dimension tamanoPantalla=mipantalla.getScreenSize();
	
	int alturaPantalla=tamanoPantalla.height;
	int anchoPantalla=tamanoPantalla.width;
	
	URL rutaMono=getClass().getResource("monkey.jpg");
	ImageIcon miIcono=new ImageIcon(rutaMono);
	
    //ImageIcon miIcono= new ImageIcon(mipantalla.getImage("src/graficos/monkey.jpg"));

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        //Create the menu bar.
        menuBar = new JMenuBar();
        wfile=false;
        stop=false;
        
        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Save as",
                                 KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Saving the result of the simulation in a file");
        menuItem.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
    		{
        		JFileChooser chooser = new JFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		    "dat & txt", "dat", "txt");
        		chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(null);
        		if(returnVal == JFileChooser.APPROVE_OPTION) {
        			file = chooser.getSelectedFile();
        			wfile=true;

        		}
    		}
        });
        
        menu.add(menuItem);

        //Build second menu in the menu bar.
        //First, we build the object for ValuesModel
        marco2=new Marco();
    	ap=new ValuesModel(marco2);
    	
        menu = new JMenu("Parameters");
        menu.setMnemonic(KeyEvent.VK_B);
        menu.getAccessibleContext().setAccessibleDescription(
                "Set the parameters of the simulation");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Set Parameters",
                KeyEvent.VK_R); 
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
        		"Change the parameters of the Simulation");
        menuItem.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		 SwingUtilities.invokeLater(new Runnable() {
        	          public void run() {
        	                //Turn off metal's use of bold fonts
        		        UIManager.put("swing.boldMetal", Boolean.FALSE);
        		    	marco2.add(ap);
        		    	marco2.pack();
        		    	marco2.setLocationRelativeTo(null); 
        		    	marco2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        		    	marco2.setTitle("Parameters");
        		    	marco2.setVisible(true);
        		    	boton.setEnabled(true);
        	            }
        	       });
        	 }
        	});

        menu.add(menuItem);

        //About the program
        menu = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_C);
        menu.getAccessibleContext().setAccessibleDescription(
                "Information about the program");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("About SLVM Simulation",
                KeyEvent.VK_U); 
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
        		KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
        		"Change the parameters of the Simulation");
        menuItem.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		Runnable r = new Runnable() {
                    public void run() {
                        String pt1 = "<html><body width='";
                        String pt2 =
                            "'><h1>About SLVM Simulator</h1>" +
                            "<p>SLV Simulator simulates a group of random foragers looking for preys." +
                            " Red circles represent the predators of the system, while the blue circles represent the preys. " +
                            " Green circle represents the patch where the preys are reproduced. " +
                            " Prey can't get out of this patch. "+
                            " Parameters of the system are fixed in the menu option Parameters. "+
                            " To use the program you must set the parameters of the system and execute it with the start button."+
                            " You can save data in a file just open menu option File<br><br>"+
                            "<p>Version v1.1 <br><br>" +
                            "<p>Author José Gabriel Mercado Vásquez";

                        int width = 175;
                        String s = pt1 + width + pt2;

                        JOptionPane.showMessageDialog(null,s,"About "+title,JOptionPane.INFORMATION_MESSAGE,miIcono);
                    }
                };
                SwingUtilities.invokeLater(r);
        	      
        	 }
        	});

        menu.add(menuItem);
        
      //Menu starts
        boton=new JButton ("Start");
        boton.setEnabled(false);
        boton.setMnemonic(KeyEvent.VK_D);
        boton.getAccessibleContext().setAccessibleDescription(
                "Start the simulation");
        boton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		if(wfile==true){
        		parameters=ap.getValues();
        		boton2.setEnabled(true);
        		Runnable miRunnable = new Runnable()
  		      {
  		         public void run()
  		         {
  		            try
  		            {
  		            	
  		            	start(parameters[0],parameters[1],parameters[2],parameters[3],parameters[4],(int)parameters[5],
  		            			(int) parameters[6],(int) parameters[7],(int) parameters[8],(int) parameters[9]);
  		            }
  		            catch (Exception e)
  		            {
  		               e.printStackTrace();
  		            }
  		         }
  		      };
  		      Thread hilo = new Thread (miRunnable);
  		      hilo.start();
        	 }
		      	else{
            	JOptionPane.showMessageDialog(null, "You have to set a data file");
            	}
        	}
        	});
        
        menuBar.add(boton);
        
        //Button stop
        boton2=new JButton ("Stop");
        boton2.setEnabled(false);
        boton2.setMnemonic(KeyEvent.VK_F);
        boton2.getAccessibleContext().setAccessibleDescription("Stop the simulation");
        boton2.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		stop=true;
        	 }
        	});
        
        menuBar.add(boton2);
        return menuBar;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        marco1=new Marco();
        marco1.setSize(500,500);
        //Create and set up the content pane.
        MenuSimulation demo = new MenuSimulation();
        marco1.setJMenuBar(demo.createMenuBar());
		marco1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		marco1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco1.setTitle(title);
		marco1.setVisible(true);
        
    }
    
    public static void start(double beta, double r, double lambda, double mu, double sigma,int pre,
    		int dep, int toTime, int patch, int zoom) {
		int[] pob=new int[3];
		Toolkit mipantalla =Toolkit.getDefaultToolkit();
		Dimension tamanoPantalla=mipantalla.getScreenSize();
		
		int alturaPantalla=tamanoPantalla.height;
		int anchoPantalla=tamanoPantalla.width;
		
		SimulationModel app =new SimulationModel(beta, r, lambda, mu, sigma, pre, dep,
				alturaPantalla,anchoPantalla,patch,zoom);
		
		//Create a split pane with the two scroll panes in it.
		//splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,app, app2);
		//splitPane.setOneTouchExpandable(true);
		//splitPane.setDividerLocation(anchoPantalla/2);
		
		marco1.add(app);
		marco1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		marco1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco1.setVisible(true);
		
		pob=app.getPrePop();
		
		try {
			FileWriter out = new FileWriter(file);
			PrintWriter pout = new PrintWriter(out);
			
			pout.println("//Parameters (beta,r,lambda,mu,sigma: "+beta+" "+r+" "+lambda+" "+mu+" "+sigma);
			pout.println("//(t,depredador,presa)");
			
			while (pob[1]!=0) {
				app.movePredator();
				app.reproduceDep();
				app.reproducePrey();
				app.mortalityPredator();
				app.repaint();
				
				//Escribe en archivo (tiempo,poblacion depredador, poblacion presa) 
				pob=app.getPrePop();
				pout.println(pob[0]+" "+pob[1]+" "+pob[2]);
				
				try {
				    Thread.sleep(10);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				    // handle the exception...        
				    // For example consider calling Thread.currentThread().interrupt(); here.
				}
				
				 if(pob[0]==toTime || stop==true){
					 boton2.setEnabled(false);
					 stop=false;
					 break;
				 }
				 
				 
			} 
			out.close();
		}

			catch(IOException ex){
				ex.printStackTrace();
			    }
		
	}
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
  
}