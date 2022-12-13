/**
 * implements queue with two stacks
 */
public class QueueTwoStack<Item extends Comparable<Item>> {

    private final LinkedStack<Item> stack1 = new LinkedStack<Item>();
    private final LinkedStack<Item> stack2 = new LinkedStack<Item>();
    public void enqueue(Item item) {
        stack1.push(item);
    }

    public Item dequeue() {
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        return stack2.pop();
    }

    public boolean isEmpty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public static void main(String[] args) {
        QueueTwoStack<String> q = new QueueTwoStack<String>();
        q.enqueue("to");
        q.enqueue("be");
        q.enqueue("or");
        q.enqueue("not");

        while (!q.isEmpty()) {
            System.out.println(q.dequeue());
        }
    }
}
