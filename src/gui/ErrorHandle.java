package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ErrorHandle {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public ErrorHandle(){
      prepareGUI();
   }

      
   private void prepareGUI(){
      mainFrame = new JFrame("Error message!");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));

      headerLabel = new JLabel("",JLabel.CENTER );
      statusLabel = new JLabel("",JLabel.CENTER);        

      statusLabel.setSize(350,100);
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   void showEventDemo(String errorMassage){
      headerLabel.setText(errorMassage); 
      JButton okButton = new JButton("OK");
      okButton.setActionCommand("OK");
      okButton.addActionListener(new ButtonClickListener()); 
      controlPanel.add(okButton);
      mainFrame.setVisible(true);  
   }

   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if( command.equals( "OK" ))  {
        	 mainFrame.dispose();
         }
      }		
   }
   
}
