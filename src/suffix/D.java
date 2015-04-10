package suffix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daria on 10.04.15.
 */
public class D {
    class FastScanner {
        StreamTokenizer st;

        FastScanner() {
            st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        }

        FastScanner(File f) {
            try {
                st = new StreamTokenizer(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        int nextInt() throws IOException {
            st.nextToken();
            return (int) st.nval;
        }

        String nextString() throws IOException {
            st.nextToken();
            return st.sval;
        }
    }

    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        SuffixAutomaton auto = new SuffixAutomaton(in.nextString());

        int counter = 0;
        int pos = 0;
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i = 0; i<auto.states.length; i++) {
            counter = 0;
            for (int j = 0; j<128; j++) {
                int u = auto.states[i].next[j];
                if (u!=-1) {
                    edges.add(new Edge(i+1, u+1, (char) j));
                    counter++;
                }
            }
            if (counter==0) {
                pos = i;
            }
        }
        out.println(auto.states.length + " " + edges.size());
        for (Edge e : edges) {
            out.println(e.begin+ " "+ e.end+ " " + e.c);
        }
        ArrayList<Integer> terminals = new ArrayList<>();
        for (int p = pos; p!=-1; p = auto.states[p].link) {
            terminals.add(p+1);
        }
        out.println(terminals.size());
        for (int a : terminals) {
            out.print(a+" ");
        }
    }

    class Edge {
        int begin;
        int end;
        char c;
        public Edge(int b, int e, char c) {
            this.begin = b;
            this.end = e;
            this.c = c;
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("automaton" + ".in"));
            out = new PrintWriter("automaton" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new D().run();
    }

    public class SuffixAutomaton {
        class State {
            int length, link, endpos;
            int[] next = new int[128];

            ArrayList<Integer> iLink;

            public State() {
                Arrays.fill(next, -1);
                iLink = new ArrayList<>();
            }
        }

        State[] states;
        String s;

        SuffixAutomaton(String s) {
            buildAutomaton(s);
        }

        void buildAutomaton(String s) {
            int n = s.length();
            states = new State[Math.max(2, 2 * n - 1)];
            states[0] = new State();
            states[0].link = -1;
            states[0].endpos = -1;
            int last = 0;
            int size = 1;
            for (char c : s.toCharArray()) {
                int cur = size++;
                states[cur] = new State();
                states[cur].length = states[last].length + 1;
                states[cur].endpos = states[last].length;
                int p;
                for (p = last; p != -1 && states[p].next[c] == -1; p = states[p].link) {
                    states[p].next[c] = cur;
                }
                if (p == -1) {
                    states[cur].link = 0;
                } else {
                    int q = states[p].next[c];
                    if (states[p].length + 1 == states[q].length)
                        states[cur].link = q;
                    else {
                        int clone = size++;
                        states[clone] = new State();
                        states[clone].length = states[p].length + 1;
                        states[clone].next = states[q].next.clone();
                        states[clone].link = states[q].link;
                        for (; p != -1 && states[p].next[c] == q; p = states[p].link)
                            states[p].next[c] = clone;
                        states[q].link = clone;
                        states[cur].link = clone;
                        states[clone].endpos = -1;
                    }
                }
                last = cur;
            }
            for (int i = 1; i < size; i++) {
                states[states[i].link].iLink.add(i);
            }

            states = Arrays.copyOf(states, size);
        }
    }
}