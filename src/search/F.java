package search;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by daria on 12.03.15.
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
        String pattern = in.nextString();
        int[] prefix = prefix(pattern + pattern);

        int n = pattern.length();

        int pos = 2 * n;

        for (int i = 0; i < 2 * n; i++) {
            if (prefix[i] >= n) {
                pos = i;
                break;
            }
        }

        if (pos < 2 * n) {
            out.print(pos - prefix[pos] + 1);
            return;
        }

        out.print(n);
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
            in = new FastScanner(new File("period.in"));
            out = new PrintWriter("period.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new F().run();
    }
}
