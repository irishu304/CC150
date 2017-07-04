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
//in-order traversal, assume no duplicate values
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

//4.7 Builder Order
//DFS
Stack<Project> findBuildOrder(String[] projects, String[][] dependencies) {
	Graph graph = buildGraph(projects, dependencies);
	return orderProjects(graph.getNodes());
}

Stack<Project> orderProjects(ArrayList<Project> projects) {
	Stack<Project> stack = new Stack<Project>();
	for (Project project : projects) {
		if (project.getState() == Project.State.Blank) {
			if(!doDFS(project, stack)) {
				return null;
			}
		}
	}
	return stack;
}

boolean doDFS(Project project, Stack<Project> stack) {
	if (project.getState() == Project.State.Partial) {
		return false;
	} //cycle exists
	if (project.getState() == Project.State.Blank) {
		project.setState(Project.State.Partial);
		ArrayList<Project> children = project.getChildren();
		for (Project child : children) {
			if (!doDFS(child, stack)) {
				return false;
			}
		}
		project.setState(Project.State.Complete);
		stack.push(project);
	}
	return true;
}

Graph buildGraph(String[] projects. String[][] dependencies) {
	Graph graph = new Graph();
	for (String project : projects) {
		graph.createNode(project);
	}
	for (String[] dependency : dependencies) {
		String first = dependency[0];
		String second = dependency[1];
		graph.addEdge(first, second);
	}
	return graph;
}

public class Graph {
	private ArrayList<Project> nodes = new ArrayList<Project>();
	private HashMap<String, Project> map = new HashMap<String, Project>();

	public Project getOrCreateNode(String name) {
		if (!map.containsKey(name)) {
			Project node = new Project(name);
			nodes.add(node);
			map.put(name, node);
		}
		return map.get(name);
	}

	public void addEdge(String startName, String endName) {
		Project start = getOrCreateNode(startName);
		Project end = getOrCreateNode(endName);
		start.addNeighbor(end);
	}

	public ArrayList<Project> getNodes() { return nodes };
}

public class Project {
	public enum State {Complete, Partial, Blank};
	private State state = State.Blank;
	public State getState() { return state };
	public void setState(State st) { state = st };

    private ArrayList<Project> children = new ArrayList<project>();
    private HashMap<String, Project> map = new HashMap<String, Project>();
    private String name;
    private int dependencies 0;

    public Project(String n) {name n;}

    public void addNeighbor(Project node) {
        if (!map. containsKey(node.getName())) {
            children.add(node) ;
            map.put(node.getName(), node);
            node.incrementDependencies();
        }
    }
    public String getName() { return name; }
    public ArrayList<Project> getChildren() { return children; }
    public int getNumberDependencies() { return dependencies; }
}
//O(prject # + dependency #) time

//4.8 First Common Ancestor
//with links to parents
TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	//Check if either node is not in the tree, or if one covers the other
	if (!covers(root, p) || !covers(root, q)) {
		return null;
	} else if (covers(p, q)) {
		return p;
	} else if (covers(q, p)) {
		return q;
	}
	TreeNode sibling = getSibling(p);
	TreeNode parent = p.parent;
	while (!covers(sibling, q)) {
		sibling = getSibling(parent);
		parent = parent.parent;
	}
	return parent;
}

boolean covers(TreeNode root, TreeNode p) {
	if (root == null) return false;
	if (root == p) return true;
	return covers(root.left, p) || covers(root.right, p);
}

TreeNode getSibling(TreeNode node) {
	if (node == null || node.parent == null) return null;
	TreeNode parent = node.parent;
	parent.left == node ? parent.right : parent.left;
}
//O(t) time, t is size of common ancestor subtree
//worst case O(n) time, n is nodes # in tree

//4.9 BST Sequences
ArrayList<LinkedList<Integer>> allSequences(Treenode node) {
	ArrayList<LinkedList<Integer>> result = 
	new ArrayList<LinkedList<Integer>>();
	if (node == null) {
		result.add(new LinkedList<Integer>());
		return result;
	}

	LinkedList<Integer> prefix = new LinkedList<Integer>();
	prefix.add(node.data);

	//recurse on left&right subtrees
	ArrayList<LinkedList<Integer>> leftSeq = allSequences(node.left);
	ArrayList<LinkedList<Integer>> rightSeq = allSequences(node.right);
	//weave together each list from left&right sides
	for (LinkedList<Integer> left : leftSeq) {
		for (LinkedList<Integer> right : rightSeq) {
			ArrayList<LinkedList<Integer>> weaved = 
			new ArrayList<LinkedList<Integer>>();
			weaveLists(left, right, weaved, prefix);
			result.addAll(weaved);
		}
	}
	return result;
}

/* Weave lists together in all possible ways. This algorithm works by 
removing the head from one list, recursing, and then doing the same thing 
with the other list. */
void weaveLists(LinkedList<Integer> left, LinkedList<Integer> right,
	ArrayList<LinkedList<Integer>> weaved, LinkedList<Integer> prefix) {
	//One list is empty. Add remainder to prefix and store result
		if (first.size() == 0 || second.size() == 0) {
		LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
		result.addAll(first);
		result.addAll(second);
		results.addAll(result);
		return;
	}

	/* Recurse with head of first added to the prefix. Removing the head 
	will damage first, so we'll need to put it back where we found it 
	afterwards */
	int headFirst = first.removeFirst();
	prefix.addLast(headFirst);
	weaveLists(first, second, results, prefix);
	prefix.removeLast();
	first.addFirst(headFirst);

	//Do the same thing with second, damaging and then restoring the list
	int headSecond = second.removeFirst();
	prefix.addLast(headSecond);
    weaveLists(first, second, results, prefix);
    prefix.removeLast();
    second.addFirst(headSecond);
}

//4.10 Check Subtree
//check pre-order substring
boolean containsTree(TreeNode t1, TreeNode t2) {
	StringBuilder string1 = new StringBuilder();
	StringBuilder string2 = new StringBuilder();
	gerOrderString(t1, string1);
	getOrderString(t2, string2);
	return string1.indexOf(string2.toString()) != -1; 
	//indexOf(), if not found, return -1
}

void getOrderString(TreeNode node, StringBuilder s) {
	if (node == null) {
		s.append(null);
		return;
	} //add null indicator for empty nodes
	s.append(node.data + " "); //pre-order, add root first
	getOrderString(node.left, s); //add left
	getOrderString(node.right, s); // add right
}
//O(n+m) time&space. n = t1 nodes #, m = t2 nodes #, simpler tree.

//larger tree, check every time t2's root appears in t1
boolean containsTree(TreeNode t1, TreeNode t2) {
	if (t2 == null) return true;
	return subTree(t1, t2);
}

boolean subTree(TreeNode r1, TreeNode r2) { //larger tree r1
    if (r1 == null) return false; //r2 is not null as checked above
    else if (r1.data == r2.data && matchTree(r1, r2)) return true;
    return subTree(r1.left, r2) || subTree(r1.right, r2);
}

boolean matchTree(TreeNode r1, TreeNode r2) {
	if (r1 == null && r2 == null) return true;
	else if (r1 == null || r2 == null) return false;
	else if (r1.data != r2.data) return false;
	else {
	return matchTree(r1.left, r2.left) && matchTree(r1.right, r2.right);
	}
}
//O(n+km) time, k = r2's root occurence times. O(log n + log m) space

//4.11 Random Nodes
//possibility of going left/right depends on nodes # of left/right
class TreeNode {
	private int data;
	public TreeNode left;
	public TreeNode right;
	private int size = 0; //store a size variable in each node

	public TreeNode(int d) {
		data = d;
		size = 1;
	}

	public TreeNode getRandomNode() {
		int leftSize = left == null ? 0 : left.size();
		Random random = new Random();
		int index = random.nextInt(size);
		if (index < leftSize) {
			return left.getRandomNode();
		} else if (index == leftSize) {
			return this;
		} else {
			return right.getRandomNode();
		}
	}

	public void insertInOrder(int d) {
		if (d <= data) {
			if (left == null) {
				left = new TreeNode(d);
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (right == null) {
				right = new TreeNode(d);
			} else {
				right.insertInOrder(d);
			}
		}
		size++;
	}

	public int size() { return size };
	public int data() { return data };

	public TreeNode find(int d) {
		if (d == data) {
			return this;
		} else if (d <= data) {
			return left != null ? left.find(d) : null;
		} else if (d > data) {
			return right != null ? right.find(d) : null;
		}
		return null;
	}
}
//O(log n) time in a balanced tree

//4.12 Paths with Sum
//brute force
int count (TreeNode root, int targetSum) {
	if (root == null) return 0;
	//count paths with sum starting from root
	int pathRoot = countNode(root, targetSum, 0);
	//try nodes on left or right
	int pathLeft = count(root.left, targetSum);
	int pathRight = count(root.right, targetSum);
	return pathRoot + pathLeft + pathRight;
}

int countNode(TreeNode node, int targetSum, int currentSum) {
	if (node == null) return 0;
	currentSum += node.data;

	int totalPath = 0;
	if (currentSum == targetSum) totalPath++;
	totalPath += countNode(node.left, targetSum, currentSum);
	totalPath += countNode(node.right, targetSum, currentSum);
	return totalPath;
}
//O(N log N) time

//optimized
int count (TreeNode root, int targetSum) {
	return count(root, targetSum, 0, new HashMap<Integer, Integer>());
}
int count(TreeNode node, int targetSum, int runningSum, 
	      HashMap<Integer, Integer> pathCount) {
	if (node == null) return 0;

	//count paths with sum ending in current node
	runningSum += node.data;
	int sum = runningSum - targetSum;
	int totalPath = pathCount.getOrDefault(sum, 0); //return hashmap value
    //If runningSum equals targetSum, add one path starts at root
    if (runningSum == targetSum) totalPath++;
}















