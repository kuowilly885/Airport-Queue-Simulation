
public class Generator extends Thread {
	int averageGeneratedTime = 0;
	LinkedListQueue Queue;
	public Generator(LinkedListQueue Queue) {
		this.Queue = Queue;
		if (Queue.type.equals(Category.FIRST_CLASS))
			averageGeneratedTime = Category.FIRST_GENERATING_TIME;
		else if (Queue.type.equals(Category.COACH_CLASS))
			averageGeneratedTime = Category.COACH_GENERATING_TIME;
	}

	public void run() {
		generate();
	}

	public void generate() {
		while (true) {
			try {
				if (Clock.finished)
					break;
				else {
					Thread.sleep((int )(Math.random() * averageGeneratedTime * 2 + 1));
					Queue.enqueue(System.currentTimeMillis());
					System.out.println(Queue.type + " enqueue");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
