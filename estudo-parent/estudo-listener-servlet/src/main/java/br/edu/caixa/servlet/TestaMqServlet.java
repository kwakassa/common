package br.edu.caixa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(urlPatterns = "/EstudoServletRequest", loadOnStartup = 1)
public class TestaMqServlet extends HttpServlet{
	
	private static Logger logger = Logger.getLogger(TestaMqServlet.class);
	private static final long serialVersionUID = 1L;
	private static String STR_HEADER = "<html><body>";
	private static String STR_FOOTER = "</body></html>";
	//A String deve coincidir com o jni-name do connection-definition, do resource-adapter, definido no domain.xml do jboss-eap
	private static String JNI_MQ = "java:/activemq/ConnectionFactory";
	//A String deve coincidir com o jni-name do admin-object, do resource-adapter, definido no domain.xml do jboss-eap 
	private static String JNI_QUEUE = "java:/queue/EstudoMDBQueue";
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		testaAcessoActiveMQRar(response);
		//testaGetMessagesActiveMQRar(response);
	}
	
	public void testeSimples(HttpServletResponse response){
		try {
			PrintWriter writer = response.getWriter();
			writer.println(STR_HEADER);
			writer.println("<h1>Teste Sevlet</h1>");
			writer.println(STR_FOOTER);
		} catch (IOException e) {
			logger.error("",e);;
		}
	}
		
	public void testaAcessoActiveMQRar(HttpServletResponse response){
		PrintWriter writer = null;
		try {
			int count = 0;
			writer = response.getWriter();
			writer.println(STR_HEADER);
			InitialContext initialContext = new InitialContext();
			QueueConnectionFactory connFactory = (QueueConnectionFactory) initialContext.lookup(JNI_MQ);
			QueueConnection qConn = (QueueConnection)connFactory.createQueueConnection();
			QueueSession qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
			qConn.start();
			Queue queue = (Queue) initialContext.lookup(JNI_QUEUE);
			QueueBrowser qBrowser = qSession.createBrowser(queue);
			
			Enumeration msgs = qBrowser.getEnumeration();
			if (!msgs.hasMoreElements() ) { 
				writer.println("No messages in queue");
			} else { 
			    while (msgs.hasMoreElements()) {
			    	count++;
			        Message tempMsg = (Message)msgs.nextElement();			        
			        writer.println("<br>Interaction : " + count);
			        writer.println("<br>Message: <xmp>" + tempMsg + "<br>");
			        writer.println("</xmp>");
			        if (tempMsg instanceof TextMessage) {
		                TextMessage textMessage = (TextMessage) tempMsg;
		                String text = textMessage.getText();
		                writer.println("<br>Received: <xmp>" + text);
		                writer.println("</xmp>");
		            } else {
		            	writer.println("<br>Received: <xmp>" + tempMsg);
		            	writer.println("</xmp>");
		            }
			        writer.println("<br>");
			    }
			}
			writer.println(STR_FOOTER);
			qBrowser.close();
            qSession.close();
            qConn.close();
		} catch (NamingException e) {
			logger.error("",e);;
			writer.println("<h1>Erro no Teste Acesso Active MQ</h1>");			
			writer.println(STR_FOOTER);
		} catch (JMSException e) {
			logger.error("",e);;
			writer.println("<h1>Erro no Teste Acesso Active MQ</h1>");			
			writer.println(STR_FOOTER);
		} catch (IOException e) {
			logger.error("",e);;
		} 
	}
	/** Achar um jeito de printa xml na pagina html sem cortas as tags*/
	public void testaGetMessagesActiveMQRar(HttpServletResponse response){
		PrintWriter writer = null;
		try {
			int count = 0;
			writer = response.getWriter();
			writer.println(STR_HEADER);
			InitialContext initialContext = new InitialContext();
			QueueConnectionFactory connFactory = (QueueConnectionFactory) initialContext.lookup(JNI_MQ);
			QueueConnection qConn = (QueueConnection)connFactory.createQueueConnection();
			QueueSession qSession = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE); 
			qConn.start();
			Queue queue = (Queue) initialContext.lookup(JNI_QUEUE);
			Destination destination = qSession.createQueue("CEF.TESTE.MQ");
            
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageConsumer consumer = qSession.createConsumer(destination);
         // Wait for a message
            Message message = consumer.receive(1000);
            
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                writer.println("<br>Received: <xmp>" + text);
                writer.println("</xmp>");
            } else {
            	writer.println("<br>Received: <xmp>" + message);
            	writer.println("</xmp>");
            }
			
			writer.println(STR_FOOTER);

            consumer.close();
            qSession.close();
            qConn.close();
		} catch (NamingException e) {
			logger.error("",e);;
			writer.println("<h1>Erro no Teste Acesso Active MQ</h1>");			
			writer.println(STR_FOOTER);
		} catch (JMSException e) {
			logger.error("",e);;
			writer.println("<h1>Erro no Teste Acesso Active MQ</h1>");			
			writer.println(STR_FOOTER);
		} catch (IOException e) {
			logger.error("",e);;
		} 
	}
	
	public static void main(String[] args) {
		
	}

}
