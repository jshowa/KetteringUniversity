/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtpmailclientv2;

import java.net.*;
import java.util.*;
import javax.swing.*;
/* $Id: Envelope.java,v 1.8 1999/09/06 16:43:20 kangasha Exp $ */

/**
 * SMTP envelope for one mail message.
 *
 * @author Jussi Kangasharju
 */

/**
 *
 * This class holds the SMTP sender and recipient
 * information, the SMTP server of the recipient's domain,
 * and the Message object previously described up above. In
 * other words it contains the recipients information and the
 * senders information along with the email message (To
 * header, From header, target host information, and Message
 * object).
 *
 */
public class SMTPEnvelope {
    /* SMTP-sender of the message (in this case, contents of From-header. */
    private String sndr;

    /* SMTP-recipient, or contents of To-header. */
    private String rcpt;

    /* Target MX-host */
    private String destHost;
    private InetAddress destAddress;

    /* The actual message */
    private Message msg;

    /**
     * Constructor which extracts information from the message and creates
     * the envelope information needed by SMTP to send the email.
     * 
     * @param message A Message object containing the to, from, date, and subject
     *                headers and the message body information.
     * @param localServer The SMTP local mail server used as the destination for
     *                    the email.
     * 
     * @throws UnknownHostException thrown if the domain of the destination mail
     *                              server cannot be resolved
     *
     */
    public SMTPEnvelope(Message message, String localServer) throws UnknownHostException {
        /* Get sender and recipient. */
        sndr = message.getFrom();
        rcpt = message.getTo();

        /* Get message. We must escape the message to make sure that
        there are no single periods on a line. This would mess up
        sending the mail. */
        msg = escMsg(message);

        /* Take the name of the local mailserver and map it into an
         * InetAddress */
        destHost = localServer;
        try {
            destAddress = InetAddress.getByName(destHost);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Unknown host: " + destHost + "\n" + e);
            throw e;
        }
    }
    /**
     * Escape the message by doubling all periods at the beginning of a line.
     * This is needed to prevent problems with the message body terminator being
     * a CRLF "." CRLF.
     *
     * @param message A Message object containing the to, from, date, and subject
     *                headers and the message body information.
     * @return the email message with the period escape characters in the message body
     */
    private Message escMsg(Message message) {
        String escapedBody = "";
        String token;

        // parse the message body into tokens
        StringTokenizer parser = new StringTokenizer(message.getBdy(), "\n", true);

        // look at the beginning of each token and escape it with another period.
        while (parser.hasMoreTokens()) {
            token = parser.nextToken();
            if (token.startsWith(".")) {
                token = "." + token;
            }
            escapedBody += token;
        }

        // re-write the message body with the period escape chars
        message.setBdy(escapedBody);
        return message;
    }

    /**
     * Accessor method used for pulling the senders email address hostname.
     *
     * @return A string representing the sending email address host name
     */
    public String getSndr() {
        return sndr;
    }

    /**
     * Accessor method used for pulling the recipients email address hostname.
     *
     * @return A string representing the recipient email address host name.
     */
    public String getRcpt() {
        return rcpt;
    }

    /**
     * Accessor method used to get the destination hostname of the local mail server.
     *
     * @return The destination mail server host name.
     */
    public String getDestHost() {
        return destHost;
    }

    /**
     * Accessor method used to ge the destination IP address of the local mail server.
     *
     * @return The IP address of the destination mail server.
     */
    public InetAddress getDestAddress() {
        return destAddress;
    }

    /**
     * Accessor method used to pull the Message object which contains
     * the email message and header information
     * 
     * @return A Message object containing the email message and header information.
     */
    public Message getMessage() {
        return msg;
    }

    /**
     * Mutator method used for setting the senders email address hostname.
     *
     * @param sndr The sender email address.
     */
    public void setSndr(String sndr) {
        this.sndr = sndr;
    }

    /**
     * Mutator method used for setting the reciepents email address hostname.
     *
     * @param rcpt The recipient email address.
     */
    public void setRcpt(String rcpt) {
        this.rcpt = rcpt;
    }

    /**
     * Mutator method used for setting the host name of the destination.
     *
     * @param destHost The host name of the destination mail server.
     */
    public void setDestHost(String destHost) {
        this.destHost = destHost;
    }

    /**
     * Mutator method used to set the IP address of the destination email server
     *
     * @param destAddress The IP address of the destination mail server.
     */
    public void setDestAddress(InetAddress destAddress) {
        this.destAddress = destAddress;
    }

    /**
     * Mutator method used for setting the Message objects data which contains
     * the email body and header information.
     *
     * @param message A Message object containing the to, from, date, and subject
     *                headers and the message body information.
     */
    public void setMessage(Message msg) {
        this.msg = msg;
    }

    /**
     * Returns a string containing an SMTPEnvelope objects contents which
     * includes the email message, the sender and recipient addresses, and
     * the destination mail server host name and IP address.
     *
     * @return A string containing the contents of the SMTPEnvelope object.
     */
    @Override
    public String toString() {
        String res = "Sender: " + sndr + '\n';
        res += "Recipient: " + rcpt + '\n';
        res += "MX-host: " + destHost + ", address: " + destAddress + '\n';
        res += "Message:" + '\n';
        res += msg.toString();

        return res;
    }

}//end Envelope

