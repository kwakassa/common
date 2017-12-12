package br.gov.caixa.teste.mq;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author c096489
 */
public class CreateQueuesApacheMQMain {

    private static Logger logger = Logger.getLogger(CreateQueuesApacheMQMain.class);
    
    private static List<String> listQueues = new ArrayList<String>();
    
    static{
    	listQueues.add("SIBAR.REQ.AUTENTICACAO_BIOMETRICA");listQueues.add("SIBAR.RSP.AUTENTICACAO_BIOMETRICA");
    	listQueues.add("SIBAR.REQ.CONSULTA_BENEFICIARIO_INSS");listQueues.add("SIBAR.RSP.CONSULTA_BENEFICIARIO_INSS");
    	listQueues.add("SIBAR.REQ.CONSULTA_CONTA_SID09");listQueues.add("SIBAR.RSP.CONSULTA_CONTA_SID09");
    	listQueues.add("SIBAR.REQ.CONSULTA_SALDO");listQueues.add("SIBAR.RSP.CONSULTA_SALDO");
    	listQueues.add("SIBAR.REQ.CONTROLE_CORRESPONDENTE");listQueues.add("SIBAR.RSP.CONTROLE_CORRESPONDENTE");
    	listQueues.add("SIBAR.REQ.PAGAMENTO_BENEFICIO_SOCIAL");listQueues.add("SIBAR.RSP.PAGAMENTO_BENEFICIO_SOCIAL");
    	listQueues.add("SIBAR.REQ.PAGAMENTO_FGTS");listQueues.add("SIBAR.RSP.PAGAMENTO_FGTS");
    	listQueues.add("SIBAR.REQ.PAGAMENTO_PIS");listQueues.add("SIBAR.RSP.PAGAMENTO_PIS");
    	listQueues.add("SIBAR.REQ.PAGAMENTO_SEGURO_DESEMPREGO");listQueues.add("SIBAR.RSP.PAGAMENTO_SEGURO_DESEMPREGO");
    	listQueues.add("SIBAR.REQ.VALIDA_CARTAO");listQueues.add("SIBAR.RSP.VALIDA_CARTAO");
    	listQueues.add("SIBAR.REQ.VALIDA_PERMISSAO");listQueues.add("SIBAR.RSP.VALIDA_PERMISSAO");
    	listQueues.add("SIBAR.REQ.VALIDA_SENHA");listQueues.add("SIBAR.RSP.VALIDA_SENHA");
    	listQueues.add("SIMTX.REQ.AGENCIA");listQueues.add("SIMTX.RSP.AGENCIA");
    	listQueues.add("SIMTX.REQ.AUTOATENDIMENTO");listQueues.add("SIMTX.RSP.AUTOATENDIMENTO");
    	listQueues.add("SIMTX.REQ.CAIXAAQUI");listQueues.add("SIMTX.RSP.CAIXAAQUI");
    	listQueues.add("SIMTX.REQ.INTERNETBANKING");listQueues.add("SIMTX.RSP.INTERNETBANKING");
    	listQueues.add("SIMTX.REQ.MULTIAPLICACAO");listQueues.add("SIMTX.RSP.MULTIAPLICACAO");
    	listQueues.add("SIMTX.REQ.URA");listQueues.add("SIMTX.RSP.URA");
    	listQueues.add("SIMTX.REQ.RESOLVE_PENDENCIA");listQueues.add("SIMTX.RSP.RESOLVE_PENDENCIA");
    	listQueues.add("SIAPX.REQ.CONSIGNACAO");listQueues.add("SIAPX.RSP.CONSIGNACAO");
    	listQueues.add("SICCO.REQ.RECEBE_INFORMACAO");listQueues.add("SICCO.RSP.RECEBE_INFORMACAO");
    	listQueues.add("SIISO.REQ.CONSULTA_DOCUMENTO");listQueues.add("SIISO.RSP.CONSULTA_DOCUMENTO");
    }
    
    public static void createQueuesActiveMQ(){
    	try {
            logger.info("Inicio de Envio de Mensagem teste para file MQ");
            String defaultBrokerUrl = ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(defaultBrokerUrl);
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            
            // Create Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = null;
            MessageProducer producer = null;
            for (String queueName : listQueues) {
            	destination = session.createQueue(queueName);            
            	// Create a MessageProducer from the Session to the Topic or Queue
            	producer = session.createProducer(destination);				
			}

            session.close();
            connection.close();
            logger.info("Fim do Teste");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        createQueuesActiveMQ();
    }

}
