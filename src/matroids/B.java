package matroids;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by daria on 05.05.15.
 */
public class B {
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
        long nextLong() throws  IOException {
            st.nextToken();
            return (long) st.nval;
        }
    }

    FastScanner in;
    PrintWriter out;

    int[] parents;
    ArrayList<Edge> edges;

    public void solve() throws IOException {
        int ver = in.nextInt(), ed = in.nextInt(); long s = in.nextLong();
        long graphSum = 0;
        edges = new ArrayList<>();
        for (int i = 0; i < ed; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1; long weight = in.nextLong();
            edges.add(new Edge(i, from, to, weight));
            graphSum += weight;
        }

        parents = new int[ver];
        Collections.sort(edges);

        for (int i = 0; i < ver; i++) {
            parents[i] = i;
        }

        long cost = 0;
        ArrayList<Edge> destroyedEdges = new ArrayList<>();

        for (Edge edge : edges) {
            int a = edge.from, b = edge.to;

            if (dsuGet(a) != dsuGet(b)) {
                cost += edge.weight;
                dsuUnite(a, b);
            }
            else {
                destroyedEdges.add(edge);
            }
        }


        int start = 0;


        while (graphSum - cost > s && destroyedEdges.size() > 0) {
            cost += destroyedEdges.get(start).weight;
            start++;
        }

        out.println(destroyedEdges.size() - start);


        int[] ans = new int[destroyedEdges.size() - start];

        for (int i = start; i < destroyedEdges.size(); i++) {
            ans[i - start] = destroyedEdges.get(i).index + 1;
        }

        Arrays.sort(ans);
        for (int j = 0; j < ans.length; j++) {
            out.print(ans[j] + " ");
        }
    }

    int dsuGet(int v) {
        return (v == parents[v]) ? v : (parents[v] = dsuGet(parents[v]));
    }

    void dsuUnite(int a, int b) {
        a = dsuGet(a);
        b = dsuGet(b);
        if (a != b) {
            parents[a] = b;
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("destroy" + ".in"));
            out = new PrintWriter("destroy" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Edge implements Comparable<Edge>{
        int index, from, to; long weight;

        public Edge(int index, int from, int to, long weight) {
            this.index = index;
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(o.weight, this.weight);
        }
    }

    public static void main(String[] args) {
        new B().run();
    }
}