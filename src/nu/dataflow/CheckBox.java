package nu.dataflow;

public class CheckBox extends View {
	boolean flag = false;
	public CheckBox(boolean f){
		flag = f;
	}
	public boolean isChecked(){
		return flag;
	}
}
