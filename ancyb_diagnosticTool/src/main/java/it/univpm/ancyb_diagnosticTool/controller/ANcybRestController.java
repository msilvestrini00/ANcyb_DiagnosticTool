package it.univpm.ancyb_diagnosticTool.controller;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.ancyb_diagnosticTool.Exception.FilterFailure;
import it.univpm.ancyb_diagnosticTool.Exception.VersionMismatch;
import it.univpm.ancyb_diagnosticTool.mqtt.dataReceived.ANcybFishData;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;

@RestController
public class ANcybRestController {
	@Autowired
	AncybDiagnosticToolService a;
	private JSONObject j;
	ArrayList<ANcybFishData> list;

	//AncybFishDataSim dataSim = new AncybFishDataSim();	// da togliere finito il testing

	
	@RequestMapping(value = "/{macAddr}/forecast")
	public ResponseEntity<Object> getForecast(@PathVariable("macAddr") String macAddr) {
		/*
		//VECCHIA ROTTA
		j = a.getRealTimeForecast(macAddr).toJSON();
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		//TODO sistema la parte su questa rotta
		//TODO rivedi tutto il codice fatto fin'ora
		//TODO fai UML fatto bene anche per le altre rotte e altre classi
		*/
		/**
		 * NUOVA ROTTA
		 */
		j = null;
		try {
			j = a.getRealTimeForecast(macAddr).toJSON();
		} catch (FilterFailure | VersionMismatch e) {
			System.err.println("Exception" + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		
	}
	
	/**
	 * Rotta che restituisce l'ultima posizione inviata dal dispositivo corrispondente
	 * al Mac address inserito come parametro.
	 * @param macAddr
	 * @return
	 */
	@RequestMapping(value = "/{macAddr}/device/last", method = RequestMethod.GET)
	public ResponseEntity<Object> getLastPosition(@PathVariable("macAddr") String macAddr) {
		
		j = null;
		try {
			j = a.getLastTimePosition(macAddr).toJSON();
			return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure e) {
			System.err.println("Exception: " + e);
			return new ResponseEntity<>(j.toMap(), HttpStatus.BAD_REQUEST);
		}
		
	}

	@RequestMapping(value = "/{macAddr}/device/all", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllPositions(@PathVariable("macAddr") String macAddr) {
		
		try {
			list = a.getAllPositions(macAddr);
			return new ResponseEntity<>(list.hashCode(), HttpStatus.OK);
		} catch (VersionMismatch | FilterFailure e) {
			System.err.println("Exception: " + e);
			//TODO ovviamente non funziona, trova il mondo di fargli passare l'arraylist
			return new ResponseEntity<>( list, HttpStatus.BAD_REQUEST);
		}
		
	}

}





/*

public class ProductController {
	
	
	@RequestMapping(value = "/products", method = RequestMethod.GET) // scrivendo method così faccio in modo da poter prendere solo le chiamate di tipo GET, e non le altre
	public ResponseEntity<Object> getProduct() {
		return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object>
	updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		
		productService.updateProduct(id,  product);
		return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<Object> createproduct(@RequestBody Product product) {
		productService.createProduct(product);
		return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
	}
}
*/