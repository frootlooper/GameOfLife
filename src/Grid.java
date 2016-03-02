import java.util.Random;
import java.util.ArrayList;
import java.io.*;

/**
 * Created by Gwen Macnichol on 1/22/2016.
 * 
 * This class creates a Grid in the form of a two dimensional array.
 * It can be any size, but defaults to 6 by 8.
 * Each entry in the grid is called a cell and can be set to either 0 or 1. 
 * 0 represents a dead cell.
 * 1 represents a live cell.
 * No other values are used.
 * This will be assumed for all method descriptions.
 */
public class Grid {
	
    private ArrayList<ArrayList<Integer>> grid;

    /*
     * Instantiates the grid to the default size 6x8 with all cells set to dead.
     */
    Grid () {
        grid = new ArrayList<ArrayList<Integer>>();
        for (int x=0; x<6; x++) {
        	grid.add(new ArrayList<Integer>());
        	for (int y=0; y<8; y++) {
        		grid.get(x).add(0);
        	}
        }
    }

    /*
     * Instantiates the grid to a custom size with all cells set to dead.
     * If either rows or cols is a negative number, the grid will be 0x0.
     * @param rows The number of rows in the grid
     * @param cols The number of columns in the grid
     */
    Grid (int rows, int cols) {
    	if (rows < 0 || cols < 0) {
    		rows = 0;
    		cols = 0;
    	}
    	grid = new ArrayList<ArrayList<Integer>>();
    	for (int x=0; x<rows; x++) {
    		grid.add(new ArrayList<Integer>());
    		for (int y=0; y<cols; y++) {
    			grid.get(x).add(0);
    		}
    	}
    }
    
    /*
     * Creates and populates the Grid according to an input file.
     * The grid in the input file must contain nothing but the content of the new Grid
     * The data should be formatted like a grid with only the two characters that are also passed in
     * Using these characters, parse through the file and set the grid.
     * @param fileLocation The location of the file to be read in
     * @param alive The character representing the living cells in the input file
     * @param dead The character representing the dead cells in the input file
     */
    Grid(String fileLocation, char alive, char dead) throws FileNotFoundException, IOException {
    	String line = null;
    	grid = new ArrayList<ArrayList<Integer>>();
    	try {
    		BufferedReader inFile = new BufferedReader(new FileReader(fileLocation));
    		boolean invalidFormat = false;
    		boolean fileEmpty = true;
    		while ((line = inFile.readLine()) != null) {
    			fileEmpty = false;
    			ArrayList<Integer> row = new ArrayList<Integer>();
    			for (char c : line.toCharArray()) {
    				if (c == alive) {
    					row.add(1);
    				} else if (c == dead) {
    					row.add(0);
    				} else {
    					invalidFormat = true;
    				}
    			}
    			if (grid.size() > 0 && grid.get(0).size() != row.size()) {
    				System.out.println("File is in an invalid format.");
    				grid = new ArrayList<ArrayList<Integer>>();
    				return;
    			}
    			grid.add(row);
    		}
    		if (invalidFormat) {
    			System.out.println("File is in an invalid format.");
    			grid = new ArrayList<ArrayList<Integer>>();
    		}
    		if (fileEmpty) {
    			System.out.println("File is empty.");
    			grid = new ArrayList<ArrayList<Integer>>();
    		}
    		inFile.close();
    	}
    	catch (FileNotFoundException e) {
    		System.out.println("File was not found.");
    	}
    	catch (IOException e) {
    		System.out.println("Error reading file.");
    	}
    }
    
    /*
     * Returns the number of rows the grid has.
     * @return The number of rows in the Grid
     */
    public int getNumRows() {
        return grid.size();
    }

    /*
     * Returns the number of columns the grid has.
     * @return The number of columns in the Grid
     */
    public int getNumCols() {
    	int result = 0;
    	if (getNumRows()>0) {
    		result = grid.get(0).size();
    	}
    	return result;
    }

    /*
     * Checks whether there are any living cells in the grid.
     * @return Whether there are any live cells in the Grid
     */
    public boolean empty() {
        boolean result = true;
        for(int x=0; x<getNumRows(); x++) {
            for (int y=0; y<getNumCols(); y++) {
                if (grid.get(x).get(y) == 1) {
                    result = false;
                }
            }
        }
        return result;
    }

    /*
     * Populates the Grid with a random distribution of living cells.
     * The probability a cell is set to live is 25%.
     */
    public void generateRandom() {
        for (int x = 0; x < getNumRows(); x++) {
            for (int y = 0; y < getNumCols(); y++) {
                Random r = new Random();
                int check = r.nextInt(3);
                if (check == 0) {
                    grid.get(x).set(y, 1);
                }
            }
        }
    }

    /*
     * Prints out the contents of the grid to the console.
     * "X" represents live cells and "_" represents dead cells.
     */
    public void printGrid() {
    	for (ArrayList<Integer> row : grid) {
    		for (Integer cell : row) {
    			if (cell == 1) {
    				System.out.print("X ");
    			} else {
    				System.out.print("_ ");
    			}
    		}
    		System.out.println();
    	}
    	if (getNumRows() == 0) {
    		System.out.println("The grid is 0x0");
    	}
    }

    /*
     * Calculate the number of live cell neighbors given a specific cell location.
     * A cell's neighbors are those cells which are horizontally, vertically, or
     * diagonally adjacent. Most cells will have eight neighbors. Cells located on
     * the edge of the grid will have fewer.
     * 
     * row and col are assumed to be valid since this is a private function.
     * @param row The row of the cell
     * @param col The column of the cell
     * @return The number of live neighbors for a specific cell at row, col
     */
    private int numLiveNeighbors(int row, int col) {
        int result = 0;
        if (row>0 && col>0 && grid.get(row-1).get(col-1) == 1) result++;
        if (row>0 && grid.get(row-1).get(col) == 1) result++;
        if (row>0 && col<getNumCols()-1 && grid.get(row-1).get(col+1) == 1) result++;
        if (col>0 && grid.get(row).get(col-1) == 1) result++;
        if (col<getNumCols()-1 && grid.get(row).get(col+1) == 1) result++;
        if (row<getNumRows()-1 && col>0 && grid.get(row+1).get(col-1) == 1) result++;
        if (row<getNumRows()-1 && grid.get(row+1).get(col) == 1) result++;
        if (row<getNumRows()-1 && col<getNumCols()-1 && grid.get(row+1).get(col+1) == 1) result++;
        return result;
    }

    /*
     * Using the existing population of the grid, generate the next generation
     * by following the rules of Conway's Game Of Life.
     * 1. Any live cell with fewer than two live neighbors dies
     * 2. Any live cell with more than three live neighbors dies
     * 3. Any live cell with two or three live neighbors lives
     * 4. Any dead cell with exactly three live neighbors becomes live
     */
    public void tick() {
    	if (!grid.isEmpty()) {
    		int[][] neighbors = new int[getNumRows()][getNumCols()];

    		for (int x=0; x<getNumRows(); x++) {
    			for (int y=0; y<getNumCols(); y++) {
    				neighbors[x][y] = numLiveNeighbors(x,y);
    			}
    		}

    		for (int x=0; x<getNumRows(); x++) {
    			for (int y=0; y<getNumCols(); y++) {
    				int n = neighbors[x][y];
    				if (n == 3) {
    					grid.get(x).set(y, 1);
    				} else if (n < 2) {
    					grid.get(x).set(y, 0);
    				} else if (n > 3) {
    					grid.get(x).set(y, 0);
    				}
    			}
    		}
        }
    }
}
