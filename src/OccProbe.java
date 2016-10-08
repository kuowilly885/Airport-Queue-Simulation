
public class OccProbe extends Thread {
	Counter counter;
	public int servingTicks = 0;
	public OccProbe (Counter counter) {
		this.counter = counter;
	}

	public void run() {
		try {
			while (true) {
				if (!Main.allFinished)
				{
					if (counter.serving)
						servingTicks ++;
				}
				else
					break;
				Thread.sleep(1);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
