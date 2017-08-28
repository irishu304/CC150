//7.1 Deck of Cards
public class BlackJackHand extends Hand<BlackJackHand> {
	public int score() {
		//Return the highest possible score that's under 21 
		//Or the lowest score that's over.
		ArrayList<Integer> scores = possibleScores();
		int maxUnder = Integer.MIN_VALUE;
		int minOver = Integer.MAX_VALUE;
		for (int score : scores) {
			if (score > 21 && score < minOver) {
				minOver = score; //upper bound
			} else if (score <= 21 && score > maxUnder) {
				maxUnder = score; //lower bound
			}
		}
		return maxUnder == Integer.MIN_VALUE ? minOver : maxUnder;
		//return the altered value
	}

	private ArrayList<Integer> possibleScores() {}
	//return all possible scores this hand could have as a list
	//aces have multiple values, evaluate each ace as both 1 and 11

	public boolean busted() {return score() > 21;} //out of bound
	public boolean is21() {return socre() == 21;}
	public boolean is BlackJack() {}
}

public class BlackJackCard extends Card {
	public BlackJackCard(int c, Suit s) {super(c, s);}
	public int value() {
		if (isAce()) return 1;
		else if(faceValue >= 11 && faceValue <= 13) return 10;
		else return faceValue;
	}
	public int minValue() {
		if (isAce()) return 1;
		else return value();
	}
	public int maxValue() {
		if (isAce()) return 11;
		else return value();
	}
	public boolean isAce() {
		return faceValue == 1;
	}
	public boolean isFaceCard() {
		return faceValue >= 11 && faceValue <= 13;
	}
}

//7.2 Call Center
public class CallHandler {
	private final int LEVELS = 3;
	private final int NUM_RESPONDENTS = 10; //level 0
	private final int NUM_MANAGERS = 4;     //level 1
	private final int NUM_DIRECTORS = 2;    //level 2
	List<List<Employee>> employeeLevels; //list of employees, by levels
	List<List<Call>> callQueues; //queues for each call's rank
	
	public CallHandler() {...}
	public Employee getHandlerForCall(Call call) {...}
	public void dispatchCall(Caller caller) {
		Call call = new Call(caller);
		dispatchCall(call);
	}
	public void dispatchCall(Call call) {
		Employee emp = getHandlerForCall(call);
		//Try to route the call to an employee with minimal rank
		if (emp != null) {
			emp.receiveCall(call);
			call.setEmployee(emp);
		} else {
			call.reply("Wait");
			callQueues.get(call.getRank().getValue()).add(call);
			//Place the call into corresponding call queue by its rank
		}
	}
	public boolean assignCall(Employ emp) {...}
}

public class Call {
	private Rank rank;
	private Caller caller;
	private Employ handler;
	public Call(Caller c) {
		caller = c;
		rank = Rank.Respondent;
	}
	...
}

abstract class Employee {
	//there should be no reason to instantiate an Employee type directly
	private Call currentCall = null;
	protected Rank rank;
	...
	public boolean isFree() {return currentCall == null;}
	public Rank getRank() {return rank;}
}
class Respondent extends Employee {
	public Respondent() {rank = Rank.Respondent;}
}
class Manager extends Employee {
	public Manager() {rank = Rank.Manager;}
}
class Director extends Employee {
	public Director() {rank = Rank.Director;}
}

//7.3 Jukebox
public class Jukebox {...}
public class CDPlayer {
	private Playlist p;
	private CD c;
	...
	public Playlist getPlaylist {return p;}
	public void setPlaylist(Playlist p) {this.p = p;}
	public CD getCD {return c;}
	public void setCD(CD c) {this.c = c;} 
}
public class Playlist {
	private Song song;
	private Queue<Song> queue;
	public Playlist(Song song, Queue<Song> queue) {...}
	public Song nextSong() {return queue.peek();}
	public void queueUpSong(Song s) {queue.add(s);}
}
public class CD {...}
public class Song {...}
public class User {...}

//7.4 Parking Lot
public enum VehicleSize {Motorcycle, Compact, Large}
public abstract class Vehicle {
	protected ArrayList<ParkingSpot> parkingSpot = new ArrayList<>();
	protected String licensePlate;
	protected int spotsNeeded;
	protected VehicleSize size;
	public int getSpotsNeeded() {return spotsNeeded;}
	public VehicleSize getSize() {return size;}
	public void parkInSpot(ParkingSpot s) {parkingSpot.add(s);}
	public void clearSpots() {...}
	public abstract boolean canFit(ParkingSpot spot);
}
public class Car extends Vehicle {
	public Car() {
		spotsNeeded = 1;
		size = VehicleSize.Compact;
	}
	public boolean canFit(ParkingSpot spot) {...}
}
public class Bus extends Vehicle {...}
public class Motorcycle extends Vehicle {...}

public class ParkingLot {...}
public class Level {...}
public class ParkingSpot {...}




















