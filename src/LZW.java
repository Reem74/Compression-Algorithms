import java.util.ArrayList;
import java.util.Hashtable;

public class LZW {

	public static void compress(String file) {
		byte[] input = new FileOperations(file).readBits();
		Hashtable<String, Integer> prefixs = new Hashtable<>();
		int lastPreNum = 0;
		for (char i = 0; i <= 256; i++) {
			prefixs.put("" + i, lastPreNum++);
		}
		ArrayList<Integer> ret = new ArrayList<Integer>();
		String tmp = new String("");
		for (int i = 0; i < input.length; i++) {
			if (i + 1 == input.length && prefixs.containsKey(tmp + (char) input[i])) {
				ret.add(prefixs.get(tmp + (char) input[i]));
			} else if (!prefixs.containsKey(tmp + (char) input[i])) {
				prefixs.put((tmp + (char) input[i]), lastPreNum++);
				ret.add(prefixs.get(tmp));
				tmp = "";
				i--;
				continue;
			}
			tmp = tmp + (char) input[i];
		}
		writeOnFile(ret, "/home/reem/workspaces/workspace_java/Compression-Tool/compressed.lz77");
	}

	private static void writeOnFile(ArrayList<Integer> data, String file) {
		FileOperations fio = new FileOperations(file);
		fio.writeInt(data);
	}

	public static void decompress() {
		ArrayList<Integer> data = readFromFile("/home/reem/workspaces/workspace_java/Compression-Tool/compressed.lz77");
		Hashtable<Integer, String> prefixs = new Hashtable<>();
		int lastPreNum = 0;
		for (char i = 0; i <= 256; i++)
			prefixs.put(lastPreNum++, "" + i);

		ArrayList<String> output = new ArrayList<String>();
		for (int i = 0; i < data.size(); i++) {
			if (prefixs.containsKey(data.get(i))) {
				if (output.size() > 0) {
					prefixs.put(lastPreNum++, output.get(output.size() - 1) + prefixs.get(data.get(i)).charAt(0));
				}
				output.add(prefixs.get(data.get(i)));
			} else {
				if (output.size() > 0) {
					prefixs.put(lastPreNum++, output.get(output.size() - 1) + output.get(output.size() - 1).charAt(0));
					output.add(output.get(output.size() - 1) + output.get(output.size() - 1).charAt(0));
				}
			}
		}

		ArrayList<Integer> ret = new ArrayList<Integer>();
		for (int i = 0; i < output.size(); i++) {
			for (int j = 0; j < output.get(i).length(); j++) {
				ret.add((int) output.get(i).charAt(j));
			}
		}
		writeOnFile(ret, "/home/reem/workspaces/workspace_java/Compression-Tool/decompressed.txt");
	}

	private static ArrayList<Integer> readFromFile(String file) {
		FileOperations fio = new FileOperations(file);
		return fio.readInt();
	}

}
