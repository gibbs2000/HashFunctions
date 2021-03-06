/**
 * A class with helper methods and constants to aid in creating a TicTacToe
 * board
 * 
 * @author Mrs. Kelly/Javadocs provided by Sean Gibbons
 *
 */
public class TicTacToe {
	public final static int ROWS = 3;
	public final static int COLS = 3;
	public final static int POSSIBILITIES = (int) Math.pow(3, 9);
	public final static int CHAR_POSSIBILITIES = 3; // x, o or space

	/**
	 * Returns the number of times a given character occurs in a 2d array of
	 * characters
	 * 
	 * @param b
	 *            the 2d array of characters to be searched
	 * @param ch
	 *            the character to be searched for
	 * @return the number of times a given character occurs in a 2d array of
	 *         characters
	 */
	private static int numChars(char[][] b, char ch) {
		int total = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++)
				if (ch == b[r][c])
					total++;
		return total;
	}

	/**
	 * Returns whether a given 2d array of characters is a valid TicTacToe board
	 * 
	 * @param board
	 *            the 2d array of characters to be checked
	 * @return whether a given 2d array of characters is a valid TicTacToe board
	 */
	public static boolean valid(char[][] board) {

		// Ensure there are at least 3 xs and 2 os, or 3 os and 2 xs
		// Make sure there are at least one more x or one more o
		int numX = numChars(board, 'x');
		int numO = numChars(board, 'o');
		if (!(numX > 2 || numO > 2))
			return false;
		if ((numX == numO + 1) || (numO == numX + 1))
			return true;
		return false;
	}

	/**
	 * Converts a 2d array of characters to a single long String and returns the
	 * String
	 * 
	 * @param b
	 *            the 2d array of characters to be converted
	 * @return the String representation of the parameter
	 */
	public static String boardToString(char[][] b) {
		String result = "";
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++)
				result += b[r][c];
			// result += "\n";
		}
		return result;
	}

	/**
	 * Converts a single long String to a 2d array of characters with dimensions 3x3
	 * 
	 * @param board
	 *            the String to be converted
	 * @return a 2d array of characters which made up the String
	 */
	public static char[][] stringToBoard(String board) {
		char[][] b = new char[ROWS][COLS];
		int index = 0;
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++)
				b[r][c] = whichLetter(board.charAt(index++));
		}
		return b;
	}

	/**
	 * Returns the appropriate symbol value of a given parameter in the rules of
	 * TicTacToe
	 * 
	 * @param ch
	 *            the character to be checked
	 * @return
	 */
	public static char whichLetter(char ch) {
		switch (ch) {
		case '1':
			return 'x';
		case '2':
			return 'o';
		case '0':
			return ' ';
		default:
			return ch;
		}
	}

	public static char[][] makeBoard(String s) {
		char[][] b = new char[ROWS][COLS];
		int ch = 0;
		for (int r = 0; r < ROWS; r++)
			for (int c = 0; c < COLS; c++) {
				b[r][c] = whichLetter(s.charAt(ch));
				ch++;
			}
		return b;
	}

	private static String addOne(String s) {
		// s is a 9 character string, composed of 0s, 1s, and 2s. Add 1 to the last
		// char, adjusting
		// all the rest of the characters as necessary.
		boolean carry = false;
		char ch[] = s.toCharArray();
		ch[ch.length - 1] = (char) ((ch[ch.length - 1]) + 1);
		for (int n = ch.length - 1; n >= 0; n--) {
			if (carry)
				ch[n] = (char) ((ch[n]) + 1);
			if (ch[n] == '3') {
				carry = true;
				ch[n] = '0';
			} else
				carry = false;
		}
		return new String(ch);
	}

	public static String[] fillValues() {
		String strBoard = "000000000";
		String[] values = new String[POSSIBILITIES];
		int index = 0;
		values[index++] = strBoard;
		while (!strBoard.equals("222222222")) {
			strBoard = addOne(strBoard);
			values[index++] = strBoard;
		}
		return values;
	}

	private static boolean diagonalWin(char[][] board) {

		if ((board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x')
				|| (board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o')) {
			return true;
		} else if ((board[0][2] == 'x' && board[1][1] == 'x' && board[2][0] == 'x')
				|| (board[0][2] == 'o' && board[1][1] == 'o' && board[2][0] == 'o')) {
			return true;
		}
		return false;
	}

	private static boolean rowWin(char[][] board) {
		char ch;
		for (int r = 0; r < ROWS; r++) {
			ch = board[r][0];
			for (int c = 0; c < COLS; c++)
				if (ch != board[r][c])
					return false;
		}
		return true;
	}

	private static boolean colWin(char[][] board) {
		char ch;
		for (int c = 0; c < COLS; c++) {
			ch = board[0][c];
			for (int r = 0; r < ROWS; r++)
				if (ch != board[r][c])
					return false;
		}
		return true;
	}

	public static boolean isWin(char[][] b) {
		return valid(b) && (rowWin(b) || colWin(b) || diagonalWin(b));
	}

	public static boolean isWin(String s) {
		char[][] b = stringToBoard(s);
		return isWin(b);
	}
}
