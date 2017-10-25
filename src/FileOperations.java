import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FileOperations {
	private static String path;

	public FileOperations(String filePath) {
		path = filePath;
	}

	public void write(ArrayList<Byte> input) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			for (int i = 0; i < input.size(); i++)
				writer.write((byte) input.get(i));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public byte[] read() {
		byte[] getBytes = {};
		try {
			File file = new File(path);
			getBytes = new byte[(int) file.length()];
			InputStream is = new FileInputStream(file);
			is.read(getBytes);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getBytes;
	}

	public void writeAsInt(ArrayList<Integer> input) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			for (int i = 0; i < input.size(); i++) {
				writer.write((int) input.get(i));
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Integer> readAsInt() {
		ArrayList<Integer> getBytes;
		try {
			File file = new File(path);
			getBytes = new ArrayList<Integer>();
			BufferedReader scanner = new BufferedReader(new FileReader(file));
			while (scanner.ready()) {
				int cur = scanner.read();
				getBytes.add(cur);
			}
			scanner.close();
			return getBytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}