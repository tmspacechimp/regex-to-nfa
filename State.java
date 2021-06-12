import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class State {
    private HashSet<Edge> edges;
    boolean isAccepting;
    private HashMap<Character, HashSet<State> > possibleStates;


    // initializes the State object, should be given a boolean which dictates if the state is accepting or not
    public State(boolean isAccepting){
        this.isAccepting = isAccepting;
        edges = new HashSet<>();
        possibleStates = new HashMap<>();
        generatePossibleStates();
    }

    //generates possible states for each symbol
    public void generatePossibleStates() {
        for(Edge e: edges){
            if(!possibleStates.containsKey(Character.valueOf(e.getSymbol()))){
                possibleStates.put(Character.valueOf(e.getSymbol()), new HashSet<>());
            }
            possibleStates.get(Character.valueOf(e.getSymbol())).add(e.getEndingState());
        }

    }

    // Public API


    // mutators

    // makes a state either accepting or declining

    public void setAccepting(boolean isAccepting){
        this.isAccepting=isAccepting;
    }


    // adds an outgoing edge to the current state, should be given the direction and the symbol of the edge

    public void addOutgoingEdge(State to, char symbol){
        Edge out = new Edge(this, to, symbol);
        edges.add(out);
    }



    //getters

    // return true if the state is accepting, returns false otherwise

    public boolean getAccepting(){
        return isAccepting;
    }


    // return a HashSet of outgoing edges

    public HashSet<Edge> getEdges(){
        return edges;
    }

    // return every possible state for every possible symbol
    public HashMap<Character, HashSet<State> > getPossibleStates() {
        return possibleStates;
    }
}
