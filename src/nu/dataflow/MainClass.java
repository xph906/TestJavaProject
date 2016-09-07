package nu.dataflow;

import java.util.HashSet;
import java.util.List;

public class MainClass {
	public static void main(String[] args) {
		MainClass first = new MainClass();
		MainClass second = new MainClass();
		MainClass third = new MainClass();
		
		//when processing method2, we need to put method1 back to re-analyze
		//because the fields have been changed.
		//However, when adding method1, do not adding second.method1.
		first.method1("abc");
		first.method2();
		
		//second.method1 should only be included once.
		second.method1("def");
		
		third.method1("hgi");
		SecondMainClass smc = new SecondMainClass(third);
		//this statement should re-add third.method1.
		smc.callMainClassMethod2();
		MainClass.innerClass inn = first.new innerClass(2);
		inn.innerMethod();
		
	}
	
	private String strRead;
	private String strWrite;
	private String[] arrReady;
	private String[] arrNonReady;
	
	private Button btn1;
	private Button btn2;
	private CheckBox cb;
	private int integer;
	
	private OnClickEventListener listener;
	
	public MainClass(){
		//read  : 
		//write : this.arr, this.strRead, this.strWrite. 
		arrReady = new String[10];
		strRead = new String();
		strWrite = new String();
		
		listener = new OnClickEventListener();
		int l = copyConstant();
		setInteger(l);
	}
	
	public void handleInnerClass(){
		innerClass ic = new innerClass((int)System.currentTimeMillis());
		ic.innerMethod();
	}
	
	public void readString(){
		//read : this.strRead, this.strWrite
		System.out.println(strRead+strWrite);
	}
	
	
	public void method1(String p){
		//read  : this.btn1, this.btn2, this.listner
		//write : this.btn1, this.btn2
		if(btn1 != null)
			btn1.setOnClickEventListener(new EventListener(){
				String localStr = "abc";
				String paramStr = p+'-';
				@Override
				public void onClick(View v) {
					//write : this.this.strWrite
					//read  : this.localStr, this.paramStr, this.this.strRead
					strWrite = localStr+paramStr+strRead;
					System.out.println(strWrite);
				}
			});
		if(btn2 != null)
			btn2.setOnClickEventListener(listener);
	}
	
	public int copyConstant(){
		int x= 200;
		int y = x;
		return y;
	}
	
	private void setInteger(int x){
		integer = x;
		System.out.println(integer);
	}
	
	public void method2(){
		//write: this.btn1, this.btn2, this.cb
		btn1 = (Button)View.findViewById(0);
		btn2 = (Button)View.findViewById(2);
		cb = (CheckBox)View.findViewById(1);
		
	}
	
	//test instance ref
	public void accessIF(){
		boolean x = System.currentTimeMillis() % 2 == 0;
		String val = "";
		if(x){
			strRead = "ABC";
		}
		val += strRead;
		System.out.println(val);
	}
	
	//test array write.
	public String modifyArray(){
		if(System.currentTimeMillis()%2==0)
			arrNonReady = new String[4];
		arrNonReady = new String[30];
		for(int i=0; i<arrNonReady.length; i++)
			if (i %2 ==0)
				arrNonReady[i] = btn1.toString()+arrReady[i];
			else {
				arrNonReady[i] = "a"+i;
				arrReady[i] = arrNonReady[i];
			}
		return arrNonReady[0];
	}
	
	//test modify param ref via param
	public View modifyParam(String str, List<String> rs, View v1, View v2){
		if(str.length() > 100){
			rs.add(str);
			View.fieldStatic = str;
		}
		else{
			String[] tmp = str.split("abc");
			for(int i=0; i<tmp.length; i++)
				rs.add(tmp[i]);
			v1 = new Button();
			v2.field1 = String.valueOf(tmp.length);
		}
		View v = v1;
		if(str.length() > 100){
			v1 = v2;
			v = v2;
		}
		else
			v = v1;
		
		v.fielldSet = new HashSet(3);
		
		return v1;
	}
	
	public void modifyField(){
		View v = btn1;
		v.arr[3] = strRead.length();
		v.arr[2] = 200;
		v.arr[0] = v.arr[4];
		OnClickEventListener ooo = (OnClickEventListener)v.listener;
		strWrite = "abcdef";
		View v2 = v;
		Button v3 = (Button)v;
		v3.field2 = (int)System.currentTimeMillis();
		int x = (int) (System.currentTimeMillis()%3 + 1);
		//v.arr[1] = ooo.val.hashCode();
		if(x == 1)
			ooo.val = strRead;
		else if(x==2)
			ooo.val = "ABC";
		v.arr[1] = ooo.val.hashCode();
		//ooo.val = "12345";
		//v2.field1 = "1234";
		Button v4 = new Button();
		if(x==1)
		 v4 = (Button)v;
		System.out.println(ooo.val+" "+v.arr[x]+" "+ooo.val+strWrite);
		System.out.println(v4.field2);
	}
	
	public class innerClass{
		private int v1;
		public innerClass(int v1){
			this.v1 = v1;
		}
		public void innerMethod(){
			strWrite = strRead+ String.valueOf(v1);
		}
	}
}
