import java.util.ArrayList;

public class Huffman {
	private static String compFile = "/home/reem/workspaces/workspace_java/Compression-Tool/compressed.lz77";
	private static String decompFile = "/home/reem/workspaces/workspace_java/Compression-Tool/decompressed.txt";

	private static ArrayList<Integer> readIIntInput(String file) {
		FileOperations fio = new FileOperations(file);
		return fio.readInt();
	}

	private static void writeOutput(ArrayList<Byte> data, String file) {
		FileOperations fio = new FileOperations(file);
		fio.writeBits(data);
	}

	private static void writeIntOutput(ArrayList<Integer> data, String file) {
		FileOperations fio = new FileOperations(file);
		fio.writeInt(data);
	}

	private static byte[] readInput(String file) {
		FileOperations fio = new FileOperations(file);
		return fio.readBits();
	}

	public static void compress(String file) {
		byte[] input = readInput(file);
		Tree comp = new Tree();
		comp.buildFrequencies(input);
		comp.buildFrequenciesNodes();
		comp.buildTree();
		comp.buildCodes(0);
		ArrayList<Integer> output = new ArrayList<Integer>();
		for (int i = 0; i < input.length; i++) {
			Node newNode = comp.codedNodes.get("" + (char) input[i]);
			String code = newNode.code;
			for (int j = 0; j < code.length(); j++) {
				output.add((int) code.charAt(j) - '0');
			}
		}
		for (char i = 0; i <= 256; i++) {
			if (comp.codedNodes.containsKey("" + i)) {
				Node newNode = comp.codedNodes.get("" + i);
				output.add(newNode.freq + 1);
				output.add((int) i);
			}
		}
		writeIntOutput(output, compFile);
	}

	public static ArrayList<Byte> createOutputForDecompress(ArrayList<Byte> input, Tree decomp) {
		ArrayList<Byte> output = new ArrayList<Byte>();
		String crawl = new String();
		for (int i = 0; i < input.size(); i++) {
			crawl += (input.get(i));
			if (decomp.codedNodes.containsKey(crawl)) {
				Node newNode = decomp.codedNodes.get(crawl);
				crawl = "";
				output.add(newNode.val);
			}
		}
		return output;
	}

	public static void decompress() {
		ArrayList<Integer> tmpInput = readIIntInput(compFile);
		ArrayList<Byte> input = new ArrayList<Byte>();
		Tree decomp = new Tree();
		for (int i = 0, flag = 0; i < tmpInput.size(); i++) {
			if (tmpInput.get(i) > 1 || flag == 1) {
				flag = 1;
				decomp.visitLetters[tmpInput.get(i + 1)] = tmpInput.get(i);
				i++;
			} else {
				input.add((byte) tmpInput.get(i).intValue());
			}
		}
		decomp.buildFrequenciesNodes();
		decomp.buildTree();
		decomp.buildCodes(1);
		ArrayList<Byte> output = createOutputForDecompress(input, decomp);
		writeOutput(output, decompFile);
	}
}
