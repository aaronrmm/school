
public class DebugPrint {
	public static final boolean on = false;
	
	public static void print_debug(String s){
		if (on)
			System.out.println(s);
	}
}
