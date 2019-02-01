
import java.util.ArrayList;

public class Wriggle {
	public class Head {
		public final String Up = "U";
		public final String Down = "D";
		public final String Left = "L";
		public final String Right = "R";
	}
	
	public class Body{
		public final String Up = "^";
		public final String Down ="v";
		public final String Left = "<";
		public final String Right = ">";
	}
	
	public class Tail{
		ArrayList<Integer> id = new ArrayList<Integer>(10);
	}
	
	
}
