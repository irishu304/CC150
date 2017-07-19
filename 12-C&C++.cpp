//12.1 Last K Lines
void printLast10Lines(char* filename) {
	const int K = 10;
	ifstream file(filename); //input file stream
	string L[K];
	int size = 0;

	//read file line by line into circular array 
    while(file.peek() != EOF) { //check if null or at the end
    	getline(file, L[size % K]); 
    	//receive one line from file and put it in L[]
    	size++; //count on line # in file
    }
    
    //compute start of circular array, and the size of array
    int start = size > K ? size % K : 0; //index to track the oldest
    int count = min(K, size);
    
    //print elements in the order they were read
    for (int i = 0; i < count; i++) {
    	cout << L[(start + i) % K] << endl;
    }
}

//12.2 Reverse String in C
void reverse(char *str) {
	char* end = str; //a pointer to str
	char temp;
	if (str) {
		while(*end) { //find string end
			++end;
		}
		--end; //last char is null, set one char back
	}

	while(str < end) {
		temp = *str;
		*str = *end;
		*end = temp;
		str++;
		end--;
	}
}

//12.8 Copy Node
typedef map<Node*, Node*> NodeMap;
Node * copy_recursive(Node * cur, NodeMap & nodeMap) {
	if (cur == NULL) {
		return NULL;
	}

	NodeMap::iterator i = nodeMap.find(cur);
	if(i != Map.end()) {
		return i -> second;
	} //been here before, return the copy

	Node * node = new Node;
	nodeMap[cur] = node; //map current before traversing links
	node -> ptr1 = copy_recursive(cur -> ptr1, nodeMap);
	node -> ptr2 = copy_recursive(cur -> ptr2, nodeMap);
	return node;
}

Node * copy_structure(Node * root) {
	NodeMap nodeMap; //need an empty map
	return copy_recursive(root, nodeMap);
}

//12.9 Smart Pointer
template <class T> class SmartPointer {
	/* The smart pointer class needs pointers to both the object itself 
	and to the ref count. These must be pointers, rather than the actual 
	object or ref count value */
	public:
		SmartPointer(T * ptr) {
			ref = ptr;
			ref_count = (unsigned)malloc(sizeof(unsigned));
			*ref_count = 1;
		}
		SmartPointer(SmartPointer<T> & sptr) {
			ref = sptr.ref;
			ref_count = sptr.ref_count;
			++(*ref_count);
		}
		SmartPointer<T> & operator = (SmartPointer<T> & sptr) {
			if (this = &sptr) return *this;
			if (*ref_count > 0) {
				remove();
			} //If already assigned to an object, remove one reference
			ref = sptr.ref;
			ref_count = sptr.ref_count;
			++(*ref_count);
			return *this;
		}
		~SmartPointer() {
			remove(); //remove reference to an object
		}
		T getValue() {
			return *ref;
		}

	protected:
		void remove() {
			--(*ref_count);
			if (*ref_count == 0) {
				delete ref;
				free(ref_count);
				ref = NULL;
				ref_count = NULL;
			}
		}

	T * ref;
	unsigned * ref_count;
};

//12.10 Malloc
void* aligned_malloc(size_t required_bytes, size_t alignment) {
	void* p1; //initial block
	void* p2; //aligned block inside initial block
	int offset = alignment - 1 + sizeof(void*);
	//guarantee both an aligned address and space for this pointer
	if ((p1 = (void*)malloc(required_bytes + offset)) == NULL) {
		return NULL;
	}
	p2 = (void*)(((size_t)(p1) + offset) & ~(alignment - 1));
	//acquire the address within extra bytes that is divisible by alignment
	((void **)p2)[-1] = p1;
	return p2;
}

void* aligned_free(void *p2) {
	void* p1 = ((void**)p2)[-1];
	free(p1);
}

//12.11 2D Alloc
int** my2DAlloc(int rows, int cols) {
	int i;
	int header = rows * sizeof(int);
	int data = rows * cols * sizeof(int);
	int** rowptr = (int**)malloc(header + data);
	if (rowptr == NULL) return NULL;

	int* buf = (int*) (rowptr + rows);
	for (i = 0; i < rows; i++) {
		rowptr[i] = buf + i * cols;
	}
	return rowptr;
}
