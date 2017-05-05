//Three in one
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
        //increment stack pointer and update top value
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
		return offset + size - 1;
	}
}