import java.util.Iterator;

/**
 * single linked list implementation of stack
 * @param <Item>
 */
public class LinkedStack<Item extends Comparable<Item>> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node first;

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        return item;
    }

    public Item max() {
        Item value = first.item;
        Node p = first;
        while (p != null) {
            if (p.item.compareTo(value) > 0) {
                value = p.item;
            }
            p = p.next;
        }
        return value;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("to");
        stack.push("be");
        stack.push("a");
        stack.push("better");
        stack.push("man");
        stack.push(".");

        System.out.println(stack.pop());

        for (String s: stack) {
            System.out.println(s);
        }

        System.out.printf("max:%s%n", stack.max());
    }


}
