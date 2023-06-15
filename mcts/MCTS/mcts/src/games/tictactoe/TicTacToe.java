package games.tictactoe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicTacToe
{
    private int plyCount;
    private int crosses;
    private int noughts;
    private int legal;
    private boolean gameOver;
    private static final int[] WINNING_PATTERNS = {7, 56, 448, 73, 146, 292, 273, 84};
    private List<Integer> legalMovesBitboards = new ArrayList<Integer>();
    public enum Outcome { WIN, LOSS, DRAW, NA }

    public TicTacToe()
    {
        reset();
    }

    private boolean checkWin(int board)
    {
        for (int pattern : WINNING_PATTERNS)
            if ((board & pattern) == pattern)
                return true;

        return false;
    }

    private List<Integer> getLegalMovesBitboards()
    {
        if (legalMovesBitboards.isEmpty())
            for (int i = 0; i < 9; i++)
                if ((legal & (1 << i)) != 0)
                    legalMovesBitboards.add(1 << i);

        return legalMovesBitboards;
    }

    private int getCurrentBitboard()
    {
        return getCurrentPlayer() == 0 ? crosses : noughts;
    }

    private int getOppositeBitboard()
    {
        return getCurrentPlayer() == 0 ? noughts : crosses;
    }

    private void setCurrentBitboard(int bitboard)
    {
        if (plyCount % 2 == 0)
            crosses = bitboard;
        else
            noughts = bitboard;
    }

    private void computeLegalMoves()
    {
        legal = ~(crosses | noughts);
        legalMovesBitboards.clear();
    }

    ////////////////////
    // Game Interface //
    ////////////////////

    public TicTacToe copy()
    {
        TicTacToe gameCopy = new TicTacToe();
        gameCopy.plyCount = plyCount;
        gameCopy.gameOver = gameOver;
        gameCopy.crosses = crosses;
        gameCopy.noughts = noughts;
        gameCopy.legal = legal;

        return gameCopy;
    }

    /*public List<String> getMoves()
    {
        return null;
    }*/

    public int getNumberOfMoves()
    {
        return getLegalMovesBitboards().size();
    }

    public int getCurrentPlayer()
    {
        return plyCount % 2;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public void makeMove(int move)
    {
        // if the move taken is illegal
        if (move < 0 || move >= getNumberOfMoves())
            throw new IllegalArgumentException("Illegal move!");

        setCurrentBitboard(getCurrentBitboard() | getLegalMovesBitboards().get(move));
        plyCount++;
        computeLegalMoves();
        gameOver = checkWin(getOppositeBitboard()) || getLegalMovesBitboards().isEmpty();
    }

    public void reset()
    {
        plyCount = 0;
        crosses = 0;
        noughts = 0;
        legal = 511;            // 9 legal moves
        gameOver = false;
        legalMovesBitboards.clear();
    }

    public String gameToString()
    {
        StringBuilder builder = new StringBuilder();

        if (!isGameOver())
        {
            builder.append(("Player to move: " + getCurrentPlayer() + "\n"));
            builder.append(("Legal moves: " + getNumberOfMoves() + "\n"));
        }
        else
            builder.append("Game over\n");

        builder.append("\n");

        for (int i = 0; i < 9; i++)
        {
            if ((crosses & (1 << i)) != 0)
                builder.append(" X ");
            else if ((noughts & (1 << i)) != 0)
                builder.append(" O ");
            else
                builder.append(" - ");

            if (i % 3 == 2)
                builder.append("\n");
        }

        return builder.toString();
    }

    public Outcome[] getOutcomes()
    {
        if (!isGameOver())
            return new Outcome[] {Outcome.NA, Outcome.NA};

        if (checkWin(crosses))
            return new Outcome[] {Outcome.WIN, Outcome.LOSS};

        if (checkWin(noughts))
            return new Outcome[] {Outcome.LOSS, Outcome.WIN};

        return new Outcome[] {Outcome.DRAW, Outcome.DRAW};
    }

    ////////////
    // Object //
    ////////////

    @Override
    public String toString()
    {
        return "Tic Tac Toe";
    }

    public static void main(String[] args)
    {    	
        Random rng = new Random();        
        TicTacToe tic = new TicTacToe();
        System.out.println(tic.gameToString());
        
        GameState gs = new GameState();
        

        while (!tic.isGameOver())
        {
            // el numero de movimientos legales que puedes tomar
            int numberOfMoves = tic.getNumberOfMoves();
            // para escoger que movimiento quieres hacer solo regresas el indice del movimiento
            // en este caso uno random
            int move = rng.nextInt(numberOfMoves);
            // aqui haces el movimiento y cambia el tablero
            tic.makeMove(move);
            System.out.println(tic.gameToString());
        }

        System.out.println(Arrays.toString(tic.getOutcomes()));
    }
    
    /*public int getAction(){
    	TreeNodeTicTacToe nodeTTT = new TreeNodeTicTacToe();
    	int trails = 100;
    	return 0;
    }*/
    
}
