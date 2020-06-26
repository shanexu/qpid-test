package org.xusheng.qpidtest;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;


public class Hello {

    public Hello() {
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.runTest();
    }

    private void runTest() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("hello.properties"));
            Context context = new InitialContext(properties);

            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("qpidConnectionfactory");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = (Destination) context.lookup("topicExchange");

            MessageProducer messageProducer = session.createProducer(destination);
            MessageConsumer messageConsumer = session.createConsumer(destination);

            TextMessage message = session.createTextMessage("Hello world!");
            messageProducer.send(message);

            message = (TextMessage) messageConsumer.receive();
            System.out.println(message.getText());

            connection.close();
            context.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
