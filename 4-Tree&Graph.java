//4.1 Route Between Nodes
enum State {Visited, Unvisited, Visiting;}
boolean search(Graph g, Node start, Node end) {
	if (start == end) return true;

	LinkedList<Node> q = new LinkedList<Node>(); //operate as queue
    for (Node u : g.getNodes()) {
    	u.state = State.Unvisited;
    }
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
    		}
    		u.state = State.Visited;
    	}
    }
    return false;
}