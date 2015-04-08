package suffix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daria on 08.04.15.
 */
public class G {
    class FastScanner {
        StreamTokenizer states;

        FastScanner() {
            states = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        }

        FastScanner(File f) {
            try {
                states = new StreamTokenizer(new BufferedReader(new FileReader(f)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        int nextInt() throws IOException {
            states.nextToken();
            return (int) states.nval;
        }

        String nextString() throws IOException {
            states.nextToken();
            return states.sval;
        }
    }

    FastScanner in;
    PrintWriter out;

    public void solve() throws IOException {
        SuffixAutomaton automaton = new SuffixAutomaton(in.nextString(), in.nextString());
        out.print(automaton.commonString);
    }

    public void run() {
        try {
            in = new FastScanner(new File("common" + ".in"));
            out = new PrintWriter("common" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new G().run();
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
        String s1, s2, commonString;

        SuffixAutomaton(String s1, String s2) {
            this.s1 = s1;
            this.s2 = s2;
            lcs();
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
        }
        
        void lcs() {
            buildAutomaton(s1);
            int bestState = 0;
            int len = 0;
            int bestLen = 0;
            int bestPos = -1;
            for (int i = 0, cur = 0; i < s2.length(); ++i) {
                char c = s2.charAt(i);
                if (states[cur].next[c] == -1) {
                    for (; cur != -1 && states[cur].next[c] == -1; cur = states[cur].link) {
                    }
                    if (cur == -1) {
                        cur = 0;
                        len = 0;
                        continue;
                    }
                    len = states[cur].length;
                }
                ++len;
                cur = states[cur].next[c];
                if (bestLen < len) {
                    bestLen = len;
                    bestPos = i;
                    bestState = cur;
                }
            }
            commonString =  s2.substring(bestPos - bestLen + 1, bestPos + 1);
        }
    }
}