//3.1 Three in One
//fixed division
class FixedMultiStack {
	private int numberOfStacks;
	private int stackCapacity;
	private int[] values;
	private int[] sizes;

	public FixedMultiStack(int stackSize) {
		stackCapacity = stackSize;
		values = new int[stackSize * numberOfStacks];
		sizes = new int[numberOfStacks];
	}

	//push value onto stack
	public void push(int stackNum, int value) throws FullStackException {
		//check if have space for next element		
		if (isFull(stackNum)) {
			throw new FullStackException();
		} 
        //increase the size of stack stackNum and update its top value
		sizes[stackNum]++;
		values[indexOfTop(stackNum)] = value;
	}

	//pop item from stack
	public void pop(int stackNum) {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}
		int value = values[indexOfTop(stackNum)]; //get top value
		values[indexOfTop(stackNum)] = 0; //clear
		sizes[stackNum]--; //shrink
		return value;
	}

	//return top element
	public int peek(int stackNum) {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}
		return values[indexOfTop(stackNum)];
	}

	public boolean isFull(int stackNum) {
		return sizes[stackNum] == stackCapacity;
	}

	public boolean isEmpty(int stackNum) {
		return sizes[stackNum] == 0;
	}

	//return index of stack top
	private int indexOfTop(int stackNum) {
		int offset = stackNum * stackCapacity;
		int size = sizes[stackNum];
		return offset + size - 1; //top of stack stackNum
	}
}

//flexible division？？？
public class multiStack {
	private class stackInfo {
		public int start, size, capacity;
		public stackInfo(int start, int capacity) {
			this.start = start;
			this.capacity = capacity;
		}

		//check if an index on the full array is within stack boundries
		public boolean isWithinStackCapacity(int index) {
			if (index < 0 || index >= values.length) return false;
			//if index wraps around, adjust it
			int contiIndex = index < start ? index + values.length : index;
			int end = start + capacity;
			return start <= contiIndex && contiIndex < end;
		}

	}
}

//3.2 Stack Min
public class StackWithMin extends Stack<NodeWithMin> { //inherit
	public void push(int value) {
		int newMin = Math.min(value, min());
		super.push(new NodeWithMin(value, newMin));
	}
	//compare value with current min, result is newMin, and push it to peek

	public int min() {
		if (this.isEmpty()) {
			return Integer.MAX_VALUE; //error value
		}
		else {
			return peek().min;
		}
	}
}

class NodeWithMin {
	public int value;
	public int min;
	public NodeWithMin(int v, int min) {
		value = v;
		this.min = min;
	}
}
//super is used to access methods of the base class 
//this is used to access methods of the current class

//use an additional stack s2 to track mins
public class StackWithMin2 extends Stack<Integer> {
	Stack<Integer> s2;
	public StackWithMin2() {
		s2 = new Stack<Integer>();
	}

	public void push(int value) {
		if (value <= min()) {
			s2.push(value);
		}
		super.push(value);
	} //push a newMin at s2 peek

	public Integer pop() {
		int value = super.pop();
		if (value == min()) {
			s2.pop();
		}
		return value;
	} //if the newMin is popped out, pop it out from s2

	public int min() {
		if (s2.isEmpty()) {
			return Integer.MAX_VALUE; //error value
		}
		else {
			return s2.peek();
		} //current min is always at s2 peek
	}
}

//3.3 Stack of Plates
public class SetOfStacks {
	ArrayList<Stack> stacks = new ArrayList<Stack>();
	public int capacity;
	public SetOfStacks(int capacity) {
		this.capacity = capacity;
	}

	public Stack getLastStack() {
		if (stacks.size() == 0) return null;
		return stacks.get(stacks.size() - 1);
	}

	public void push (int val) {
		Stack last = stacks.getLastStack();
		if (last != null && !last.isFull()) {
			last.push(v);
		}
		else { //if full, creat a new stack at the end
			Stack stack = new Stack(capacity);
			stack.push(v);
			stacks.add(stack);
		}
	}

	public void pop() {
		Stack last = stacks.getLastStack();
		if (last == null)  throw new EmptyStackException();
		int val = last.pop();
		if (last.size == 0) stacks.remove(stacks.size() - 1);
		return val;
	}

	public boolean isEmpty() {
		Stack last = stacks.getLastStack();
		return last == null || last.isEmpty();
	}

	public int popAt(int index) {
		return leftShift(index, true);
	}

	public int leftShift(int index, boolean removeTop) {
		Stack stack = stacks.get(index);
		int removed_item;
		if (removeTop) removed_item = stack.pop();
		else removed_item = stack.removeBottom();
		if (stack.isEmpty()) {
			stacks.remove(index);
		}
		else if (stacks.size > index + 1) {
			int v = leftShift(index + 1, false);
			stack.push(v);
		}
		return removed_item;
	} //???
}

public class Stack {
	private int capacity;
	public Node top, bottom;
	public int size = 0;

	public Stack(int capactiy) {this.capacity = capacity};
	public boolean isFull() { return capacity == size};

	public void join(Node above, Node below) {
		if (below != null) below.above = above;
		if (above != null) above.below = below;
	}

	public boolean push(int v) {
		if (size >= capacity) return false;
		size++;
		Node n = new Node();
		if (size == 1) bottom = n;
		join(n, top);
		top = n;
		return true;
	}

	public int pop() {
		Node t = top;
		top = top.below;
		size--;
		return t.value;
	}

	public boolean isEmpty() {
		return size = 0;
	}

	public int removeBottom() {
		Node b = bottom;
		bottom = bottom.above;
		if (bottom != null) bottom.below = null;
		size--;
		return b.value;
	}
}

//3.4 Queue via Stacks
public class MyQueue<T> {
	Stack<T> stackNewest, stackOldest;
	public MyQueue() {
		stackNewest = new Stack<T>(); //used for input
		stackOldest = new Stack<T>(); //used for output
	}

	public int size() {
		return stackNewest.size() + stackOldest.size();
	}

	//push onto stackNewest, which always has newest element on top
	public void add(T value) {
		stackNewest.push(value);
	}

	//shift all elements from stackNewest into stackOldest
	//so we always do operations on stackOldest
	private void shiftStacks() {
		if (stackOldest.isEmpty()) {
			while (!stackNewest.isEmpty()) {
				stackOldest.push(stackNewest.pop());
			}
		}
	}

	public T peek() {
		shiftStacks(); //ensure stackOldest has current elements
		return stackOldest.peek(); //retrieve the oldest value
	}

	public T remove() {
		shiftStacks();
		return stackOldest.pop(); //pop the oldest item
	}
	//define three ops: add, peek, remove
}

//3.5 Sort Stacks
void sort(Stack<Integer> s) {
	Stack<Integer> r = new Stack<Integer>();

	//insert each element in s in sorted order into r
	while (!s.isEmpty()) {
		int temp = s.pop();
		while (!r.isEmpty() && r.peek() > temp) {
			s.push(r.pop());
		}
		r.push(temp);
	}
    
    //r is in decreasing order, s is in increasing order
    //copy the elements from r back into s
    while(!r.isEmpty()) {
    	s.push(r.pop());
    }
}
//O(N^2) time, O(N) space

//3.6 Animal Shelter
abstract class Animal {
	private int order;
	protected String name;
	public Animal(String n) {name = n};
	public setOrder(int ord) {order = ord};
	public int getOrder() {return order};
	public boolean isOlderThan(Animal a) {
		return this.order < a.getOrder();
	}
} //abstract class, only statement, no instance

class AnimalQueue {
	LinkedList<Dog> dogs = new LinkedList<Dog>();
	LinkedList<Cat> cats = new LinkedList<Cat>();
	private int order = 0; //timestamp

	public void enqueue(Animal a) {
		a.setOrder(order);
		order++;
		//the older one has smaller order value

		//test if an object is an instance of a specific class
		//return boolean
		if (a instanceof Dog) dogs.addLast(Dog(a));
		else if (a instanceof Cat) cats.addLast(Cat(a));
	}

	public Animal dequeueAny() {
		if (dogs.size() == 0) return dequeueCat();
		else if (cats.size() == 0) return dequeueDog();
		//check top of cats&dogs, pop the oldest animal
		Dog dog = dogs.peek();
		Cat cat = cats.peek();
		if (dog.isOlderThan(cat)) return dequeueDog();
		else return dequeueCat();
	}

	public Cat dequeueCat() {
		return cats.poll();
	}
	public Dog dequeueDog() {
		return dogs.poll();
	}
	//when queue is empty, remove() throws an exception
	//poll() return null 

	public class Dog extends Animal {
		public Dog(String n) {super n};
	}
	public class Cat extends Animal {
		public Cat(String n) {super n};
	} //use variable n in base class Animal
}


