/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receiver;

import applet.MessageReceiverApplet;
import applet.UserTable;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import producer.ProduceSender;

/**
 *
 * @author junxin
 */
public class MessageReceiver implements MessageListener, ExceptionListener {

    public static final String TOPIC = "jms/myTopic1";

    /**
     * @param args the command line arguments
     */

    public MessageReceiver() throws JMSException, NamingException, IOException {
        // MessageReceiver mr=new MessageReceiver();
        Context initialContext = MessageReceiver.getInitialContext();
        Topic topic = (Topic) initialContext.lookup(ProduceSender.TOPIC);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup("jms/myTopicFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        subscribe(topicConnection, topic);
        topicConnection.start();

    }

    MessageReceiverApplet applet;
    UserTable userApplet;

    public void setApplet(MessageReceiverApplet applet) {
        this.applet = applet;
    }
    
    public void setUserApplet(UserTable applet) {
        this.userApplet = applet;
    }

    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            producer.CommunicationMessage communicationMessage = (producer.CommunicationMessage) objectMessage.getObject();
            /*
             System.out.println("Sender:"+communicationMessage.getName());
             System.out.println("Message:"+communicationMessage.getMessage());
             */
            //applet.print("Sender:" + communicationMessage.getName() + "\n"
                    //+ "Message:" + communicationMessage.getMessage());
            userApplet.refresh();

        } catch (JMSException ex) {
            Logger.getLogger(ProduceSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void subscribe(TopicConnection topicConnection, Topic topic) throws JMSException, IOException {
        TopicSession subscribeSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        TopicSubscriber topicSubscriber = subscribeSession.createSubscriber(topic);
        topicSubscriber.setMessageListener(this);
        topicConnection.setExceptionListener(this);
        /*
         BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
         String message=null;
         while(true){
         message=reader.readLine();
         if(message.equalsIgnoreCase("exit")){
         topicConnection.close();
         System.exit(0);
         }
         }
         */

    }

    public void onException(JMSException exception) {
        applet.print("something bad happended: " + exception);
    }

    public static Context getInitialContext() throws JMSException, NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }

}
