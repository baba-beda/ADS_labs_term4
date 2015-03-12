package search;

import java.io.*;

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
