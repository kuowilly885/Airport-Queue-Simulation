
public class Clock extends Thread {
	int duration;
	public static boolean finished = false; 
	public Clock(int duration) {
		this.duration = duration;
	}

	public void run() {
		try {
			Thread.sleep(duration);
			finished = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
