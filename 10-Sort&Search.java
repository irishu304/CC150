//10.1 Sorted Merge
void merge(int[] a, int[] b, int lastA, int lastB) {
	int indexA = lastA - 1; //index of last element in array A
	int indexB = lastB - 1;
	int indexMerged = lastA + lastB - 1;

	while (indexB >= 0) {
		if (indexA >= 0 && a[indexA] > b[indexB]) {
			a[indexMerged] = a[indexA];
			indexA--;
		} else {
			a[indexMerged] = b[indexB];
			indexB--;
		}
		indexMerged--;
	}
}

//10.2 Group Anagrams
//to fully sort the anagram strings array
class AnagramComparator implements Comparator<String> {
	public String sortChars(String s) {
		char[] content = s.toCharArray();
		Arrays.sort[content];
		return new String(content);
	}

	public int compare(String s1, String s2) {
		return sortChars(s1).compareTo(sortChars(s2));
	}
}
Arrays.sort(array, new AnagramComparator());
//O(n logn) time

//just group the anagram strings
void sort(String[] array) {
	HashMapList<String, String> mapList = new HashMapList<String,String>();
	for (String s : array) {
		String key = sortChars(s);
		mapList.put(key, s);
	} //group anagram s by its sorted string key

	//convert hash table to array
	int index = 0;
	for (String key : mapList.keySet()) {
		ArrayList<String> list = mapList.get(key); //get a group of anagram
		for (String t : list) {
			array[index] = t;
			index++;
		}
	} //move to next sorted string key
}

String sortChars(String s) {
	char[] content = s.toCharArray();
	Arrays.sort[content];
	return new String(content);
}
//HashMapList<String, Integer> is a HashMap that maps from Strings to
//ArrayList<Integer>.
//http://blog.csdn.net/qiulongtianshi/article/details/8146717

//10.3 Search in Rotated Array
int search(int[] a, int left, int right, int x) {
	int mid = (left + right) / 2;
	if (x == a[mid]) return mid;
	if (right < left) return -1; //index

	/* Either the left or right half must be normally ordered. Find out 
	which side is normally ordered, then use the normal half to figure 
	out which side to search. */
	if (a[left] < a[mid]) { //left half is ordered
		if (x >= a[left] && x < a[mid]) {
			return search(a, left, mid - 1, x);
		} else {
			return search(a, mid + 1, right, x);
		}
	} else if (a[left] > a[mid]) { //right half is ordered
		if (x > a[mid] && x <= a[right]) {
			return search(a, mid + 1, right, x);
		} else {
			return search(a, left, mid - 1, x);
		}
	} else if (a[left] == a[mid]) {
		if (a[mid] != a[right]) { //search if right is different
			return search(a, mid + 1, right, x);
		} else { //search both halves
			int result = search(a, left, mid - 1, x);
			if (result == -1) {
				return search(a, mid + 1, right, x);
			} else return result;
		}
	}
	return -1;
}
//O(log n) time if all unique elements. O(n) time if many duplicates.

//10.4 Sorted Search, No Size
int search(Listy list, int value) {
	int index = 1;
	while (list.elementAt(index) != -1 && list.elementAt(index) < value) {
		index = index * 2;
	}
	return binarySearch(list, value, index / 2, index);
}

int binarySearch(Listy list, int value, int low, int high) {
	int mid;
	while (low <= high) {
		mid = (low + high) / 2;
		int middle = list.elementAt(mid);
		if (middle > value || middle == -1) {
			high = mid - 1;
		} else if (middle < value) {
			low = mid + 1;
		} else return mid;
	}
	return -1;
}
//O(log n) time to find length, O(log n) time to search

//10.5 Sparse Search
int search(String[] strings, String str) {
	if (strings == null || str == null || str == "") {
		return -1;
	}
	return search(strings, str, 0, strings.length - 1);
}

int search(String[] strings, String str, int first, int last) {
	if (first > last) return -1;
	int mid = (first + last) / 2;

	if (strings[mid].isEmpty()) { //move mid to the nearest non-empty str
		int left = mid - 1;
		int right = mid + 1;
		while (true) {
			if (left < first && right > last) {
				return -1;
			} else if (right <= last && !strings[right].isEmpty()) {
				mid = right;
				break;
			} else if (left >= first && !strings[left].isEmpty()) {
				mid = left;
				break;
			}
			left--;
			right++;
		}
	}

	if (str.equals(strings[mid])) {
		return mid;
	} else if (str.compareTo(strings[mid]) < 0) { //mid in front of str
		return search(strings, str, mid + 1, last);
	} else {
		return search(strings, str, first, mid - 1);
	}
}
//O(n) time in worst case

//10.6 Sort Big File
/* We only bring part of the data into memory.
We'll divide the file into chunks, which are x megabytes each, where x is 
the amount of memory we have available. Each chunk is sorted separately 
and then saved back to the file system. Once all the chunks are sorted, we 
merge the chunks, one by one. At the end, we have a fully sorted file.
This algorithm is known as external sort. */

//10.7 Missing Int
/* 1. Create a bit vector (BV) with 4 billion bits. Recall that a bit 
vector  is an array that compactly stores boolean values by using an array 
of ints (or another data type). Each int represents 32 boolean values.
2. Initialize BV with all Os.
3. Scan all numbers (num) from the file and call BV.set (num, 1).
4. Now scan again BV from the Oth index.
5. Return the first index which has a value of 0. */
long numberOfInts = ((long) Integer.MAX_VALUE + 1);
byte[] bitfield = new byte[(int) (numberOfInts) / 8];
String filename = ...

void findOpenNumber() throws FileNotFoundException {
	Scanner in = new Scanner(new FileReader(filename));
	while (in.hasNextInt()) {
		int n = in.nextInt();
		/* Find the corresponding number in bitfield by using OR operator 
		bit by bit to set the nth bit of a byte (eg. 10 would correspond 
		to the 2nd bit of index 2 in the byte array */
		bitfield [n / 8] |= 1 << b % 8;
	}

	for (int i = 0; i < bitfield.length; i++) {
		for (int j = 0; j < 8; j++) {
			//Retrieve the individual bits of each byte
			//When 0 bit is found, print corresponding value 
			if ((bitfield[i] & (1 << j)) == 0) {
				System.out.println(i * 8 + j);
				return;
			}
		}
	}
}

//10.8 Find Duplicates
void checkDuplicates(int[] array) {
	BitSet bs = new BitSet(32000);
	for (int i = 0; i < array.length; i++) {
		int num = array[i];
		int num0 = num - 1; //bitset start at 0, numbers start at 1
		if (bs.get(num0)) { //if already exists, print it
			System.out.println(num);
		} else {
			bs.set(num0);
		}
	}
}

class BitSet { //???
	int[] bitset;
	public BitSet(int size) {
		bitset = new int[(size >> 5) + 1]; //divide by 32
	}
	boolean get(int pos) {
		int wordNumber = (pos >> 5);
		int bitNumber = (pos & 0x1F); //mod 32
		return (bitset[wordNumber] & (1 << bitNumber)) != 0;
	}
	void set(int pos) {
		int wordNumber = (pos >> 5);
		int bitNumber = (pos & 0x1F);
		bitset[wordNumber] |= 1 << bitNumber;
	}
} 

//10.9 Sorted Matrix Search
//simply do binary search on each row
boolean findElement (int[][] matrix, int elem) {
	int row = 0;
	int col = matrix[0].length - 1;
	while (row < matrix.length && col >= 0) {
		if (matrix[row][col] == element) {
			return true;
		} else if (matrix[row][col] > element) {
			col--;
		} else {
			row++;
		}
	} //cut the matrix into a smaller one
	return false;
}
//O(m log n), column # m, row # n

//binary search the diagonal, divide the matrix into four rigions
//remove top left (smaller) and bottom right (greater) regions

//10.10 Rank from Stream
RankNode root = null;
void track(int number) {
	if (root == null) {
		root = new RankNode(number);
	} else {
		root.insert(number);
	}
}

int getRankOfNumber(int number) {
	return root.getRank(number);
}

public class RankNode {
	public RankNode left, right;
	public int data = 0;
	public int left_size = 0;
	public RankNode(int d) {
		data = d;
	}
    public void insert(int d) {
    	if (d <= data) {
    		if (left != null) left.insert(d);
    		else left = new RankNode(d);
    		left_size++;
    	} else {
    		if (right != null) right.insert(d);
    		else right = new RankNode(d);
    	}
    }
    public int getRank(int d) {
    	if (d == data) return left_size;
    	else if (d < data) {
    		if (left == null) return -1; //not found case
    		else return left.getRank(d);
    	} else {
    		if (right == null) return -1;
    		else {
    			int right_size = right.getRank(d);
    			return left_size + 1 + right_size;
    		}
    	}
    }
}
//O(log N) time on a balanced tree. O(N) time on unbalanced tree.

//10.11 Peaks and Valleys
//sort then swap every two elements
void sortPV(int[] array) {
	Arrays.sort(array);
	for (int i = 1; i < array.length; i += 2) {
		swap(array, i - 1, i);
	}
}
 void swap (int[] array, int left, int right) {
 	int temp = array[left];
 	array[left] = array[right];
 	array[right] = temp;
}
//O(n log n) time

void sortPV(int[] array) {
	for (int i = 1; i < array.length; i += 2) {
		int biggestIndex = maxIndex(array, i - 1, i , i + 1);
		if (i != biggest) {
			swap(array, i, biggestIndex);
		}
	}
}

int biggestIndex(int[] array, int a, int b, int c) {
	int len = array.length;
	int aValue = (a >= 0 && a < len ? array[a] : Integer.MIN_VALUE); //???
	int bValue = (b >= 0 && b < len ? array[b] : Integer.MIN_VALUE);
	int cValue = (c >= 0 && c < len ? array[c] : Integer.MIN_VALUE);

	int max = Math.max(aValue, Math.max(bValue, cValue));
	if (max == aValue) return a;
	else if (max == bValue) return b;
	else return c;
}

void swap(int[] array, int left, int right) {
	int temp = array[left];
	array[left] = array[right];
	array[right] = temp;
}
//O(n) time
