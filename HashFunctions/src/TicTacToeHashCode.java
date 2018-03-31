import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class that uses hashing and lookup tables for TicTacToe in order to quickly
 * determine winner files
 * 
 * @author Sean Gibbons
 *
 */
@SuppressWarnings("serial")
public class TicTacToeHashCode extends Board {

	boolean[] winners; // True if the hash string that maps to this index is a winner, false otherwise

	/**
	 * A constructor with one string parameter that uses the super constructor then
	 * creates a boolean array where winning board arrangements are stored
	 * 
	 * @param s
	 */
	TicTacToeHashCode(String s) {
		super(s);
		winners = new boolean[TicTacToe.POSSIBILITIES];
		Scanner wins = fileToScanner("TicTacToeWinners.txt");
		while (wins.hasNextLine()) {
			super.setBoardString(wins.nextLine());
			winners[this.myHashCode()] = true;
		}
		// int i = 0;
		// for (boolean state : winners) {
		// System.out.println(state + " " + (i++));
		// }
		// tester
		wins.close();
	}

	@Override
	public int myHashCode() {
		int result = 0;

		int powersOf3[][] = new int[][] { { 1, 3, 9 }, { 27, 81, 243 }, { 729, 2187, 6561 } };

		for (int row = 0; row < TicTacToe.ROWS; row++) {
			for (int col = 0; col < TicTacToe.COLS; col++) {
				switch (charAt(row, col)) {
				case 'x':
					result += powersOf3[row][col];
					break;
				case 'o':
					result += (2 * powersOf3[row][col]);
					break;
				default:
					break;
				}

			}
		}
		return result;
	}

	/**
	 * Converts the given file to Scanner
	 * 
	 * @param fName
	 *            The String name of a file
	 * 
	 * @return A Scanner of the file with the given file name
	 */
	private static Scanner fileToScanner(String fName) {

		File fileName = new File(fName);
		Scanner words = null;

		try {
			words = new Scanner(fileName);
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to Open file: " + fName + ", please see output");
			return null;

		}
		if (!words.hasNext())
			throw new IllegalArgumentException("File is empty");
		return words;

	}

	@Override
	public boolean isWin(String s) {
		this.setBoardString(s);
		return isWin();
	}

	@Override
	public boolean isWin() {
		return winners[this.myHashCode()];
	}

	public static void main(String[] args) throws InterruptedException {
		Scanner test = fileToScanner("TTT_Tests.txt");
		TicTacToeHashCode board = new TicTacToeHashCode("Tic Tac Toe");
		while (test.hasNextLine()) {
			String line = test.nextLine();
			board.setBoardString(line);
			board.setHashCodeLabel(board.myHashCode());
			System.out.println(board.myHashCode() + " " + board.isWin());
			board.setWinnerLabel(board.isWin());
			Thread.sleep(4000);// Pause 4 seconds between test
		}
		test.close();
		Thread.sleep(10000);// remain open for 10 seconds then close
		System.exit(0);

		/* Mrs Kelly Original Tests */
		// TicTacToeHashCode board = new TicTacToeHashCode("Tic Tac Toe");
		// board.displayRandomString();
		// while (true) {
		//
		// board.setHashCodeLabel(board.myHashCode());
		// // TODO Update this line to call your isWin method.
		// board.setWinnerLabel(board.isWin(board.getBoardString()));
		//
		// Thread.sleep(4000);
		// }
	}
}
