package honeyGUI;

import honeyLIP.HoneyLIPThreads;
import honeyLIP.Module;
import honeyLIP.Protocol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import database.DatabaseConnection;

class ModuleGUI extends JPanel implements ActionListener{


	JLabel labelModule ;
	JButton startButton;
	JButton stopButton;
	JLabel actionLabel;
	Protocol protocol;
	HoneyLIPThreads threads;
	Module module;
	
	public ModuleGUI(Protocol protocol){
		
		this.protocol = protocol;
		
		labelModule = new JLabel(protocol.toString());
		labelModule.setHorizontalAlignment(JLabel.CENTER);
		labelModule.setLocation(0, 100);
		GridLayout layout = new GridLayout(0, 1);
		
		setLayout(layout);
		
		startButton = new JButton("Start "+protocol.toString());
		startButton.setActionCommand("start");
		startButton.setPreferredSize(new Dimension(120,40));
		startButton.setBackground(Color.YELLOW);
		startButton.addActionListener(this);
		startButton.setEnabled(true);
		
		stopButton = new JButton("Stop "+protocol.toString());
		stopButton.setActionCommand("stop");
		stopButton.setPreferredSize(new Dimension(120,40));
		stopButton.setBackground(Color.YELLOW);
		stopButton.addActionListener(this);
		stopButton.setEnabled(false);
		
		JPanel buttons = new JPanel();
		buttons.add(startButton);
		buttons.add(stopButton);
		
		actionLabel = new JLabel("Not listening");
		actionLabel.setHorizontalAlignment(JLabel.CENTER);
		
		
		
		add(labelModule,"push, align center");
		add(buttons);
		add(actionLabel,"push, align center");
		
		
		
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if("start".equals(e.getActionCommand())) { //start button clicked
			stopButton.setEnabled(true);
			startButton.setEnabled(false);
			setBackground(Color.GREEN);
			actionLabel.setText("Listening");
			try{
			module = new Module(this.protocol,this.protocol.getPort());
			}
			catch(Exception exc){
				exc.printStackTrace();
			}
			if(threads==null){
				threads = new HoneyLIPThreads();		
			}
			threads.startModule(module);
			
		}
		if("stop".equals(e.getActionCommand())){
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			setBackground(UIManager.getColor("Panel.background"));
			actionLabel.setText("Not Listening");
			module.setListening(false);
		}
		
	}
	
	
}
