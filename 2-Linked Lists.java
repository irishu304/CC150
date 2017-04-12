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
//Recursive
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






