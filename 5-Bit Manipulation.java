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

//5.4 Next Number
//get next largest number
int getNext(int n) {
	int c = n;
	int c0 = 0; //# of 0s to the right of flipping point
	int c1 = 0; //# of 1s to the right of flipping point
	while (((c & 1) == 0) && (c != 0)) { //???
		c0++;
		c >>= 1;
	}
	while ((c & 1) == 1) {
		c1++;
		c >>= 1;
	}
	/* Error: if n == 11...1100...00, then there is no bigger number with 
	the same number of 1s */
	if (c0 + c1 == 31 || c0 + c1 == 0) return -1;

	int p = c0 + c1; //find rightmost non-trailing 0(with 1 to its right)
	n |= 1 << p; //flip p
	n &= ~((1 << p) - 1); //clear all btis to the right of p
	n |= (1 << (c1 - 1)) - 1; //insert (c1 - 1) 1s to the right
	return n;
}
//get next smallest number
int getPrev(int n) {
	int temp = n;
	int c0 = 0;
	int c1 = 0;
	//c1 is # of trailing 1s, c0 is the size of the block of 0s
    //immediately to the left of the trailing 1s.
    while ((temp & 1) == 1) {
    	c1++;
    	temp >>= 1;
    }
    if (temp == 0) return -1; //trailing 1, no 0 exists
    while (((temp & 1) == 0) && (temp != 0)) {
    	c0++;
    	temp >>= 1;
    }

    int p = c0 + c1;
    n & = ((~0) << (p + 1)); //clear from bit p onwards
    int mask = (1 << (c1 + 1) - 1); //sequence of (c1 + 1) 1s
    n |= (mask << (c0 - 1));
    return n;
}

//5.6 Conversion
int bitSwap(int a, int b) {
	int count = 0;
	for (int c = a ^ b; c != 0; c = c >> 1) {
		count += c & 1; //if different, c == 1
	}
	/* simpler approach
	for (int c = a ^ b; c != 0; c = c & (c - 1)) {
		//continuously clear lst and count how long for c to reach 0???
		count++;
	} */
	return count;
}

//5.7 Pairwise Swap
int swap(int x) {
	return (((x & 0xaaaaaaaa) >>> 1) | ((x & 0x55555555) << 1));
}        //get odd bits, move to even.   get even bits, move to odd
//five instructions in total

//5.8 Draw Line
void drawLine(byte[] screen, int width, int x1, int x2, int y) {
	int start_offset = x1 % 8;
	int first_full_byte = x1 / 8;
	if (start_offset != 0) first_full_byte++;
	int end_offset = x2 % 8;
	int last_full_byte = x2 / 8;
	if (end_offset != 7) last_full_byte--;

	for (int b = first_full_byte; b <= last_full_byte; b++) {
		screen[(width / 8) * y + b] = (byte) 0xFF;
	} //set full bytes

	byte start_mask = (byte) (0xFF >> start_offset);
	byte end_mask = (byte) ~(0xFF >> (end_offset) + 1);

	//set start and end of the line
	if ((x1 / 8) == (x2 / 8)) { //x1 and x2 are in the same byte
		byte mask = (byte) start_mask & end_mask;
		screen[(width / 8) * y + x1 / 8] |= mask; //locate the byte
	} else {
		if (start_offset != 0) {
			int byte_number = (width / 8) * y + first_full_byte - 1;//revert
			screen[byte_number] |= start_mask;
		}
		if (end_offset != 7) {
			int byte_number = (width / 8) * y + last_full_byte + 1;//revert
			screen[byte_number] |= end_mask;
		}
	}
}
