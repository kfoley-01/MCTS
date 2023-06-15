package games.core;

public enum Outcome {
	 WIN ('W'), DRAW ('D'), LOSS ('L'), NA ('N');

	    private final char c;

	    private Outcome(char c)
	    {
	        this.c = c;
	    }

	    public char getChar()
	    {
	        return c;
	    }
}
