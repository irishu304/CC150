//4.1 Route Between Nodes
//an iterative implementation of breadth-first search
enum State {Visited, Unvisited, Visiting;}
/*enumeration, enum很像特殊的class，实际上enum声明定义的类型就是一个类。 
而这些类都是类库中Enum类的子类
compareTo(): 返回两个枚举值的顺序之差。values(): 返回一个包含全部枚举值的数组。
toString(): 返回枚举常量的名称。valueOf()：返回带指定名称的指定枚举类型的枚举常量。
*/
boolean search(Graph g, Node start, Node end) {
	if (start == end) return true;

	LinkedList<Node> q = new LinkedList<Node>(); //operate as queue
    for (Node u : g.getNodes()) {
    	u.state = State.Unvisited;
    } //clear or reset
    start.state = State.Visiting;
    q.add(start);
    Node u;
    while (!q.isEmpty()) {
    	u = q.removeFirst(); //dequeue
    	if (u != null) {
    		for (Node v : getAdjacent()) {
    			if (v.state == State.Unvisited) {
    				if (v == end) return true;
    				else {
    					v.state = State.Visiting;
    					q.add(v);
    				}
    			}
    		} //all adjacent nodes of node u are reached
    		u.state = State.Visited;
    	} //u = one of formal u's adjacement node
    }
    return false;
}

//4.2 Minimal Tree
//use a method that just traverse a subsection of the array and return root
TreeNode createMinimalBST(int array[]) {
	return createMinimalBST(array, 0, array.length - 1);
}

TreeNode createMinimalBST(int a[], int start, int end) {
	if (end < start) return null;
	int mid = (start + end) / 2;
	TreeNode n = new TreeNode(a[mid]);
	n.left = createMinimalBST(a, start, mid);
	n.right = createMinimalBST(a, mid + 1, end);
	return n;
}

//4.3 List of Depths
//depth-first search
void createLevelLinkedList(ArrayList<LinkedList<TreeNode>> lists,
	                       TreeNode root, int level) {
	if (root == null) return; //base case

	LinkedList<TreeNode> list = null;
    if(lists.size == level) {
    	list = new LinkedList<TreeNode>();
    	lists.add(list);
    	//levels are traversed in order, so add the level at the end
    } else {
    	list = lists.get(level);
    }
    list.add(root);
    createLevelLinkedList(root.left, lists, level + 1);
    createLevelLinkedList(root.right, lists, level + 1);
    //similar to pre-order traversal
}

ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
	ArrayList<LinkedList<TreeNode>> lists = 
	new ArrayList<LinkedList<TreeNode>>();
	createLevelLinkedList(root, lists, 0);
	return lists;
}

//breadth-first search
//to visit nodes on level i, just look at the children of all i-1 nodes
ArrayList<LinkedList<TreeNode>> createLevelLinkedList(TreeNode root) {
	ArrayList<LinkedList<TreeNode>> result = 
	new ArrayList<LinkedList<TreeNode>>();
	LinkedList<TreeNode> current = new LinkedList<TreeNode>();
	if (root != null) {
		current.add(root);
	}
	while (current.size() > 0) {
		result.add(current); //add previous level
		LinkedList<TreeNode> parents = current; //go to next level
		current = new LinkedList<TreeNode>();
		for(TreeNode parent : parents) {
			if (parent.left != null) current.add(parent.left);
			if (parent.right != null) current.add(parent.right);
		}
	}
	return result;
}
//O(N) time, O(N) space

//4.4 Check Balanced
int getHeight(TreeNode root) {
	if (root == null) return -1;
	return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
}

boolean isBalanced(TreeNode root) {
	if (root == null) return true;
	int heightDiff = getHeight(root.left) - getHeight(root.right);
	//height difference for two subtrees of root
	if (Math.abs(heightDiff) > 1) return false;
	else {
		return isBalanced(root.left) && isBalanced(root.right);
	} //recurse to check lower levels
}
//O(N logN) time, too complex

int checkHeight(TreeNode root) {
	if (root == null) return -1;
	
	int leftHeight = checkHeight(root.left);
	if (leftHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
	int rightHeight = checkHeight(root.right);
	if (rightHeight == Integer.MIN_VALUE) return Integer.MIN_VALUE;
	//recursively check two subtrees, if balanced, return true height
	//if not balanced, pass error value up
	//Integer.MAX_VALUE = 2147483647, Integer.MIN_VALUE = -2147483647

	int heightDiff = leftHeight - rightHeight;
	if (Math.abs(heightDiff) > 1) return Integer.MIN_VALUE;
	else return Math.max(leftHeight, rightHeight) + 1;
}

boolean isBalanced(TreeNode root) {
	return checkHeight(root) != Integer.MIN_VALUE;
}
//O(N) time, O(height) space

//4.5 Check Balanced
//in-order traveral, assume no duplicate values
Integer last_printed = null;
boolean checkBST(TreeNode n) {
	if (n == null) return true;
	if (!checkBST(n.left)) return false;
	if (last_printed != null && n.data <= last_printed) {
		return false;
	}
	last_printed = n.data;
	if (!checkBST(n.right)) return false;

	return true;
}

//all left <= current < all right
boolean checkBST(TreeNode n) {
	return checkBST(n, null, null);
}

//when branch left, max updates. when branch right, min updates
boolean checkBST(TreeNode n, Integer min, Integer max) {
	if (n == null) return true;
    if ((min != null && n.data <= min) || (max != null && n.data > max)) {
    	return false;
    }
    if ( !(checkBST(n.left, min, n.data) && 
    	   checkBST(n.right, n.data, max)) ) {
    	return false;
    }
    return true;
}
//O(N) time, O(logN) space

//4.6 Successor
//three different cases
TreeNode inorderSucc(TreeNode n) {
	if (n == null) return null;
	if (n.right != null) {
		return leftmostChild(n.right);
	} else {
		TreeNode m = n;
		TreeNode p = m.parent;
		while (p != null && p.left != m) {
			m = p;
			p = p.parent;
		} //go up until p is the middle node, i.e. traverse right later)
		return p;
	}
}

TreeNode leftmostChild(TreeNode n) {
	if (n == null) {
		return null;
	} else {
		while (n.left != null) {
			n = n.left;
		}
	}
	return n;
}





















