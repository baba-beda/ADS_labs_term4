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

        int[] normalZf = zFunction(p.toString() + "#" + t.toString());

        int[] reversedZf = zFunction(p.reverse().toString() + "#" + t.reverse().toString());

        for (int i = 0; i <= txtLen - patLen; i++) {
            int j = patLen + txtLen + 1 - patLen - i;

            if (normalZf[i + patLen + 1]  + reversedZf[j] >= patLen - 1) {
                ans.add(i + 1);
            }
        }
        out.println(ans.size());
        for (int i : ans) {
            out.print(i + " ");
        }
    }

    int[] zFunction(String str) {
        int[] zf = new int[str.length()];

        int left = 0;
        int right = 0;

        for (int i = 1; i < str.length(); i++) {
            zf[i] = Math.max(0, Math.min(right - i, zf[i - left]));
            while (i + zf[i] < str.length() && str.charAt(zf[i]) == str.charAt(i + zf[i])) {
                zf[i]++;
            }
            if (i + zf[i] >= right) {
                left = i;
                right = i + zf[i];
            }
        }

        return zf;
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
