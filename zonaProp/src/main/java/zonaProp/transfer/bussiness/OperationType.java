package zonaProp.transfer.bussiness;

public enum OperationType {
	SELL("Venta",1), RENT("Alquiler",2);

	private String name;
	private int number;

	
	
	private OperationType(String name, int number) {
		this.name = name;
		this.number=number;
	}

	public String toString(){
		return name;
	}

	public int getNumber() {
		return this.number;
	}

	public String getName() {
		return this.name;
	}

}
