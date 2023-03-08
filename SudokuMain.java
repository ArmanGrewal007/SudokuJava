// package sudoku;
import java.awt.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   JPanel buttonPanel = new JPanel();
   JButton btnResetGame = new JButton("Reset Game");
   JButton btnSolve = new JButton("Solve");

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

      // Add a button to the buttonPanel to re-start the game via board.newGame()
      btnResetGame.addActionListener(event -> board.newGame());
      buttonPanel.add(btnResetGame);
      
      btnSolve.addActionListener(event -> board.solveAndSet());
      buttonPanel.add(btnSolve);
      
      // Add the button panel to JFrame
      cp.add(buttonPanel, BorderLayout.SOUTH);

      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setLocationRelativeTo(null);
      setTitle("Sudoku");
      setVisible(true);
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      new SudokuMain();
   }
}
