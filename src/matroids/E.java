package matroids;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by daria on 05.05.15.
 */
public class E {
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
        int n = in.nextInt(), m = in.nextInt();
        Element[] weight = new Element[n];
        for (int i = 0; i < n; i++) {
            weight[i] = new Element(i, in.nextInt());
        }
        int[] pows = new int[n];
        pows[0] = 1;
        for (int i = 1; i < n; i++) {
            pows[i] = pows[i - 1] * 2;
        }

        ArrayList<Integer> masks = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            int mask = 0;
            for (int j = 0; j < k; j++) {
                int a = in.nextInt();
                mask += pows[a - 1];
            }
            masks.add(mask);
        }

        Arrays.sort(weight);

        int mainMask = 0;
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int j = 0;
            while (j < m && ((mainMask + pows[weight[i].index]) & masks.get(j)) != masks.get(j)) {
                j++;
            }

            if (j == m) {
                mainMask += pows[weight[i].index];
                ans += weight[i].weight;
            }
        }

        out.print(ans);

    }

    class Element implements Comparable<Element> {
        int index, weight;

        public Element(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }

        @Override
        public int compareTo(Element o) {
            return Integer.compare(o.weight, this.weight);
        }
    }
    public void run() {
        try {
            in = new FastScanner(new File("cycles" + ".in"));
            out = new PrintWriter("cycles" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new E().run();
    }
}