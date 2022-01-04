package it.univpm.ancyb_diagnosticTool.test;

public class SimpleJavaObject {

	private String name;
	private int number;
	
	public SimpleJavaObject(int number, String name ) {
	
		this.name = name;
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	   public String printArrayList() {
	        return "number: " + this.getNumber() + "\n" +
	               "name: " + this.getName();
	   }
	
	
}
