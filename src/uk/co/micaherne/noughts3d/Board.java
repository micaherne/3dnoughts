package uk.co.micaherne.noughts3d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.co.micaherne.noughts3d.Board.State;

public class Board {
	
	public enum Player {
		O,
		X
	}
	
	public enum State {
		EMPTY,
		O,
		X
	}
	
	public State[][] squares;
	public Set<Line> lines;
	
	public boolean noughtsToMove = true;
	
	public void init() {
		squares = new State[3][9];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				setSquareState(i, j, State.EMPTY);
			}
		}
		generateLines();
	}
	
	public void generateLines() {
		lines = new HashSet<Line>();
		// Lines on planes
		for(Integer i = 0; i < 3; i++) {
			for (Integer j = 0; j < 7; j += 3) {
				Line line = new Line();
				line.squares.add(new Square(i, j));
				line.squares.add(new Square(i, j + 1));
				line.squares.add(new Square(i, j + 2));
				lines.add(line);
			}
			for (Integer j = 0; j < 3; j++) {
				Line line = new Line();
				line.squares.add(new Square(i, j));
				line.squares.add(new Square(i, j + 3));
				line.squares.add(new Square(i, j + 6));
				lines.add(line);
			}
		}
		
		// Columns
		for(Integer j = 0; j < 9; j++) {
			Line line = new Line();
			line.squares.add(new Square(0, j));
			line.squares.add(new Square(1, j));
			line.squares.add(new Square(2, j));
			lines.add(line);
		}
		
		// Diagonals on planes
		for(Integer i = 0; i < 3; i++) {
			Line line = new Line();
			line.squares.add(new Square(i, 0));
			line.squares.add(new Square(i, 4));
			line.squares.add(new Square(i, 8));
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Square(i, 2));
			line.squares.add(new Square(i, 4));
			line.squares.add(new Square(i, 6));
			lines.add(line);
		}
		
		// Diagonals on columns - front to back
		for(Integer j = 0; j < 3; j++) {
			Line line = new Line();
			line.squares.add(new Square(0, j));
			line.squares.add(new Square(1, j + 3));
			line.squares.add(new Square(2, j + 6));
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Square(0, j + 6));
			line.squares.add(new Square(1, j + 3));
			line.squares.add(new Square(2, j));
			lines.add(line);
		}
		// left to right
		for(Integer j = 0; j < 7; j += 3) {
			Line line = new Line();
			line.squares.add(new Square(0, j));
			line.squares.add(new Square(1, j + 1));
			line.squares.add(new Square(2, j + 2));
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Square(0, j + 2));
			line.squares.add(new Square(1, j + 1));
			line.squares.add(new Square(2, j));
			lines.add(line);
		}
		
		// Diagonal diagonals
		Line line = new Line();
		line.squares.add(new Square(0, 0));
		line.squares.add(new Square(1, 4));
		line.squares.add(new Square(2, 8));
		lines.add(line);
		
		line = new Line();
		line.squares.add(new Square(0, 2));
		line.squares.add(new Square(1, 4));
		line.squares.add(new Square(2, 6));
		lines.add(line);
	}

	public State getSquareState(int i, int j) {
		return squares[i][j];
	}

	public void move(int j) throws IllegalMoveException {
		State newState = noughtsToMove ? State.O : State.X;
		for(int i = 0; i < 3; i++) {
			if (getSquareState(i, j) == State.EMPTY) {
				setSquareState(i, j, newState);
				noughtsToMove = !noughtsToMove;
				return;
			}
		}
		
		throw new IllegalMoveException();
	}

	public void setSquareState(int i, int j, State newState) {
		squares[i][j] = newState;
	}

	public Set<Integer> moveGen() {
		HashSet<Integer> result = new HashSet<Integer>();
		for(int j = 0; j < 9; j++) {
			if (getSquareState(2, j) == State.EMPTY) {
				result.add(j);
			}
		}
		return result;
	}

	public Map<State, Integer> checkLine(Line l) {
		Map<State, Integer> result = new HashMap<State, Integer>();
		result.put(State.EMPTY, 0);
		result.put(State.O, 0);
		result.put(State.X, 0);
		for (Square sq : l.squares) {
			Integer existingValue = result.get(getSquareState(sq.i, sq.j));
			existingValue++;
			result.put(getSquareState(sq.i, sq.j), existingValue);
		}
		return result;
	}

	public Player getWinner() {
		for(Line l : lines) {
			Map<State, Integer> check = checkLine(l);
			if (check.get(State.O) == 3) {
				return Player.O;
			} else if (check.get(State.X) == 3) {
				return Player.X;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		String result = " 1   2   3\n--- --- ---\n";
		for(int a = 6; a >= 0; a -= 3) { // row
			for(int b = 0; b < 3; b++) { // layer
				for(int c = 0; c < 3; c++) { // col
					Integer i = b;
					Integer j = a + c;
					switch (getSquareState(i, j)) {
					
						case O:
							result += 'O';
							break;
						case X:
							result += 'X';
							break;
						case EMPTY:
							result += '+';
							break;
					}
				}
				result += " ";
			}
			result += "\n";
		}
		
		return result;
	}
}
