package honeyGUI;

import honeyLIP.HoneyLIPThreads;
import honeyLIP.Module;
import honeyLIP.Protocol;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.xml.bind.DataBindingException;

import database.DatabaseConnection;

import protocols.FTPProtocol;
import protocols.HTTPProtocol;
import protocols.IrcProtocol;
import protocols.SMTPProtocol;

public class HoneyGUI extends JFrame implements ActionListener, WindowListener {

	public void makeGUI() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
		UIManager.setLookAndFeel(lookAndFeel);
		List<JPanel> panels;
		ArrayList<String> protocols = new ArrayList<>();
		protocols.add("FTP");
		protocols.add("IRC");
		protocols.add("HTTP");
		protocols.add("SMTP");
		panels = createPanels(protocols);

		GridLayout layout = new GridLayout(0, 2);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(layout);

		for (JPanel panel : panels) {
			mainPanel.add(panel);
		}
		CombinationBox dbPanel = new CombinationBox();
		mainPanel.add(dbPanel);

		setDefaultLookAndFeelDecorated(true);
		addWindowListener(this);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);

	}

	public List<JPanel> createPanels(List<String> protocols) {

		ArrayList<JPanel> panels = new ArrayList<JPanel>();

		for (String protocolName : protocols) {
			Protocol protocol = null;
			if (protocolName.equals("HTTP")) {
				protocol = new HTTPProtocol();
			} else if (protocolName.equals("FTP")) {
				protocol = new FTPProtocol();
			} else if (protocolName.equals("SMTP")) {
				protocol = new SMTPProtocol();
			} else if (protocolName.equals("IRC")) {
				protocol = new IrcProtocol();
			}

			panels.add(new ModuleGUI(protocol));

		}
		return panels;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panel = (JPanel) e.getSource();

	}

	@Override
	public void windowOpened(WindowEvent e) {
		try {

			DatabaseConnection.makeConnection();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void windowClosing(WindowEvent e) {
		DatabaseConnection.mongoClient.close();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
