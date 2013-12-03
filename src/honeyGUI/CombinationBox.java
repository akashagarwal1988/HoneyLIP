package honeyGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import database.DatabaseConnection;

public class CombinationBox extends JPanel implements ActionListener{

	JLabel label;
	JComboBox protocolList;
	String choice = "FTP";
	JButton button;
	
	public CombinationBox(){
		label = new JLabel("Get reports");
		String[] protocols = { "FTP", "IRC", "HTTP", "SMTP"};

		//Create the combo box, select item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		JComboBox protocolList = new JComboBox(protocols);
		protocolList.setSelectedIndex(0);
		protocolList.addActionListener(this);
		button = new JButton("Go");
		button.setActionCommand("go");
		button.addActionListener(this);
		
		add(label);
		add(protocolList);
		add(button);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("go") && choice!=null){
			try {
				DatabaseConnection.queryforModule(choice);
				JOptionPane.showMessageDialog(null, "Report generated and saved in the home directory of this project");

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("go") && choice ==null){
			JOptionPane.showMessageDialog(null, "Please select one of the options from the dropdown");
		}
		

		
		else{
		 JComboBox cb = (JComboBox)e.getSource();
	        choice = (String)cb.getSelectedItem();
	       
		}
		
		
	}

}
