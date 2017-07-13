//15.3 Dining Philosopher
//deadlock occurs when each philosopher has left and waits for right
class Chopstick {
	private Lock lock;
	public Chopstick() {
		lock = new ReentrantLock();
	}
	public void pickUp() {
		lock.lock();
	}
	public void putDown() {
		lock.unlock();
	}
}

class Philosopher extends Thread {
	private int bite = 10;
	private Chopstick left, rigth;
	public Philosopher(Chopstick left, Chopstick right) {
		this.left = left;
		this.right = right;
	}
	public void eat() {
		pickUp();
		chew();
		putDown();
	}
	public void pickUp() {
		left.pickUp();
		right.pickUp();
	}
	public void chew() { };
	public void putDown() {
		right.putDown();
		left.putDown();
	}
	public void run() {
		for (int i = 0; i < bite; i++) {
			eat();
		}
	}
}

//each philosopher picks up the lower priority chopstick first
public class Philosopher extends Thread {
	private int bite = 10;
	private Chopstick lower, higher;
	private int index;
	public Philosopher(int i, Chopstick left, Chopstick right) {
		index = i;
		if (left.getNumber() < right.getNumber()) {
			this.lower = left;
			this.higher = right;
		} else {
			this.lower = right;
			this.higher = left;
		}
	}
	public void eat() {
		pickUp();
		chew();
		putDown();
	}
	public void pickUp() {
		lower.pickUp();
		higher.pickUp();
	}
	public void chew() { };
	public void putDown() {
		higher.putDown();
		lower.putDown();
	}
	public void run() {
		for (int i = 0; i < bite; i++) {
			eat();
		}
	}
}

public class Chopstick {
	private Lock lock;
	private int number;
	public Chopstick(int n) {
		lock = new ReentrantLock();
		this.number = n;
	}
	public void pickUp() {
		lock.lock();
	}
	public void pickDown() {
		lock.unlock();
	}
	public int getNumber() {
		return number;
	}
}

//15.4 Deadlock-Free Class
boolean checkForCycle(locks[] locks) {
	touchedNodes = hash table(lock -> boolean)
	intialize touchedNodes to false for each lock in locks
	for each (lock x in process.locks) {
		if (touchedNodes[x] == false) {
			if (hasCycle(x, touchedNodes)) {
				return true;
			}
		}
	}
	return false;
}

boolean hasCycle(node x, touchedNodes) {
	touchedNodes[r] = true;
	if (x.state == Visiting) {
		return true;
	} else if (x.state == Fresh) {
		...
	}
}
//In the pesudocode, we may do several depth-first search, but touchedNodes
//is only initialized once.

//15.5 Call In Order
public class Foo {
	public Semaphore sem1, sem2;
	//信号量控制，Semaphore可以控制某个资源可被同时访问的个数
	//acquire() 获取一个许可，如果没有就等待; release() 释放一个许可
	//A lock in Java is owned by the same thread which locked it.
	public Foo() {
		try {
			sem1 = new Semaphore(1);
			sem2 = new Semaphore(1);
			sem1.acquire();
			sem2.acquire();
		} catch(...) {...}
	}

	public void first() {
		try {
			...
			sem1.release();
		} catch(...) {...}
	}
	public void second() {
		try {
			sem1.acquire();
			sem1.release();
			...
			sem2.release();
		} catch(...) {...}
	}
	public void third() {
		try {
			sem2.acquire();
			sem2.release();
			...
		} catch(...) {...}
	}
}

//15.7 FizzBuzz
//single thread
void fizzbuzz(int n) {
	for (int i = 1; i <= n; i++) {
		if (i % 3 == 0 && i % 5 == 0) { //check first
			System.out.println("FizzBuzz"); 
		} else if (i % 3 == 0) {
			System.out.println("Fizz");
		} else if (i % 5 == 0) {
			System.out.println("Buzz");
		} else {
			System.out.println(i);
		}
	}
}

//multithread
Thread[] threads = { new FBThread(true, true, n, "FizzBuzz"),
					 new FBThread(true, false, n, "Fizz"),
					 new FBThread(false, true, n, "Buzz"),
					 new FBThread(false, false, n) };
for (Thread thread : threads) {
	thread.start();
}

public class FBThread extends Thread {
	private static Object lock = new Object();
	protected static int current = 1;
	private int max;
	private boolean div3, div5;
	private String toPrint;

	public FBThread(boolean div3, boolean div5, int max, String toPrint) {
		this.div3 = div3;
		this.div5 = div5;
		this.max = max;
		this.toPrint = toPrint; //three results in string
	}

	public void print() {
		System.out.println(toPrint);
	}

	public void run() {
		while (true) {
			synchronized (lock) {
				if (current > max) {
					return;
				}
				if ((current % 3 == 0) == div3 && 
					(current % 5 == 0) == div5) {
					print();
					current++;
				}
			}
		}
	}
}

public class NumberThread extends FBThread {
	public NumberThread(boolean div3, boolean div5, int max) {
		super(div3, div5, max, null);
	}
	public void print() { //override
		System.out.println(current);
	}
}
