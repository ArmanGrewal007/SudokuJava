// package sudoku;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for the game board properties
   public static final int GRID_SIZE = 9;    // Size of the board
   public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int BOARD_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
                                             // Board width/height in pixels


   // Define properties
   /** The game board composes of 9x9 Cells (customized JTextFields) */
   private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
   /** It also contains a Puzzle with array numbers and isGiven */
   private Puzzle puzzle = new Puzzle();
   private int[][] toCheck = puzzle.numbers;
   
   /** Constructor */
   public GameBoardPanel() {
      super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // JPanel

      // Allocate the 2D array of Cell, and add into JPanel.
      addBordersandCells();

      // Allocate a common listener as the ActionEvent listener for all the Cells (JTextFields)
      CellInputListener listener = new CellInputListener();
      // Adds this common listener to all editable cells
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            if (cells[row][col].isEditable()) {
               cells[row][col].addActionListener(listener);   // For all editable rows and cols
            }
         }
      }    
      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }
   
   public void addBordersandCells() {
	    Border thinBorder = BorderFactory.createLineBorder(Color.BLACK, 0); // thinner inner border   
	    // Initialize grid with empty text fields
	    for (int row = 0; row < GRID_SIZE; row++) {
	        for (int col = 0; col < GRID_SIZE; col++) {
	            cells[row][col] = new Cell(row, col);
	            super.add(cells[row][col]);   // JPanel
	            /* 8 types of borders are there, for each unique cell in 3x3 
	             * sub-grid (central cell has no border */
	            // top-row
	            if(row%3 == 0 && col%3 == 0) {
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK), thinBorder));
	            }
	        	// top-mid
	        	else if (row%3 == 0 && col%3 == 1) {
	        		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 0, 1, 1, Color.BLACK), thinBorder));
	        	}
	        	// top-right
	        	else if (row%3 == 0 && col%3 == 2) {
	            	if (col!= 8)
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 0, 1, 0, Color.BLACK), thinBorder));
	            	else 
	                	cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(3, 0, 1, 3, Color.BLACK), thinBorder));
	        	}
	            
	            // mid-row
	            else if(row%3 == 1 && col%3 == 0) {
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 3, 1, 1, Color.BLACK), thinBorder));
	            }
	        	// mid-mid
	            else if(row%3 == 1 && col%3 == 1) {
	        		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), thinBorder));
	            }
	        	// mid-right
	            else if (row%3 == 1 && col%3==2) { // WHY NOT col%3 == 2?
	            	if (col!= 8)
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK), thinBorder));
	            	else 
	                	cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 3, Color.BLACK), thinBorder));
	        	}
	            // bottom-left
	            else if(row%3 == 2 && col%3 == 0 ) {
	            	if (row!=8)
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 3, 0, 1, Color.BLACK), thinBorder));
	            	else
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 3, 3, 1, Color.BLACK), thinBorder));
	
	            }
	        	// bottom-mid
	        	else if (row%3 == 2 && col%3 == 1) {
	        		if(row!=8)
	        			cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK), thinBorder));
	        		else
	            		cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 3, 1, Color.BLACK), thinBorder));
	        	}
	            // bottom-right
	        	else if (row%3 == 2 && col%3 == 2) {
	        	    if(row!=8) {
	        	        if(col!=8) {
	        	            cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK), thinBorder));
	        	        }
	        	        else {
	        	            cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK), thinBorder));
	        	        }
	        	    }
	        	    else {
	        	        if(col!=8) {
	        	            cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK), thinBorder));
	        	        }
	        	        else {
	        	            cells[row][col].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.BLACK), thinBorder));
	        	        }
	        	    }
	        	}
	        }
	    }
   }
   
   /************************************************************************
    * Function added to the btnSolve
    ************************************************************************/
   /**
    * Use solve() and also set solved puzzle to display
    */
   public void solveAndSet() {
	   if(solve(puzzle.numbers)) {
	       // Update grid with solved puzzle
	       for (int row = 0; row < 9; row++) {
	           for (int col = 0; col < 9; col++) {
	               cells[row][col].setText(Integer.toString(puzzle.numbers[row][col]));
	           }
	       }
//	       JOptionPane.showMessageDialog(this, "Puzzle solved!");
       } 
	   else {
//           JOptionPane.showMessageDialog(this, "Unable to solve puzzle.");
       }
   }
   /**
    * Solve the puzzle using backtracking
    */
   public boolean solve(int[][] numbers) {
       for (int row = 0; row < GRID_SIZE; row++) {
           for (int col = 0; col < GRID_SIZE; col++) {
               if (numbers[row][col] == 0) { // If empty cell
                   for (int value = 1; value <= 9; value++) {
                       if (isValid(numbers, row, col, value)) {
                           numbers[row][col] = value;
//                           count++; System.out.println("count: " + count);
                           if (solve(numbers)) {
                               return true;
                           } 
                           else {
                               numbers[row][col] = 0; // Backtrack
//                               count++; System.out.println("count: " + count);
                           }
                       }
                   }
                   return false; // Unable to place valid
               }
           }
       }
		return true;
   }

   /************************************************************************
    * Helper func: Return true if given cell placement is valid
    ************************************************************************/
    public boolean isValid(int[][] numbers, int row, int col, int num) {
       // Same number should not be present in row and column O(9)
       for (int i = 0; i < GRID_SIZE; i++) {
           if (numbers[row][i] == num || numbers[i][col] == num) {
               return false;
           }
       }
       // Same number should not be present in sub-grid O(9)
       int boxRow = row - row % SUBGRID_SIZE; // Determine the starting row of sub-grid
       int boxCol = col - col % SUBGRID_SIZE; // Determine the starting col of sub-grid
       for (int i = boxRow; i < boxRow + SUBGRID_SIZE; i++) {
           for (int j = boxCol; j < boxCol + SUBGRID_SIZE; j++) {
               if (numbers[i][j] == num) {
                   return false;
               }
           }
       }
       return true;
   }

   /************************************************************************
    * Helper func: Return true if the puzzle is solved
    ************************************************************************/
   public boolean isSolved() {
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      return true;
   }

   // Define a Listener Inner Class for all the editable Cells
   private class CellInputListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Get a reference of the JTextField that triggers this action event
         Cell sourceCell = (Cell)e.getSource();

         // Retrieve the int entered
         int numberIn = Integer.parseInt(sourceCell.getText());
         // For debugging
         System.out.println("You entered " + numberIn);

         /*
          * Check the numberIn against sourceCell.number.
          * Update the cell status sourceCell.status,
          * and re-paint the cell via sourceCell.paint().
          */
         solve(toCheck);
          if (numberIn == toCheck[sourceCell.row][sourceCell.col]) {
             sourceCell.status = CellStatus.CORRECT_GUESS;
          } 
          else {
              sourceCell.status = CellStatus.WRONG_GUESS;
          }
          sourceCell.paint();   // re-paint this cell based on its status

         /*
          *   Check if the player has solved the puzzle after this move,
          *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
          */
          if(isSolved()) {
        	  JOptionPane.showMessageDialog(null, "Solved!");
          }
      }
   }
   
   /************************************************************************
    * Function added to btnResetGame
    ************************************************************************/
   /**
    * Obsolete newGame(), generates the hardcoded puzzle from Puzzle.numbers
    */
   public void newGame() {
      // Generate a new puzzle
      puzzle.newPuzzle(2, true);

      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
   }
}