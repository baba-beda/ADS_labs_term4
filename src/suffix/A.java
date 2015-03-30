package suffix;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daria on 31.03.15.
 */
public class A {
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
        Trie trie = new Trie(in.nextString());

        out.println(trie.number + " " + (trie.number - 1));

        for (Edge e : trie.edges) {
            out.println(e.from + " " + e.to + " " + e.c);
        }
    }


        public void run() {
            try {
                in = new FastScanner(new File("trie.in"));
                out = new PrintWriter("trie.out");

                solve();

                out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new A().run();
    }

    @SuppressWarnings("unchecked")
    class Trie {
        HashMap<Character, Integer>[] trie;
        int number;
        StringBuilder str;
        ArrayList<Edge> edges;
        int strLength;

        Trie(String s) {
            str = new StringBuilder(s);
            strLength = str.length();
            trie = new HashMap[strLength*strLength];
            for (int i = 0; i < strLength * strLength; i++) {
                trie[i] = new HashMap<>();
            }
            edges = new ArrayList<>();
            number = 1;

            build();
        }

        void add(int i, int j) {
            int current = 0;
            for (int k = i; k < j; k++) {
                char c = str.charAt(k);
                if (!trie[current].containsKey(c)) {
                    trie[current].put(c, number);
                    number++;
                    edges.add(new Edge(current + 1, number, c));
                }
                current = trie[current].get(c);
            }
        }

        void build() {
            for (int i = 0; i < strLength; i++) {
                add(i, strLength);
            }
        }
    }

    class Edge implements Comparable<Edge>{
        int to, from;
        char c;

        public Edge(int from, int to, char c) {
            this.to = to;
            this.from = from;
            this.c = c;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(from, o.from);
        }
    }
}
