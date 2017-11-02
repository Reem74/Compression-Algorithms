import java.util.ArrayList;

public class LZ77 {
	public static final int windowSize = 126;

	public static void compress(String file) {
		byte[] input = readFromFile(file);

		ArrayList<Byte> ret = new ArrayList<Byte>();
		for (int i = 0; i < input.length; i++) {
			int mxLen = 0, beg = i;
			for (int j = Math.max(i - windowSize, 0); j < i; j++) {
				int tmpOfI = i, len = 0;
				for (int k = j; k < input.length && tmpOfI < input.length && k < tmpOfI && len < windowSize + 1; k++) {
					len++;
					if (input[tmpOfI] != (input[k]))
						break;
					tmpOfI++;
				}
				if (len > mxLen + 1 || len == mxLen + 1 && beg < j) {
					beg = j;
					mxLen = len - 1;
				}
			}
			ret.add((byte) (i - beg));
			ret.add((byte) mxLen);
			i += mxLen;
			if (i + mxLen != input.length) {
				ret.add((byte) input[i]);
			} else {
				ret.add((byte) ' ');
			}
		}
		writeOnFile(ret, "/home/reem/workspaces/workspace_java/Compression-Tool/compressed.lz77");
	}

	private static void writeOnFile(ArrayList<Byte> data, String file) {
		FileOperations fio = new FileOperations(file);
		fio.writeBits(data);
	}

	public static void decompress() {
		byte[] data = readFromFile("/home/reem/workspaces/workspace_java/Compression-Tool/compressed.lz77");
		ArrayList<Byte> output = new ArrayList<Byte>();
		for (int i = 0; i < data.length; i += 3) {
			if (data[i] == 0) {
				if (data[i + 2] != ' ')
					output.add(data[i + 2]);
				continue;
			}
			int tmpOfI = output.size() - data[i];
			for (int j = tmpOfI; j < tmpOfI + data[i + 1]; j++) {
				output.add(output.get(j));
			}
			if (data[i + 2] != ' ')
				output.add(data[i + 2]);
		}
		writeOnFile(output, "/home/reem/workspaces/workspace_java/Compression-Tool/decompressed.txt");
	}

	private static byte[] readFromFile(String file) {
		FileOperations fio = new FileOperations(file);
		return fio.readBits();
	}

}
