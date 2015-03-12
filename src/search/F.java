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
        ArrayList<Integer> zf = zFunction(pattern);
        int n = pattern.length();


        int min = zf.get(n);
        zf.remove(n);

        Collections.sort(zf, Comparator.<Integer>reverseOrder());

        if (zf.get(n - 1) == 1) {
            out.print(1);
            return;
        }


        if (n % min != 0) {
            out.print(n);
            return;
        }


        for (int i = 1; i < n / min; i++) {
            if (zf.get(i) != zf.get(i - 1) - min) {
                out.print(n);
                return;
            }
        }


        out.print(min);
    }

    ArrayList<Integer> zFunction(String str) {
        int n = str.length();

        ArrayList<Integer> zf = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            zf.add(0);
        }
        zf.set(0, n);

        int min = n;

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            zf.set(i, Math.max(0, Math.min(right - i, zf.get(i - left))));
            while (i + zf.get(i) < n && str.charAt(zf.get(i)) == str.charAt(i + zf.get(i))) {
                zf.set(i, zf.get(i) + 1);

            }

            if (zf.get(i) < min && zf.get(i) != 0 && zf.get(i) != 1) {
                min = zf.get(i);
            }

            if (i + zf.get(i) >= right) {
                left = i;
                right = i + zf.get(i);
            }
        }

        zf.set(n, min);
        return zf;
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
