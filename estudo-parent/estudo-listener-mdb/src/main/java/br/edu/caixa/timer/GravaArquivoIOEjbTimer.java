package br.edu.caixa.timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import javax.ejb.Schedule;

import org.apache.log4j.Logger;

public class GravaArquivoIOEjbTimer {
	
	private static final Logger logger = Logger.getLogger(GravaArquivoIOEjbTimer.class);
	
	private PrintWriter output;
	private Random random = new Random();
	private String[] frases = {
			"Texto Frase 1",
			"Texto Frase 2",
			"Texto Frase 3",
			"Texto Frase 4",
			"Texto Frase 5",
			"Texto Frase 6",
			"Texto Frase 7",
			"Texto Frase 8",
			"Texto Frase 9"};	
	
	@Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)
	public void gravaTextoEmArquivo(){
		try {
			output = new PrintWriter(new File("/apl/ejbtimer_tmp.txt"));
			output.append(frases[random.nextInt(frases.length)]+"\n");
			output.close();
		} catch (FileNotFoundException e) {
			logger.error("",e);
		}
	}
	
	
}
