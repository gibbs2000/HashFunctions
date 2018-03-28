import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class representing a Tic Tac Toe Board
 * 
 * @author Mrs. Kelly, Javadocs provided by Sean Gibbons
 *
 */
@SuppressWarnings("serial")
abstract class Board extends JFrame implements ActionListener {

	private JButton buttons[][];
	private JLabel lblHashCode;
	private JLabel lblWinTitle;

	private String boardString = "";

	/**
	 * Constructor, creates a JFrame with a title based on the given parameter and
	 * sets up the frame using helper methods
	 * 
	 * @param title
	 *            the name of the JFrame to be created
	 */
	public Board(String title) {
		super(title);
		setupFrame();
	}

	/**
	 * Updates the hashCodeLabel with a given integer
	 * 
	 * @param hashcode
	 *            the integer value to set the HashCodeLabel to
	 */
	public void setHashCodeLabel(int hashcode) {
		lblHashCode.setText("" + hashcode);
	}

	/**
	 * Updates the winnerLabel to a given String
	 * 
	 * @param result
	 *            the String value to set the winnerLabel to
	 */
	public void setWinnerLabel(String result) {
		lblWinTitle.setText(result);
	}

	/**
	 * Updates the winnerLabel using a boolean and calls the overloaded method based
	 * on the parameter
	 * 
	 * @param result
	 *            the boolean which dictates what to set the winnerLabel to
	 */
	public void setWinnerLabel(boolean result) {
		if (result)
			setWinnerLabel("Winner");
		else
			setWinnerLabel("Loser");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * Instantiates and returns the main panel, including TItle, layout,
	 * HashCodeLabel, and winnerLabel
	 * 
	 * @return the JPanel which has been created
	 */
	JPanel setupPanelOne() {
		JPanel panel = new JPanel();
		JLabel lblHCTitle = new JLabel("Hash Code");
		;
		lblHashCode = new JLabel("" + myHashCode());
		lblWinTitle = new JLabel(""); // Will say either Winner or Loser
		setWinnerLabel(TicTacToe.isWin(boardString));
		panel.setLayout(new FlowLayout());
		panel.add(lblHCTitle);
		panel.add(lblHashCode);
		panel.add(lblWinTitle);
		return panel;
	}

	/**
	 * Instantiates and returns the secondary panel, with the GridLayout of buttons
	 * which form the Board of the TicTacToe setup
	 * 
	 * @return the JPanel which has been created
	 */
	private JPanel setupPanelTwo() {
		JButton b;
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(TicTacToe.ROWS, TicTacToe.COLS));
		buttons = new JButton[TicTacToe.ROWS][TicTacToe.COLS];

		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();
				b = new JButton("" + ch);
				boardString += ch;
				b.setActionCommand("" + r + ", " + c);
				b.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton) e.getSource();
						btn.setText("" + cycleValue(btn.getText().charAt(0)));
						resetBoardString();
						setHashCodeLabel(myHashCode());
						setWinnerLabel(isWin());

					}
				});
				panel.add(b);
				buttons[r][c] = b;
			}

		return panel;
	}

	/**
	 * Returns the next character in the sequence " " -> "x" -> "o"
	 * 
	 * @param ch
	 *            the current character
	 * @return the next character in the sequence
	 */
	private static char cycleValue(char ch) {
		switch (ch) {
		case 'x':
			return 'o';
		case 'o':
			return ' ';
		default:
			return 'x';
		}
	}

	/**
	 * Sets up the entire frame using both setupPanelOne and setUpPanelTwo to fill
	 * the inside of the JFrame
	 */
	private void setupFrame() {
		JPanel panel2 = new JPanel();

		// Setup Frame
		super.setSize(250, 200);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Setup Panels
		panel2 = setupPanelTwo(); // panelOne displays a value that requires panelTwo to be ready
		super.add(setupPanelOne());
		super.add(panel2);

		super.setVisible(true);
	}

	/**
	 * Returns a random character from the dataset of {"x", "o", " "}
	 * 
	 * @return a random character
	 */
	private char randomXO() {
		int rnd = (int) (Math.random() * TicTacToe.CHAR_POSSIBILITIES);
		switch (rnd) {
		case 1:
			return 'x';
		case 2:
			return 'o';
		default:
			return ' ';
		}
	}

	/**
	 * Returns the HashCode representation of the given boardString
	 * 
	 * @return an int representation of the given boardString using hashing
	 */
	abstract int myHashCode();

	/**
	 * Sets the boardString to the parameter and then returns whether the
	 * boardString is a winning using a lookup table
	 * 
	 * @param s
	 *            the String parameter to which boardString will be set
	 * @return whether the boardString is a winner
	 */

	abstract boolean isWin(String s);

	/**
	 * Returns whether the current boardString is a winner using a lookup table
	 * 
	 * @return whether the current boardString is a winner
	 */
	abstract boolean isWin();

	/**
	 * Returns the character at a given position in the board grid
	 * 
	 * @param row
	 *            the row of the specific character
	 * @param col
	 *            the column of the specific character
	 * @return the character at a given row and col
	 */
	public char charAt(int row, int col) {
		String value = buttons[row][col].getText();
		if (value.length() > 0)
			return value.charAt(0);
		else
			return '*';
	}

	/**
	 * Returns the character at a given position in a given String if the String was
	 * represented asa 3x3 grid. Arithmetic is performed on the row and column
	 * parameters so that, instead of being in a 2d array, they are represented in a
	 * single longer String
	 * 
	 * @param s
	 *            the String to be checked
	 * @param row
	 *            the row of the specific character
	 * @param col
	 *            the column of the specific character
	 * @return the character at a given row and column in a given String
	 */
	public char charAt(String s, int row, int col) {
		int pos = row * TicTacToe.COLS + col;
		if (s.length() >= pos)
			return s.charAt(pos);
		else
			return '*';
	}

	/**
	 * Updates the values of the button layout to represent a given String
	 * 
	 * @param s
	 *            the String to which the buttons will be updated to fit
	 */
	public void show(String s) {
		int pos = 0;
		String letter;
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = s.charAt(pos);
				switch (ch) {
				case '1':
					letter = "x";
					break;
				case '2':
					letter = "o";
					break;
				case '0':
					letter = " ";
					break;
				default:
					letter = "" + ch;
				}
				buttons[r][c].setText(letter);
				pos++;
			}
	}

	/**
	 * Resets the boardString to whatever is currently displayed on the buttons
	 */
	public void resetBoardString() {
		boardString = "";
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				boardString += buttons[r][c].getText();
			}
	}

	/**
	 * Sets the boardString to a given parameter, then updates the buttons to fit
	 * that parameter
	 * 
	 * @param s
	 *            the new String paramter to which the boardString and button layout
	 *            will be set
	 */
	public void setBoardString(String s) {
		boardString = s;
		show(s);
	}

	/**
	 * Returns the current boardString
	 * 
	 * @return a String representation of the button layout
	 */
	public String getBoardString() {
		return boardString;
	}

	/**
	 * Sets boardString and the buttonLayout to a random String which may or may not
	 * be valid
	 */
	public void displayRandomString() {
		for (int r = 0; r < TicTacToe.ROWS; r++)
			for (int c = 0; c < TicTacToe.COLS; c++) {
				char ch = randomXO();
				boardString += ch;
				buttons[r][c].setText("" + ch);
			}
		setHashCodeLabel(myHashCode());
		setWinnerLabel(isWin());
	}

}