import java.util.ArrayList;

public class Edge {
    private State from;
    private State to;
    private char symbol;


    // initializes the Edge object, should be given starting and ending States as well as the symbol

    public Edge(State from, State to, Character symbol){
        this.from = from;
        this.to = to;
        this.symbol = symbol;
    }

    // Public getters

    // returns the starting state
    public State getStartingState(){
        return  from;
    }

    // returns the ending state
    public State getEndingState(){
        return to;
    }

    // returns the symbol of the Edge
    public Character getSymbol(){
        return symbol;
    }

}
