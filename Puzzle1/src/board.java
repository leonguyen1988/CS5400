import java.util.ArrayList;

public class board {
	
	private  int width ;
	
	private  int height ;
	
	private  int numberOfWorms;
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int Width) {
		this.width = Width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int Height) {
		this.height = Height;
	}
	
	public int get_number_of_worm() {
		return this.numberOfWorms;
	}
	
	public void set_number_of_worm(int worms) {
		this.numberOfWorms = worms;
	}
	


	public  String[][] blocks = null;
		
}

