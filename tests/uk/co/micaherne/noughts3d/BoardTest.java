package uk.co.micaherne.noughts3d;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.init();
	}

	@Test
	public void testInit() {
		assertEquals(Board.State.EMPTY, board.getSquareState(0, 0));
		assertEquals(Board.State.EMPTY, board.getSquareState(0, 8));
	}
	
	@Test
	public void testMove() throws IllegalMoveException {
		board.move(4);
		assertEquals(Board.State.O, board.getSquareState(0,4));
		assertFalse(board.noughtsToMove);
		board.move(4);
		assertEquals(Board.State.X, board.getSquareState(1,4));
		assertTrue(board.noughtsToMove);
		board.move(4);
		assertEquals(Board.State.O, board.getSquareState(2,4));
		assertFalse(board.noughtsToMove);
		try {
			board.move(4);
			fail();
		} catch (IllegalMoveException e) {
			assertFalse(board.noughtsToMove);
		}
	}
	
	@Test
	public void testMoveGen() throws IllegalMoveException {
		Set<Integer> legalMoves = board.moveGen();
		assertEquals(9, legalMoves.size());
		board.move(4);
		board.move(4);
		board.move(4);
		legalMoves = board.moveGen();
		assertEquals(8, legalMoves.size());
		assertFalse(legalMoves.contains(4));
	}
	
	@Test
	public void testGenerateLines() {
		assertEquals(65, board.lines.size());
	}

}
