
public class Node {
    private Node next;
    private long date;

    public Node getNext() { 
        return next; 
    }

    void setNext(Node next) { 
        this.next = next;
    }

    public long getDate() { 
        return date;
    }

    void setDate(long date) { 
        this.date = date;
    }

    public Node(long date) {
        this.date = date;
    }
}
