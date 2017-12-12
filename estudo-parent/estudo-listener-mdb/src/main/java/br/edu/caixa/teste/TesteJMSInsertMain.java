package br.edu.caixa.teste;

import javax.jms.JMSException;
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

import br.edu.caixa.mdb.TesteMDB;

public class TesteJMSInsertMain {
	private static final Logger logger = Logger.getLogger(TesteJMSInsertMain.class);
	public static void main(String[] args) {
//		try {
//			//TextMessage msg = new JMSTe
//		
//			InitialContext initialContext = new InitialContext();
//			QueueConnectionFactory connFactory = (QueueConnectionFactory) initialContext.lookup(JNI_MQ);
//			QueueConnection qConn = (QueueConnection)connFactory.createQueueConnection();
//			QueueSession qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
//			qConn.start();
//			
//			Queue queue = (Queue) initialContext.lookup(JNI_QUEUE);
//			QueueSender qSender = qSession.createSender(queue);
//			
//			
//			TextMessage received = (TextMessage) msg;
//			TextMessage txtMessage = qSession.createTextMessage();
//            txtMessage.setStringProperty("JMS_IBM_Character_Set", "819");
//            txtMessage.setText(received.getText());
//            
//            qSender.send(txtMessage);
//            
//            qSender.close();
//			qSession.close();
//			qConn.close();
//		} catch (NamingException e) {
//			logger.error("",e);;
//		} catch (JMSException e) {
//			logger.error("",e);

	}

}
