/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node next;
        public Node prev;
    }

    private int size;
    private Node sentinel;

    // construct an empty deque
    public Deque() {
        size = 0;
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null to deque");
        }

        Node node = new Node();
        node.item = item;
        node.next = sentinel.next;
        node.prev = sentinel;
        sentinel.next = node;
        node.next.prev = node;
        size += 1;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null to deque");
        }

        Node node = new Node();
        node.item = item;
        node.next = sentinel;
        node.prev = sentinel.prev;

        sentinel.prev = node;
        node.prev.next = node;
        size += 1;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        Node first = sentinel.next;
        first.next.prev = sentinel;
        sentinel.next = first.next;
        size -= 1;
        return first.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        Node last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        size -= 1;
        return last.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = sentinel.next;

        public boolean hasNext() {
            return current.item != null;
        }

        public Item next() {

            if (current.item == null) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("world! ");
        deque.removeLast();
        deque.addFirst("world! ");
        deque.removeFirst();
        deque.addFirst("world! ");
        deque.addFirst("hello, ");
        deque.addLast("I ");
        deque.addLast("From ");
        deque.removeFirst();
        deque.removeLast();

        for (String s : deque) {
            System.out.print(s);
        }
    }

}
