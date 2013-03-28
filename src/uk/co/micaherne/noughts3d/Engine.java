package uk.co.micaherne.noughts3d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Engine {
	
	public static void main(String args[]) throws IOException, IllegalMoveException, NoValidMovesException {
		
		Board board = new Board();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean play = true;
		
		while(play) {
			board.init();
			
			while (board.getWinner() == null) {
				if (board.noughtsToMove) {
					System.out.println(board);
					System.out.println("Your move");
					String humanMove = br.readLine();
					Integer humanMoveInt = Integer.parseInt(humanMove);
					board.move(humanMoveInt);
					
					if (board.getWinner() == null) {
						int move = board.bestMove(5);
						System.out.println("My move: " + move);
						board.move(move);
					}
				}
			}
			
			System.out.println(board.getWinner() + " wins!!");
			System.out.println(board);
			
			System.out.println("Play again? (y/n)");
			String playAgain = br.readLine();
			if (!"y".equals(playAgain.toLowerCase())) {
				play = false;
			}
		}
	}

}
