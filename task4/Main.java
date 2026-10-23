import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

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
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > 32 && c != -1) {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
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
        Node head = null;
        Node tail = null;
        for (int i = 0; i < n; i++) {
            Node node = new Node(scanner.nextInt());
            if (head == null) head = node;
            else tail.next = node;
            tail = node;
        }

        Node groupPrevious = null;
        Node groupStart = head;
        while (groupStart != null) {
            Node groupEnd = groupStart;
            for (int i = 1; i < k && groupEnd != null; i++) groupEnd = groupEnd.next;
            if (groupEnd == null) break;

            Node nextGroup = groupEnd.next;
            Node previous = nextGroup;
            Node current = groupStart;
            while (current != nextGroup) {
                Node nextNode = current.next;
                current.next = previous;
                previous = current;
                current = nextNode;
            }

            if (groupPrevious == null) head = groupEnd;
            else groupPrevious.next = groupEnd;
            groupPrevious = groupStart;
            groupStart = nextGroup;
        }

        java.io.BufferedWriter output = new java.io.BufferedWriter(new java.io.OutputStreamWriter(System.out));
        boolean first = true;
        for (Node node = head; node != null; node = node.next) {
            if (!first) output.write(' ');
            output.write(Integer.toString(node.value));
            first = false;
        }
        output.newLine();
        output.flush();
    }
}
