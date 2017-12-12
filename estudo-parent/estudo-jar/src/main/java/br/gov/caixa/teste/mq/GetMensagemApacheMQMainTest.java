package br.gov.caixa.teste.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author c096489
 */
public class GetMensagemApacheMQMainTest {

    private static Logger logger = Logger.getLogger(GetMensagemApacheMQMainTest.class);
    private static String urlConnection = "tcp://localhost:61616";
    private static String queueName1 = "CEF.REQ.TESTE";
    private static String queueName2 = "CEF.TESTE.MQ";
    private static String queueName3 = "SIMTX.RSP.AGENCIA";
    private static String queueName4 = "SIMTX.REQ.AGENCIA";
    private static String queueName5 = "SIMTX.REQ.AUTOATENDIMENTO";
    private static String queueName6 = "SIMTX.RSP.AUTOATENDIMENTO";
    private static String txt1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><consulta_doc_nis:SERVICO_ENTRADA xmlns:consulta_doc_nis=\"http://caixa.gov.br/siiso/consulta_documento_nis\" xmlns:sibar_base=\"http://caixa.gov.br/sibar\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://caixa.gov.br/siiso/consulta_documento_nis Consulta_Documento_NIS.xsd \">  <sibar_base:HEADER>    <VERSAO>VERSAO</VERSAO>    <OPERACAO>OPERACAO</OPERACAO>    <DATA_HORA>DATA_HORA</DATA_HORA>  </sibar_base:HEADER>  <DADOS>    <CONSULTA_DOCUMENTOS>      <TIPO_BUSCA>TIPO_BUSCA</TIPO_BUSCA>    </CONSULTA_DOCUMENTOS>  </DADOS></consulta_doc_nis:SERVICO_ENTRADA>";
    private static String txt2 = "GROSELHA"; 
    
    public static void getMsgFromQueue(String queue){
    	try {
            logger.info("Inicio de Envio de Mensagem teste para file MQ");
            String defaultBrokerUrl = ActiveMQConnection.DEFAULT_BROKER_URL;
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(urlConnection);
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            
            // Create Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName1);
            
            // Create a MessageProducer from the Session to the Topic or Queue
            MessageConsumer consumer = session.createConsumer(destination);
            
            // Wait for a message
            Message message = consumer.receive(1000);
            
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                logger.info("Received: " + text);
            } else {
            	logger.info("Received: " + message);
            }

            consumer.close();
            session.close();
            connection.close();
            
            logger.info("Fim do Teste");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        getMsgFromQueue(queueName5);
    }

}
