import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A class that creates a hashMap using my hashCode function
 * 
 * @author Sean Gibbons
 *
 */
public class TicTacToeMyHashMap {

	HashMap<TTT_HC, Boolean> map;

	/**
	 * Instantiates and fills the HashMap using an input file, then closes the
	 * Scanner
	 */
	TicTacToeMyHashMap() {

		map = new HashMap<TTT_HC, Boolean>();
		Scanner wins = fileToScanner("TicTacToeWinners.txt");

		while (wins.hasNextLine()) {
			TTT_HC ticTac = new TTT_HC("TicTac");
			ticTac.setBoardString(wins.nextLine());
			map.put(ticTac, true);
		}
		wins.close();
	}

	/**
	 * Returns the default capacity of the hashMap that I created
	 * 
	 * @return the capacity of the hashMap
	 * @throws NoSuchFieldException
	 *             in case the field "table" does not exist
	 * @throws IllegalAccessException
	 *             in case Java gets mad
	 */
	private int capacity() throws NoSuchFieldException, IllegalAccessException {

		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(map);

		return table == null ? 0 : table.length;
	}

	/**
	 * Prints the required reporting on my hashCode method and HashMap class,
	 * including capacity and distribution
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void reportStats()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String output = "";

		output += "Capacity: " + this.capacity() + "\n";

		Field tableField = HashMap.class.getDeclaredField("table");
		tableField.setAccessible(true);
		Object[] table = (Object[]) tableField.get(map);
		output += reportStats(table);
		System.out.println(output);
	}

	/**
	 * Returns a String output of the required states of a given table, including
	 * max and average bucket size, and distribution
	 * 
	 * @param table
	 *            the table to be analyzed
	 * @return a String output containing the required reporting
	 */
	public String reportStats(Object[] table) {
		String output = "";
		int size = 0;
		int bucketNum = 0, singles = 0, biggestBucket = 0;
		double avgSize = 0.0;

		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				size++;
				if (table[i] instanceof ArrayList<?>) {
					ArrayList<?> thisBucket = (ArrayList<?>) table[i];
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
		output += "Array Size: " + size + "\n";
		output += "Bucket Reporting: \n\nTotal Buckets: " + bucketNum + "\nEntries without collisions: " + singles
				+ "\nBiggest bucket: " + biggestBucket + "\nAverage Bucket Size: " + avgSize + "\n\n";

		output += quartiles(table) + "\n" + tenths(table);
		return output;
	}

	/**
	 * Returns a String output of the quartile distribution of a given table
	 * 
	 * @param table
	 *            the table to be analyzed
	 * @return a String representing the quartile distribution of a given table
	 */
	public String quartiles(Object[] table) {
		String output = "Reporting on Quartile Distribution...\n\n";

		for (int curQ = 0; curQ < 4; curQ++) {
			int entries = 0;
			int initial = (table.length - 1) / 4;
			for (int i = initial * curQ; i < initial * (curQ + 1); i++) {
				if (table[i] != null)
					entries++;
			}
			output += "For quartile " + (curQ + 1) + ", there are " + entries + " entries\n";

		}
		return output;
	}

	/**
	 * Returns a String output of the collisions per tenth of a given table
	 * 
	 * @param table
	 *            the table to be checked for collisions
	 * @return a String output of the collisions per tenth of a given table
	 */
	@SuppressWarnings("unchecked")
	public String tenths(Object[] table) {
		String output = "Reporting on Collisions per Tenth...\n\n";

		for (int curTenth = 0; curTenth < 10; curTenth++) {
			int collisions = 0;
			int initial = (table.length - 1) / 10;
			for (int i = initial * curTenth; i < initial * (curTenth + 1); i++) {
				if (table[i] instanceof ArrayList<?>)
					for (@SuppressWarnings("unused")
					Object o : (ArrayList<Object>) table[i]) {
						collisions++;
					}
			}
			output += "For tenth " + (curTenth + 1) + ", there are " + collisions + " collisions\n";

		}
		return output;
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

	/**
	 * Tests the created HashMap using my hashCode
	 * 
	 * @param args
	 *            optional String parameters, not used in this case
	 * @throws NoSuchFieldException
	 *             in case the field that is being searched for does not exist
	 * @throws SecurityException
	 *             in case Java gets mad
	 * @throws IllegalArgumentException
	 *             in case the arguments are improperly formatted or cast
	 *             incorrectly
	 * @throws IllegalAccessException
	 *             in case Java gets mad
	 */
	public static void main(String[] args)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		TicTacToeMyHashMap m = new TicTacToeMyHashMap();

		m.reportStats();

	}

}