package search;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by daria on 12.03.15.
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
    }




    FastScanner in;
    PrintWriter out;


    public void solve() throws IOException {
        String pattern = in.nextString();
        String text = in.nextString();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        int[] zf = zFunction(pattern + "$"  + text);

        for (int i = pattern.length() + 1; i < text.length() + pattern.length() + 1; i++) {
            if (zf[i] == pattern.length()) {
                positions.add(i - pattern.length());
            }
        }

        out.println(positions.size());
        for (int pos : positions) {
            out.print(pos + " ");
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
            in = new FastScanner(new File("search2.in"));
            out = new PrintWriter("search2.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new B().run();
    }
}
