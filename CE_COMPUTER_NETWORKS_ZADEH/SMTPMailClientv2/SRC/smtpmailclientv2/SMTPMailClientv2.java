/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smtpmailclientv2;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class creates and initializes the GUI and its
 * components for the simple SMTP email user agent. It also
 * contains the button events that establish the SMTP connection
 * and send the message, clear the text from the text fields in
 * the GUI, and close the SMTP connection and the GUI client.
 *
 * @author jshowa
 */
public class SMTPMailClientv2 extends Frame {

    /* GUI component variable declarations. */
    private javax.swing.JButton sndbut;
    private javax.swing.JButton clrbut;                   // button to clear all text fields
    private javax.swing.JButton extbut;                   // button that closes program
    private javax.swing.JLabel fromHdr;                   // label for the from/sender address header text field
    private javax.swing.JTextField fromTxtBox;            // from header text field (sender email address input)
    private javax.swing.JScrollPane jScrollPane1;         // scroll pane for message text field
    private javax.swing.JLabel localMailSrvr;             // label for the local SMTP mail server text field
    private javax.swing.JTextField localMailSrvrTxtBox;   // local SMTP mail server text field (destination server)
    private javax.swing.JLabel msgHdr;                    // label for email body text field
    private javax.swing.JTextArea msgTxtBox;              // message body input text field
    private javax.swing.JLabel subjctHdr;                 // label for subject header text field
    private javax.swing.JTextField sbjctTxtBox;           // subject header text field (subject of email body)
    private javax.swing.JLabel toHdr;                     // label for to/recipient address header text field
    private javax.swing.JTextField toTxtBox;              // to header text field (recipient email address input)

    /**
     * Zero argument constructore that constructs
     * the SMTP mail client GUI and its necessary components.
     */
    public SMTPMailClientv2 () {
        super("SMTP Mail Client");

        initComponents();
    }

    /**
     * Creates and configures the GUI components and
     * creates the GUI layout.
     */
    private void initComponents() {

        /* Instantiate all GUI components */
        sndbut = new JButton();
        clrbut = new JButton();
        extbut = new JButton();
        localMailSrvr = new JLabel();
        localMailSrvrTxtBox = new JTextField();
        toHdr = new JLabel();
        toTxtBox = new JTextField();
        fromTxtBox = new JTextField();
        fromHdr = new JLabel();
        jScrollPane1 = new JScrollPane();
        msgTxtBox = new JTextArea();
        sbjctTxtBox = new JTextField();
        subjctHdr = new JLabel();
        msgHdr = new JLabel();

        /* Initialize sndbut event */
        sndbut.setText("Send");
        sndbut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                subbutActionPerformed(event);
            }
        });

        /* Initialize clrbut event */
        clrbut.setText("Clear");
        clrbut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                clrbutActionPerformed(event);
            }
        });

        /* Initialize extbut event */
        extbut.setText("Exit");
        extbut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                extbutActionPerformed(event);
            }
        });

        // configure the text field labels
        localMailSrvr.setText("Local Mail Server:");
        toHdr.setText("To:");
        fromHdr.setText("From:");
        subjctHdr.setText("Subject:");
        msgHdr.setText("Message:");

        // configure the text fields
        localMailSrvrTxtBox.setText("");
        toTxtBox.setText("");
        fromTxtBox.setText("");
        sbjctTxtBox.setText("");
        msgTxtBox.setColumns(20);
        msgTxtBox.setLineWrap(true);
        msgTxtBox.setRows(5);
        jScrollPane1.setViewportView(msgTxtBox);

        // set up the component layout
        GroupLayout mailClientGUILayout = new GroupLayout(this);
        this.setLayout(mailClientGUILayout);
        mailClientGUILayout.setHorizontalGroup(
            mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mailClientGUILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(mailClientGUILayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(localMailSrvr)
                            .addComponent(toHdr)
                            .addComponent(fromHdr)
                            .addComponent(subjctHdr)
                            .addComponent(msgHdr))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(sbjctTxtBox, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(fromTxtBox, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(toTxtBox, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(localMailSrvrTxtBox, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)))
                    .addGroup(GroupLayout.Alignment.TRAILING, mailClientGUILayout.createSequentialGroup()
                        .addComponent(extbut)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(clrbut)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sndbut)))
                .addContainerGap())
        );
        mailClientGUILayout.setVerticalGroup(
            mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, mailClientGUILayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(localMailSrvrTxtBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(localMailSrvr, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(toTxtBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(toHdr, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(fromTxtBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(fromHdr, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sbjctTxtBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(subjctHdr))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(mailClientGUILayout.createSequentialGroup()
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(mailClientGUILayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(sndbut)
                            .addComponent(clrbut)
                            .addComponent(extbut)))
                    .addComponent(msgHdr))
                .addContainerGap())
        );

        // show the frame
        this.setVisible(true);
    }

    /**
     * This event is triggered when the Send button is clicked
     * and establishes the SMTP connection and sends the contents
     * of the text fields in the GUI as an email to the recipient.
     *
     * @param event An ActionEvent object with information on how the
     *              event was triggered.
     */
    private void subbutActionPerformed(ActionEvent event) {
        SMTPEnvelope envelope = null;
        SMTPConnection connection = null;

        // check if the text fields have any text in them
        if((localMailSrvrTxtBox.getText()).equals(null)) {
            JOptionPane.showMessageDialog(null, "Need domain of local mail server");
            return;
        }
        if((fromTxtBox.getText()).equals(null)) {
            JOptionPane.showMessageDialog(null, "Need sender email address.");
            return;
        }
        if((toTxtBox.getText()).equals(null)) {
            JOptionPane.showMessageDialog(null, "Need recipient email address.");
            return;
        }

        // create the Message object from the text in the fields.
        Message email = new Message(fromTxtBox.getText(), toTxtBox.getText(),
                                    sbjctTxtBox.getText(), msgTxtBox.getText());

        // check the sender and recipient email addresses for validity.
        if(!email.isValidEmailAddress()) {
            return;
        }

        
        // create the envelope for the SMTP connection.
        try {
            envelope = new SMTPEnvelope(email, localMailSrvrTxtBox.getText());
        }
        catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, ex);
            envelope = null;
            return;
        }

        // establish the SMTP connection, send the email message and
        // envelope, then close the connection.
        try {
            if (!(envelope.equals(null))) {
                connection = new SMTPConnection(envelope);
                connection.send(envelope);
                connection.close();
            }
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            return;
        }

        JOptionPane.showMessageDialog(null, "Email sent successfully.");
    }

    /**
     * This event is triggered when the Clear button is clicked
     * and it clears all the text currently in the textfields
     *
     * @param event An ActionEvent object with information on how the
     *              event was triggered.
     */
    private void clrbutActionPerformed(ActionEvent event) {
        fromTxtBox.setText("");
        toTxtBox.setText("");
        sbjctTxtBox.setText("");
        msgTxtBox.setText("");
    }

    /**
     * This event is triggered when the Exit button is clicked
     * and closes the program.
     *
     * @param event An ActionEvent object with information on how the
     *              event was triggered.
     */
    private void extbutActionPerformed(ActionEvent event) {
        System.exit(0);
    }


    /**
     * The main method where program execution begins.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SMTPMailClientv2();
    }

}
