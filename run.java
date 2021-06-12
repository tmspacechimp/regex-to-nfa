import com.sun.jdi.IntegerValue;

import java.util.*;

public class run {
    private static Automata automata;
    private static String word;
    private static  Scanner sc;
    public static void main(String args[]){
        sc= new Scanner(System.in);
        word = sc.next();
        buildAutomata();
        System.out.println(simulateForWord());
    }

    private static String simulateForWord() {
        String ans = "";
        for(int i=1; i<= word.length(); i++){
            if(simulateRec(word.substring(0,i),0,automata.getStartingState()))
                ans+="Y";
            else ans+="N";
        }
        return ans;
    }

    private static boolean simulateRec(String word, int index, State s) {
        if(index==word.length()){
            return s.isAccepting;
        }

        HashSet<State> pos = s.getPossibleStates().get(Character.valueOf(word.charAt(index)));

        if(pos != null){
            for( State posState: pos){
                if(simulateRec(word, index +1, posState)) return true;
            }
        }

        return false;
    }

    private static void buildAutomata() {
        int n,a,t;

        n= Integer.valueOf(sc.next());
        a= Integer.valueOf(sc.next());
        t= Integer.valueOf(sc.next());

        Set<Integer> accepting = new HashSet<>();
        for(int i=0; i<a; i++)
            accepting.add(Integer.valueOf(sc.next()));

        automata = new Automata(' ');

        for(int i=0; i< n-1; i++){
            automata.getStates().add(new State(false));
        }

        int sum=0;

        for(int i=0; i<n; i++){
            int numEdges = Integer.valueOf(sc.next());
            State s = automata.getStates().get(i);
            if(accepting.contains(i))s.setAccepting(true);
            else s.setAccepting(false);

            for(int j=0; j<numEdges; j++){
                char symbol= sc.next().charAt(0);
                State to = automata.getStates().get(Integer.valueOf(sc.next()));
                s.addOutgoingEdge(to, symbol);
            }

        }
        for(State s : automata.getStates())
            s.generatePossibleStates();

    }
}
