import java.util.Scanner;
public class Main {
	public static boolean allFinished = false;
	public static void main(String[] args) {
		int generateTime = 0;
		System.out.println("Welcome to airport queue simulation program\n"
		+"We have 2 first class counters and 3 coach class counters\n"
		+"We have 1 first class customer queue and 1 coach class customer queue\n"
		+"The average coach customer queueing time is 4 minutes\n"
		+"The average first class customer queueing time is 6 minutes\n"
		+"The average coach customer processing time is 8 minutes\n"
		+"The average first class customer processing time is 10 minutes\n"
		+"Please input customer business time(minutes).\n"
		+"For accuracy, I advise entering 1000~10000 minutes");
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.next();
        	try {
        		generateTime = Integer.parseInt(str);
        		scanner.close();
        		break;
        	}
        	catch (Exception e) {
        		System.out.println("Please input number...");
        		continue;
        	}
        }

		long start = 0;
		long duration = 0;
		LinkedListQueue firstClassQueue, coachClassQueue;
		Counter firstClassCounter1, firstClassCounter2, coachClassCounter1, coachClassCounter2, coachClassCounter3;
		Generator firstClassGenerator, coachClassGenerator;
		OccProbe firstClassProbe1, firstClassProbe2, coachClassProbe1, coachClassProbe2, coachClassProbe3;
		Clock clock;
		firstClassQueue = new LinkedListQueue(Category.FIRST_CLASS);
		coachClassQueue = new LinkedListQueue(Category.COACH_CLASS);
		firstClassCounter1 = new Counter(firstClassQueue, coachClassQueue, 1);
		firstClassCounter2 = new Counter(firstClassQueue, coachClassQueue, 2);
		coachClassCounter1 = new Counter(coachClassQueue, 1);
		coachClassCounter2 = new Counter(coachClassQueue, 2);
		coachClassCounter3 = new Counter(coachClassQueue, 3);
		firstClassGenerator = new Generator(firstClassQueue);
		coachClassGenerator = new Generator(coachClassQueue);
		firstClassProbe1 = new OccProbe(firstClassCounter1);
		firstClassProbe2 = new OccProbe(firstClassCounter2);
		coachClassProbe1 = new OccProbe(coachClassCounter1);
		coachClassProbe2 = new OccProbe(coachClassCounter2);
		coachClassProbe3 = new OccProbe(coachClassCounter3);

		// Start
		start = System.currentTimeMillis();
		clock = new Clock(generateTime);
		clock.start();
		firstClassCounter1.start();
		firstClassCounter2.start();
		coachClassCounter1.start();
		coachClassCounter2.start();
		coachClassCounter3.start();
		firstClassGenerator.start();
		coachClassGenerator.start();
		firstClassProbe1.start();
		firstClassProbe2.start();
		coachClassProbe1.start();
		coachClassProbe2.start();
		coachClassProbe3.start();

		while (true) {
			if (!firstClassCounter1.isAlive() && !firstClassCounter2.isAlive()
					&& !coachClassCounter1.isAlive() && !coachClassCounter2.isAlive()
					&& !coachClassCounter3.isAlive())
				break;
		}

		// End
		duration = System.currentTimeMillis() - start;
		allFinished = true;

		int firstClassNumber = (firstClassCounter1.firstClassNumberServed + firstClassCounter2.firstClassNumberServed);
		int coachClassNumber = (coachClassCounter1.coachClassNumberServed + coachClassCounter2.coachClassNumberServed
				+ coachClassCounter3.coachClassNumberServed + firstClassCounter1.coachClassNumberServed + firstClassCounter2.coachClassNumberServed);
		int totalNumber = firstClassNumber + coachClassNumber;
		boolean totalNumberLessThanOne = false;
		if (totalNumber < 1) {
			totalNumber = 1;
			totalNumberLessThanOne = true;
		}
		long[] WaitTimes = {firstClassCounter1.MaxWaitTime, firstClassCounter2.MaxWaitTime, coachClassCounter1.MaxWaitTime, coachClassCounter2.MaxWaitTime, coachClassCounter3.MaxWaitTime};
		long MaxWaitTime = 0;
		MaxWaitTime = findMax(WaitTimes, MaxWaitTime);
		long AverageWaitTime = (firstClassCounter1.TotalWaitTime + firstClassCounter2.TotalWaitTime + coachClassCounter1.TotalWaitTime + coachClassCounter2.TotalWaitTime + coachClassCounter3.TotalWaitTime)/totalNumber;
		long[] ServiceTimes = {firstClassCounter1.MaxServiceTime, firstClassCounter2.MaxServiceTime, coachClassCounter1.MaxServiceTime, coachClassCounter2.MaxServiceTime, coachClassCounter3.MaxServiceTime};
		long MaxServiceTime = 0;
		MaxServiceTime = findMax(ServiceTimes, MaxServiceTime);
		long AverageServiceTime = (firstClassCounter1.TotalServiceTime + firstClassCounter2.TotalServiceTime + coachClassCounter1.TotalServiceTime + coachClassCounter2.TotalServiceTime + coachClassCounter3.TotalServiceTime)/totalNumber;
		long[] QueueLengths = {firstClassCounter1.MaxQueueLength, firstClassCounter2.MaxQueueLength, coachClassCounter1.MaxQueueLength, coachClassCounter2.MaxQueueLength, coachClassCounter3.MaxQueueLength};
		long MaxQueueLength = 0;
		MaxQueueLength = findMax(QueueLengths, MaxQueueLength);
		long AverageQueueLength = (firstClassCounter1.TotalQueueLength + firstClassCounter2.TotalQueueLength + coachClassCounter1.TotalQueueLength + coachClassCounter2.TotalQueueLength + coachClassCounter3.TotalQueueLength)/totalNumber;
		System.out.println("\n" 
		+ "***Statistic Result***\n"
		+ "Number of passengers served : \n" 
		+ "first class number: " + firstClassNumber + "\n"
		+ "coach class number: " + coachClassNumber + "\n"
		+ "total number: " + (totalNumberLessThanOne?0:totalNumber) + "\n"
		+ "Maximum wait time : " + timeTransform(MaxWaitTime) + "\n"
		+ "Average wait time : " + timeTransform(AverageWaitTime) + "\n"
		+ "Maximum service time : " + timeTransform(MaxServiceTime) + "\n"
		+ "Average service time : " + timeTransform(AverageServiceTime) + "\n"
		+ "Maximum queue length : " + MaxQueueLength + "\n"
		+ "Average queue length : " + AverageQueueLength + "\n"
		+ "First class counter 1 occupied rate : " + ((float)(firstClassProbe1.servingTicks)/(float)duration) * 100 + "%\n"
		+ "First class counter 2 occupied rate : " + ((float)(firstClassProbe2.servingTicks)/(float)duration) * 100 + "%\n"
		+ "Coach class counter 1 occupied rate : " + ((float)(coachClassProbe1.servingTicks)/(float)duration) * 100 + "%\n"
		+ "Coach class counter 2 occupied rate : " + ((float)(coachClassProbe2.servingTicks)/(float)duration) * 100 + "%\n"
		+ "Coach class counter 3 occupied rate : " + ((float)(coachClassProbe3.servingTicks)/(float)duration) * 100 + "%\n"
		+ "Total simulation time : " + timeTransform(duration) + "\n");
	}

	public static String timeTransform(long original) {
		long time;
		time = original;
		return "" + time + " mins";
	}

	public static long findMax(long[] array, long currentMax) {
		for (int i = 0 ; i < array.length ; i++)
			if (array[i] > currentMax)
				currentMax = array[i];
		return currentMax;
	}
}
