import java.sql.SQLOutput;
import java.util.*;

public class build {

    public static void main(String args[]){
        String input = readInput();
        Automata ans = evaluate(generatePostfix(input));
        answerQuestions(ans);
    }

    /* answers the questions in the format required */
    private static void answerQuestions(Automata ans) {
        int n,a,t;
        n= ans.getStates().size();
        a=0;
        t=0;
        List<Integer> accepting = new ArrayList<>();
        for(int i=0; i< ans.getStates().size(); i++){
            State s = ans.getStates().get(i);
            if (s.isAccepting){
                a++;
                accepting.add(i);
            }
            t+=s.getEdges().size();
        }
        System.out.println(n + " " + a + " " + t);
        for(Integer i : accepting)
            System.out.print(i + " ");
        System.out.print("\n");

        Map<State,Integer> map = new HashMap<>();
        map.put(ans.getStartingState(),0);
        int cnt=0;
        for(State s : ans.getStates()){
            if(!map.containsKey(s))
                cnt++;
                map.put(s, cnt);
        }

        for(State s : ans.getStates()) {
            HashSet<Edge> edges = s.getEdges();
            System.out.print(edges.size() + " ");
            for (Edge e : edges)
                System.out.print(e.getSymbol() + " " + map.get(e.getEndingState()) + " ");
            System.out.print("\n");
        }

    }


    /* reads the string from the console and fixes it up a little bit, to suit the solution better */
    private static String readInput() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();

        String modified = adjustForPostfix(input);

        return modified;
    }

    /* adds lines for concatenation when two expressions are divided by nothing.
    adds spaces instead of empty brackets to make iterating through the string easier for the algorithm
    */

    private static String adjustForPostfix(String input){
        String addLine="";
        boolean needsLine = false;
        for (int i =0; i< input.length(); i++){

            if (Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i)==' ' ){
                if(needsLine){
                    addLine += "-";
                }
                addLine+= input.charAt(i) + "";
                needsLine = true;
            }
            else if (input.charAt(i)=='*') {
                addLine += "*";
                needsLine = true;
            }
            else if (input.charAt(i)== ')') {
                if (i + 1 < input.length() && input.charAt(i + 1) == '(') {
                    addLine += ")" + '-';
                } else {
                    needsLine = true;
                    addLine += ")";
                }
            }
            else if(input.charAt(i) == '(' && i+1<input.length() && input.charAt(i+1) == ')'){
                if(needsLine) addLine+="-";
                addLine+="(" + " " ;
            }
            else {
                needsLine = false;
                addLine += input.charAt(i) + "";
            }

        }
        return addLine;
    }



    /* evaluates the postfix expression passed */

    private static Automata evaluate(String postfix) {
        Stack<Automata> stack = new Stack<>();

        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);

            if (Character.isLetterOrDigit(c) || c==' ') {
                Automata a = new Automata(c);
                stack.push(a);
            }
            else if(c=='*') {
                Automata a = stack.pop();
                stack.push(a.Star());
            } else {
                Automata a= stack.pop();
                Automata b= stack.pop();

                if(c == '-') b.Concatenate(a);
                if(c == '|') b.Unite(a);

                stack.push(b);
            }
        }
        return stack.pop();
    }



    /* returns the priorities, star being the tightest and unite being the loosest */
    private static int priority(char c) {
        if(c=='|') return 0;
        if(c=='-') return 1;
        if(c=='*') return 2;

        return -1;
    }


    /* turns the modified regex into a postfix expression which makes operating easy*/
    static String generatePostfix(String exp) {
        String postfix = "";
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            if (Character.isLetterOrDigit(c) || c==' ')
                postfix += c;
            else if (c == '(')
                stack.push(c);
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    postfix += stack.pop();

                stack.pop();
            } else
            {
                while (!stack.isEmpty() && priority(c) < priority(stack.peek()))
                    postfix += stack.pop();

                stack.push(c);
            }

        }

        while (!stack.isEmpty()) {
            postfix += stack.pop();
        }
        return postfix;
    }
}
