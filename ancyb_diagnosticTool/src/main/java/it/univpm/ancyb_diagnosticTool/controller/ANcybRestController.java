package it.univpm.ancyb_diagnosticTool.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.univpm.ancyb_diagnosticTool.datasim.AncybFishDataSim;
import it.univpm.ancyb_diagnosticTool.model.ForecastObject;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolDataManager;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolService;
import it.univpm.ancyb_diagnosticTool.service.AncybDiagnosticToolServiceImpl;	// da controllare che non sia illecito
import it.univpm.ancyb_diagnosticTool.utilities.Time;

@RestController
public class ANcybRestController {
	@Autowired
	AncybDiagnosticToolService a;

	AncybFishDataSim dataSim = new AncybFishDataSim();	// da togliere finito il testing

		
	@RequestMapping(value = "/{macAddr}/forecast")
	public ResponseEntity<Object> getForecast(@PathVariable("macAddr") String macAddr) {

		JSONObject j = a.getRealTimeForecast(macAddr).toJSON();
		return new ResponseEntity<>(j.toMap(), HttpStatus.OK);
		
		//TODO sistema la parte su questa rotta
		//TODO rivedi tutto il codice fatto fin'ora
		//TODO fai UML fatto bene anche per le altre rotte e altre classi
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