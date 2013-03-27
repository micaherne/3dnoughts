package uk.co.micaherne.noughts3d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {
	
	public enum State {
		EMPTY,
		O,
		X
	}
	
	public class Line {
		
		public List<Integer[]> squares = new ArrayList<Integer[]>();
		
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
				line.squares.add(new Integer[]{i, j});
				line.squares.add(new Integer[]{i, j + 1});
				line.squares.add(new Integer[]{i, j + 2});
				lines.add(line);
			}
			for (Integer j = 0; j < 3; j++) {
				Line line = new Line();
				line.squares.add(new Integer[]{i, j});
				line.squares.add(new Integer[]{i, j + 3});
				line.squares.add(new Integer[]{i, j + 6});
				lines.add(line);
			}
		}
		
		// Columns
		for(Integer j = 0; j < 9; j++) {
			for (Integer i = 0; i < 3; i++) {
				Line line = new Line();
				line.squares.add(new Integer[]{i, j});
				line.squares.add(new Integer[]{i + 1, j});
				line.squares.add(new Integer[]{i + 2, j});
				lines.add(line);
			}
		}
		
		// Diagonals on planes
		for(Integer i = 0; i < 3; i++) {
			Line line = new Line();
			line.squares.add(new Integer[]{i, 0});
			line.squares.add(new Integer[]{i, 4});
			line.squares.add(new Integer[]{i, 8});
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Integer[]{i, 2});
			line.squares.add(new Integer[]{i, 4});
			line.squares.add(new Integer[]{i, 6});
			lines.add(line);
		}
		
		// Diagonals on columns - front to back
		for(Integer j = 0; j < 3; j++) {
			Line line = new Line();
			line.squares.add(new Integer[]{0, j});
			line.squares.add(new Integer[]{1, j + 3});
			line.squares.add(new Integer[]{2, j + 6});
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Integer[]{0, j + 6});
			line.squares.add(new Integer[]{1, j + 3});
			line.squares.add(new Integer[]{2, j});
			lines.add(line);
		}
		// left to right
		for(Integer j = 0; j < 7; j += 3) {
			Line line = new Line();
			line.squares.add(new Integer[]{0, j});
			line.squares.add(new Integer[]{1, j + 1});
			line.squares.add(new Integer[]{2, j + 2});
			lines.add(line);
			
			line = new Line();
			line.squares.add(new Integer[]{0, j + 2});
			line.squares.add(new Integer[]{1, j + 1});
			line.squares.add(new Integer[]{2, j});
			lines.add(line);
		}
		
		// Diagonal diagonals
		Line line = new Line();
		line.squares.add(new Integer[]{0, 0});
		line.squares.add(new Integer[]{1, 4});
		line.squares.add(new Integer[]{2, 8});
		lines.add(line);
		
		line = new Line();
		line.squares.add(new Integer[]{0, 2});
		line.squares.add(new Integer[]{1, 4});
		line.squares.add(new Integer[]{2, 6});
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

}
