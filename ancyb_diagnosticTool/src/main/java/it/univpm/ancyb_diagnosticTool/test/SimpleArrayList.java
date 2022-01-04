package it.univpm.ancyb_diagnosticTool.test;

import java.util.ArrayList;

public class SimpleArrayList {

	private ArrayList<SimpleJavaObject> list = new ArrayList<SimpleJavaObject>();
	
	public SimpleArrayList(ArrayList<SimpleJavaObject> list) {
		
		this.list = list;
		
	}
	
	
	public void addToList(SimpleJavaObject obj) {
		list.add(obj);
	}
	
	
	/*public void printSimpleArrayList() {

	    for(SimpleJavaObject s : list) {
	        System.out.println(s);
	    }
	    
	  }
	*/
	
}
