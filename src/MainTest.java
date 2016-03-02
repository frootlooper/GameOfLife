import static org.junit.Assert.*;
import java.io.*;
import org.junit.*;

public class MainTest {
	
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
	public final void testMainNoArguments() throws FileNotFoundException, IOException {
		String[] args = new String[0];
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainInvalidArg() throws FileNotFoundException, IOException {
		String[] args = {"something"};
		Main.main(args);
		assertEquals("Please enter a valid argument. See -help", out.toString().trim());
	}
	
	@Test
	public final void testMainHelp() throws FileNotFoundException, IOException {
		String[] args = {"-help"};
		Main.main(args);
		assertEquals("Usage: Main [options]\n\n\t-generate [numRows] [numColumns]\n"
						+"\t-file filelocation [aliveCharacter] [deadCharacter]\n"
						+"\nNo arguments will result in a randomly generated default sized grid.", out.toString().trim());
	}

	@Test
	public final void testMainGenerateDefaultSize() throws FileNotFoundException, IOException {
		String[] args = {"-generate"};
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainGenerateCustomSize() throws FileNotFoundException, IOException {
		String[] args = {"-generate","5","5"};
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainGenerateInvalidArgs() throws FileNotFoundException, IOException {
		String[] args = {"-generate","iminvalid"};
		Main.main(args);
		assertEquals("Wrong number of arugments. See '-help'", out.toString().trim());
	}
	
	@Test
	public final void testMainSmallFileDefaultChars() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/SmallGrid.txt"};
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainLargeFileDefaultChars() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/LargeGrid.txt"};
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainFileCustomChars() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/SmallCustomGrid.txt","X","_"};
		Main.main(args);
		assertTrue(out.toString().trim().length()>215);
	}
	
	@Test
	public final void testMainFileNoLocation() throws FileNotFoundException, IOException {
		String[] args = {"-file"};
		Main.main(args);
		assertEquals("You must specify the file location", out.toString().trim());
	}
	
	@Test
	public final void testMainFileInvalidNumArgs() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/SmallGrid.txt","."};
		Main.main(args);
		assertEquals("Wrong number of arugments. See '-help'", out.toString().trim());
	}
	
	@Test
	public final void testMainFileInvalidFormat() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/InvalidGrid.txt","O","."};
		Main.main(args);
		assertEquals("File is in an invalid format.", out.toString().trim());
	}
	
	@Test
	public final void testMainFileNotFound() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/NoGrid.txt","O","."};
		Main.main(args);
		assertEquals("File was not found.", out.toString().trim());
	}
	
	@Test
	public final void testMainFileIsEmpty() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/ZeroGrid.txt","O","."};
		Main.main(args);
		assertEquals("File is empty.", out.toString().trim());
	}
	
	@Test
	public final void testMainFileIncompleteGrid() throws FileNotFoundException, IOException {
		String[] args = {"-file","./InputFiles/GameOfLife/InvalidGrid2.txt","O","."};
		Main.main(args);
		assertEquals("File is in an invalid format.", out.toString().trim());
	}
}
