///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title: Sudoku
// 
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
//None
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 */
public class Sudoku {

    /**
     * Build a String containing character c of length len. For example, constructLine(3, '*')
     * should return "***\n". constructLine(4, '-') should return "----\n".
     * 
     * @param len The number of characters in the String
     * @param c   The character that makes up the String
     * @return A string consisting of character c with length len, followed by a newline
     */
    public static String constructLine(int len, char c) {
        String line = "";
        for (int i = 0; i < len; i++) {
            line += c;
        }
        line += "\n";
        return line;
    }

    /**
     * Convert the Sudoku board into a String following the pattern below.
     * You can assume board is always a 9*9 2d array.
       -------------------------
       | 9 1 _ | _ _ _ | 4 2 7 |
       | _ _ _ | _ _ 3 | 9 1 5 |
       | 2 5 4 | 7 _ _ | 6 8 _ |
       -------------------------
       | 4 7 _ | _ 8 6 | _ 3 2 |
       | _ 6 _ | 4 _ _ | _ _ 8 |
       | 5 _ _ | _ 1 2 | _ 6 _ |
       -------------------------
       | 3 4 _ | 6 2 _ | _ _ 1 |
       | _ _ _ | 3 _ _ | _ _ _ |
       | _ 2 6 | _ _ 8 | _ _ 9 |
       -------------------------
       
     * Some key observations from the pattern:
     *     1. In each row, there's a '|' after every 3 numbers
     *     2. There is a line of '-' after every 3 lines (use constructLine to do this)
     *     3. The length of each row is 25 (not including '\n')
       
     * In the 2d array:
     * for a number from 1 to 9, meaning it's a valid number, you should put the number itself;
     * if a number equals to Config.EMPTY, meaning this position is currently empty, you should 
     * put a '_' instead.
     * 
     * Note: Don't duplicate code. Be sure to use constructLine method.
     * Note: Don't simply copy the example given above. You need to generate your output
     *       according to the content in the input board.
     * 
     * @param board A 2d array representing the Sudoku board
     * @return A String containing the display of the Sudoku board,
     *         or empty string if board is null
     */
    public static String constructBoardString(int[][] board) {
        String sudokuBoard = "";
        if (board == null) {
            return sudokuBoard;
        }
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                sudokuBoard += constructLine(25, '-');
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    sudokuBoard += "| ";
                }
                if (board[i][j] != Config.EMPTY) {
                    sudokuBoard += board[i][j] + " ";
                } else {
                    sudokuBoard += "_ ";
                }

            }
            sudokuBoard += "|\n";
        }
        sudokuBoard += constructLine(25, '-');

        return sudokuBoard;
    }

    /**
     * Given an original Sudoku board, find the positions for pre-existing numbers
     * For each element in the returned array:
     * mark a true if the corresponding position in the board has a valid number (1~9);
     * mark a false if the corresponding position in the board is empty (Config.EMPTY).
     * 
     * A simplified example:
     * 	Given {{Config.EMPTY, 1},
     * 	       {3, Config.EMPTY}}
     * 	Return {{false, true},
     * 	        {true, false}}
     * 
     * @param board The original board at the beginning of the game
     * @return A 2d boolean array parallel to board, representing whether there's
     *         a pre-existing number for each position; or null if board is null
     */
    public static boolean[][] findOriginalPosition(int[][] board) {
        boolean[][] prexist = new boolean[9][9];
        if (board == null) {
            return null;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == Config.EMPTY) {
                    prexist[i][j] = false;
                } else {
                    prexist[i][j] = true;
                }
            }
        }
        return prexist;
    }

    /**
     * Check whether the Sudoku board is fully filled with valid numbers.
     * 
     * Note: In this method, you only need to check whether the board is filled
     * without empty positions (Config.EMPTY). You don't need to worry about if there's any
     * conflict in the board.
     * 
     * @param board The current board that needs to be checked
     * @return true if fully filled; false otherwise, and when board is null.
     */
    public static boolean checkFullForBoard(int[][] board) {
        boolean full = true;
        if (board == null) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == Config.EMPTY) {
                    full = false;
                    break;
                }
            }
        }
        return full;
    }

    /**
     * Check if some number (1~9) appears more than once in the array.
     * 
     * A possible algorithm:
     *     1. Create a boolean array of nine elements, each element corresponding to one number.
     *     2. Loop through the input array arr, if it is an empty position (Config.EMPTY), 
     *     	  do not do anything. If it is a valid number(1-9), find the corresponding position 
     *        of the number in the boolean array.
     *     3. If the position of the number is false, mark it as true.
     *        For example, let appearance be our boolean array. 
     *        If the number is 1, check appearance[0]. If appearance[0] is false, mark it as true.
     *        If the number is 5, check appearance[4]. If appearance[4] is false, mark it as true.
     *        If the number is 9, check appearance[8]. If appearance[8] is false, mark it as true.
     *     4. If the position of a number is already true, return false (duplicate number found).
     *     5. If no duplicate was found after looping through the input array arr, return true. 
     * 
     * @param arr A 1d array that needs to be checked
     * @return true if no conflict; false otherwise, and when arr is null.
     */
    public static boolean checkConflictForArray(int[] arr) {
        boolean noConflict = true;
        int[] count = new int[9];
        if (arr == null) {
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case 1:
                    count[0]++;
                    break;
                case 2:
                    count[1]++;
                    break;
                case 3:
                    count[2]++;
                    break;
                case 4:
                    count[3]++;
                    break;
                case 5:
                    count[4]++;
                    break;
                case 6:
                    count[5]++;
                    break;
                case 7:
                    count[6]++;
                    break;
                case 8:
                    count[7]++;
                    break;
                case 9:
                    count[8]++;
                    break;
                default:
                    continue;
            }
            for (int j = 0; j < 9; j++) {
                if (count[j] > 1) {
                    noConflict = false;
                    break;
                }
            }
        }
        return noConflict;
    }

    /**
     * Check whether there's no conflict in the board (in each row, column and square)
     * 
     * Note: Don't duplicate code. Be sure to use checkConflictForArray method.
     *     For each row, column and square, first create a tempArr of size 9.
     *     Then call checkConflictForArray method, using tempArr as the argument. 
     *     
     * Checking square is a little tricky, below is a possible algorithm:
     *     for (each square s from all the 9 squares in the board) {
     *         create a tempArr of size 9
     *         for (each position [r, c] from all the 9 positions in square s) {
     *             copy s[r][c] into a corresponding position in tempArr
     *             // you need to figure out a 1-to-1 map between the index in tempArr
     *             // and the index in square s
     *         }
     *         call checkConflictForArray with tempArr
     *     }
     * 
     * @param board The current board that needs to be checked
     * @return true if no conflict; false otherwise, and when board is null
     */
    public static boolean checkConflictForBoard(int[][] board) {
        int[] tempArrC = new int[9];
        int[] tempArrS = new int[9];
        if (board == null) {
            return false;
        }
        for (int row = 0; row < 9; row++) {
            if (!checkConflictForArray(board[row])) {
                return false;
            }
        }
        int count = 0;
        for (int column = 0; column < 9; column++) {
            while (count < 9) {
                tempArrC[count] = board[count][column];
                count++;
            }
            count = 0;
            if (!checkConflictForArray(tempArrC)) {
                return false;
            }
        }
        int index = 0;
        int times = 0;
        int limitR = 3;
        int limitC = 3;
        while (times < 9) {
            for (int i = limitR - 3; i < limitR; i++) {
                for (int j = limitC - 3; j < limitC; j++) {
                    tempArrS[index] = board[i][j];
                    index++;
                }
            }
            index = 0;
            if (!checkConflictForArray(tempArrS)) {
                return false;
            }
            times++;
            if (times % 3 == 0) {
                limitC = 3;
                limitR += 3;
            } else {
                limitC += 3;
            }
        }

        return true;
    }

    /**
     * First check whether it's a valid move to put the given number at the given position,
     * and then put the given number into the board if and only if it's a valid move.
     * 
     * @param row The row you want to fill number
     * @param col The column you want to fill number
     * @param val The number you want to fill in
     * @param board The current Sudoku board
     * @param original The boolean array parallel to board, representing whether
     *        there's a pre-existing number for each position
     * @return 0 if either board or original is null
     *         -1 if the row is out of valid range (0~8)
     *         -2 if the column is out of valid range (0~8)
     *         -3 if val is out of valid range (1~9 and Config.EMPTY)
     *         -4 if the number at the current position (row, col) is pre-existing
     *         -5 if there's a conflict in the board after filling in val at (row, col)
     *            Note: remember to call checkConflictForBoard for this
     *            Note: remember to recover the original value at (row, col) if there's a 
     *            		conflict after filling. 
     *         1 if there's a success filling in the number. If and only if in
     *         this case, should you actually place the number into the board.
     *         
     *         Note: You need to check the conditions exactly in the above order.
     */
    public static int fillNumber(int row, int col, int val, int[][] board, boolean[][] original) {
        if (board == null) {
            return 0;
        }
        if (original == null) {
            return 0;
        }
        if (!(row >= 0 && row <= 8)) {
            return -1;
        }
        if (!(col >= 0 && col <= 8)) {
            return -2;
        }
        if (!(val >= 0 && val < 10)) {
            return -3;
        }

        if (original[row][col]) {
            return -4;
        }
        int ori = board[row][col];
        board[row][col] = val;
        if (!checkConflictForBoard(board)) {
            board[row][col] = ori;
            return -5;
        } else {
            board[row][col] = val;
        }
        return 1;
    }

    /**
     * Read the original Sudoku board from a file.
     * Refer to Chapter 11 in zyBook about how to read files.
     * 
     * You can assume the file content is always in the following format:
     * 	1. There are 9 lines in the file.
     * 	2. Each line has 9 numbers separated by a space.
     * 
     * @param fileName The file containing a Sudoku board
     * @return A 2d array representing the Sudoku board read from the file
     * @throws FileNotFoundException
     */
    public static int[][] readBoardFromFile(String fileName) throws FileNotFoundException {
        int[][] board = new int[9][9];
        File sudoku = new File(fileName);
        Scanner reader = new Scanner(sudoku);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = reader.nextInt();
            }
            if (i < 8) {
                reader.nextLine();
            }
        }
        reader.close();
        return board;
    }

    /**
     * Write the current Sudoku board to a file with the following format:
     * 	1. Write each row of the board into a separate line
     * 	2. Numbers in each line are separated by a space
     * 
     * Refer to Chapter 11 in zyBook about how to write files.
     * 
     * Note: You can assume board won't be null for this method
     * 
     * @param board The current Sudoku board
     * @param fileName The file that needs to be written
     * @throws FileNotFoundException
     */
    public static void saveBoardToFile(int[][] board, String fileName)
        throws FileNotFoundException {
        File solution = new File(fileName);
        PrintWriter writer = new PrintWriter(fileName);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                writer.print(board[i][j] + " ");
            }
            if (i != 8) {
                writer.print(board[i][8]);
                writer.println();
            } else {
                writer.print(board[i][8]);
            }
        }
        writer.close();

    }

    /**
     * This is the main program loops, prompts for user input and prints to the
     * console.
     * 
     * Algorithm
     * 1. Initialize and welcome, print "Welcome to Sudoku!\n"
     * 2. Prompt for input of a filename from where to load the original board:
     *    "Please enter a file name to load Sudoku board (default is sudoku_board.txt): "
     *    	- If user enters nothing, use the default file: sudoku_board.txt
     * 2. Load the original Sudoku board from file
     *    	- If file doesn't exist, quit with message "Cannot read from file <filename>\n"
     *    	  with <filename> replaced by the filename
     * 3. Create a 2d boolean array, parallel to the board, to mark the original
     *    positions with pre-existing numbers
     *    Note: Don't duplicate code. Be sure to use findOriginalPosition method.
     * 4. Game loop
     * 		a) Construct a string representation of the board and print it out. 
     * 		   Note: Don't duplicate code. Be sure to use constructBoardString method.
     * 		b) Prompt for input in the format of (row column value):
     *           "Choose a position and fill a number (row column value): "
     *         Note: For this step, you can assume the user will type in three numbers 
     *               and won't give you unexpected input
     * 		c) If row is invalid, print "Row out of range (0~8)!\n"
     * 		d) If column is invalid, print "Column out of range (0~8)!\n"
     * 		e) If value is invalid, print "Value out of range (0~9)!\n"
     * 		f) If it's a position with pre-existing number, print "The number at
     * 		   this position is given to you. You cannot change!\n"
     * 		g) If there's some conflict, print "The input caused a conflict in
     * 		   row, column, or square! Try again!\n"
     * 		h) If the input number is filled into the board successfully, check
     * 		   whether the board is fully filled and finish the game if it is.
     * 		Note: Don't duplicate code. Be sure to use fillNumber method.
     * 5. If the game is finished with a success,
     * 		a) print "Congratulations! You have won!\n"
     * 		b) prompt for input ("Do you want to save your solution? (y/n): ")
     * 		c) if y, save the finished board to file (solution.txt); if n, do nothing;
     * 		   if neither, print "Unknown command. Defaulting to y...\n" and save to file
     *      d) if file doesn't exist, quit with message "Cannot write to file <filename>\n"
     *         with <filename> replaced by the filename
     * 6. Print ending information ("Thanks for playing Sudoku!\n")
     * 
     * Note: use System.out.print for all the printing operations. Notice that some messages 
     * have "\n" at the end, some do not. 
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String fileName = "sudoku_board.txt";
        int[][] board = new int[9][9];
        boolean[][] oriPosition = new boolean[9][9];
        String sdBoard = "";
        System.out.print("Welcome to Sudoku!\n");
        System.out
            .print("Please enter a file name to load Sudoku board (default is sudoku_board.txt): ");
        if (input.hasNext()) {
            fileName = input.next();
        }
        try {
            board = readBoardFromFile(fileName);
            oriPosition = findOriginalPosition(board);
            sdBoard = constructBoardString(board);

        } catch (FileNotFoundException e) {
            System.out.print("Cannot read from " + fileName + "\n");
        }
        System.out.print(sdBoard);
        System.out.print("Choose a position and fill a number (row column value): ");
        while (input.hasNextInt()) {
            int row = input.nextInt();
            int column = input.nextInt();
            int value = input.nextInt();
            int check = fillNumber(row, column, value, board, oriPosition);
            switch (check) {
                case -1:
                    System.out.print("Row out of range (0~8)!\n");
                    System.out.print(sdBoard);
                    System.out.print("Choose a position and fill a number (row column value): ");
                    break;
                case -2:
                    System.out.print("Column out of range (0~8)!\n");
                    System.out.print(sdBoard);
                    System.out.print("Choose a position and fill a number (row column value): ");
                    break;
                case -3:
                    System.out.print("Value out of range (0~9)!\n");
                    System.out.print(sdBoard);
                    System.out.print("Choose a position and fill a number (row column value): ");
                    break;
                case -4:
                    System.out
                        .print("The number at this position is given to you. You cannot change!\n");
                    System.out.print(sdBoard);
                    System.out.print("Choose a position and fill a number (row column value): ");
                    break;
                case -5:
                    System.out.print(
                        "The input caused a conflict in row, column, or square! Try again!\n");
                    System.out.print(sdBoard);
                    System.out.print("Choose a position and fill a number (row column value): ");
                    break;
                case 1:
                    board[row][column] = value;
                    if (checkFullForBoard(board)) {
                        System.out.print(sdBoard);
                        System.out.print("Congratulations! You have won!\n");
                        System.out.print("Do you want to save your solution? (y/n): ");
                    } else {
                        sdBoard = constructBoardString(board);
                        System.out.print(sdBoard);
                        System.out
                            .print("Choose a position and fill a number (row column value): ");
                    }
                    break;
            }

        }

        if (input.hasNext()) {
            String choice = input.next().toLowerCase();
            if (choice.equals("y")) {
                try {
                    saveBoardToFile(board, "solution.txt");
                } catch (FileNotFoundException e) {
                    System.out.print("Cannot write to file solution.txt\n");
                }
            } else if (!choice.equals("n")) {
                System.out.print("Unknown command. Defaulting to y...\n");
            }
        }
        System.out.print("Thanks for playing Sudoku!\n");
    }
}
