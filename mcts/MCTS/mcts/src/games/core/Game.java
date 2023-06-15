package games.core;

import java.util.List;

public interface Game <GAME extends Game<GAME>>{
	 // returns a copy of the current game
    GAME copy();

    // Returns the index of the player in turn.
    // The player indexes start from 0, i.e., [0, 1, 2, ...]
    int getCurrentPlayer();

    // Returns true if the game is over.
    boolean isGameOver();

    // Returns the number of legal moves for the player in turn.
    // For example, if the number of moves is 5, the player in
    // turn must take a move from the set {0, 1, 2, 3, 4}. Any
    // other move is considered an illegal move.
    //
    int getNumberOfMoves();

    // Returns a list of the legal moves. The moves are represented as strings
    // using game specific representation. The main reason to have this
    // method is to be able to save the history of moves made.
    List<String> getMoves();

    // Returns an array with the outcomes for each of the players.
    // For example, if there are 4 players in the game,
    // the length of the outcomes array will be 4.
    Outcome[] getOutcomes();

    // Makes the nth move for the player in turn.
    void makeMove(int move);

    // Restarts the game.
    void reset();

    // Returns a string representation of the current game.
    // This shouldn't be confused with toString(), which should
    // specify the name of the game only.
    String gameToString();

    // Name of the game.
    @Override
    public String toString();
}
