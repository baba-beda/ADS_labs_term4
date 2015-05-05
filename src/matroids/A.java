package matroids;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by daria on 05.05.15.
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
        int n = in.nextInt();
        ArrayList<Task> tasks = new ArrayList<>();

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int time = in.nextInt(), fee = in.nextInt();
            if (time == 0) {
                ans += fee;
            }
            else {
                tasks.add(new Task(time, fee));
            }
        }

        Collections.sort(tasks);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        int j = 0;

        for (int i =  tasks.get(0).time; i > 0; i--) {
            while (j < tasks.size() && tasks.get(j).time == i) {
                heap.add(tasks.get(j).fee);
                j++;
            }
            heap.poll();
        }

        while (!heap.isEmpty()) {
            ans += heap.poll();
        }

        out.print(ans);
    }

    class Task implements Comparable<Task> {
        int time, fee;

        public Task(int time, int fee) {
            this.time = time;
            this.fee = fee;
        }

        @Override
        public int compareTo(Task o) {
            return Integer.compare(o.time, this.time);
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("schedule" + ".in"));
            out = new PrintWriter("schedule" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new A().run();
    }
}