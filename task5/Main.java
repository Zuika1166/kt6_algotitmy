import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }
    }

    private static int find(int[] parent, int[] parity, int value) {
        if (parent[value] != value) {
            int previousParent = parent[value];
            parent[value] = find(parent, parity, previousParent);
            parity[value] ^= parity[previousParent];
        }
        return parent[value];
    }

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        int[] parent = new int[n + 1];
        int[] size = new int[n + 1];
        int[] parity = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        boolean possible = true;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int relation = scanner.nextInt();
            if (possible) {
                int rootU = find(parent, parity, u);
                int rootV = find(parent, parity, v);
                if (rootU == rootV) {
                    if ((parity[u] ^ parity[v]) != relation) possible = false;
                } else {
                    if (size[rootU] < size[rootV]) {
                        int temporary = rootU;
                        rootU = rootV;
                        rootV = temporary;
                    }
                    parent[rootV] = rootU;
                    parity[rootV] = parity[u] ^ parity[v] ^ relation;
                    size[rootU] += size[rootV];
                }
            }
            output.append(possible ? "YES" : "NO").append('\n');
        }
        System.out.print(output);
    }
}
