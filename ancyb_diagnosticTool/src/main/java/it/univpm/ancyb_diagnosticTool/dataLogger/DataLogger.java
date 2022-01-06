package it.univpm.ancyb_diagnosticTool.dataLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;    

//TODO creare superclasse da cui eredita.

public class DataLogger {
	
	private String fileName;

	public DataLogger() {
		
		//creo una stringa con la data di questo momento
		
		String date = Time.currentDateTime();
		this.fileName = (date + "_DataLogger.txt");
		
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
			file_output.println(date + " - DataLogger" + "\n" + ">--------------------------------<");
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
        	file_output.println(str + '\n');
        	// chiude lo stream di output
        	file_output.close ();
        	}
        	catch (IOException e) {
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
        	file_output.print(data.toString());
        	// chiude lo stream di output
        	file_output.close ();
        	}
        	catch (IOException e) { // in caso di errori ...
        	System.out.println(" ERRORE metodo DataLogger.write");
        	System.out.println(e);
        	}
		
	}
	
}