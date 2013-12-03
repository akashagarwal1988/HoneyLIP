package protocols;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import honeyLIP.Protocol;

public class SMTPProtocol implements Protocol {

	private final static int NO_CONN = 0;
	private final static int READY = 1;
	private final static int KILLED = 2;
	private final static int MAIL_INPUT = 3;

	private int connectionState = NO_CONN;

	@Override
	public String whoTalksFirst() {
		// TODO Auto-generated method stub
		return "Client";
	}

	@Override
	public Vector<String> processInput(String msg) {
		// TODO Auto-generated method stub

		switch (connectionState) {
		case NO_CONN:

			if (msg == null) {
				connectionState = KILLED;
			}
			if (msg != null
					&& (Pattern.matches("^EHLO .*", msg) || Pattern.matches(
							"^HELO .*", msg))) {
				connectionState = READY;
				String networkName = msg.split(" ")[1];
				return vectorFromString("250 agarwal.gatech.edu Hello "
						+ networkName);
			}
		case READY:
			if (msg == null) {
				return vectorFromString("500 Syntax Error");
			}

			// MAIL FROM Pattern Matching
			int mailFromCount = 0;
			Pattern mailFromPattern = Pattern
					.compile(
							"^MAIL FROM: [_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
							Pattern.CASE_INSENSITIVE);
			Matcher matcher = mailFromPattern.matcher(msg);

			if (msg != null && matcher.find()) {
				mailFromCount++;
				return vectorFromString("250 OK");

			} else if (msg != null
					&& (Pattern.matches("^MAIL FROM: .*", msg) || Pattern
							.matches("^mail from: .*", msg))) {
				return vectorFromString("553 Requested action not taken: mailbox name not allowed");
			}

			// RCPT TO matching
			int rcptToCount = 0;
			Pattern rcptToPattern = Pattern
					.compile(
							"^RCPT TO: [_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
							Pattern.CASE_INSENSITIVE);
			if (msg != null && rcptToPattern.matcher(msg).find()
					&& mailFromCount == 0) {
				return vectorFromString("503 Bad sequence of commands");
			} else if (msg != null && rcptToPattern.matcher(msg).find()
					&& mailFromCount > 0) {
				rcptToCount++;
				return vectorFromString("250 OK");
			}

			if (msg != null && Pattern.matches("^DATA.*", msg)
					&& mailFromCount > 0 && rcptToCount > 0) {
				connectionState = MAIL_INPUT;
				return vectorFromString("354 Enter mail, end with \".\" on a line by itself");

			}
			if (msg != null && Pattern.matches("^QUIT\\s?", msg)) {
				connectionState = KILLED;
				return vectorFromString("221 Service closing control connection.");
			} else {
				return vectorFromString("500 Syntax Error");
			}

		case MAIL_INPUT:
			if (msg != null && Pattern.matches("^QUIT\\s?", msg)) {
				connectionState = KILLED;
				return vectorFromString("221 Service closing control connection.");
			}
			if (msg != null && Pattern.matches("^.*\\.$", msg)) {
				return vectorFromString("250 Requested mail action okay, completed");
			}
		default:
			connectionState = KILLED;
			return vectorFromString("421 Service not available, closing control connection.");
		}
		
		
	}

	@Override
	public int getPort() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public boolean isConnectionOver() {
		// TODO Auto-generated method stub
		return connectionState == KILLED;
	}

	public Vector<String> vectorFromString(String string) {
		Vector<String> vector = new Vector<String>();
		vector.add(string);
		return vector;

	}
	@Override
	public String toString(){
		return "SMTP";
	}

}
