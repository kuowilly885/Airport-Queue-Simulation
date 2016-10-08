
public class Counter extends Thread {
	String type = null;
	int num = -1;
	LinkedListQueue firstClassQueue;
	LinkedListQueue coachClassQueue;
	int firstClassNumberServed = 0;
	int coachClassNumberServed = 0;
	long MaxWaitTime = 0;
	long TotalWaitTime = 0;
	long MaxServiceTime = 0;
	long TotalServiceTime = 0;
	int MaxQueueLength = 0;
	int TotalQueueLength = 0;
	boolean serving = false;
	public Counter(LinkedListQueue firstClassQueue, LinkedListQueue coachClassQueue, int num) {
		type = Category.FIRST_CLASS;
		this.num = num;
		this.firstClassQueue = firstClassQueue;
		this.coachClassQueue = coachClassQueue;

	}
	public Counter(LinkedListQueue coachClassQueue, int num) {
		type = Category.COACH_CLASS;
		this.num = num;
		this.firstClassQueue = null;
		this.coachClassQueue = coachClassQueue;
	}

	public void run() {
		try {
			for(;;) {
				boolean firstHandleCoach = false;
				long waitTime = 0;
				int queueLength = 0;
				int serviceTime = 0;
				if (type.equals(Category.FIRST_CLASS)) {
					synchronized (firstClassQueue) {
						if (firstClassQueue.size() > 0) {
							// check queue length
							queueLength = firstClassQueue.size();

							// dequeue
							waitTime = System.currentTimeMillis() - firstClassQueue.dequeue();
						} else {
							synchronized (coachClassQueue) {
								if (coachClassQueue.size() > 0) {
									// check queue length
									queueLength = coachClassQueue.size();

									// dequeue
									waitTime = System.currentTimeMillis() - coachClassQueue.dequeue();

									firstHandleCoach = true;
								} else {
									if (Clock.finished)
										break;
									else
										continue;
								}
							}
						}
					}
				} else if (type.equals(Category.COACH_CLASS)) {
					synchronized (coachClassQueue) {
						if (coachClassQueue.size() > 0) {
							// check queue length
							queueLength = coachClassQueue.size();

							// dequeue
							waitTime = System.currentTimeMillis() - coachClassQueue.dequeue();
						} else {
							if (Clock.finished)
								break;
							else
								continue;
						}
					}
				}
				// process
				TotalQueueLength += queueLength;
				if (queueLength > MaxQueueLength)
					MaxQueueLength = queueLength;

				TotalWaitTime += waitTime;
				if (waitTime > MaxWaitTime)
					MaxWaitTime = waitTime;

				if (type.equals(Category.FIRST_CLASS) && !firstHandleCoach) {
					serviceTime = (int )(Math.random() * Category.FIRST_PROCESS_TIME * 2 + 1);
					firstClassNumberServed ++;
				} else if (type.equals(Category.FIRST_CLASS) && firstHandleCoach) {
					serviceTime = (int )(Math.random() * Category.COACH_PROCESS_TIME * 2 + 1);
					coachClassNumberServed ++;
				} else if (type.equals(Category.COACH_CLASS)) {
					serviceTime = (int )(Math.random() * Category.COACH_PROCESS_TIME * 2 + 1);
					coachClassNumberServed ++;
				}

				serving = true;
				Thread.sleep(serviceTime);
				serving = false;

				TotalServiceTime += serviceTime;
				if (serviceTime > MaxServiceTime)
					MaxServiceTime = serviceTime;

				System.out.println(type + " counter"+ num + " served one " + (firstHandleCoach?Category.COACH_CLASS:type));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
