import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class representing a TicTacToe board with a more advanced Hashcode
 * functionality
 * 
 * @author Sean Gibbons
 *
 */
@SuppressWarnings("serial")
public class TTT_HC extends Board {
	Object[] winners;
	private final int maxHash = 720; // value determined by finding largest possible hashCode score (all o's)

	@SuppressWarnings("unchecked")
	/**
	 * A constructor that takes in the title of the TTT_HC to be created and uses
	 * the super appropriately
	 * 
	 * @param title
	 *            the String name of the TTT_HC to be created
	 */
	public TTT_HC(String title) {
		super(title);
		// Creates the winner array
		winners = new Object[maxHash];

		// fills the winner array using the TicTacToeWinners.txt file
		Scanner wins = fileToScanner("TicTacToeWinners.txt");
		while (wins.hasNextLine()) {
			this.setBoardString(wins.nextLine());
			if (winners[this.myHashCode()] == null) {
				// without collisions, the hashCode works as normal
				winners[this.myHashCode()] = true;
				continue;
			}
			if (!(winners[this.myHashCode()] instanceof ArrayList<?>)) {
				// creates a bucket and makes the first element of bucket the prexisting
				// value already in the spot, then adds the current value to the bucket
				ArrayList<Boolean> bucket = new ArrayList<Boolean>();
				bucket.add(new Boolean((boolean) winners[this.myHashCode()]));
				bucket.add(true);
				winners[this.myHashCode()] = bucket;
			} else {
				// or if the bucket already exists, simply adds to the bucket
				((ArrayList<Boolean>) winners[this.myHashCode()]).add(true);
			}

		}
		wins.close();
	}

	@Override
	public int hashCode() {
		int score = 0;
		for (int i = 1; i < TicTacToe.ROWS + 1; i++) {
			for (int j = 1; j < TicTacToe.COLS + 1; j++) {
				int tempI = i * 3;
				int tempJ = j * 7;
				switch (super.charAt(i - 1, j - 1)) {

				case ' ':
					score += (tempI + tempJ) * 3;
					break;
				case 'x':
					score += (tempI + tempJ) * 5;
					break;
				case 'o':
					score += (tempI + tempJ) * 7;
					break;
				default:
					break;
				}
			}
		}
		return score - 540;
		// through testing, 540 was found to be the lowest possible (all " "s), so
		// subtracting 540 ensures that the lookup table starts at the right place (0)
	}

	@Override
	int myHashCode() {
		return hashCode();
	}

	@Override
	public boolean isWin(String s) {
		this.setBoardString(s);
		return isWin();
	}

	@Override
	boolean isWin() {
		if (this.myHashCode() >= this.winners.length) {
			return false;
		}
		if (this.winners[this.myHashCode()] instanceof Boolean) {
			return (boolean) winners[this.myHashCode()];
		}
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

	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < winners.length; i++) {
			if (winners[i] != null) {
				output += winners[i] + " ";
			}
			if (i % 10 == 1)
				output += "\n";
		}
		return output;
	}

	/**
	 * Prints the required reporting on the size, distribution, buckets, etc. which
	 * evaluate the effectiveness of the Hashcode function
	 */
	public void reportStats() {
		String output = "";
		int size = 0;
		int bucketNum = 0, singles = 0, biggestBucket = 0;
		double avgSize = 0.0;

		for (int i = 0; i < winners.length; i++) {
			if (winners[i] != null) {
				size++;
				if (winners[i] instanceof ArrayList<?>) {
					@SuppressWarnings("unchecked")
					ArrayList<Boolean> thisBucket = (ArrayList<Boolean>) winners[i];
					bucketNum++;
					avgSize += thisBucket.size();

					if (thisBucket.size() > biggestBucket)
						biggestBucket = thisBucket.size();
					continue;
				}
				singles++;

			}
		}
		avgSize = avgSize / bucketNum;
		output += "Array Size: " + size + "\nLoad Factor: " + (((double) size) / ((double) bucketNum)) + "\n";
		output += "Bucket Reporting: \n\nTotal Buckets: " + bucketNum + "\nEntries without collisions: " + singles
				+ "\nBiggest bucket: " + biggestBucket + "\nAverage Bucket Size: " + avgSize + "\n\n";

		System.out.println(output);
		quartiles();
		tenths();
	}

	/**
	 * Prints the distribution of the Hashcode across its four quartiles
	 */
	public void quartiles() {
		String output = "Reporting on Quartile Distribution...\n\n";

		for (int curQ = 0; curQ < 4; curQ++) {
			int entries = 0;
			int initial = (winners.length - 1) / 4;
			for (int i = initial * curQ; i < initial * (curQ + 1); i++) {
				if (winners[i] != null)
					entries++;
			}
			output += "For quartile " + (curQ + 1) + ", there are " + entries + " entries\n";

		}
		System.out.println(output);
	}

	/**
	 * Prints the collisions per tenth of the Hashcode
	 */
	@SuppressWarnings("unchecked")
	public void tenths() {
		String output = "Reporting on Collisions per Tenth...\n\n";

		for (int curTenth = 0; curTenth < 10; curTenth++) {
			int collisions = 0;
			int initial = (winners.length - 1) / 10;
			for (int i = initial * curTenth; i < initial * (curTenth + 1); i++) {
				if (winners[i] instanceof ArrayList<?>)
					collisions += ((ArrayList<Object>) winners[i]).size();
			}
			output += "For tenth " + (curTenth + 1) + ", there are " + collisions + " collisions\n";

		}
		System.out.println(output);

	}

	/**
	 * Tests the myHashCode method thoroughly using the same initial test values
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		Scanner test = fileToScanner("TTT_Tests.txt");
		TTT_HC ticTac = new TTT_HC("TTT");
		ticTac.reportStats();
		test.close();
	}

}
