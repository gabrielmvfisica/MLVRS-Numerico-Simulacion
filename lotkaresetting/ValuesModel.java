/*Se definen los parámetros iniciales, los cuales se pueden modificar desde la barra de menú.  
 */
package lotkaresetting;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.*;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ValuesModel extends JPanel {
    //Values for the fields
    private double beta=3;
    private double r=0.0;
    private double lambda=.1;
    private double mu=0.01;
    private double sigma=.1;
    private int pre=700;
    private int dep=100;
    private int toTime=2000;
    private int patch=15;
    private int zoom=10;

    //Labels to identify the fields
    private JLabel betaLabel;
    private JLabel rLabel;
    private JLabel lambdaLabel;
    private JLabel muLabel;
    private JLabel sigmaLabel;
    private JLabel depLabel;
    private JLabel preLabel;
    private JLabel toTimeLabel;
    private JLabel patchLabel;
    private JLabel zoomLabel;

    //Strings for the labels
    private static String betaString = "Exponent (beta): ";
    private static String resetString = "Reset rate (r): ";
    private static String lambdaString = "Reproduction rate (lambda): ";
    private static String muString = "Mortality rate (mu): ";
    private static String sigmaString = "Regenerate rate (sigma): ";
    private static String preString = "Prey Population: ";
    private static String depString = "Predator Population: ";
    private static String patchString = "Patch radio ";
    private static String toTimeString = "Simulation time: ";
    private static String zoomString = "Simulation zoom: ";

    //Spinner for the parameters
    private JSpinner spin1,spin2,spin3,spin4,spin5,spin6,spin7,spin8,spin9,spin10;
    
    //Button for start simulation
    private JButton accept;
    //Container c=getContentPane();
    private JFrame myframe1;
    
    public ValuesModel(JFrame myframe) {
        super(new BorderLayout());
       	myframe1=myframe;
        Oyente escucha = new Oyente();
        //We create the spinners
        
        spin1 = new JSpinner(new SpinnerNumberModel(beta,1.01,4.0,.1));
	    ((JSpinner.NumberEditor) spin1.getEditor()).getFormat().setMaximumFractionDigits(4);
        spin1.addChangeListener(escucha);
        
        spin2 = new JSpinner(new SpinnerNumberModel(r,0,1,.01));
	    ((JSpinner.NumberEditor) spin2.getEditor()).getFormat().setMaximumFractionDigits(4);
		spin2.addChangeListener(escucha);
		
		spin3 = new JSpinner(new SpinnerNumberModel(lambda,0,1,.01));
	    ((JSpinner.NumberEditor) spin3.getEditor()).getFormat().setMaximumFractionDigits(4);
		spin3.addChangeListener(escucha);
		
		spin4 = new JSpinner(new SpinnerNumberModel(mu,0,1,.01));
	    ((JSpinner.NumberEditor) spin4.getEditor()).getFormat().setMaximumFractionDigits(4);
		spin4.addChangeListener(escucha);
		
		
		spin5 = new JSpinner(new SpinnerNumberModel(sigma,0,1,.01));
	    ((JSpinner.NumberEditor) spin5.getEditor()).getFormat().setMaximumFractionDigits(4);
		spin5.addChangeListener(escucha);
		
		spin6 = new JSpinner(new SpinnerNumberModel(pre,1,50000,50));
		spin6.addChangeListener(escucha);
		
		spin7 = new JSpinner(new SpinnerNumberModel(dep,1,50000,50));
		spin7.addChangeListener(escucha);
		
		spin8 = new JSpinner(new SpinnerNumberModel(toTime,1,100000,500));
		spin8.addChangeListener(escucha);
		
		spin9 = new JSpinner(new SpinnerNumberModel(patch,5,400,5));
		spin9.addChangeListener(escucha);
		
		spin10 = new JSpinner(new SpinnerNumberModel(zoom,1,30,1));
		spin10.addChangeListener(escucha);
		
        //Create the labels.
        betaLabel = new JLabel(betaString);
        rLabel = new JLabel(resetString);
        lambdaLabel = new JLabel(lambdaString);
        muLabel = new JLabel(muString);
        sigmaLabel = new JLabel(sigmaString);
        preLabel = new JLabel(preString);
        depLabel = new JLabel(depString);
        toTimeLabel = new JLabel(toTimeString);
        patchLabel = new JLabel(patchString);
        zoomLabel = new JLabel(zoomString);



        //OyenteBoton escucha2=new OyenteBoton();
		//creamos un boton para iniciar el programa		
		accept=new JButton("Accept");
		accept.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
    		{
        		myframe1.dispose();
    		}
        });
		
        //Tell accessibility tools about label/textfield pairs.
        betaLabel.setLabelFor(spin1);
        rLabel.setLabelFor(spin2);
        lambdaLabel.setLabelFor(spin3);
        muLabel.setLabelFor(spin4);
        sigmaLabel.setLabelFor(spin5);
        preLabel.setLabelFor(spin6);
        depLabel.setLabelFor(spin7);
        toTimeLabel.setLabelFor(spin8);
        patchLabel.setLabelFor(spin9);
        zoomLabel.setLabelFor(spin10);
        
        //Lay out the labels in a panel.
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(betaLabel);
        labelPane.add(rLabel);
        labelPane.add(lambdaLabel);
        labelPane.add(muLabel);
        labelPane.add(sigmaLabel);
        labelPane.add(patchLabel);
        labelPane.add(preLabel);
        labelPane.add(depLabel);
        labelPane.add(toTimeLabel);
        labelPane.add(zoomLabel);
        
       //Layout the Spinner in a panel
        JPanel spinnerPane = new JPanel(new GridLayout(0,1));
        spinnerPane.add(spin1);
        spinnerPane.add(spin2);
        spinnerPane.add(spin3);
        spinnerPane.add(spin4);
        spinnerPane.add(spin5);
        spinnerPane.add(spin9);
        spinnerPane.add(spin6);
        spinnerPane.add(spin7);
        spinnerPane.add(spin8);
        spinnerPane.add(spin10);
        
        //Put the panels in this panel, labels on left,
        //text fields on right.

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(spinnerPane, BorderLayout.LINE_END);
        add(accept,BorderLayout.SOUTH);
    }
    
    private class Oyente implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				Object source = e.getSource();
				if(source==spin1){
				beta = ((Number)spin1.getValue()).doubleValue();
				} else if (source == spin2) {
		            r = ((Number)spin2.getValue()).doubleValue();
		        } else if (source == spin3) {
		            lambda = ((Number)spin3.getValue()).doubleValue();
		        } else if (source == spin4) {
		            mu = ((Number)spin4.getValue()).doubleValue();
		        } else if (source == spin5) {
		           sigma = ((Number)spin5.getValue()).doubleValue();
		        } else if (source == spin6) {
		            pre = ((Number)spin6.getValue()).intValue();
		        } else if (source == spin7) {
		            dep = ((Number)spin7.getValue()).intValue();
		        } else if (source == spin8) {
		            toTime = ((Number)spin8.getValue()).intValue();
		        } else if (source == spin9) {
		            patch = ((Number)spin9.getValue()).intValue();
		        } else if (source == spin10) {
		            zoom = ((Number)spin10.getValue()).intValue();
		        } 
				
			}
    }

    public double[] getValues(){
    	double[] values={beta,r,lambda,mu,sigma,pre,dep,toTime,patch,zoom};
    	return values;
    }
}