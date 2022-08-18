
///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           13.8 **zyLab: Sudoku
// Course:          CS200, Fall, 2021
//
// Author:          your name
// Email:           your email
// Lecturer's Name: name of your lecturer
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
// https://cs200-www.cs.wisc.edu/wp/syllabus/#academicintegrity
// Source or Recipient; Description
// Examples:
// Jane Doe; helped me with for loop in reverse method
// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html; 
//         counting for loop
// John Doe; I helped with switch statement in main method.
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is a testbench for the Sudoku program.
 * 
 * @author Rui Huang TODO add your name when you add test cases.
 *
 */
public class TestSudoku {

	/**
	 * This contains the calls to the various test methods. Uncomment the method
	 * calls and run to execute the test cases.
	 * 
	 * These test cases are not intended to be comprehensive but provide some
	 * examples. Extend with your own test cases to be comprehensive.
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		// Part 1: Print Sudoku Board
		testConstructLine();
		testConstructBoardString();
		testFindOriginalPosition();
		
		// Part 2: Supporting Methods
		testCheckFullForBoard();	
		testCheckConflictForArray();
		testCheckConflictForBoard();
		testFillNumber();
		
		// Part 3: Sudoku main and File IO
		testReadBoardFromFile();
		testSaveBoardToFile();
		
		// implement main, test with various output examples
	}

	/**
	 * Some test cases for the readBoardFromFile method.
	 */
	private static void testReadBoardFromFile() {
		boolean error = false;
		{
			int[][] board;
			try {
				board = Sudoku.readBoardFromFile("sudoku_board.txt");

				int[][] expected = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
						{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
						{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
						{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

				if (!Arrays.deepEquals(board, expected)) {
					error = true;
					System.out.println("testReadBoardFromFile 1: board is not correctly read.");
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		{
			int[][] board;
			try {
				board = Sudoku.readBoardFromFile("notExistingFile.txt");
				error = true;
				System.out.println("testReadBoardFromFile 2: should have thrown exception for non-existing file.");
			} catch (FileNotFoundException e) {
				// expected, so not an error.
			}
		}

		if (error) {
			System.out.println("testReadBoardFromFile failed.");
		} else {
			System.out.println("testReadBoardFromFile passed.");
		}
	}

	/**
	 * A supporting method for testSaveBoardToFile.
	 */
	private static String readFile(String filename) {
		String result = "";
		Scanner input = null;
		try {
			input = new Scanner(new File(filename));
			while (input.hasNextLine()) {
				result += input.nextLine().trim() + "\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (input != null)
				input.close();
		}
		return result;
	}

	/**
	 * Some test cases for the saveBoardToFile method.
	 */
	private static void testSaveBoardToFile() {
		boolean error = false;
		int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 }, { 2, 5, 4, 7, 0, 0, 6, 8, 0 },
				{ 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 }, { 5, 0, 0, 0, 1, 2, 0, 6, 0 },
				{ 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 }, { 0, 2, 6, 0, 0, 8, 0, 0, 9 } };
		{
			try {
				Sudoku.saveBoardToFile(board, "testSaveBoardToFile_output.txt");
				String content = readFile("testSaveBoardToFile_output.txt");
				String expected = "9 1 0 0 0 0 4 2 7\n" + "0 0 0 0 0 3 9 1 5\n" + "2 5 4 7 0 0 6 8 0\n"
						+ "4 7 0 0 8 6 0 3 2\n" + "0 6 0 4 0 0 0 0 8\n" + "5 0 0 0 1 2 0 6 0\n" + "3 4 0 6 2 0 0 0 1\n"
						+ "0 0 0 3 0 0 0 0 0\n" + "0 2 6 0 0 8 0 0 9\n";
				if (!content.equals(expected)) {
					error = true;
					System.out.println("testSaveBoardToFile 1: file is not correctly written.");

				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		{
			try {
				Sudoku.saveBoardToFile(board, "nonExistingDirectory/testSaveBoardToFile_output.txt");
				error = true;
				System.out.println("testSaveBoardToFile 2: should have thrown exception for non-existing file.");
			} catch (FileNotFoundException e) {
				// expected, so not an error.
			}
		}

		if (error) {
			System.out.println("testSaveBoardToFile failed.");
		} else {
			System.out.println("testSaveBoardToFile passed.");
		}
	}

	/**
	 * Some test cases for the checkFullForBoard method.
	 */
	private static void testCheckFullForBoard() {
		boolean error = false;

		{
			int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
					{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
					{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

			boolean expected = false;
			boolean actual = Sudoku.checkFullForBoard(board);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckFullForBoard 1: actual: " + actual + " expected: " + expected);
			}
		}

		{
			int[][] board = { { 9, 1, 3, 3, 3, 3, 4, 2, 7 }, { 3, 3, 3, 3, 3, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 3, 3, 6, 8, 3 }, { 4, 7, 3, 3, 8, 6, 3, 3, 2 }, { 3, 6, 3, 4, 3, 3, 3, 3, 8 },
					{ 5, 3, 3, 3, 1, 2, 3, 6, 3 }, { 3, 4, 3, 6, 2, 3, 3, 3, 1 }, { 3, 3, 3, 3, 3, 3, 3, 3, 3 },
					{ 3, 2, 6, 3, 3, 8, 3, 3, 9 } };

			boolean expected = true;
			boolean actual = Sudoku.checkFullForBoard(board);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckFullForBoard 2: actual: " + actual + " expected: " + expected);
			}
		}

		if (error) {
			System.out.println("testCheckFullForBoard failed.");
		} else {
			System.out.println("testCheckFullForBoard passed.");
		}
	}
	
	/**
	 * Some test cases for the findOriginalPosition method.
	 */
	private static void testFindOriginalPosition() {
		boolean error = false;

		{
			int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
					{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
					{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

			boolean[][] expected = {{true, true, false, false, false, false, true, true, true},
					{false, false, false, false, false, true, true, true, true},
					{true, true, true, true, false, false, true, true, false},
					{true, true, false, false, true, true, false, true, true},
					{false, true, false, true, false, false, false, false, true},
					{true, false, false, false, true, true, false, true, false},
					{true, true, false, true, true, false, false, false, true},
					{false, false, false, true, false, false, false, false, false},
			        {false, true, true, false, false, true, false, false, true}};
			
			boolean[][] actual = Sudoku.findOriginalPosition(board);
			if (!Arrays.deepEquals(actual, expected)) {
				error = true;
				System.out.println("testFindOriginalPosition 1:\nactual: " + Arrays.deepToString(actual) + "\nexpected: " + Arrays.deepToString(expected));
			}
		}

		if (error) {
			System.out.println("testFindOriginalPosition failed.");
		} else {
			System.out.println("testFindOriginalPosition passed.");
		}
	}
	
	/**
	 * A supporting method for testFillNumber.
	 */
	private static int[][] arrayDeepCopy(int[][] src) {
		int[][] dst = new int[src.length][src[0].length];
		for (int r = 0; r < src.length; r++) {
			for (int c = 0; c < src.length; c++) {
				dst[r][c] = src[r][c];
			}
		}
		return dst;
	}

	/**
	 * Some test cases for the fillNumber method.
	 */
	private static void testFillNumber() {
		boolean error = false;
		int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
				{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
				{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
				{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };
		
		boolean[][] original = Sudoku.findOriginalPosition(board);
		
		{
			int expected = -1;
			int[][] prevBoard = arrayDeepCopy(board);
			int actual = Sudoku.fillNumber(-1, 0, 3, prevBoard, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 1: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(prevBoard, board)) {
				error = true;
				System.out.println("testFillNumber 1: the original board has been changed");
			}
		}

		{
			int expected = -2;
			int[][] prevBoard = arrayDeepCopy(board);
			int actual = Sudoku.fillNumber(0, 9, 3, prevBoard, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 2: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(prevBoard, board)) {
				error = true;
				System.out.println("testFillNumber 2: the original board has been changed");
			}
		}
		
		{
			int expected = -3;
			int[][] prevBoard = arrayDeepCopy(board);
			int actual = Sudoku.fillNumber(0, 2, 10, prevBoard, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 3: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(prevBoard, board)) {
				error = true;
				System.out.println("testFillNumber 3: the original board has been changed");
			}
		}
		
		{
			int expected = -4;
			int[][] prevBoard = arrayDeepCopy(board);
			int actual = Sudoku.fillNumber(0, 1, 3, prevBoard, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 4: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(prevBoard, board)) {
				error = true;
				System.out.println("testFillNumber 4: the original board has been changed");
			}
		}
		
		{
			int expected = -5;
			int[][] prevBoard = arrayDeepCopy(board);
			int actual = Sudoku.fillNumber(0, 2, 9, prevBoard, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 5: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(prevBoard, board)) {
				error = true;
				System.out.println("testFillNumber 5: the original board has been changed");
			}
		}
		
		{
			int expected = 1;
			int[][] expectedBoard = arrayDeepCopy(board);
			expectedBoard[0][2] = 3;
			int actual = Sudoku.fillNumber(0, 2, 3, board, original);
			if (expected != actual) {
				error = true;
				System.out.println("testFillNumber 6: actual: " + actual + " expected: " + expected);
			}
			if (!Arrays.deepEquals(expectedBoard, board)) {
				error = true;
				System.out.println("testFillNumber 6: the value is not filled in correctly");
			}
		}

		if (error) {
			System.out.println("testFillNumber failed.");
		} else {
			System.out.println("testFillNumber passed.");
		}
	}

	/**
	 * Some test cases for the checkConflictForBoard method.
	 */
	private static void testCheckConflictForBoard() {
		boolean error = false;

		{
			int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
					{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
					{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

			boolean expected = true;
			boolean actual = Sudoku.checkConflictForBoard(board);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckConflictForBoard 1: actual: " + actual + " expected: " + expected);
			}
		}

		{
			int[][] board = { { 9, 1, 5, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
					{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
					{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

			boolean expected = false;
			boolean actual = Sudoku.checkConflictForBoard(board);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckConflictForBoard 2: actual: " + actual + " expected: " + expected);
			}
		}

		if (error) {
			System.out.println("testCheckConflictForBoard failed.");
		} else {
			System.out.println("testCheckConflictForBoard passed.");
		}
	}
	
	/**
	 * Some test cases for the checkConflictForArray method.
	 */
	private static void testCheckConflictForArray() {
		boolean error = false;
		
		{
			int[]  arr = { 9, 1, 0, 0, 0, 0, 4, 2, 7 };

			boolean expected = true;
			boolean actual = Sudoku.checkConflictForArray(arr);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckConflictForArray 1: actual: " + actual + " expected: " + expected);
			}
		}
		
		{
			int[]  arr = { 9, 1, 3, 5, 4, 0, 4, 2, 7 };

			boolean expected = false;
			boolean actual = Sudoku.checkConflictForArray(arr);
			if (expected != actual) {
				error = true;
				System.out.println("testCheckConflictForArray 2: actual: " + actual + " expected: " + expected);
			}
		}

		if (error) {
			System.out.println("testCheckConflictForArray failed.");
		} else {
			System.out.println("testCheckConflictForArray passed.");
		}
	}
	
	/**
	 * Some test cases for the constructLine method.
	 */
	private static void testConstructLine() {
		boolean error = false;
		
		{
			String expected = "\n";
			String actual = Sudoku.constructLine(0, '-');
			if (!expected.equals(actual)) {
				error = true;
				System.out.println("testConstructLine 1: actual: " + actual + " expected: " + expected);
			}
		}
		
		{
			String expected = "*****\n";
			String actual = Sudoku.constructLine(5, '*');
			if (!expected.equals(actual)) {
				error = true;
				System.out.println("testConstructLine 2: actual: " + actual + " expected: " + expected);
			}
		}
		
		if (error) {
			System.out.println("testConstructLine failed.");
		} else {
			System.out.println("testConstructLine passed.");
		}
	}
	
	/**
	 * Some test cases for the constructBoardString method.
	 */
	private static void testConstructBoardString() {
		boolean error = false;

		{
			int[][] board = { { 9, 1, 0, 0, 0, 0, 4, 2, 7 }, { 0, 0, 0, 0, 0, 3, 9, 1, 5 },
					{ 2, 5, 4, 7, 0, 0, 6, 8, 0 }, { 4, 7, 0, 0, 8, 6, 0, 3, 2 }, { 0, 6, 0, 4, 0, 0, 0, 0, 8 },
					{ 5, 0, 0, 0, 1, 2, 0, 6, 0 }, { 3, 4, 0, 6, 2, 0, 0, 0, 1 }, { 0, 0, 0, 3, 0, 0, 0, 0, 0 },
					{ 0, 2, 6, 0, 0, 8, 0, 0, 9 } };

			String expected = "-------------------------\n" + 
					"| 9 1 _ | _ _ _ | 4 2 7 |\n" + 
					"| _ _ _ | _ _ 3 | 9 1 5 |\n" + 
					"| 2 5 4 | 7 _ _ | 6 8 _ |\n" + 
					"-------------------------\n" + 
					"| 4 7 _ | _ 8 6 | _ 3 2 |\n" + 
					"| _ 6 _ | 4 _ _ | _ _ 8 |\n" + 
					"| 5 _ _ | _ 1 2 | _ 6 _ |\n" + 
					"-------------------------\n" + 
					"| 3 4 _ | 6 2 _ | _ _ 1 |\n" + 
					"| _ _ _ | 3 _ _ | _ _ _ |\n" + 
					"| _ 2 6 | _ _ 8 | _ _ 9 |\n" + 
					"-------------------------\n";
			
			String actual = Sudoku.constructBoardString(board);
			if (!expected.equals(actual)) {
				error = true;
				System.out.println("testConstructBoardString 1:\nactual:\n" + actual + "\nexpected:\n" + expected);
			}
		}

		if (error) {
			System.out.println("testConstructBoardString failed.");
		} else {
			System.out.println("testConstructBoardString passed.");
		}
	}
}
