/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtpmailclientv2;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * 
 * Opens an SMTP connection to a local mail server
 * and performs the necessary actions to send the
 * constructed email.
 *
 * @author Jacob S. Howarth
 *
 */
public class SMTPConnection {
    /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromSrvr;
    private DataOutputStream toSrvr;

    /* SMTP constants for the reserved SMTP port number, SMTP mail commands,
     * and SMTP server reply codes. */
    private static final int SMTP_PORT = 25;
    private static final String[] SMTP_CMD = {"HELO", "MAIL FROM:", "RCPT TO:",
                                               "DATA", "QUIT"};
    private static final String CRLF = "\r\n";
    private static final int[] SRVR_RC = {500, 501, 502, 503, 504, 211, 214,
                                          220, 221, 421, 250, 251, 450, 550,
                                          451, 551, 452, 552, 553, 354, 554};

    /* Server reply string offset to extract reply code. */
    private final int RC_OFFSET = 3;

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /**
     * Create an SMTPConnection object. Create the socket and the
     * associated streams. Initialize SMTP connection and perform necessary
     * handshaking.
     *
     * @param envelope An envelope for an SMTP email containing the body,
     *                   sender and recipient email addresses, subject, and local
     *                   mail server.
     * @throws IOException thrown when the server reply code doesn't match the expected
     *                       one, indicating an error in the command
     * @throws UnknownHostException thrown if the TCP socket is not able to establish
     *                                a connection with the local mail server
     * @throws InterruptedException thrown by waiting the thread for the SMTP command
     *                                and data to be transferred from
     */
    public SMTPConnection(SMTPEnvelope envelope) throws IOException, UnknownHostException, InterruptedException {
	connection = new Socket(envelope.getDestHost(), SMTP_PORT); // comment out for debug purpose.
        fromSrvr = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toSrvr = new DataOutputStream(connection.getOutputStream());
      
        /* uncomment for debug/test purposes */
        //fromSrvr = new BufferedReader(new InputStreamReader(System.in));
	//toSrvr = new DataOutputStream(System.out);
	/* uncomment for debug/test purposes */

        String inFrmSrvrData = "";

	/* Read a line from server and check that the reply code is 220.
	   If not, throw an IOException. */
        inFrmSrvrData = fromSrvr.readLine();
        if (parseReply(inFrmSrvrData) != SRVR_RC[7])
            throw new IOException(inFrmSrvrData);
        else {

            /* SMTP handshake. We need the name of the local machine.
               Send the appropriate SMTP handshake command. */
            String localhost = InetAddress.getLocalHost().getHostName();
            sendCommand(SMTP_CMD[0] + " " + localhost + CRLF, SRVR_RC[10]);

            isConnected = true;
        }
    }

    /**
     * Send the message. Write the correct SMTP-commands in the
     * correct order. No checking for errors, just throw them to the
     * caller.
     * 
     * @param envelope An envelope for an SMTP email containing the body,
     *                   sender and recipient email addresses, subject, and local
     *                   mail server.
     * @throws IOException thrown when the server reply code doesn't match the expected
     *                       one, indicating an error in the command
     * @throws InterruptedException thrown by waiting the thread for the SMTP command
     *                                and data to be transferred from
     */
    public void send(SMTPEnvelope envelope) throws IOException, InterruptedException {
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
        sendCommand(SMTP_CMD[1] + " " + envelope.getSndr() + CRLF, SRVR_RC[10]);
        sendCommand(SMTP_CMD[2] + " " + envelope.getRcpt() + CRLF, SRVR_RC[10]);
        sendCommand(SMTP_CMD[3] + CRLF, SRVR_RC[19]);
        sendCommand(envelope.getMessage() + CRLF + "." + CRLF, SRVR_RC[10]);
    }

    /**
     * Close the connection. First, terminate on SMTP level, then close the socket.
     *
     */
    public void close() {
	isConnected = false;
	try {
	    sendCommand(SMTP_CMD[4] + CRLF, SRVR_RC[8]); // send quit command
	    connection.close(); // close socket connection (comment out during debugging/testing)
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, e);
	    isConnected = true;
	}
        catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, e);
            isConnected = true;
        }
    }

    /**
     * Send an SMTP command to the server. Check that the reply code is
     * what is is supposed to be according to RFC 5321.
     * 
     * @param command A string representing the SMTP command and data to be sent
     *                to the server.
     * @param rc An integer representing the expected server reply code of the sommand
     *           being sent.
     * @throws IOException thrown when the server reply code doesn't match the expected
     *                       one, indicating an error in the command
     * @throws InterruptedException thrown by waiting the thread for the SMTP command
     *                                and data to be transferred from
     *
     */
    private void sendCommand(String command, int rc) throws IOException, InterruptedException {
	String srvrRply = ""; //, rplyLine = "";

        /* Write command to server. */
	toSrvr.writeBytes(command);

        /* Wait for local mail server to finish message transfer and return reply */
        try {
            Thread.sleep(100); // sleep for 100 ms
        } catch (InterruptedException ex) {
            throw ex;
        }

        /* Read server reply from input stream buffer. */
        //while(!((rplyLine = fromSrvr.readLine()).equals(null)))
            //srvrRply += rplyLine;

        //while (fromSrvr.ready()) {
            srvrRply += fromSrvr.readLine();
        //}

        /* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
	if (rc != parseReply(srvrRply))
            throw new IOException(srvrRply);

    }

    /**
     * Parse the reply line from the server. Returns the reply code.
     *
     * @param reply a string containing the reply code and message from
     *              the local mail server.
     * @return the server reply code extracted from the server reply message
     */
    private int parseReply(String reply) {
	/* reply code from server reply message. */
        int rc;

        /* Extract the first three chars in the string which
         * contains the reply code and convert it to an integer
         * and return. */
        rc = Integer.parseInt(reply.substring(0, RC_OFFSET));
        return rc;
    }

    /**
     * Method: finalize
     * 
     * Description: Destructor. Closes the connection if something bad happens.
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
	if(isConnected) {
	    close(); // close connections
	}
	super.finalize();
    }
}

