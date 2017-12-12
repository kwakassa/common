package br.edu.caixa.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;


@MessageDriven(activationConfig = { 
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
	@ActivationConfigProperty(propertyName = "destination", propertyValue = "CEF.REQ.TESTE"),
})
public class TesteMDB implements MessageListener{
	private static final Logger logger = Logger.getLogger(TesteMDB.class);
	//A String deve coincidir com o jni-name do connection-definition, do resource-adapter, definido no domain.xml do jboss-eap
		private static String JNI_MQ = "java:/activemq/ConnectionFactory";
		//A String deve coincidir com o jni-name do admin-object, do resource-adapter, definido no domain.xml do jboss-eap 
		private static String JNI_QUEUE = "java:/queue/EstudoRspTesteQueue";
	@Override
	public void onMessage(Message msg) {
		try {
			InitialContext initialContext = new InitialContext();
			QueueConnectionFactory connFactory = (QueueConnectionFactory) initialContext.lookup(JNI_MQ);
			QueueConnection qConn = (QueueConnection)connFactory.createQueueConnection();
			QueueSession qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
			qConn.start();
			
			Queue queue = (Queue) initialContext.lookup(JNI_QUEUE);
			QueueSender qSender = qSession.createSender(queue);
			
			TextMessage received = (TextMessage) msg;
			TextMessage txtMessage = qSession.createTextMessage();
            txtMessage.setStringProperty("JMS_IBM_Character_Set", "819");
            txtMessage.setText(received.getText());
            
            qSender.send(txtMessage);
            
            qSender.close();
			qSession.close();
			qConn.close();
		} catch (NamingException e) {
			logger.error("",e);;
		} catch (JMSException e) {
			logger.error("",e);;
		}
	}

}
