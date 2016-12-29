public class ElementAlreadyThereException extends Exception {
	public Object value;
	
	public ElementAlreadyThereException(Object value) {
		this.value = value;
		System.out.println("Error: Element with value " + value.toString() + " already exists at this location in three");
	}
	
	public ElementAlreadyThereException(String msg) {
		System.out.println(msg);
	}
}