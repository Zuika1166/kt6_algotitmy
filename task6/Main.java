import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        String next() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            StringBuilder value = new StringBuilder();
            while (c > 32 && c != -1) {
                value.append((char) c);
                c = read();
            }
            return value.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
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
        int k = scanner.nextInt();
        int[] edges = new int[k];
        int[] indegree = new int[k];
        String previous = scanner.next();
        boolean possible = true;

        for (int i = 1; i < n; i++) {
            String current = scanner.next();
            int commonLength = Math.min(previous.length(), current.length());
            int position = 0;
            while (position < commonLength && previous.charAt(position) == current.charAt(position)) position++;
            if (position == commonLength) {
                if (previous.length() > current.length()) possible = false;
            } else {
                int from = previous.charAt(position) - 'a';
                int to = current.charAt(position) - 'a';
                int bit = 1 << to;
                if ((edges[from] & bit) == 0) {
                    edges[from] |= bit;
                    indegree[to]++;
                }
            }
            previous = current;
        }

        PriorityQueue<Integer> available = new PriorityQueue<>();
        for (int letter = 0; letter < k; letter++) {
            if (indegree[letter] == 0) available.add(letter);
        }

        StringBuilder order = new StringBuilder();
        while (!available.isEmpty()) {
            int letter = available.remove();
            order.append((char) ('a' + letter));
            for (int to = 0; to < k; to++) {
                if ((edges[letter] & (1 << to)) != 0 && --indegree[to] == 0) available.add(to);
            }
        }

        if (!possible || order.length() != k) System.out.println("IMPOSSIBLE");
        else System.out.println(order);
    }
}
