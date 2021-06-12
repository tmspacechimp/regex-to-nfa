import java.util.ArrayList;

public class  Automata {
    private State startingState;
    private ArrayList<State> states;


    //Constructor

    /* initializes the automata, theres 2 main cases:
    1.when the passed symbol is ' ' this is equivalent to (),
    which should have only one starting state that is also accepting.
    2.when passed any other symbol we have two states a starting one which is declining,
    and the second accepting state. starting state connects to the second state with an edge
    using the passed symbol
    */
    public Automata(char symbol){
        states = new ArrayList<>();
        State first;
        if(symbol == ' '){
            first = new State(true);
            states.add(first);
        } else{
            first = new State(false);
            State second = new State(true);
            first.addOutgoingEdge(second, symbol);
            states.add(first);
            states.add(second);
        }
        startingState = first;

    }

    // Mutators

    /* Concatenates the current Automata with the one passed.
    Concatenation is performed when two expressions are next to each other
    with no other operation symbol in between them
    */

    public Automata Concatenate(Automata a){
        State passedStartingState = a.getStartingState();


        /* every accepting state from the current Automata should connect to the states
        that the starting state of the passed Automata is connected to.
        if the passed starting State is declining the accept states from
        the first automata should also become declining.
        */

        for(State s: states) {
            if (s.getAccepting()){
                s.setAccepting(passedStartingState.getAccepting());
                for(Edge e : passedStartingState.getEdges())
                    s.addOutgoingEdge(e.getEndingState(), e.getSymbol());
            }
        }

        for(State s: a.getStates()){
            if(s!= passedStartingState) states.add(s);
        }

        return this;
    }



    /* Unites the current Automata with the one passed.
    Union is performed when two expressions are divided by “|” symbol
    */
    public Automata Unite(Automata a){
        State passedStartingState = a.getStartingState();
        // if the starting state of the passed automata is accepting, current should become one too.
        if(passedStartingState.getAccepting()) startingState.setAccepting(true);

        //connect current starting state to every state passed starting state is connected to.


        for(Edge e: passedStartingState.getEdges()){
            startingState.addOutgoingEdge(e.getEndingState(), e.getSymbol());
        }
        for (State s: a.states){
            if(s!=passedStartingState) states.add(s);
        }

        return this;
    }


    /* Performs star operation when expression is followed by '*'
    */
    public Automata Star(){
        // starting state becomes accepting so that when we have 0 repetitions of the expression we still accept it
        startingState.setAccepting(true);

        // accepting states loop back to the state the starting state would have gone to with the same symbol
        for(State s: states){
            if(s.getAccepting()){
                if(s != startingState) {
                    for (Edge e : startingState.getEdges())
                        if (s != startingState) s.addOutgoingEdge(e.getEndingState(), e.getSymbol());
                }
            }
        }


        return this;
    }


    // getters

    public ArrayList<State> getStates() {
        return states;
    }

    public State getStartingState() {
        return startingState;
    }


}
