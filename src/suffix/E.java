package suffix;

import java.io.*;

/**
 * Created by daria on 08.04.15.
 */
public class E {
    FastScanner in;
    PrintWriter out;
    int max = 0;
    int maxDepth = 0;
    int beg;

    public static void main(String[] args) {
        new E().run();
    }

    public void solve() throws IOException {
        int n = in.nextInt(), m = in.nextInt();
        byte[] input = new byte[n + 1];
        for (int i = 0; i < n; i++) {
            input[i] = in.nextByte();
            input[i]--;
        }

        input[n] = (byte) m;

        Tree tree = new Tree(input, m + 1);

        countLeafs(tree.root);
        for (int i = 0; i < tree.root.children.length; i++) {
            if (tree.root.children[i] != null) {
                findMax(tree.root.children[i], tree.root.children[i].begin);
            }
        }

        if (max != 0) {

            out.println(max);
            out.println(maxDepth);
            for (int i = beg; i < beg + maxDepth; i++) {
                out.print((input[i] + 1) + " ");
            }
        } else {
            out.println(input.length - 1);
            out.println(input.length - 1);
            for (int i = 0; i < input.length - 1; i++) {
                out.print((input[i] + 1) + " ");
            }
        }
    }

    int countLeafs(Tree.Node node) {
        int chNum = 0;
        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                chNum++;
                node.leafCount += countLeafs(node.children[i]);
            }
        }

        if (chNum == 0) {
            return 1;
        }

        return node.leafCount;
    }

    void findMax(Tree.Node node, int begin) {

        int depth = node.end - begin;

        if (depth * node.leafCount >= max) {
            max = depth * node.leafCount;
            maxDepth = depth;
            beg = begin;
        }

        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                findMax(node.children[i], begin);
            }
        }
    }

    public void run() {
        try {
            in = new FastScanner(new File("refrain" + ".in"));
            out = new PrintWriter("refrain" + ".out");

            solve();

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        byte nextByte() throws IOException {
            st.nextToken();
            return (byte) st.nval;
        }

        String nextString() throws IOException {
            st.nextToken();
            return st.sval;
        }
    }

    public class Tree {
        Node root;
        byte[] input;

        Tree(byte[] input, int m) {
            this.input = input;
            buildSufTree(m);
        }

        void buildSufTree(int m) {
            int n = input.length;
            root = new Node(0, 0, 0, null, m);
            Node node = root;
            for (int i = 0, tail = 0; i < n; i++, tail++) {
                Node last = null;
                while (tail >= 0) {
                    Node ch = node.children[input[i - tail]];
                    while (ch != null && tail >= ch.end - ch.begin) {
                        tail -= ch.end - ch.begin;
                        node = ch;
                        ch = ch.children[input[i - tail]];
                    }
                    if (ch == null) {
                        node.children[input[i]] = new Node(i, n, node.depth + node.end - node.begin, node, m);
                        if (last != null) last.suffixLink = node;
                        last = null;
                    } else {
                        byte t = input[ch.begin + tail];
                        if (t == input[i]) {
                            if (last != null) last.suffixLink = node;
                            break;
                        } else {
                            Node splitNode = new Node(ch.begin, ch.begin + tail, node.depth + node.end - node.begin, node, m);
                            splitNode.children[input[i]] = new Node(i, n, ch.depth + tail, splitNode, m);
                            splitNode.children[t] = ch;
                            ch.begin += tail;
                            ch.depth += tail;
                            ch.parent = splitNode;
                            node.children[input[i - tail]] = splitNode;
                            if (last != null) {
                                last.suffixLink = splitNode;
                            }
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
        }

        public class Node {
            int begin;
            int end;
            int depth;
            Node parent;
            Node[] children;
            Node suffixLink;
            int leafCount;

            public Node(int begin, int end, int depth, Node parent, int m) {
                this.begin = begin;
                this.end = end;
                this.depth = depth;
                this.parent = parent;
                children = new Node[m];
                leafCount = 0;
            }

        }
    }
}