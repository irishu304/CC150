//2.1 Remove Dups
//interate the list in one pass
void deleteDups(LinkedListNode n) {
	Hashset<Integer> set = new Hashset<Integer>();
	LinkedListNode previous = null;
	while (n != null) {
		if (set.contains(n.data)) {
			previous.next = n.next
		}
		else {
			set.add(n.data);
			previous = n;
		}
		n = n.next;
		//node previous & n both move forward
	}
}
//HashSet instruction
//http://jingyan.baidu.com/article/48206aead61355216bd6b34a.html

//no temporary buffer
void deleteDups(LinkedListNode head) {
	LinkedListNode current = head;
	while (current != null) {
		LinkedListNode runner = current;
		//remove all future nodes with same value
		while (runner.next != null) {
			if (runner.next.data == current.data) {
				runner.next = runner.next.next;
			}
			else {
				runner = runner.next;
			}
		}
		current = current.next;
	}
}
//O(1) space, O(n^2) time

//2.2 Return Kth to Last
// Recursive???
class Index {
	public int value = 0;
}
//wrap the counter value with a simple class to return counter&index
LinkedListNode kthToLast(LinkedListNode head, int k) {
    Index idx = new Index();
	return kLast(head, k, idx);
}

LinkedListNode klast(LinkedListNode head, int k, Index idx) {
	if (head == null) return null;
	LinkedListNode node = klast(head.next, k, idx);
	idx.value = idx.value + 1;
	if (idx.value == k) {
		return head;
	}
	return node;
}

//Iterative
LinkedListNode kthToLast(LinkedListNode head, int k) {
	LinkedListNode p1 = head;
	LinkedListNode p2 = head;
	//move p1 k nodes into the list
	for (int i = 0; i < k; i++) {
		if (p1 == null) return null;
		//out of bound
		p1 = p1.next;
	}
	//move p1&p2 at same pace
	while (p1 != null) {
		p1 = p1.next;
		p2 = p2.next;
	}
	return p2;
}

//2.3 Delete Middle Node
boolean delete(LinkedListNode n) {
	if (n == null || n.next == null)
		return false; //failure
	LinkedListNode next = n.next;
	n.data = next.data;
	n.next = next.next;
	return true;
}

//2.4 Partition
//two lists: before&after, then merge???
LinkedListNode partition(LinkedListNode node, int x) {
	LinkedListNode beforeStart = null;
	LinkedListNode beforeEnd = null;
	LinkedListNode afterStart = null;
    LinkedListNode afterEnd = null;

    while (node != null) {
    	LinkedListNode next = node.next;
    	node.next = null; //partite a single node
    	if (node.data < x) {
    		if (beforeStart == null) {
    			beforeStart = node;
    			beforeEnd = beforeStart;
    		}
    		else {
    			beforeEnd.next = node;
    			beforeEnd = node;
    		}
    	}
    	else {
    		if (afterStart == null) {
    			afterStart = node;
    			afterEnd = afterEnd;
    		}
    		else {
    			afterEnd.next = node;
    			afterEnd = node;
    		}
    	}
    	node = next;
    }

    if (beforeStart == null) 
    	return afterStart;
    else {
    	beforeEnd.next = afterStart;
    	return beforeStart;
    }
}

//less insert at head, greater insert at tail
LinkedListNode partition(LinkedListNode node, int x) {
	LinkedListNode head = node;
	LinkedListNode tail = node;

	while (node != null) {
		LinkedListNode next = node.next;
		if (node.data < x) {
			head.next = node;
			head = node;
		}
		else {
			node.next = tail;
			tail = node;
		}
		node = next;
	}
	tail.next = null;
	return head;
}





