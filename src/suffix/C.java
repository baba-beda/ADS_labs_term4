package suffix;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by daria on 31.03.15.
 */
public class C {
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

    int count = 1;
    ArrayList<Edge> edges = new ArrayList<>();

    public void solve() throws IOException {
        Tree t = new Tree();
        Tree.Node root = t.buildSufTree(in.nextString());

        root.number = count++;
        setOutput(root);

        out.println((count - 1) + " " + edges.size());
        for (Edge edge : edges) {
            out.println(edge.toString());
        }
    }

    void setOutput(Tree.Node node) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                node.children[i].number = count++;

                setOutput(node.children[i]);

                edges.add(new Edge(node.number, node.children[i].number, node.children[i].begin + 1, node.children[i].end));
            }
        }
    }

    class Edge {
        int parent, children, left, right;

        public Edge(int parent, int children, int left, int right) {
            this.parent = parent;
            this.children = children;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return parent + " " + children + " " + left + " " + right;
        }
    }
    public void run() {
        try {
            in = new FastScanner(new File("tree.in"));
            out = new PrintWriter("tree.out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new C().run();
    }

    public class Tree {
        final String alph = "abcdefghijklmnopqrstuvwxyz";

        public class Node {
            int number;
            int begin;
            int end;
            int depth;
            Node parent;
            Node[] children;
            Node suffixLink;

            public Node(int begin, int end, int depth, Node parent) {
                this.begin = begin;
                this.end = end;
                this.depth = depth;
                this.parent = parent;
                children = new Node[alph.length()];
            }

        }

        Node buildSufTree(String s) {
            int n = s.length();
            byte[] a = new byte[n];
            for (int i = 0; i < n; i++) a[i] = (byte) alph.indexOf(s.charAt(i));
            Node root = new Node(0, 0, 0, null);
            Node node = root;
            for (int i = 0, tail = 0; i < n; i++, tail++) {
                Node last = null;
                while (tail >= 0) {
                    Node ch = node.children[a[i - tail]];
                    while (ch != null && tail >= ch.end - ch.begin) {
                        tail -= ch.end - ch.begin;
                        node = ch;
                        ch = ch.children[a[i - tail]];
                    }
                    if (ch == null) {
                        node.children[a[i]] = new Node(i, n, node.depth + node.end - node.begin, node);
                        if (last != null) last.suffixLink = node;
                        last = null;
                    } else {
                        byte t = a[ch.begin + tail];
                        if (t == a[i]) {
                            if (last != null) last.suffixLink = node;
                            break;
                        } else {
                            Node splitNode = new Node(ch.begin, ch.begin + tail, node.depth + node.end - node.begin, node);
                            splitNode.children[a[i]] = new Node(i, n, ch.depth + tail, splitNode);
                            splitNode.children[t] = ch;
                            ch.begin += tail;
                            ch.depth += tail;
                            ch.parent = splitNode;
                            node.children[a[i - tail]] = splitNode;
                            if (last != null) last.suffixLink = splitNode;
                            last = splitNode;
                        }
                    }
                    if (node == root) {
                        --tail;
                    } else {
                        node = node.suffixLink;
                    }
                }
            }
            return root;
        }
    }
}