import java.io.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Grid myGrid = null;
		boolean run = true;
		int maxGens = 50;
		
		if (args.length > 0) {
			if (args[0].equals("-help")) {
				System.out.println("Usage: Main [options]\n\n"
						+"\t-generate [numRows] [numColumns]\n"
						+"\t-file filelocation [aliveCharacter] [deadCharacter]\n"
						+"\nNo arguments will result in a randomly generated default sized grid.");
				run = false;
			} else if (args[0].equals("-generate")) {
				if (args.length == 3) {
					//Create custom size grid
					int row = Integer.parseInt(args[1]);
					int col = Integer.parseInt(args[2]);
					myGrid = new Grid(row, col);
					myGrid.generateRandom();
				} else if (args.length == 1){
					//Create a default size grid
					myGrid = new Grid();
					myGrid.generateRandom();
				} else {
					run = false;
					System.out.println("Wrong number of arugments. See '-help'");
				}
			} else if (args[0].equals("-file")) {
				if (args.length > 1) {
					String fileLoc = args[1];
					if (args.length == 2) {
						//Create grid from file using default character representation ('O'=alive, '.'=dead)
						myGrid = new Grid(fileLoc, 'O', '.');
						if (myGrid.getNumRows() == 0) {
							run = false;
						}
					} else {
						//Create grid from file using input character representation
						if (args.length == 4) {
							char alive = args[2].charAt(0);
							char dead = args[3].charAt(0);
							myGrid = new Grid(fileLoc, alive, dead);
							if (myGrid.getNumRows() == 0) {
								run = false;
							}
						} else {
							run = false;
							System.out.println("Wrong number of arugments. See '-help'");
						}
					}
				} else {
					System.out.println("You must specify the file location");
					run = false;
				}
			} else {
				System.out.println("Please enter a valid argument. See -help");
				run = false;
			}
		} else {
			// Run generated population with default size
			myGrid = new Grid();
			myGrid.generateRandom();
		}
		
		if (run == true & myGrid != null && (myGrid.getNumRows() == 0 | myGrid.getNumCols() == 0)) {
			System.out.println("The grid is 0x0");
		}
		
		//Now that grid is generated and populated with generation 0, run continuous evolution
		if (run & myGrid != null && myGrid.getNumRows() != 0 & myGrid.getNumCols() != 0) {
			int gen = 0;
			do {
				System.out.println("Generation "+gen);
				myGrid.printGrid();
				myGrid.tick();
				gen++;
				maxGens--;
			} while (!myGrid.empty() & maxGens > 0);
			System.out.println("Generation "+gen);
			myGrid.printGrid();
		}
	}
}