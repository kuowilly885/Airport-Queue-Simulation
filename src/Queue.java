
public abstract class Queue {
    protected int count;
    abstract public void enqueue(long date);
    abstract public long dequeue();
    abstract public long peek();

    public int size() {
        return count;
    }
}
