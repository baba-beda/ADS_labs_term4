package matroids;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by daria on 05.05.15.
 */
public class C {
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

    ArrayList<Integer>[] graph;
    boolean[] used;
    int[] matching;
    Pair[] order;

    public void solve() throws IOException {
        int n = in.nextInt();
        used = new boolean[n];
        order = new Pair[n];
        graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            order[i] = new Pair(i, in.nextInt());
        }
        Arrays.sort(order);


        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            int k = in.nextInt();
            for (int j = 0; j < k; j++) {
                int v = in.nextInt() - 1;

                graph[i].add(v);
            }
        }

        matching = new int[n];
        Arrays.fill(matching, -1);

        for (int i = 0; i < n; i++) {
            int v = order[i].v;
            Arrays.fill(used, false);
            kuhn(v);
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (matching[i] != -1) {
                ans[matching[i]] = i + 1;
            }
        }

        for (int i = 0; i < n; i++) {
            out.print(ans[i] + " ");
        }
    }

    boolean kuhn(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (int to : graph[v]) {
            if (matching[to] == -1 || kuhn(matching[to])) {
                matching[to] = v;
                return true;
            }
        }
        return false;
    }

    public void run() {
        try {
            in = new FastScanner(new File("matching" + ".in"));
            out = new PrintWriter("matching" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class Pair implements Comparable<Pair>{
        int v, weight;
        public Pair(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Pair o) {
            return Integer.compare(o.weight, this.weight);
        }
    }
    public static void main(String[] args) {
        new C().run();
    }
}