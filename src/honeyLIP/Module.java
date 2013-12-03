package honeyLIP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;

import database.DatabaseConnection;

import stix.Identity;
import stix.Record;
import stix.ThreatActor;

public class Module implements Runnable {

	static final int DEFAULT_TIME_OUT_MSEC = 100000000;

	private Protocol protocol;
	Record record;
	private int port;
	private boolean listening;

	public Module(Protocol protocol, int port) {
		this.protocol = protocol;
		this.port = port;
		this.record = new Record(protocol.toString());
		this.listening = true;

	}

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isListening() {
		return listening;
	}

	public void setListening(boolean listening) {
		this.listening = listening;
	}

	public void run() {

		PrintWriter out = null;
		BufferedReader in = null;
		String inputLine; // temporary strings
		List<String> outputLines;

		while (this.listening == true) {
			try {
				
				ServerSocket socket = new ServerSocket();
				socket.setReuseAddress(true);
				socket.bind(new InetSocketAddress(this.port));
				Socket clientSocket = socket.accept();
				InetAddress ipAddressClient = clientSocket.getInetAddress();
				
				int remotePort = clientSocket.getPort();
				
				ThreatActor threatActor = new ThreatActor();
				Identity identity = new Identity(
						ipAddressClient.getHostAddress(), new Integer(
								remotePort).toString());
				threatActor.setIdentity(identity);
				this.record.setThreatActor(threatActor);
				this.record.setTimeStamp(new Date());
				this.record.setModuleName(this.protocol.toString());
				if (clientSocket != null) {
					InputStream inputStream = clientSocket.getInputStream();
					out = new PrintWriter(clientSocket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(
							inputStream));
					String whoTalksFirst = this.protocol.whoTalksFirst();
					switch (whoTalksFirst) {

					case "Client":
						inputLine = in.readLine();

						outputLines = this.protocol.processInput(inputLine);
						for (String outputLine : outputLines) {
							if (outputLine != null) {
								out.println(outputLine);
							}
						}
						if (this.record.getThreatActor().getActivity() == null) {
							this.record.getThreatActor().setActivity(
									outputLines);
						} else {
							this.record.getThreatActor().getActivity()
									.addAll(outputLines);

						}
						break;
					case "Server":
						break;
					}
					
					while ((new Date().getTime()
							- (record.getTimeStamp().getTime()) <= Module.DEFAULT_TIME_OUT_MSEC)
							&& (inputLine = in.readLine()) != null) { // loop
																		// recieving
																		// messages
						this.record.getThreatActor().getActivity()
								.add(inputLine); // log the rcvd message

						// parse and reply
						outputLines = this.protocol.processInput(inputLine);
						for (String outputLine : outputLines) {
							if (outputLine != null) {
								out.println(outputLine);
								
							}
						}
						if (this.record.getThreatActor().getActivity() == null) {
							this.record.getThreatActor().setActivity(
									outputLines);
						} else {
							this.record.getThreatActor().getActivity()
									.addAll(outputLines);

						}
						
						
						
						if (this.protocol.isConnectionOver()){ 
							break;				
						}
															
					}
					DatabaseConnection.insertRecord(record);
					socket.close();

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

}
