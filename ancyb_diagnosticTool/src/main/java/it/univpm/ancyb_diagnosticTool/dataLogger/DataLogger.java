package it.univpm.ancyb_diagnosticTool.dataLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import it.univpm.ancyb_diagnosticTool.utilities.DataReceived;
import it.univpm.ancyb_diagnosticTool.utilities.Time;    

/**
 * <b>Classe</b> che implementa un file di testo che funge da data logger.
 * @author Giacomo Fiara
 *
 */
public class DataLogger {
	
	/**
	 * Stringa utilizzata per generare il nome del file
	 */
	private String fileName;

	/**
	 * <b>Costruttore</b> che genera il DataLogger che crea il nome del file basandosi sulla data e orario corrente
	 * ({@link it.univpm.ancyb_diagnosticTool.utilities.Time#currentDateTime() currentDateTime()})
	 * 
	 * @see it.univpm.ancyb_diagnosticTool.utilities.Time#currentDateTime() currentDateTime()
	 * @see java.io.File#File(String pathname) File(String)
	 */
	public DataLogger() {
		
		//creo una stringa con la data di questo momento
		
		String date = Time.currentDateTime();
		this.fileName = (date + "_DataLogger.txt");
		
		//creo il file
		try {
			File myDataLogger = new File(this.fileName);
		    if (myDataLogger.createNewFile()) {
		    System.out.println("File created: " + myDataLogger.getName());
		    } else System.out.println("File already exists.");
		} catch (IOException e) {
			System.err.println("ERRORE nella creazione datalogger");
			System.err.println("Exception: " + e);
		}
		
		//inserisco l'header
		try {
			//crea lo stream ( formattato ) di output e metto come nome la data e l'orario in cui l'ho creato
			PrintWriter file_output =
					new PrintWriter(new BufferedWriter (
							new FileWriter (this.fileName, true)));
			
			//scrive i valori usando println scrivo come prima riga un semplice header
			file_output.println(date + " - DataLogger" + "\n" + ">--------------------------------<");
			
			// chiude lo stream di output
			file_output.close ();
			}
			catch (IOException e) { // in caso di errori ...
			System.out.println(" ERRORE nell'inserimento dell'header");
			System.out.println(e);
			}
		
	}
	
	/**
	 * <b>Metodo</b> che trascrive una stringa sul data logger.
	 * 
	 * <b>ATTENZIONE</b> - questo metodo non è stato implementato nell'applicativo.
	 * 
	 * @param str stringa che si vuole stampare sul file txt del data logger.
	 */
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
        } catch (IOException e) {
        	System.out.println(" ERRORE metodo DataLogger.write");
        	System.out.println(e);
        }
		
	}
	
	/**
	 * <b>Metodo</b> che trascrive una oggetto di tipo {@link it.univpm.ancyb_diagnosticTool.utilities.DataReceived DataReceived}
	 * sul data logger tramite metodotoString().
	 * @param data dato che si vuole stampare sul file txt del data logger.
	 */
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
		} catch (IOException e) { // in caso di errori ...
			System.out.println(" ERRORE metodo DataLogger.write");
        	System.out.println(e);
		}
		
	}
	
}