import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class connect_four_play {

	public static void main(String args[]) {
		int size = 10;
		int move;
		char [][] board = new char [size][size];
		char player = 'R';
		boolean game_over = false;
		boolean error = true;
		
		
		Scanner myScanner = new Scanner(System.in);
		
		init_board(board);
		print_board(board);
		
		while(!game_over) {
			System.out.println("Player "+ player + ", enter a move (0-9): ");
			error = true; 
						
			while (error) {
				
				try {
					move = myScanner.nextInt();
					
					while ((get_top_index(board,move)-1)<0) {
						System.out.println("Column full. Enter another move: ");
						move = myScanner.nextInt();
					}
					
					
					make_move(board,player,move);
					print_board(board); 
					myScanner.nextLine();
					
					if(row_contains_horizontal_win(board, get_top_index(board,move), player) || row_contains_vertical_win(board,move,player) || row_contains_diagonal_win(board,move,player) || row_contains_diagonal_win2(board,move,player)) {
						
						System.out.println("Congrats player: "+ player + " You win!");
						game_over = true;
					}
					
					
					error = false; 
				}
				
				catch (InputMismatchException e) {
					myScanner.nextLine();
					System.out.println("Invalid move. Please enter a number between 0 - 9: ");

				}
				catch (ArrayIndexOutOfBoundsException e) {
					myScanner.nextLine();
					System.out.println("Invalid move. Please enter a number between 0 - 9: ");

				}
		
				
			}	
			
		
		
		if(player == 'R') {
			player = 'B';
		}	
		else {
			player = 'R';
		}
		}
		
		myScanner.close();
	}
	
	
	public static int get_top_index(char [][] board, int column) {
		//returns ROW that corresponds to the top of the current stack
		for (int i = (board.length - 1); i >=0; i--) {
			
			if (board[i][column]== ' ') {
				return (i+1); 
			}
		}
		return 0;
	}
	
	public static boolean row_contains_horizontal_win(char [][] board, int row, char player) {
			
			//1234, 2345, 3456, 4567, 5678, ...
			//if size is 4 we have 1 iteration 
			//if size is 5 we have 2 : (1 + (n-4)) = 2
			//if size is 6 we have 3: (1 + (2)) = 3
		
			boolean win = false;			
			
			for (int k = 0; k < (1 + (board.length -4)); k++) { 
				//outer loop gives us starting index
				//assume win is true to start and change to false if it enters if statement
				win = true;
				
				for (int i = k; i < (k+4); i++) {
					//starts at starting index k 
					//checks the 4 adjacent spots after it
					
					if (board[row][i] !=player) {
						//if any of these particular 4 spots are not char player, it is automatic not a win
						win = false;
						
					}
				}
				
				//after inner loop checks the four adj spots, it goes here 
				//only return/ break if its a win, or else, keep checking
				if (win) {
					return win;
				}
				//return win;	
			}
			
		
		return win;
	}
	
	public static boolean row_contains_vertical_win(char [][] board, int column, char player) {
	
		boolean win = false;	
			
			for (int k = 0; k < (1 + (board.length -4)); k++) { 
				//outer loop gives us starting index
				//assume win is true to start and change to false if it enters if statement
				win = true;
				
				for (int i = k; i < (k+4); i++) {
					//starts at starting index k 
					//checks the 4 adjacent spots after it
					
					if (board[i][column] !=player) {
						win = false;
						
					}
				}
				
				if (win) {
					return win;
				}
			}
			
		
		return win;
	}
	
	
	public static boolean row_contains_diagonal_win(char [][] board, int column, char player) {
		//checks positive slope for diagonal win
		int row_init;
		int row;
		row_init = get_top_index(board, column); 
		int column_init = column;
		
		boolean win = false;	
		int streak = 0;
		
		boolean second_loop = false;
		
			row = row_init;
			column = column_init;
			while (row >= 0 && column < 10) {
				//while in bounds of the board
				//checks right diagonal upwards to end of board
				//increment column and increment row
				second_loop = false;
		
				if(board [row][column] == player) {
					streak++;
				}
				else {
					//clear streak
					streak = 0;
					
					//start at the last index and check other direction
					row = row + 1;
					column = column -1;
					
					while (row < 10 && column >= 0) {
						second_loop = true; 

						if(board [row][column] == player) {
							streak++;
							
							if(streak ==4) {
								win = true;
								return win;
							}		
							
						}
						else {
							streak = 0;
							win = false; 
							return win; 
						}
						
						row++;
						column --;
						
					}								

				}
				
				if(streak ==4) {
					win = true;
					return win;
				}
				if(second_loop) {
							win = false; 
							return win; 
						}
				row--;
				column++;			
			}
		
		return win;
	}
	
	
	public static boolean row_contains_diagonal_win2(char [][] board, int column, char player) {
		//checks negative slope for diagonal win
		int row_init;
		int row;
		row_init = get_top_index(board, column); 
		int column_init = column;
		
		boolean win = false;	
		int streak = 0;
		
		boolean second_loop = false;
		
			row = row_init;
			column = column_init;
			while (row >= 0 && column >= 0) {
				second_loop = false;
	
				if(board [row][column] == player) {
					streak++;
				}
				else {
					//clear streak
					streak = 0;
					
					//start at the last index and check other direction
					row = row + 1;
					column = column +1;
					
					while (row < 10 && column < 10) {
						second_loop = true; 
						if(board [row][column] == player) {
							streak++;
							
							if(streak ==4) {
								win = true;
								return win;
							}
						
						}
						else {
							streak = 0;
							win = false; 
							return win; 
						}
						
						row++;
						column ++;	
					}								
				}
				
				if(streak ==4) {
					win = true;
					return win;
				}
				if(second_loop) {
							win = false; 
							return win; 
						}
				row--;
				column--;			
			}
		
		return win;
	}
	
	//another possible implementation for horizontal wins
	public static boolean row_contains_horizontal_win2(char [][] board, int row, char player) {
		int streak = 0;
		for (int i = 0; i < board.length; i++) {
			if(board[row][i]==player) {
				streak++;
				if (streak == 4) {
					return true;
				}
				else {
					streak = 0;
				}
			}
		}
		return false;
	}
	
	
	
	public static void init_board(char [][] board) {
		//fills it all with spaces instead of zeroes
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	public static void make_move(char [][] board, char player, int column) {
		//mutate boards when moves are made	
		
		
		int row = get_top_index(board, column)-1;
		board[row][column] = player;
				
	}
	
	public static void print_board(char [][] board) {
		String s = "";
		
		for (char[] row : board) {			
			s += Arrays.toString(row) + "\n";
			
		}
		System.out.println(s);
		
	}
	
}





