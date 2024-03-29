package uk.co.micaherne.noughts3d;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.spi.DirStateFactory.Result;

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
		List<Integer> legalMoves = board.moveGen();
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
		assertEquals(49, board.lines.size());
		Line l = new Line();
		l.squares.add(new Square(0, 5));
		l.squares.add(new Square(1, 5));
		l.squares.add(new Square(2, 5));
		assertTrue(board.lines.contains(l));
	}
	
	@Test
	public void testCheckLine() throws IllegalMoveException {
		board.move(5);
		board.move(5);
		board.move(5);
		
		Line l = new Line();
		l.squares.add(new Square(0, 5));
		l.squares.add(new Square(1, 5));
		l.squares.add(new Square(2, 5));
		
		Map<Board.State, Integer> result = board.checkLine(l);
		Integer r1 = result.get(Board.State.O);
		assertEquals(2, r1.longValue());
		Integer r2 = result.get(Board.State.X);
		assertEquals(1, r2.longValue());
		Integer r3 = result.get(Board.State.EMPTY);
		assertEquals(0, r3.longValue());
	}
	
	@Test
	public void testWin() throws IllegalMoveException, InvalidPositionException {
		board.move(5);
		board.move(6);
		board.move(5);
		board.move(6);
		assertNull(board.getWinner());
		board.move(5);
		assertEquals(Board.Player.O, board.getWinner());
		
		board.init();
		// winning line 0,6 1,4 2,2
		board.importPosition("XO+ +++ +++ +X+ +X+ +O+ OXO +XO +OX");
		assertEquals(Board.Player.X, board.getWinner());
	}
	
	@Test
	public void testEvaluate() throws IllegalMoveException {
		board.move(5);
		board.move(6);
		board.move(5);
		board.move(6);
		board.move(5);
		assertEquals(Board.Player.O, board.getWinner());
		assertTrue(-0.9 > board.evaluate());
	}
	
	@Test 
	public void testImportPosition() throws InvalidPositionException {
		board.importPosition("+O+ +++ +++ +X+ +X+ +++ OXO +X+ +O+");
		assertEquals(Board.State.O, board.getSquareState(0, 0));
		assertTrue(board.noughtsToMove);
	}	
	
	@Test
	public void testBestMove() throws Exception {
		board.move(5);
		board.move(6);
		board.move(5);
		board.move(6);
		assertEquals(5, board.bestMove(4));
		
		board.init();
		board.importPosition("+OO +++ +++ +X+ XX+ +++ OXO +++ +O+");
		assertEquals(4, board.bestMove(1));
		assertEquals(4, board.bestMove(2));
		assertEquals(4, board.bestMove(3));
		assertEquals(4, board.bestMove(4));
		
		board.init();
		board.importPosition("+++ +++ +++ +O+ +X+ +++ O++ +++ +++");
		System.out.println(board);
		assertEquals(8, board.bestMove(1));
		assertEquals(8, board.bestMove(2));
		assertEquals(8, board.bestMove(3)); // by here it has realised it's lost
		assertEquals(8, board.bestMove(4));
			
	}
	
	@Test
	public void testDepth() throws NoValidMovesException, IllegalMoveException {
		System.out.println(board.bestMove(8));
	}

}
