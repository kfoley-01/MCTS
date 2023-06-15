package games.c4;

import java.util.List;

public interface GameStateInterface {
	GameStateInterface copy();
	public int getNumberOfMoves();
	boolean isGameOver();
	public List<Integer> getLegalMovesBitboards();
	public void makeMove(int move);
}
