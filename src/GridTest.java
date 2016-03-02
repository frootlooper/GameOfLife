import static org.junit.Assert.*;
import java.io.*;

import org.junit.*;

public class GridTest {
	
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}
	
	@Test
	public final void testGrid() {
		Grid testGrid = new Grid();
		assertEquals(6, testGrid.getNumRows());
		assertEquals(8, testGrid.getNumCols());
		assertTrue(testGrid.empty());
	}

	@Test
	public final void testGridIntInt() {
		//Test an empty grid
		Grid testGrid = new Grid(0,0);
		assertEquals(0, testGrid.getNumRows());
		assertEquals(0, testGrid.getNumCols());
		assertTrue(testGrid.empty());
		//Test a small grid
		testGrid = new Grid(2,3);
		assertEquals(2, testGrid.getNumRows());
		assertEquals(3, testGrid.getNumCols());
		assertTrue(testGrid.empty());
		//Test a large grid
		testGrid = new Grid (25, 30);
		assertEquals(25, testGrid.getNumRows());
		assertEquals(30, testGrid.getNumCols());
		assertTrue(testGrid.empty());
		//Test negative numbers
		testGrid = new Grid(-1, 3);
		assertEquals(0, testGrid.getNumRows());
		assertEquals(0, testGrid.getNumCols());
		assertTrue(testGrid.empty());
		testGrid = new Grid(4, -2);
		assertEquals(0, testGrid.getNumRows());
		assertEquals(0, testGrid.getNumCols());
		assertTrue(testGrid.empty());
		testGrid = new Grid(-3, -1);
		assertEquals(0, testGrid.getNumRows());
		assertEquals(0, testGrid.getNumCols());
		assertTrue(testGrid.empty());
	}
	
	@Test
	public final void testGridStringCharCharZeroGrid() throws FileNotFoundException, IOException {
		//0x0 grid
		Grid testGrid = new Grid("./InputFiles/GameOfLife/ZeroGrid.txt", 'O','.');
		assertEquals("File is empty.", out.toString().trim());
	}
	
	@Test
	public final void testGridStringCharCharSmallGrid() throws FileNotFoundException, IOException {
		//Small grid
		Grid testGrid = new Grid("./InputFiles/GameOfLife/SmallGrid.txt", 'O','.');
		testGrid.printGrid();
		assertEquals("______X_XXX___X_______X____________XX______XX___", out.toString().replaceAll("\\s", ""));
	}
	
	@Test
	public final void testGridStringCharCharLargeGrid() throws FileNotFoundException, IOException {
		//Large grid
		Grid testGrid = new Grid("./InputFiles/GameOfLife/LargeGrid.txt", 'O','.');
		testGrid.printGrid();
		assertEquals("______X___XX_____________XXX___X______XXX___X__XXX______X_XX______X____________________________________XX_______________________XX__________________________X__________________XXX___X________________________X______________________________________________XX_________XX___X________XX___XXXXXXXXX_____XXX", out.toString().replaceAll("\\s",""));
	}
	
	@Test
	public final void testGridStringCharCharInvalidFileFormat() throws FileNotFoundException, IOException {
		//Invalid file format
		Grid testGrid = new Grid("./InputFiles/GameOfLife/InvalidGrid.txt", 'O','.');
		assertEquals("File is in an invalid format.", out.toString().trim());
	}
	
	@Test
	public final void testGridStringCharCharInvalidFileLocation() throws FileNotFoundException, IOException {
		//Invalid file location
		Grid testGrid = new Grid("./InputFiles/GameOfLife/nofilehere", 'O', '.');
		assertEquals("File was not found.", out.toString().trim());
	}

	@Test
	public final void testGetNumRows() {
		//Zero rows
		Grid testGrid = new Grid(0, 0);
		assertEquals(0, testGrid.getNumRows());
		//Small number of rows
		testGrid = new Grid(4, 0);
		assertEquals(4, testGrid.getNumRows());
		//Large number of rows
		testGrid = new Grid(51, 0);
		assertEquals(51, testGrid.getNumRows());
	}

	@Test
	public final void testGetNumCols() {
		//Zero columns
		Grid testGrid = new Grid (1, 0);
		assertEquals(0, testGrid.getNumCols());
		//Small number of columns
		testGrid = new Grid (1, 3);
		assertEquals(3, testGrid.getNumCols());
		//Large number of columns
		testGrid = new Grid(1, 56);
		assertEquals(56, testGrid.getNumCols());
	}

	@Test
	public final void testEmpty() {
		//Zero size grid
		Grid testGrid = new Grid(0,0);
		assertTrue(testGrid.empty());
		//Empty small grid
		testGrid = new Grid(4,3);
		assertTrue(testGrid.empty());
		//Empty large grid
		testGrid = new Grid(75, 43);
		assertTrue(testGrid.empty());
		//Populated large grid
		testGrid.generateRandom();
		assertFalse(testGrid.empty());
		//Populated small grid
		testGrid = new Grid(6,4);
		testGrid.generateRandom();
		assertFalse(testGrid.empty());
	}

	@Test
	public final void testGenerateRandom() {
		Grid testGrid = new Grid(10,10);
		testGrid.generateRandom();
		assertFalse(testGrid.empty());
	}

	@Test
	public final void testPrintGridZeroGrid() {
		//Test 0x0 grid
		Grid testGrid = new Grid(0,0);
		testGrid.printGrid();
		assertEquals("The grid is 0x0", out.toString().trim());
	}

	@Test
	public final void testPrintGrid() throws IOException {
		Grid testGrid = new Grid("./InputFiles/GameOfLife/LargeGrid.txt", 'O','.');
		testGrid.printGrid();
		assertEquals("______X___XX_____________XXX___X______XXX___X__XXX______X_XX______X____________________________________XX_______________________XX__________________________X__________________XXX___X________________________X______________________________________________XX_________XX___X________XX___XXXXXXXXX_____XXX", out.toString().replaceAll("\\s",""));
	}
	
	@Test
	public final void testTick() throws FileNotFoundException, IOException {
		//Input a grid from file where we know what the next generation should look like and compare
		Grid testGrid = new Grid("./InputFiles/GameOfLife/SmallGrid.txt", 'O','.');
		testGrid.tick();
		testGrid.printGrid();
		assertEquals("_X_______X___XXX_X_________________XX______XX___", out.toString().replaceAll("\\s",""));
	}

}
