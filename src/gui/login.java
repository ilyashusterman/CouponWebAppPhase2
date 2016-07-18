package gui;

import javax.swing.*;

import exceptions.CouponSystemException;
import facade.ClientType;
import facadedb.AdminFacadeDB;
import facadedb.CompanyFacadeDB;
import facadedb.CustomerFacadeDB;
//import musicPlay.PlayMusic;
import system.CouponSystem;
import test.TestDB;

import java.awt.event.*;
import java.io.*;
import java.util.*;

public class login extends JFrame {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  JButton blogin;
  JPanel loginpanel;
  JTextField txuser;
  JTextField pass;
  JButton newUSer;
  JLabel username;
  JLabel password;
  JLabel clientType;
  public static CouponSystem couponSys;
  public static boolean inition=false;

  public login(){
    super("Login Autentification Coupon System");
    try {
		couponSys=CouponSystem.getInstance();
	} catch (CouponSystemException e) {
		new ErrorHandle().showEventDemo(e.getMessage());  
	}
    if (!inition) {
    	try {
			new TestDB(couponSys);
		} catch (CouponSystemException e1) {
			new ErrorHandle().showEventDemo(e1.getMessage());  
		}
    	inition=true;
	}
    blogin = new JButton("Login");
    loginpanel = new JPanel();
    txuser = new JTextField(15);
    pass = new JPasswordField(15);
    newUSer = new JButton("Close");
    username = new JLabel("UserName : ");
    password = new JLabel("Password : ");
    clientType = new JLabel("ClientType : ");
    JRadioButton adminClient = new JRadioButton("Admin");
    JRadioButton companyClient = new JRadioButton("Company");
    JRadioButton customerClient = new JRadioButton("Customer");
    ButtonGroup clientTypes = new ButtonGroup();
    clientTypes.add(customerClient);
    clientTypes.add(companyClient);
    clientTypes.add(adminClient);
    this.add(customerClient);
    this.add(companyClient);
    this.add(adminClient);
    adminClient.setSelected(true);
    companyClient.setSelected(true);
    customerClient.setSelected(true);

    setSize(350,270);
    setLocation(500,280);
    loginpanel.setLayout (null); 

    adminClient.setBounds(90,98,80,20);
    companyClient.setBounds(150,98,80,20);
    customerClient.setBounds(230,98,90,20);
    txuser.setBounds(100,30,150,20);
    pass.setBounds(100,65,150,20);
    blogin.setBounds(110,150,80,20);
    newUSer.setBounds(110,185,80,20);
    username.setBounds(20,28,80,20);
    password.setBounds(20,63,80,20);
    clientType.setBounds(20,98,80,20);

    loginpanel.add(blogin);
    loginpanel.add(txuser);
    loginpanel.add(pass);
    loginpanel.add(newUSer);
    loginpanel.add(username);
    loginpanel.add(password);
    loginpanel.add(clientType);

    getContentPane().add(loginpanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    Writer writer = null;
    File check = new File("userPass.txt");
    if(check.exists()){

      //Checks if the file exists. will not add anything if the file does exist.
    }else{
      try{
        File texting = new File("userPass.txt");
        writer = new BufferedWriter(new FileWriter(texting));
        writer.write("message");
      }catch(IOException e){
    	  new ErrorHandle().showEventDemo(e.getMessage());  
      }
    }




    blogin.addActionListener(new ActionListener() {
      @SuppressWarnings("resource")
	public void actionPerformed(ActionEvent e) {
        try {
          File file = new File("userPass.txt");
          new Scanner(file);;
          new FileWriter(file, true);


          String puname = txuser.getText();
          String ppaswd = pass.getText();



            
            if(customerClient.isSelected()) {
				CustomerFacadeDB customer = (CustomerFacadeDB) couponSys.login(puname, ppaswd, ClientType.CUSTOMER);
				if (customer!=null) {
					System.out.println("Login approved");
					new CustomerFrame(customer);
//					new PlayMusic();
			        dispose();
				}
				else {
	            	
	            	JOptionPane.showMessageDialog(null,"Wrong Username / Password");
	            	txuser.setText("");
	            	pass.setText("");
	            	txuser.requestFocus();
	            }
						}
            else if (companyClient.isSelected()) {

							CompanyFacadeDB company = (CompanyFacadeDB) couponSys.login(puname, ppaswd, ClientType.COMPANY);
							if (company!=null) {
								System.out.println("Login approved");
								new CompanyFrame(company);
						        dispose();
							}
							else {
				            	
				            	JOptionPane.showMessageDialog(null,"Wrong Username / Password");
				            	txuser.setText("");
				            	pass.setText("");
				            	txuser.requestFocus();
				            }
						}
            else if (adminClient.isSelected()) {
							AdminFacadeDB admin = (AdminFacadeDB) couponSys.login(puname, ppaswd, ClientType.ADMIN);
							if (admin != null) {
								System.out.println("Login approved");
								  new AdminFrame(admin);
						        dispose();
							}
							else {
				            	
				            	JOptionPane.showMessageDialog(null,"Wrong Username / Password");
				            	txuser.setText("");
				            	pass.setText("");
				            	txuser.requestFocus();
				            }
						} 
            else if(puname.equals("") && ppaswd.equals("")){
            	JOptionPane.showMessageDialog(null,"Please insert Username and Password");
            }
          
        } catch (IOException | CouponSystemException d) {
        	new ErrorHandle().showEventDemo(d.getMessage());  
        }

      }
    });

    newUSer.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
       try {
		couponSys.shutdown();
		dispose();
	} catch (CouponSystemException e1) {
		new ErrorHandle().showEventDemo(e1.getMessage());  
	}

      }
    });
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

}