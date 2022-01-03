package it.univpm.ancyb_diagnosticTool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;

@RestController
public class ANcybRestController {
	@Autowired
	AncybDiagnosticToolService ancybdiagnostictoolservice;

	/* VARIABILI DI PROVA, questi dati verranno sostituiti con gli appositi getter */
	String macAddress = "a4:cf:12:76:76:95";
	double lat = 43.574998;
	double lng = 13.492686;
	String time = "2022-01-05T00:00:00+00:00";	//TODO fai una stringa di conversione del tempo tra me e jack? (in caso da mettere in "utils")
	

	@RequestMapping(value = "/{macAddr}/forecast")
	public ResponseEntity<Object> getJSONData() {	// da cambiare con "getJSONForecast" per dargli l'oggetto forecastobject
		return new ResponseEntity<>("Operazione andata a buon fine", HttpStatus.OK);
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