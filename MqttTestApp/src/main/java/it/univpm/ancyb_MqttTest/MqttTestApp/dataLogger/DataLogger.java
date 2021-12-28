package it.univpm.ancyb_MqttTest.MqttTestApp.dataLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import it.univpm.ancyb_MqttTest.MqttTestApp.model.DataReceived;    

//TODO creare superclasse da cui eredita.

public class DataLogger {
	
	private String fileName;

	public DataLogger() {
		
		//creo una stringa con la data di questo momento
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH.mm.ss");
		LocalDateTime now = LocalDateTime.now(); 
		String date = dtf.format(now);
		this.fileName = (date + ".txt");
		
		//creo il file
	
		try {
		      File myDataLogger = new File(this.fileName);
		      if (myDataLogger.createNewFile()) {
		        System.out.println("File created: " + myDataLogger.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("ERRORE nella creazione datalogger");
		      e.printStackTrace();
		      System.out.println(e);
		    }
		
		//inserisco l'header
		
		try {
			/* crea lo stream ( formattato ) di output e metto 
			 * come nome la data e l'orario in cui l'ho creato
			 */
			PrintWriter file_output =
					new PrintWriter(new BufferedWriter (
							new FileWriter (this.fileName, true)));
			/* scrive i valori usando println
			 * scrivo come prima riga un semplice header
			 */
			file_output.println(date + " - DataLogger");
			// chiude lo stream di output
			file_output.close ();
			}
			catch (IOException e) { // in caso di errori ...
			System.out.println(" ERRORE nell'inserimento dell'header");
			System.out.println(e);
			}
		
	}
	
	public void write(String str) {
		
		//inserisco una nuova registrazione
		
		try {
        	// crea lo stream ( formattato ) di output
        	PrintWriter file_output =
        			new PrintWriter(new BufferedWriter (
        					new FileWriter (this.fileName, true)));
        	// scrive i valori usando println
        	file_output.println(str);
        	// chiude lo stream di output
        	file_output.close ();
        	}
        	catch (IOException e) { // in caso di errori ...
        	System.out.println(" ERRORE metodo DataLogger.write");
        	System.out.println(e);
        	}
		
	}
	
	public void write(DataReceived data) {
		
		//inserisco una nuova registrazione
		
		try {
        	// crea lo stream ( formattato ) di output
        	PrintWriter file_output =
        			new PrintWriter(new BufferedWriter (
        					new FileWriter (this.fileName, true)));
        	// scrive i valori usando println
        	file_output.print(data.getMac() + " ");
        	file_output.print(data.getDate() + " ");
        	file_output.print(data.getLatitude() + " ");
        	file_output.print(data.getLongitude() + " ");
        	file_output.println(data.getQualPos());
        	// chiude lo stream di output
        	file_output.close ();
        	}
        	catch (IOException e) { // in caso di errori ...
        	System.out.println(" ERRORE metodo DataLogger.write");
        	System.out.println(e);
        	}
		
	}
	
}