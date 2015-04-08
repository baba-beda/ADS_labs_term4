package suffix;

import java.io.*;
import java.util.Arrays;

/**
 * Created by daria on 07.04.15.
 */
public class F {
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
        SuffixArray suffixArray = new SuffixArray(in.nextString());
        long sum = 0;
        for (int i : suffixArray.lcp) {
            sum += i;
        }

        long n = suffixArray.s.length();

        long result = n * (n + 1) / 2 - sum;
        out.print(result);
    }

    public void run() {
        try {
            in = new FastScanner(new File("count" + ".in"));
            out = new PrintWriter("count" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new F().run();
    }

    class SuffixArray {
        int[] sa;
        int[] lcp;
        String s;

        SuffixArray(String s) {
            this.s = s;
            buildArray();
            lcp();
        }

        public void buildArray() {
            int n = s.length();
            Integer[] order = new Integer[n];
            for (int i = 0; i < n; i++) {
                order[i] = n - 1 - i;
            }

            Arrays.sort(order, (a, b) -> Character.compare(s.charAt(a), s.charAt(b)));

            sa = new int[n];
            int[] classes = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = order[i];
                classes[i] = s.charAt(i);
            }

            for (int len = 1; len < n; len *= 2) {
                int[] c = classes.clone();
                for (int i = 0; i < n; i++) {
                    classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n && c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
                }
                int[] cnt = new int[n];
                for (int i = 0; i < n; i++) {
                    cnt[i] = i;
                }
                int[] s = sa.clone();
                for (int i = 0; i < n; i++) {
                    int s1 = s[i] - len;
                    if (s1 >= 0) {
                        sa[cnt[classes[s1]]++] = s1;
                    } 
                }
            }
        }

        public void lcp() {
            int n = sa.length;
            int[] rank = new int[n];
            for (int i = 0; i < n; i++)
                rank[sa[i]] = i;
            lcp = new int[n - 1];
            for (int i = 0, h = 0; i < n; i++) {
                if (rank[i] < n - 1) {
                    for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < s.length() && s.charAt(i + h) == s.charAt(j + h); ++h)
                        ;
                        
                    lcp[rank[i]] = h;
                    if (h > 0) {
                        --h;
                    }
                }
            }
        }
    }
}