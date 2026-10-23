import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

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

    public static void main(String[] args) throws Exception {
        FastScanner scanner = new FastScanner();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] head = new int[n + 1];
        java.util.Arrays.fill(head, -1);
        int[] to = new int[m];
        int[] next = new int[m];
        int[] indegree = new int[n + 1];

        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int destination = scanner.nextInt();
            to[i] = destination;
            next[i] = head[from];
            head[from] = i;
            indegree[destination]++;
        }

        PriorityQueue<Integer> available = new PriorityQueue<>();
        for (int stage = 1; stage <= n; stage++) {
            if (indegree[stage] == 0) available.add(stage);
        }

        StringBuilder output = new StringBuilder();
        int count = 0;
        while (!available.isEmpty()) {
            int stage = available.remove();
            if (count > 0) output.append(' ');
            output.append(stage);
            count++;
            for (int edge = head[stage]; edge != -1; edge = next[edge]) {
                int destination = to[edge];
                if (--indegree[destination] == 0) available.add(destination);
            }
        }

        if (count != n) System.out.println(-1);
        else System.out.println(output);
    }
}
