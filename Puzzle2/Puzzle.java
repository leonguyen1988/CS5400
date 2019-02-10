import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
* Main class of Program
* Class will read the puzzle from text file and get 2 dimension puzzle
* BFTS class will read from it and operates it to finding the path
* 
* NOTE: Please puts the Puzzle1.txt into the folder with this java file to make
*       sure it can be read by the program. Otherwise, it will announce no find to be found
*/
public class Puzzle {
	public static void main(String argv[]) throws IOException, InterruptedException {
		/*
		*  Variable Declaration
		*/
		int number_of_worm = 0, rows, cols;
		
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter Puzzle File : ");
		String puzzleFile = reader.nextLine();
		
		// The Buffer will
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(puzzleFile));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}

		String input = br.readLine();
		String[] tokens = input.split(" ");

		// Input from text file
		number_of_worm = Integer.parseInt(tokens[2]);
		cols = Integer.parseInt(tokens[0]);
		rows = Integer.parseInt(tokens[1]);
		IDDFTS puzzle = new IDDFTS(rows,cols,number_of_worm);
		puzzle.blocks = new char[rows][cols];

		// Fill the board from the text file
		for (int row = 0; row < rows; row++) {
			int index = 0;
			input = br.readLine();
			for (int column = 0; column < cols; column++) {
				puzzle.blocks[row][column] = input.charAt(index);

				index += 2;
			}
		}
		puzzle.solvedIDDTFTS();
		reader.close();

	}
}
