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
	} //move to nex sorted string key
}

String sortChars(String s) {
	char[] content = s.toCharArray();
	Arrays.sort[content];
	return new String(content);
}
//HashMapList<String, Integer> is a HashMap that maps from Strings to
//ArrayList<Integer>.
//http://blog.csdn.net/qiulongtianshi/article/details/8146717 ???

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





















