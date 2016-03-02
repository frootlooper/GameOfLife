To run this project, you can:

1. Open the project in Eclipse and run it from there.
Or 2. Run it manually.
	a. Go to GameOfLife/bin
	b. Run "java -cp . Main [options]"
	c. Usage: Main [options]
		-generate [numRows] [numColumns]
		-file fileLocation [aliveCharacter] [deadCharacter]

Examples:
java -cp . Main
java -cp . Main -generate
java -cp . Main -generate 9 2
java -cp . Main -file ./InputFiles/GameOfLife/SmallGrid.txt
java -cp . Main -file ./InputFiles/GameOfLife/SmallGrid.txt O .

The input file format:
......O.
OOO...O.
......O.
........
...OO...
...OO...
There should be no content other than the grid in the file.
No spaces should be between each character.
The grid must be complete, otherwise you will receive an error message.
You can use other characters as long as you specify them when you run the program.

Sample input files are located in GameOfLife.InputFiles/GameOfLife

Also please note that the number of generations is limited to 50 to prevent an infinite loop.

To run the test cases:
1. Load the project in Eclipse and run the GridTest and MainTest files from there.