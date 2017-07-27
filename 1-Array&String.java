//1.1 Is Unique
//time complexity O(n^2)
public class solution {
	public boolean isUniqueChars(String str) {
		if (str.length() > 128) return false;

		for (int i = 0; i < str.length(); i++) {
			for (int j = i + 1; j < str.length(); j++) {
				if (str.charAt(i) == str.charAt(j))
					return false;
			}
		}
		return true;
	}
}

//extra space; time complexity O(n)
public class solution {
	public boolean isUniqueChars(String str) {
		if (str.length() > 128) return false;

		Set<Character> set = new HastSet<Character>();
		for (int i = 0; i < str.length(); i++) {
			if (set.contains(str.charAt(i))) {
				return false;
			}
			set.add(str.charAt(i));
		}
		return true;
		}
}

//boolean array
public class solution {
	public boolean isUniqueChars(String str) {
		if (str.length() > 128) return false;

		boolean[] char_set = new boolean[128];
		//element default false
		for (int i = 0; i < str.length(); i++) {
			int val = str.charAt(i);
			//character in string translated into ASCII value
			if (char_set[val]) return false;
			char_set[val] = true;
			//if not find, set the boolean value to be true; like a bitmap
		}
	}
	return true;
}

//1.2 Check Permutation
//identical character counts
boolean permutation(String s, String t) {
	if (s.length() != t.length()) return false;
	int[] letters = new int[128];

	char s_array = s.toCharArray();
	if (char c : s_array) {
		letters[c]++;
	}
	//count the number of each character in string

	for (int i = 0; i < t.length(); i++) {
		int c = str.charAt(i);
		letters[c]--;
		if (letters[c] < 0) return false;
	}
	return true;
}

//sort both strings
String sort(String s) {
	char[] content = s.toCharArray();
	java.util.Arrays.sort(content);
	return new String(content);
}

boolean permutation(String s, String t) {
	if (s.length() != t.length()) return false;
	return sort(s).equals(sort(t));
}

//1.3 URLify
//split the original string
public String replace(String str) {
	String[] words = str.split(" ");
	StringBuilder sentence = new StringBuilder(words[0]);

	for (int i = 1; i < words.length; i++) {
		sentence.append("%20");
		//append %20 to current end of String sentence
		sentence.append(words[i]);
	}
	return sentence.toString();
	//convert StringBuilder object to String object
}

//scan and replace from the end
void replace(char[] str, int truelength) {
	int i =0; index; spacecount = 0;
	//count space #
	for (int i = 0; i < truelength; i++) {
		if (str[i] == " ") {
			spacecount++;
		}
	}

	index = truelength + spacecount * 2;
	if (index < str.length) str[index] = '\0';
	for(i = truelength - 1; i >= 0; i--) {
		if (str[i] == " ") {
			str[index - 1] = '0';
			str[index - 2] = '2';
			str[index - 3] = '%';
			index = index - 3;
		}
		else {
			str[index - 1] = str[i];
			index--;
		}
	}
}

//1.4 Palindrome Permutation
//hash table
boolean permutation(String phrase) {
	int[] table = buildFreqTable(phrase);
	return checkMaxOneOdd(table); 
}

boolean checkMaxOneOdd(int[] table) {
	boolean foundOdd = false;
	for (int count : table) {
	//for each int element (so called count) in table	
		if (count % 2 == 1) {
			if (foundOdd) {
				return false;
				//find second odd, return false
			}
		foundOdd = true;
		}
	}
	return true;
	//if all even
}

//map each letter to a number, non-letter maps to -1, case insensitive
int getCharNumber(Character c) {
	//create an object c for Class Character
	int a = Character.getNumericValue('a');
	int z = Character.getNumericValue('z');
	int val = Character.getNumericValue(c);
	//get unicode value of a character
	if (val >= a && val <= z) {
		return val - a;
	}
	return -1;
}

int[] buildFreqTable(String phrase) {
	int[] table = new int[Character.getNumericValue('z') - 
	                      Character.getNumericValue('a') + 1];
	for (char c : phrase.toCharArray()) {
		//convert string to char array
		int x = getCharNumber(c);
		if (x != -1) {
			table[x]++;
		}
	}
	return table;
}

//check as going along
boolean permutation(String phrase) {
	int countOdd = 0;
	int[] table = new int[Character.getNumericValue('z') -
	                      Character.getNumericValue('a') + 1];
	for (char c : phrase.toCharArray()) {
		int x =getCharNumber(c);
		if (x != -1) {
			table[x]++;
			if (table[x] % 2 ==1) {
				countOdd++;
			}
			else {
				countOdd--;
			}
			/* for a specific table[x], if its ultimate value is even, 
			its countOdd == 0 */
		}
	}
	return countOdd <= 1;
}

int getCharNumber(Character c) {
	int a = Character.getNumericValue('a');
	int z = Character.getNumericValue('z');
	int val = Character.getNumericValue(c);
	for (val >= a && val <= z) {
		return val - a;
	}
	return -1;
}

//1.5 One Away
//check replace and insert separately
boolean oneEditAway(String first, String second) {
	if (first.length() == second.length()) {
		return oneEditReplace(first, second);
	}
	else if (first.length() + 1 == second.length()) {
		return oneEditInsert(first, second);
	}
	else if (first.length() - 1 == second.length()) {
		return oneEditInsert(second, first);
	}
	return false;
}

boolean oneEditReplace(String s1, String s2) {
	boolean foundDifference = false;
	for (int i = 0; i < s1.length(); i++) {
		if (s1.charAt(i) != s2.charAt(i)) {
			if (foundDifference) {
				return false;
			}
			foundDifference = true;
		}
	}
	return true;
}

boolean oneEditInsert(String s1, String s2) {
//check if you can insert one character into s1 to make s2
	int index1 = 0;
	int index2 = 0;
	while (index1 < s1.length() && index2 < s2.length()) {
		if (s1.charAt(index1) != s2.charAt(index2)) {
			if (index1 != index2) {
				return false;
			}
			index2++;
			//jump over the inserted character
		}
		else {
			index1++;
			index2++;
		}
	}
	return true;
}

//merge replace and insert
boolean oneEditAway(String first, String second) {
	if (Math.abs(first.length() - second.length()) > 1) {
		return false;
	}
	String s1 = first.length() < second.length() ? first : second;
	String s2 = first.length() < second.length() ? second : first;
    //shorter one -> s1; longer one -> s2
    int index1 = 0;
    int index2 = 0;
    boolean foundDifference = false;
    while (index1 < s1.length() && index2 < s2.length()) {
    	if (s1.charAt(index1) != s2.charAt(index2)) {
    		if (foundDifference) {
    			return false;
    		}
    		foundDifference = true;
    		//ensure it's the first difference

    		if (s1.length() == s2.length()) {
    			index1++;
    		}
    		//On replace, move shorter pointer
    	}
    	else {
    		index1++;
    	}
    	index2++;
    	//always move longer pointer
    }
    return true;
}

//1.6 String Compression
//copy and count
String compress(String str) {
	String compressed = "";
	int count = 0;
	for (int i = 0; i < str.length(); i++) {
		count++;
		if (str.charAt(i) != str.charAt(i + 1) || i + 1 >= str.length()) {
		//end of string is '\n'
			compressed += "" + str.charAt(i) + count;
		//a new copy of the string is created on each concatenation
			count = 0;
		}
	}
	return compressed.length() < str.length() ? compressed : str;
}

//StringBuilder
String compress(String str) {
	StringBuilder compressed = new StringBuilder();
	//create a resizable array of all the strings
	int count = 0;
	for (int i = 0; i < str.length(); i++) {
		count++;
		if (str.charAt(i) != str.charAt(i + 1) || i + 1 >= str.length()) {
			compressed.append(str.charAt(i));
		    compressed.append(count);
		    count = 0;
		}
	}
	return compressed.length() < str.length() ? compressed.toString() : str;
}

//1.7 Rotate Matrix
boolean rotate(int[][] matrix) {
	if (matrix.length == 0 || matrix.length != matrix[0].length) {
		return false;
		//row # == 0 || row # != column #
	}
	int n = matrix.length;
	for (int layer = 0; layer < n / 2; layer++) {
		int first = layer; //beginning of each sub-matrix
		int last = n - 1 - layer; //endpoint
		for (int i = first; i < last; i++) {
		//for each sub-matrix
		    int offset = i - first;
		    int top = matrix[first][i];
		    //left -> top
		    matrix[first][i] = matrix[last - offset][first]; 
		    //bottom -> left
		    matrix[last - offset][first] = matrix[last][last - offset]; 
		    //right -> bottom
		    matrix[last][last - offset] = matrix[i][last];
		    //top -> right 
		    matrix[i][last] = top; //
		}
	}
	return true;
}

//1.8 Zero Matrix
void setZeros(int[][] matrix) {
	boolean[] row = new boolean[matrix.length];
	boolean[] column = new boolean[matrix[0].length];

	for (int i = 0; i < matrix.length; i++){
		for (int j = 0; j < matrix[0].length; j++) {
			if (matrix[i][j] == 0) {
				row[i] = true;
				column[j] = true;
			}
		}
	}

	for (int i = 0; i < matrix.length; i++) {
		if(row[i]) nullifyRow(matrix, i);
	}
	for (int j = 0; j < matrix[0].length; j++) {
		if(column[j]) nullifyColumn(matrix, j);
	}
}

void nullifyRow(int[][] matrix, int row) {
	for (int j = 0; j < matrix[0].length; j++) {
		matrix[row][j] = 0;
	}
}

void nullifyColumn(int[][] matrix, int column) {
	for (int i = 0; i < matrix.length; i++) {
		matrix[i][column] = 0;
	}
}

//1.9 String Rotation
boolean isRotation(String s1, String s2) {
	int len = s1.length();
	//check whether same length and non-empty
	if (len == s2.length() && len > 0) {
		String s1s1 = s1 + s1;
		return isSubstring(s1s1, s2);
	}
	return false;
}
