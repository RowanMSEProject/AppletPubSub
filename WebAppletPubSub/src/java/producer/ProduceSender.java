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
        
        mr.publish();
    }

    public void publish() 
            throws JMSException, NamingException, IOException {
        TopicConnection topicConnection;
        Topic topic;
        Context initialContext = ProduceSender.getInitialContext();
        topic = (Topic) initialContext.lookup(ProduceSender.TOPIC);
        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) initialContext.lookup("jms/myTopicFactory");
        topicConnection = topicConnectionFactory.createTopicConnection();
        topicConnection.start();
        
        TopicSession publishSession = topicConnection.createTopicSession(false, AUTO_ACKNOWLEDGE);
        TopicPublisher topicPublisher = publishSession.createPublisher(topic);

        ObjectMessage objectMessage = publishSession.createObjectMessage();
        objectMessage.setObject(new CommunicationMessage("jun", "he"));
        topicPublisher.publish(objectMessage);
        
    }

    public static Context getInitialContext() throws JMSException, NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.setProperty("java.naming.provider.url", "iiop://localhost:3700");
        return new InitialContext(properties);
    }

}
