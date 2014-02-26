/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JApplet;
import javax.swing.JTextArea;
import receiver.MessageReceiver;

/**
 *
 * @author mse
 */
public class UserTable extends JApplet {

    public static final String TOPIC = "jms/myTopic1";
    /**
     * @param args the command line arguments
     */

    JTextArea textarea;
    MessageReceiver mr;

    public void init() {
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    try {
                        createGUI();
                        refresh();
                    } catch (JMSException ex) {
                        Logger.getLogger(MessageReceiverApplet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NamingException ex) {
                        Logger.getLogger(MessageReceiverApplet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MessageReceiverApplet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
    }

    void createGUI() throws JMSException, NamingException, IOException {

        mr = new MessageReceiver();
        mr.setUserApplet(this);
        textarea = new JTextArea("The start!");
        add(textarea);
        setSize(300, 300);

    }

    public void refresh() throws MalformedURLException, IOException {
        URL oracle = new URL("http://localhost:8080/WebAppletPubSub/webresources/entities.login/users");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(oracle.openStream()));

        String inputLine;
        textarea.setText("");
        while ((inputLine = in.readLine()) != null) {
            textarea.append(inputLine + "\n");
        }
        in.close();
    }

    public void print(String message) {

        textarea.setText(message);

    }

    // TODO overwrite start(), stop() and destroy() methods
}
