package games.tictactoe;

import java.util.List;

public interface GameStateInterface {
     //int plyCount;
	GameStateInterface copy();
	public int getNumberOfMoves();
	boolean isGameOver();
	public List<Integer> getLegalMovesBitboards();
	public void makeMove(int move);
    /*void next(int pacDir, int[] ghostDirs);
    MsPacMan getPacman();
    MazeInterface getMaze();
    int getLevel();
    BitSet getPills();
    BitSet getPowers();
    Ghosts[] getGhosts();
    int getScore();
    int getTotalGameTicks();
    int getEdibleGhostScore();
    int getLivesRemaining();
    boolean MsPacManDeath();
    boolean terminal();
    void reset();*/
}
