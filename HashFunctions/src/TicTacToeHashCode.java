import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO Make sure you remove all of the TODO comments from this file before turning itin

@SuppressWarnings("serial")
public class TicTacToeHashCode extends Board {

	boolean[] winners; // True if the hash string that maps to this index is a winner, false otherwise

	TicTacToeHashCode(String s) {
		super(s);
		winners = new boolean[TicTacToe.POSSIBILITIES];
		for (int i = 0; i < winners.length; i++) {
			winners[i] = false;
		}
		Scanner wins = fileToScanner("TicTacToeWinners.txt");
		while (wins.hasNextLine()) {
			String line = wins.nextLine();
			super.setBoardString(line);
			winners[this.myHashCode()] = true;
		}
		wins.close();
	}

	@Override
	public int myHashCode() {
		int result = 0;

		int powersOf3[][] = new int[][] { { 1, 3, 9 }, { 27, 81, 243 }, { 729, 2187, 6561 } };

		for (int i = 0; i < TicTacToe.ROWS; i++) {
			for (int j = 0; j < TicTacToe.COLS; j++) {
				switch (charAt(i, j)) {
				case 'x':
					result += powersOf3[i][j];
					break;
				default:
					result += 2 * powersOf3[i][j];
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

	public boolean isWin(String s) {
		this.setBoardString(s);
		return winners[this.myHashCode()];
	}

	public static void main(String[] args) throws InterruptedException {
		TicTacToeHashCode board = new TicTacToeHashCode("Tic Tac Toe");
		while (true) {

			// TODO this line no longer works
			// String currentBoard = board.boardValues[(int)(Math.random()*
			// board.boardValues.length)];

			board.displayRandomString();
			board.setHashCodeLabel(board.myHashCode());
			// TODO Update this line to call your isWin method.
			board.setWinnerLabel(board.isWin(board.getBoardString()));

			Thread.sleep(4000);
		}
	}
}