/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtpmailclientv2;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

/* $Id: Message.java, v 1.5 1999/07/22 12:10:57 Exp $ */

/**
 *
 * This class contains the blueprint for creating
 * an email with the to, from, subject header fields along
 * with the message body.
 *
 * @author Jussi Kangasharju
 */
public class Message {
    //Variables to define contents in message
    String from; //sender email address
    String to;	 //recipient email address 
    String bdy;	 //defines the contents of message
    String hdr;  //contains the to, from, and subject headers

    private final String CRLF = "\r\n"; //carriage return

   /**
    * 
    * A method constructor which creates a Message
    * object with a from header field containing the source
    * email address, a to header field containing a recipient
    * email address, a subject header field describing the
    * subject of the message, and the message body.
    *
    * @param from - the source email address
    *        to - the destination email address
    *        sbjct - subject of the message body
    *        txt - message body
    * 
    */
    public Message(String from, String to, String sbjct, String txt) {
        // remove extra whitespace from address
        this.from = "<" + from.trim() + ">";
        this.to = "<" + to.trim() + ">";

        // add from, to, and subject fields to header
        hdr = "From: " + this.from + CRLF;
        hdr += "To: " + this.to + CRLF;
        hdr += "Subject: " + sbjct.trim() + CRLF;

        // add the date (GMT)
        SimpleDateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        hdr += "Date: " + (date.format(new Date())) + CRLF;

        // add body text
        bdy = txt;
    }

    /**
     * Accessor that retrieves the sending email address header field
     * from a Message object.
     *
     * @return the sending address of the email.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Accessor that retrieves the recipient email address header field
     * from a Message object.
     *
     * @return the recipient address of the email.
     */
    public String getTo() {
        return to;
    }

    /**
     * Accessor that retrieves the body of the email from a Message object.
     *
     * @return the body of the email.
     */
    public String getBdy() {
        return bdy;
    }

    /**
     * Accessor that retrieves the email header information from a Message
     * object which contains the to, from, subject, and date information.
     * The to: field has the recipient email address. The from: header field
     * has the sender email address. The subject: header field is a phrase
     * denoting the subject of the message, and the date is the date in GMT.
     *
     * @return the email header information.
     */
    public String getHdr() {
        return hdr;
    }

    /**
     * Mutator that sets the recipient email address header field
     * of a Message object.
     *
     * @param to the recipient email address.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Mutator that sets the sending email address header field.
     *
     * @param from senders email address.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Mutator that sets the body of the email message.
     *
     * @param bdy the body of the email message.
     */
    public void setBdy(String bdy) {
        this.bdy = bdy;
    }

    /**
     * Mutator that sets the email header information which contains
     * the to, from, subject, and date information. The to: field has the
     * recipient email address. The from: header field has the sender email
     * address. The subject: header field is a phrase denoting the subject
     * of the message, and the date is the date in GMT.
     *
     * @param hdr contains the to, from, subject, and date fields of the email.
     */
    public void setHdr(String hdr) {
        this.hdr = hdr;
    }

    /**
     * Uses regular expressions to validate the email address of the sender
     * and recipient in the email.
     *
     * @return true if both messages are valid, false if either aren't valid.
     */
    public boolean isValidEmailAddress() {
        boolean validTo = false, validFrom = false;

        /* Check the sender and recipient email addresses for valid synatx. */
        validTo = to.matches("<[^\\s]+[\\w[\\d[\\W]]]+[\\W]*[@][\\w[\\d]]+[\\W]*[.][a-zA-Z]+>");
        validFrom = from.matches("<[^\\s]+[\\w[\\d[\\W]]]+[\\W]*[@][\\w[\\d]]+[\\W]*[.][a-zA-Z]+>");

        if (!validTo && !validFrom) {
            JOptionPane.showMessageDialog(null, "Invalid sender and recipient email syntax");
            return false;
        }
        else if (!validTo) {
            JOptionPane.showMessageDialog(null, "Invalid recipient email syntax");
            return false;
        }
        else if (!validFrom) {
            JOptionPane.showMessageDialog(null, "Invalid sender email syntax");
            return false;
        }
        
        return true;

    }

    /**
     * Returns a readable string containing the email message header
     * (to, from, subject, date) and the message body.
     *
     * @return The email message header and body in readable form
     */
    @Override
    public String toString() {
        return (hdr + CRLF + bdy);
    }
}

