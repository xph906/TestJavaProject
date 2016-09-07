package nu.dataflow;

public class SecondMainClass {
	private MainClass mc;
	public SecondMainClass(MainClass mc){
		this.mc = mc;
	}
	
	public void callMainClassMethod2(){
		mc.method2();
	}
}
