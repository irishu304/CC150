//8.1 Triple Step
//brute force
int countWays(int n) {
	if (n < 0) return 0;
	else if (n == 0) return 1;
	else return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
}
//O(3^n) time, each call branches out to three more calls

//recursive&cache previous results
int countWays(int n) {
	int[] memo = new int[n + 1];
	Arrays.fill(memo, -1); //set all element to -1
	//Arrays.fill(memo, 3, 5, 0);
	//set memo[3], memo[4], memo[5] to 0
	return count(n, memo);
}
int count(int n, int[] memo) {
	if (n < 0) return 0;
	else if (n == 0) return 1;
	else if (memo[n] > -1) return memo[n];
	else {
		memo[n] = count(n - 1, memo) + count(n - 2, memo) +
				  count(n - 3, memo);
		return memo[n];
	}
}
//quickly overflow the bound of integer, but long will delay

//8.2 Robot in a Grid
ArrayList<Point> getPath(boolean[][] maze) {
	if (maze == null || maze.length == 0) return null;
	ArrayList<Point> path = new ArrayList<Point>();
	if (countPath(maze, maze.length - 1, maze[0].length - 1, path)) {
		return path;
	}
	return null;
}

boolean countPath(boolean[][] maze, int row, int col, 
				  ArrayList<Point> path) {
	if (row < 0 || col < 0 || !maze[row][col]) return false;
	boolean isAtOrigin = (row == 0) && (col == 0);
	if (isAtOrigin || countPath(maze, row - 1, col, path) ||
		countPath(maze, row, col - 1, path)) {
		Point p = new Point(row, col);
		path.add(p);
		return true;
	}
	return false;
}
//O(2 ^ (r + c)) time

//dynamic programming, avoid duplicate visit
ArrayList<Point> getPath(boolean[][] maze) {
	if (maze == null || maze.length == 0) return null;
	ArrayList<Point> path = new ArrayList<Point>();
	if (countPath(maze, maze.length - 1, maze[0].length - 1, path,
		failedPoints)) return path;
	return null;
}

boolean countPath(boolean[][] maze, int row, int col, ArrayList<Point> 
				path, HashSet<Point> failedPoints) {
	if (row < 0 || col < 0 || !maze[row][col]) return false;
	Point p = new Point(row, col);
	if (failedPoints.contains(p)) return false; //already visited
	boolean isAtOrigin = (row == 0) && (col == 0);
	if (isAtOrigin || countPath(maze, row - 1, col, path, failedPoints) ||
		countPath(maze, row, col - 1, path, failedPoints)) {
		path.add(p);
		return true;
	}
	failedPoints.add(p); //cache result
	return false;
}
//O(rc) time

//8.3 Magic Index
//brute force
int magicSlow(int[] array) {
	for (int i = 0; i < array.length; i++) {
		if (array[i] == i) return i;
	}
	return -1;
}

//recursive & binary search
int magicFast(int[] array) {
	return findMagic(array, 0, array.length - 1);
}
int findMagic(int[] array, int start, int end) {
	if (end < start) return -1;
	int mid = (start + end) / 2;
	if (array[mid] == mid) return mid;
	else if (array[mid] < mid) return (array, mid + 1, end);
	else return (array, start, mid - 1);
}

//not distinct numbers
int magicFast(int[] array) {
	return findMagic(array, 0, array.length - 1);
}
int findMagic(int[] array, int start, int end) {
	if (end < start) return -1;
	int midIndex = (start + end) / 2;
	int midValue = array[midIndex];
	if (midIndex == midValue) return midIndex;

	int leftIndex = Math.min(midValue, midIndex - 1);
	int left = findMagic(array, start, leftIndex);
	if (left >= 0) return left;

	int rightIndex = Math.max(midValue, midIndex + 1);
	int right = findMagic(array, rightIndex, end);
	return right;
}

//8.4 Power Set
//recursion
List<List<Integer>> getSubsets(ArrayList<Integer> set, int index) {
	ArrayList<List<Integer>> result;
	if (set.size() == index) {
		//base case, add an empty set
		result = new ArrayList<List<Integer>>();
		result.add(new ArrayList<Integer>());
	} else {
		subsets = getSubsets(set, index + 1);
		//add a new element to previous result allsubsets
		//added results are stored in moresubsets
		int item = set.get(index);
		List<List<Integer>> moresubsets = new ArrayList<List<Integer>>();
		for (ArrayList<Integer> subset : subsets) {
			List<Integer> temp = new ArrayList<Integer>(subset);
			temp.add(item);
			moresubsets.add(temp);
		}
		result.addAll(moresubsets);
	}
	return result;
}
//O(n * 2^n) time and space

//bit manipulation

//8.5 Recursive Multiply
int minProduct(int a, int b) {
	int large = Math.max(a, b);
	int small = Math.min(a, b);
	return helper(small, large);
}
int helper(int small, int large) {
	if (small == 0) return 0;
	if (small == 1) return large;
	int s = small >> 1; //divide by 2
	int halfProd = helper(s, large);
	if (small % 2 == 0) {
		return halfProd + halfProd;
	} else {
		return halfProd + halfProd + large;
	}
}
//O(log small) time

//8.6 Towers of Hanoi

//8.7 Permutations without Dups
ArrayList<String> getPerms(String str) {
	if (str == null) return null;
	List<String> result = new ArrayList<String>();
	if (str.length() == 0) {
		result.add("");
		return result;
	}
	char c = str.charAt(0);
	List<String> words = getPerms(str.substring(1)); //recursion
	for (String word : words) {
		for (int j = 0; j < word.length(); j++) {
			String s = word.substring(0, j) + c + word.substring(j);
			result.add(s);
		}
	}
	return result;
}
//O(n!) time

//8.8 Permutations with Dups
ArrayList<String> getPerms(String str) {
	List<String> result = new ArrayList<String>();
	Map<Character, Integer> map = new HashMap<Character, Integer>();
	for (char c : str.toCharArray()) {
		if (!map.containsKey(c)) map.put(c, 0);
		map.put(c, map.get(c) + 1);
	} //build frequency table
	printPerms(map, "", str.length(), result);
	return result;
}
void printPerms(HashMap<Character, Integer> map, String prefix, int remain,
				ArrayList<String> result) {
	if (remain == 0) {
		result.add(prefix);
		return; //completed
	}
	//Try remaining letters for next char, generate remaining permutations
	for (char c : map.ketSet()) {
		int count = map.get(c);
		if (count > 0) {
			map.put(c, count - 1);
			printPerms(map, prefix + c, remain - 1, result);
			map.put(c, count);
			//recover to origin count, will use next char in key set
		}
	}
}













