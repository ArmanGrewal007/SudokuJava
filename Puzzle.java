// package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Sudoku number puzzle to be solved
 */

public class Puzzle {
   // All variables have package access
   // The numbers on the puzzle
   int[][] numbers = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
   // The clues - isGiven (no need to guess) or need to guess
   boolean[][] isGiven = new boolean[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
   
   private static final int EMPTY_CELL = 0;


   // Constructor
   public Puzzle() {
      super();
   }

   // Generate a new puzzle given the number of cells to be guessed, which can be used
   //  to control the difficulty level.
   // This method shall set (or update) the arrays numbers and isGiven
   public void obsolete_newPuzzle(int cellsToGuess) {
      // I hardcode a puzzle here for illustration and testing.
      int[][] hardcodedNumbers ={
    		  { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
    		  { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
    		  { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
    		  { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
    		  { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
    		  { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
    		  { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
    		  { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
    		  { 0, 9, 0, 0, 0, 0, 4, 0, 0 }
         	};

      // Copy from hardcodedNumbers into the array "numbers"
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
         for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
            numbers[row][col] = hardcodedNumbers[row][col];
         }
      }
      
      // Update the isGiven() array according to numbers array
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
          for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
        	 if(hardcodedNumbers[row][col] != 0)
        		 isGiven[row][col] = true;
             else
                 isGiven[row][col] = false;

          }
       }

   }

   //(For advanced students) use singleton design pattern for this class
   
   /**********************************************
    * New newPuzzle()
    *********************************************/
   public void newPuzzle(int guesses, boolean mostDifficult) {
	   int[][] out_board;
	   if(mostDifficult) 
		   out_board = getMostDifficultSudoku();
	   else
		   out_board = getSudokuGuesses(guesses);
	   
      // Copy from hardcodedNumbers into the array "numbers"
      for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
          for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
             numbers[row][col] = out_board[row][col];
          }
       }
       
       // Update the isGiven() array according to numbers array
       for (int row = 0; row < GameBoardPanel.GRID_SIZE; ++row) {
           for (int col = 0; col < GameBoardPanel.GRID_SIZE; ++col) {
         	 if(out_board[row][col] != 0)
         		 isGiven[row][col] = true;
              else
                  isGiven[row][col] = false;

           }
        }
   }
   
   /**********
    *  Greedy algorithm (will print the most difficult minimal sudoku possible)
    **********/
	public int[][] getMostDifficultSudoku() {
		int[][] candidate = generateSudokuBoard();
		int[] temp_arr;
		int flag = 0;
		while(flag == 0) {
			// [i, j, num]
			temp_arr = removeOneElement(candidate);
			if(!hasUniqueSoln(candidate)) {
				// add element back and set flag to 1
				candidate[temp_arr[0]][temp_arr[1]] = temp_arr[2];
				flag = 1;
			}
		}
		return candidate;
	}
	/******
	 * Returns minimal sudoku board with k guesses
	 ******/
	public int[][] getSudokuGuesses(int k){
		int[][] candidate = generateSudokuBoard();
		int[] temp_arr;
		int flag = 0;
		while(flag == 0 && k != 0) {
			// [i, j, num]
			temp_arr = removeOneElement(candidate);
			if(!hasUniqueSoln(candidate)) {
				// add element back and set flag to 1
				candidate[temp_arr[0]][temp_arr[1]] = temp_arr[2];
				flag = 1;
			}
			k--;
		}
		return candidate;
	}
	/*************************************************************
	 * Step 1 - Generating a valid random sudoku board
	 **************************************************************/
    private int[][] generateSudokuBoard(){
		int[][] board = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
		
		// Step 1: Fill diagonal sub-grids with random permutations
		for (int i = 0; i < GameBoardPanel.GRID_SIZE; i += GameBoardPanel.SUBGRID_SIZE) {
		    helper_fillSubgrid(board, i, i);
		}
		
		// Step 2: Fill non-diagonal sub-grids recursively
		helper_fillBoard(board, 0, GameBoardPanel.SUBGRID_SIZE);

		
		//For debugging, final methods will not print anything, just change in original grid
		// printBoard(board);
		return board;
    }
    /**
     * Helper func: fill the 3x3 sub-grids with random permutation of 1-9
     */
    private void helper_fillSubgrid(int[][] board, int row, int col) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);

        int index = 0;
        for (int i = row; i < row + GameBoardPanel.SUBGRID_SIZE; i++) {
            for (int j = col; j < col + GameBoardPanel.SUBGRID_SIZE; j++) {
                board[i][j] = nums.get(index);
                index++;
            }
        }
    }
    /**
     * Helper func: Fill the rest of board with valid numbers using backtracking
     */
    private void helper_fillBoard(int[][] board, int row, int col) {
        if (col >= GameBoardPanel.GRID_SIZE) {
            row++;
            col = 0;
        }

        if (row >= GameBoardPanel.GRID_SIZE) {
            return;
        }

        if (board[row][col] != 0) {
            helper_fillBoard(board, row, col + 1);
            return;
        }

        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            nums.add(i);
        }
        Collections.shuffle(nums);

        for (int num : nums) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                helper_fillBoard(board, row, col + 1);
                if (isSolved(board)) {
                    return;
                }
                board[row][col] = 0;
            }
        }
    }
	/***************************************************************************
	 * Step 2 - Check for minimal sudoku (hasUniqueSoln)
	 ***************************************************************************/
    public boolean hasUniqueSoln(int[][] grid) {
    	// Slightly modify allSolutions for reducing time complexity
    	// If there is one solution, add to List<int[][]> 
    	// if there are more, add only one more
    	List<int[][]> allsol = allSolutions(grid);
    	if(allsol.size() == 1) return true;
    	else return false;
    }
    /**
     * Returns all solutions possible (should be 1 ideally) 
     * @param grid
     * @return  List of all solution grids
     */
    public List<int[][]> allSolutions(int[][] grid) {
        List<int[][]> solutions = new ArrayList<>();
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        int solutionCount = 0;

        // Find the next empty cell in the grid
        for (int i = 0; i < GameBoardPanel.GRID_SIZE; i++) {
            for (int j = 0; j < GameBoardPanel.GRID_SIZE; j++) {
                if (grid[i][j] == EMPTY_CELL) {
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // If there are no empty cells, the grid is already solved
        if (isEmpty) {
            int[][] copy = new int[GameBoardPanel.GRID_SIZE][GameBoardPanel.GRID_SIZE];
            for (int i = 0; i < GameBoardPanel.GRID_SIZE; i++) {
                for (int j = 0; j < GameBoardPanel.GRID_SIZE; j++) {
                    copy[i][j] = grid[i][j];
                }
            }
            solutions.add(copy);
            return solutions;
        }

        // Try each value from 1 to 9 in the empty cell
        for (int num = 1; num <= GameBoardPanel.GRID_SIZE; num++) {
            if (isValid(grid, row, col, num)) {
                grid[row][col] = num;

                // Recursively try to solve the updated grid
                List<int[][]> subSolutions = allSolutions(grid);
                solutions.addAll(subSolutions);
                
                // Return as soon as one solution is encountered
//                solutionCount += subSolutions.size();
//                if (solutionCount > 1) {
//                	return solutions;
//                }

                // If the recursive call did not solve the grid, backtrack and try the next number
                grid[row][col] = EMPTY_CELL;
            }
        }

        // If no value from 1 to 9 works in this cell, backtrack to the previous cell
        return solutions;
    }
	/***************************************************************************
	 * Step 3 - Remove elements from sudoku board until you get a minimal sudoku
	 ***************************************************************************/
    /**
     * Removes one element
     * @return [i, j, num]
     */
	private static int[] removeOneElement(int[][] board) {
	    Random rand = new Random();
        int i = rand.nextInt(GameBoardPanel.GRID_SIZE);
        int j = rand.nextInt(GameBoardPanel.GRID_SIZE);
        int num = -1;
        if (board[i][j] != 0) {
        	num = board[i][j];
            board[i][j] = 0;
        }
        int[] arr = {i, j, num};
	    return arr;
	}
	
	/***********************************
	 * Helper functions also implemented in GameBoardPanel
	 **********************************/
    public boolean isValid(int[][] numbers, int row, int col, int num) {
        // Same number should not be present in row and column O(9)
        for (int i = 0; i < GameBoardPanel.GRID_SIZE; i++) {
            if (numbers[row][i] == num || numbers[i][col] == num) {
                return false;
            }
        }
        // Same number should not be present in sub-grid O(9)
        int boxRow = row - row % GameBoardPanel.SUBGRID_SIZE; // Determine the starting row of sub-grid
        int boxCol = col - col % GameBoardPanel.SUBGRID_SIZE; // Determine the starting col of sub-grid
        for (int i = boxRow; i < boxRow + GameBoardPanel.SUBGRID_SIZE; i++) {
            for (int j = boxCol; j < boxCol + GameBoardPanel.SUBGRID_SIZE; j++) {
                if (numbers[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isSolved(int[][] board) {
    	for(int[] row : board) {
    		for(int num:row) {
    			if(num == 0)
    				return false;
    		}
    	}
    	return true;
    }
   
}