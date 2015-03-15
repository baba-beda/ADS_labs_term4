package search;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by daria on 12.03.15.
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
        StringBuilder p = new StringBuilder(in.nextString());
        int patLen = p.length();
        StringBuilder t = new StringBuilder(in.nextString());
        int txtLen = t.length();
        ArrayList<Integer> ans = new ArrayList<>();

        int[] normalPrefix = prefix(p.toString() + "#" + t.toString());

        int[] reversedPrefix = prefix(p.reverse().toString() + "#" + t.reverse().toString());

        for (int i = patLen + 1; i <= txtLen + patLen; i++) {
            int j = txtLen + 2 * patLen  - i + 1;

            if (normalPrefix[j]  + reversedPrefix[i] >= patLen - 1) {
                ans.add(txtLen + patLen - j);
            }
        }
        out.println(ans.size());
        for (int i : ans) {
            out.print(i + " ");
        }
    }

    int[] prefix(String str) {
        int[] prefix = new int[str.length()];
        for (int i = 1; i < str.length(); i++) {
            int k = prefix[i - 1];
            while (k > 0 && str.charAt(i) != str.charAt(k)) {
                k = prefix[k -1];
            }
            if (str.charAt(i) == str.charAt(k)) {
                k++;
            }
            prefix[i] = k;
        }
        return prefix;
    }

    public void run() {
        try {
            in = new FastScanner(new File("search3.in"));
            out = new PrintWriter("search3.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new E().run();
    }
}
