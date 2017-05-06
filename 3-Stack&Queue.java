//3.1Three in One
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
        //increase size of stack stackNum and update top value
		sizes[stackNum]++;
		values[indexOfTop(stackNum)] = value;
	}

	//pop item from stack
	public void pop(int stackNum) {
		if (isEmpty(stackNum)) {
			throw new EmptyStackException();
		}
		int topIndex = indexOfTop(stackNum);
		int value = values[topIndex]; //get top value
		values[topIndex] = 0; //clear
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

//use an additional stack to track mins








