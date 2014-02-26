/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.jms.JMSContext.AUTO_ACKNOWLEDGE;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import receiver.MessageReceiver;

/**
 *
 * @author junxin
 */
public class ProduceSender {

    public static final String TOPIC = "jms/myTopic1";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException, JMSException, IOException {
        ProduceSender mr = new ProduceSender();
        Context initialContext = ProduceSender.getInitialContext();
        Topic topic = (Topic) initialContext.lookup(ProduceSender.TOPIC);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup("jms/myTopicFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        topicConnection.start();
        mr.publish(topicConnection, topic);
    }

    public ProduceSender() throws JMSException, NamingException, IOException {
        Context initialContext = ProduceSender.getInitialContext();
        Topic topic = (Topic) initialContext.lookup(ProduceSender.TOPIC);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup("jms/myTopicFactory");
        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
        topicConnection.start();
        publish(topicConnection, topic);
    }

//    public void onMessage(Message message) {
//        try {
//            ObjectMessage objectMessage=(ObjectMessage) message;
//            CommunicationMessage communicationMessage=(CommunicationMessage)objectMessage.getObject();
//            System.out.print("Sender:"+communicationMessage.getName());
//            System.out.print("Message:"+communicationMessage.getMessage());
//        } catch (JMSException ex) {
//            Logger.getLogger(ProduceSender.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void publish(TopicConnection topicConnection, Topic topic) throws JMSException, IOException {
        TopicSession publishSession = topicConnection.createTopicSession(false, AUTO_ACKNOWLEDGE);
        TopicPublisher topicPublisher = publishSession.createPublisher(topic);
//        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
//        System.out.println("Please enter your username:");
//        String username=reader.readLine();
//        String message=null;
//        while(true){
//            message=reader.readLine();
//            if(message.equalsIgnoreCase("exit")){
//                topicConnection.close();
//                System.exit(0);
//            }else{
        ObjectMessage objectMessage = publishSession.createObjectMessage();
        objectMessage.setObject(new CommunicationMessage("jun", "he"));
        topicPublisher.publish(objectMessage);
        //}
        //}
    }

    public static Context getInitialContext() throws JMSException, NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }

}
