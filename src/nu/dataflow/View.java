package nu.dataflow;

import java.util.Set;

public class View {
	public String field1;
	static String fieldStatic;
	public Set fielldSet;
	public int[] arr = new int[5];
	public EventListener listener = new OnClickEventListener();
	
	static public View findViewById(int id){
		if(id%2 == 0)
			return new Button();
		else
			return new CheckBox(false);
	}
	public void setOnClickEventListener(EventListener eventListener){
		
	}
}
