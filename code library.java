//HashMapList<T, E>
//map from an item of type T to an ArrayList of type E
public class HashMapList <T, E> {
private HashMap<T, ArrayList<E>> map = new HashMap<T, ArrayList<E>>();

//Insert item into list at key. 
public void put(T key, E item) {
	if (!map.containsKey(key)) {
		map.put(key, new ArrayList<E>()); //create a new pair at first
	}
	map.get(key).add(item);
}

//Insert list of items at key. 
public void put(T key, ArrayList<E> items) {
	map.put(key, items);
}

//Get list of items at key . 
public ArrayList<E > get(T key) {
	return map.get(key) ;
}

//Check if hashmaplist contains key
public boolean containsKey(T key) {
	return map.containsKey(key);
}

//Check if list at key contains value
public boolean containsKeyValue(T key, E value) {
	ArrayList<E> list = get(key); //get corresponding arraylist at first
	if (list == nUll) return false;
	return list.contains (value);
}

//Get the list of keys
public Set<T> keySet() {
	return map.keySet();
}

//Override
public String toString() {
	return map.toString();
}

//TreeNode(BST)
public class TreeNode {
	public int data;
	public TreeNode left, right, parent;
	private int size = 0;

	public TreeNode(int d) {
	data = d;
	size = 1;
	}

	public void insertlnOrder(int d) {
		if (d <= data) {
			if (left == null) {
				setLeftChild(new TreeNode(d));
			} else {
				left.insertInOrder(d);
			}
		} else {
			if (right == null) {
				setRightChild(new TreeNode(d));
			} else {
				right.insertinOrder(d);
			}
		}
		size++;
	}

	public int size() {
		return size;
	}

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

	public void setLeftChild(TreeNode left) {
		this.left = left;
		if (left != null) {
			left.parent = this;
		}
	}

	public void setRightChild(TreeNode right) {
		this.right = right;
		if (right != null) {
			right.parent = this;
		}
	}
}

//LinkedListNode (Linked List)
public class LinkedListNode {
	public LinkedListNode next, prev, last;
	public int data;
	public LinkedListNode(int d, LinkedListNode n, LinkedListNode p){
		data = d;
		setNext(n);
		setPrevious(p);
	}

	public LinkedListNode(int d) {
		data = d; 
	}

	public LinkedListNode() { }

	public void setNext(LinkedListNode n) {
		next = n;
		if (this == last) {
			last = n;
		}
 		if (n != null && n.prev != this) {
			n.setPrevious(this);
		}
	}

	public void setPrevious(LinkedListNode p) {
		prev = p;
		if (p != null && p.next != this) {
			p.setNext(this);
		}
	}

	public LinkedListNode clone() {
		LinkedListNode next2 = null;
		if (next != null) {
			next2 = next.clone();
		}
		LinkedListNode head2 = new LinkedListNode(data, next2, null);
		return head2;
	}
}

//Trie
public class Trie {
	private TrieNode root;

//take a list of strings as an argument
//construct a trie that stores these strings
	public Trie(ArrayList<String> list) {
		root = new TrieNode();
		for (String word : list) {
			root.addWord(word);
		}
	}

//take a list of strings as an argument
//construct a trie that stores these strings
	public Trie(String[] list) {
		root = new TrieNode();
		for (String word : list) {
			root.addWord(word);
		}
	}

//Check whether this trie contains a string with the prefix
	public boolean contains(String prefix, boolean exact) {
		TrieNode lastNode = root;
		int i = 0;
		for (i = 0; i < prefix.length(); i++) {
		 	lastNode = lastNode.getChild(prefix.charAt(i));
			if (lastNode == null) {
				return false;
			}
		}
		return !exact || lastNode.terminates();
	}

	public boolean contains(String prefix) {
		return contains(prefix, false);
	}

	public TrieNode getRoot() {
		return root;
	}
}

//Trie class uses TrieNode class
public class TrieNode {
//The children of this node in the trie.
	private HashMap<Character, TrieNode> children;
	private boolean terminates = false;

//The character stored in this node as data.
	private char character;

//Constructs an empty trie node and initializes the list of its
 //children to an empty hash map. 
 //Used only to construct the root node of the trie.
	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
	}
//Constructs a trie node and stores this character as the node's value.
//Initializes the list of child nodes of this node to an empty hash map
	public TrieNode(char character) {
		this();
		this.character = character;
	}

//Returns the character data stored in this node. */
	public char getChar() {
		return character;
	}

//Add this word to the trie, and recursively create the child nodes
	public void addWord(String word) {
		if (word == null || word.isEmpty()) {
			return;
		}
	char firstChar = word.charAt(0);
	TrieNode child = getChild(firstChar);
	if (child == null) {
		child = new TrieNode(firstChar);
		children.put(firstChar, child);
	}
	if (word.length() > 1) {
		child.addWord(word.substring(1));
	} else {
		child.setTerminates(true);
	}
}

//Find a child node of this node that has the char argument as its data. 
//Return null if no such child node is present in the trie.
	public TrieNode getChild(char c) {
		return children.get(c);
	}

//Returns whether this node represents the end of a complete word. 
	public boolean terminates() {
		return terminates;
	}

//Set whether this node is the end of a complete word
	public void setTerminates(boolean t) {
		terminates = t;
	}
}
