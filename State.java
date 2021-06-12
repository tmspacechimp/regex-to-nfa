import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class State {
    private HashSet<Edge> edges;
    boolean isAccepting;


    // initializes the State object, should be given a boolean which dictates if the state is accepting or not
    public State(boolean isAccepting){
        this.isAccepting = isAccepting;
        edges = new HashSet<>();
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



}
