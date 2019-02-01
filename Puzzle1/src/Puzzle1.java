import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Puzzle1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		 * Variable Declaration
		 * 
		 * */
        Scanner read = new Scanner(System.in);
        FileReader fr = null;
        board Board = new board() ;
        String[] line1;
        
        System.out.println("Enter your file name: ");
        String line = read.nextLine();
        read.close();
        
        try {
        	fr = new FileReader("puzzle1.txt");
        }catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        }
        
        BufferedReader ifile = new BufferedReader(fr);
        
        try {
    		line1 = ifile.readLine().split(" ");
    		
            Board.setHeight(Integer.parseInt(line1[0]));
            
            Board.setWidth(Integer.parseInt(line1[1]));
            
            Board.set_number_of_worm(Integer.parseInt(line1[2]));
            
            Board.blocks = new String[Board.getWidth()][Board.getHeight()];
            /*
             * Fill puzzle by line by line
             * 
             * */
            for (int row = 0; row < Board.getWidth(); row++)
            {
            	String[] singeLine = ifile.readLine().split(" ");
            	int index = 0;
            	for (int column = 0; column < Board.getHeight(); column++)
            	{
            		Board.blocks[row][column] = singeLine[index];
//            		System.out.print(Board.blocks[row][column] + " ");
            		index++;
            	}
//            	System.out.println();
            }
            
          
        
        } catch(IOException e) {
        	e.printStackTrace();
        }
        ifile.close();
        /*
         * Get line 1 from text file which includes height, width and number of wriggles
         * splits by space character
         * */
      

        
	}
	
	

}
