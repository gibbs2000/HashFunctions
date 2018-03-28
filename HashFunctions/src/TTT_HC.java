import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class representing a TicTacToe board with a more advanced hashcode
 * functionality
 * 
 * @author Sean Gibbons
 *
 */
public class TTT_HC extends Board {
	boolean[] winners;
	private int sizeArray;

	public TTT_HC(String title) {
		super(title);
		sizeArray = 40000;
		// puts the winners in
		Scanner wins = fileToScanner("TicTacToeWinners.txt");
		winners = new boolean[sizeArray];
		while (wins.hasNextLine()) {
			super.setBoardString(wins.nextLine());
			winners[this.myHashCode()] = true;
		}

		wins.close();
	}

	@Override
	int myHashCode() {
		String code = this.getBoardString();
		int score = 0;
		for (int i = 0; i < code.length(); i++) {
			switch (code.charAt(i)) {
			case 'o':
				score += 3;  
				break;
			case 'x':
				score += 5;
				break;
			case ' ':
				score += 7;
				break;
			default:
				break;
			}
			score *= 10;
		}

		return score;
	}

	@Override
	boolean isWin(String s) {
		return false;
	}

	@Override
	boolean isWin() {
		return false;
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

	public static void main(String[] args) throws InterruptedException {
		TTT_HC ticTac = new TTT_HC("TTT");
		System.out.println(ticTac.myHashCode());
	}
}
