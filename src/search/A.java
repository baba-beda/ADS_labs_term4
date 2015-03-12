package search;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by daria on 12.03.15.
 */
public class A {

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
        boolean found;

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            found = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                positions.add(i + 1);
            }
        }

        out.println(positions.size());
        for (int pos : positions) {
            out.print(pos + " ");
        }

    }


    public void run() {
        try {
            in = new FastScanner(new File("search1.in"));
            out = new PrintWriter("search1.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new A().run();
    }
}
