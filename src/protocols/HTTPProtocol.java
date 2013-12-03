package protocols;

import honeyLIP.Protocol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Pattern;

public class HTTPProtocol implements Protocol {

	private final static long NUM_MILLI_SECONDS = 10000000;

	public Vector<String> processInput(String msg) {

		if (msg == null) {
			try {
				return vectorFromString("HTTP/1.1 200 OK \n "
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: open \n"

						+ "Server: Apache/1.2.21 \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "Last-Modified: "
						+ changeDateFormat(new Date(new Date().getTime()
								- NUM_MILLI_SECONDS))
						+ "\n"
						+ "<html><body><h1>It works!</h1> \n"
						+ "<p>This is the default web page for this server.</p> \n"
						+ "<p>The web server software is running but no content has been added, yet.</p> \n"
						+ "</body></html>");

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String lineOne = msg.split("\\r?\\n")[0];
		String lineOneWords[] = lineOne.split(" ");
		if (msg != null
				&& (Pattern.matches("^GET.*", msg) || Pattern.matches(
						"^POST.*", msg)) && lineOneWords[1].equals("\\")) {
			try {
				return vectorFromString("HTTP/1.1 200 OK \n"
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: open \n"
						+ "Server: Apache/1.2.21 \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "Last-Modified: "
						+ changeDateFormat(new Date(new Date().getTime()
								- NUM_MILLI_SECONDS))
						+ "\n\n"
						+ "<html><body><h1>It works!</h1> \n"
						+ "<p>This is the default web page for this server.</p> \n"
						+ "<p>The web server software is running but no content has been added, yet.</p> \n"
						+ "</body></html>");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (msg != null
				&& (Pattern.matches("^GET.*", msg) || Pattern.matches(
						"^POST.*", msg))) {
			try {
				return vectorFromString("HTTP/1.1 404 NotFound \n"
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: Keep-Alive \n"
						+ "Server: Apache/2.2.22 (Ubuntu) \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "\n\n"
						+ "<!DOCTYPE HTML PUBLIC -//IETF//DTD HTML 2.0//EN>"
						+ "<html><head>"
						+ "<title>404 Not Found</title>"
						+ "</head><body>"
						+ "<h1>Not Found</h1>"
						+ "<p>The requested URL " + lineOneWords[1]+" was not found on this server.</p>"
						+ "<hr>"
						+ "<address>Apache/2.2.22 (Ubuntu) Server at localhost Port 80</address>"
						+ "</body></html>");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (msg != null
				&& Pattern.matches("^HEAD.*", msg)) {
			try {
				return vectorFromString("HTTP/1.1 404 NotFound \n"
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: Keep-Alive \n"
						+ "Server: Apache/2.2.22 (Ubuntu) \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "\n\n");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		else if (msg != null
				&& (Pattern.matches("^DELETE.*", msg))) {
			try {
				return vectorFromString("HTTP/1.1 403 Forbidden \n"
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: Keep-Alive \n"
						+ "Server: Apache/2.2.22 (Ubuntu) \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "\n\n"
						+ "<!DOCTYPE HTML PUBLIC -//IETF//DTD HTML 2.0//EN>"
						+ "<html><head>"
						+ "<title>403 Forbidden</title>"
						+ "</head><body>"
						+ "<h1>Forbidden</h1>"
						+ "<p>The requested action was forbidden by the server.</p>"
						+ "<hr>"
						+ "<address>Apache/2.2.22 (Ubuntu) Server at localhost Port 80</address>"
						+ "</body></html>");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (msg != null
				&& (Pattern.matches("^OPTIONS.*", msg))) {
			try {
				return vectorFromString("HTTP/1.1 200 OK \n"
						+ "Date:"
						+ changeDateFormat(new Date())
						+ "\n"
						+ "Connection: Keep-Alive \n"
						+ "Server: Apache/2.2.22 (Ubuntu) \n"
						+ "AcceptRange: bytes \n"
						+ "Content-Type: text/html \n"
						+ "Content-Length: 170 \n"
						+ "Allow: GET, HEAD, PUT"
						+ "\n\n"
						+ "<!DOCTYPE HTML PUBLIC -//IETF//DTD HTML 2.0//EN>"
						+ "<html><head>"
						+ "<title>200 OK</title>"
						+ "</head><body>"
						+ "<h1>Options</h1>"
						+ "<p>Allow: GET, HEAD, PUT.</p>"
						+ "<hr>"
						+ "<address>Apache/2.2.22 (Ubuntu) Server at localhost Port 80</address>"
						+ "</body></html>");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public String whoTalksFirst() {
		return "Client";
	}

	public String toString() {
		return "HTTP";
	}

	public Vector<String> vectorFromString(String string) {
		Vector<String> vector = new Vector<String>();
		vector.add(string);
		return vector;

	}

	@Override
	public int getPort() {
		return 80;
	}

	@Override
	public boolean isConnectionOver() {
		return true;
	}

	public String changeDateFormat(Date date) throws ParseException {
		String resultDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")
				.format(date);
		return resultDate;
	}
}
