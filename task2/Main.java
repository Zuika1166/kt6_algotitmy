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

    private static int find(int[] parent, int value) {
        if (parent[value] != value) parent[value] = find(parent, parent[value]);
        return parent[value];
    }

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        int[] parent = new int[n + 1];
        int[] size = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int type = scanner.nextInt();
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int rootU = find(parent, u);
            int rootV = find(parent, v);
            if (type == 1) {
                if (rootU != rootV) {
                    if (size[rootU] < size[rootV]) {
                        int temporary = rootU;
                        rootU = rootV;
                        rootV = temporary;
                    }
                    parent[rootV] = rootU;
                    size[rootU] += size[rootV];
                }
            } else {
                output.append(rootU == rootV ? "YES" : "NO").append('\n');
            }
        }
        System.out.print(output);
    }
}
