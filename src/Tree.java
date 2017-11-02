import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Tree {
	Node root;
	PriorityQueue<Node> Nodes = new PriorityQueue<Node>();
	HashMap<String, Node> codedNodes = new HashMap<String, Node>();
	int visitLetters[] = new int[266];

	public Tree() {
		Arrays.fill(visitLetters, 0);
	}

	public void buildFrequencies(byte[] input) {
		for (int i = 0; i < input.length; i++) {
			visitLetters[input[i]]++;
		}
	}

	public void buildFrequenciesNodes() {
		for (int i = 0; i <= 256; i++) {
			if (visitLetters[i] > 0) {
				Node newNode = new Node(visitLetters[i], (byte) i);
				Nodes.add(newNode);
			}
		}
	}

	public void buildTree() {
		while (Nodes.size() > 1) {
			Node newNode = new Node();
			newNode.right = Nodes.remove();
			newNode.left = Nodes.remove();
			newNode.freq = newNode.right.freq + newNode.left.freq;
			Nodes.add(newNode);
		}
		if (Nodes.size() == 1) {
			root = Nodes.remove();
		}
	}

	public void dfs(Node Pcrawl, String code, int flag) {
		if (Pcrawl.right == null) {
			Pcrawl.code = code;
			if (flag == 1) {
				codedNodes.put(Pcrawl.code, Pcrawl);
			} else {
				codedNodes.put("" + (char) Pcrawl.val, Pcrawl);
			}
			return;
		}
		dfs(Pcrawl.left, code + '0', flag);
		dfs(Pcrawl.right, code + '1', flag);
	}

	public void buildCodes(int flag) {
		dfs(root, "", flag);
	}
}
