class Node implements Comparable<Node> {
	int freq;
	byte val;
	String code;
	Node left, right;

	public Node() {
		code = "";
		left = null;
		right = null;
	}

	public Node(int freq, byte val) {
		this.freq = freq;
		this.val = val;
	}

	public int compareTo(Node otherNode) {
		return this.freq - otherNode.freq;
	}
}
