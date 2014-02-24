/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applet;

import static com.sun.tools.xjc.reader.Ring.add;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JApplet;
import javax.swing.JTextArea;
import receiver.MessageReceiver;

/**
 *
 * @author junxin
 */
public class MessageReceiverApplet extends JApplet { // implements MessageListener{

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
        mr.setApplet ( this );
        textarea = new JTextArea("The start!");
        add(textarea);
        setSize(200, 200);

    }

    public void print(String message) {

        textarea.setText(message);

    }

    /*
     public void subscribe(TopicConnection topicConnection, Topic topic) throws JMSException, IOException{
     TopicSession subscribeSession=topicConnection.createTopicSession(false, AUTO_ACKNOWLEDGE);
     TopicSubscriber topicSubscriber=subscribeSession.createSubscriber(topic);
     topicSubscriber.setMessageListener(this);
     BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
     String message=null;
     while(true){
     message=reader.readLine();
     if(message.equalsIgnoreCase("exit")){
     topicConnection.close();
     System.exit(0);
     }
     }
     }
     public static Context getInitialContext() throws JMSException, NamingException{
     Properties properties=new Properties();
     properties.setProperty("java.naming.factory.initial","com.sun.enterprise.naming.SerialInitContextFactory");
     properties.setProperty("java.naming.factory.url.pkgs","com.sun.enterprise.naming");
     properties.setProperty("java.naming.provider.url","iiop://localhost:3700");
     return new InitialContext(properties);
     }
     */
}
