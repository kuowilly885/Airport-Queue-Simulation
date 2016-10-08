
public class LinkedListQueue extends Queue {

    private Node first;
    private Node last;
    public String type = null;
   
    public LinkedListQueue(String level) {
		type = level;
		count = 0;
    }

    public void enqueue(long date) {
        Node node = new Node(date);
        if (count == 0)
            first = node;
        else
            last.setNext(node);
        last = node;
        ++count;
    }

    public long dequeue() {
        Node node = first;
        first = first.getNext();
        node.setNext(null);
        --count;
        return node.getDate();
    }

    public long peek() {
        return first.getDate();
    }
}
