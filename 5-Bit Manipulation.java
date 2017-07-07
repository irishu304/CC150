//5.1 Insertion
int updateBits(int n, int m, int i, int j) { //consider 8-bit int
	int allOnes = ~0;
	int left = allOnes << (j + 1); //1s before position j
	int right = (1 << i) - 1; //1s after position i
	int mask = left | right;
	int n_cleared = n & mask; //clear bits through j to i
	int m_shifted = m << i;
	return n_cleared | m_shifted; //leave position after i unchanged
}

//5.2 Binary to String
String printBinary(double num) {
	if (num >= 1 || num <= 0) return "ERROR";

	StringBuilder binary = new StringBuilder();
	double frac = 0.5;
	while (num > 0) {
		if (binary.length() > 32) return "ERROR";
		if (binary > frac) {
			binary.append(1);
			num -= frac;
		} else {
			binary.append(0);
		}
		frac = frac / 2;
	}
	return binary.toString();
}

//5.3 Flip Bit to Win
int flipBit(int a) {
	if (~a == 0) return Integer.BYTES * 8; //if all 1s, already longest

	int previousLen = 0;
	int currentLen = 0;
	int maxLen = 1; //at least have a sequence of 1
	while (a != 0) {
		if ((a & 1) == 1) { //current bit is 1
			currentLen++;
		} else if ((a & 1) == 0) {
			if (a & 2 == 0) { //next bit is 0
				previousLen = 0;
			} else { //next bit is 1
				previousLen = currentLen;
			}
			currentLen = 0;
		}
		maxLen = Math.max(previousLen + 1 + currentLen, maxLen);
		a >>>= 1;
	}
	return maxLen;
}
//O(b) time, sequence length b, O(1) memory