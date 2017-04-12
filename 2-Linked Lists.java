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

//2.5 Sum Lists
//start from lower digit
LinkedListNode sum(LinkedListNode l1, LinkedListNode l2, int carry) {
	if (l1 == null && l2 == null && carry == 0) 
		return null;
	LinkedListNode result = new LinkedListNode();
	int value = carry;
	if (l1 != null) value += l1.data;
	if (l2 != null) value += l2.data;
	result.data = value % 10; //remainder

	//recurse???
	if (l1 != null || l2 != null) {
		//in case one list is shorter than another
		LinkedListNode more = addLists(l1 == null ? null : l1.next,
			                           l2 == null ? null : l2.next,
			                           value >= 10 ? 1 : 0);
		result.setNext(more);
	}
	return result;
}

//???
LinkedListNode sum(LinkedListNode l1, LinkedListNode l2) {
	if (l1 == null && l2 == null) return null;
	if (l1 != null && l2 == null) return l1;
	if (l1 == null && l2 != null) return l2;

	LinkedListNode result = new LinkedListNode();
	int carry = 0;
	while (l1 != null || l2 != null || carry != 0) {
		int sum = 0;
		if (l1 != null) {
			sum += l1.data;
			l1 =l1.next;
		}
		if (l2 != null) {
			sum += l2.data;
			l2 = l2.next;
		}
		sum += carry;
		result.data = sum % 10;
		carry = sum / 10;
		result = result.next;
	}
	return result;
}

//start from higher digit
class Partialsum {
	public LinkedListNode sum = null;
	public int carry = 0;
}

LinkedListNode addLists(LinkedListNode l1, LinkedListNode l2) {
	int len1 = length(l1);
	int len2 = length(l2);
	//pad the shorter list with zeros at higher digits
	if (len1 < len2) {
		l1 = padList(l1, len2 - len1);
	}
	else {
		l2 = padList(l2, len1 - len2);
	}
	//add a list, if there was a carry value, insert it at the front of list
	//if not, just return the linked list
	Partialsum addition = addListHelper(l1, l2);
	if (addition.carry == 0) {
		return addition.sum;
	}
	else {
		LinkedListNode result =insertBefore(addition.sum, addition.carry);
		return result;
	}
}

Partialsum addListsHelper(LinkedListNode l1, LinkedListNode l2) {
	if (l1 == null && l2 == null) {
		Partialsum addition = new Partialsum();
		return addition;
	}
	//add lower digits
	Partialsum addition = addListsHelper(l1.next, l2.next);
	//add carry to current data
	int val = addition.carry + l1.data + l2.data;
	//insert addtion of current digit
	LinkedListNode full_result = insertBefore(addition.sum, val % 10);
	//return sum&carry so far
	addition.sum = full_result;
	addition.carry = val / 10;
	return addition;
}

LinkedListNode padList(LinkedListNode n, int padding) {
	LinkedListNode head = n;
	for (int i = 0; i < padding; i++) {
		head = insertBefore(head, 0);
	}
	return head;
}

//insert node in front of a linked list
LinkedListNode insertBefore(LinkedListNode m, int data) {
	LinkedListNode node = new LinkedListNode(data);
	if (m != null) {
		node.next = m;
	}
	return node;
}














