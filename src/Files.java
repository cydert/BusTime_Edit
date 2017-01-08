import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Files {
	public static void WriteData(String path, String data) {
		File file = new File(path);
		try {
			PrintWriter pw = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "unicode")));
			pw.println(data);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void WriteData(String path, String[] data) {
		File file = new File(path);
		try {
			PrintWriter pw = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "unicode")));
			for (int i = 0; i < data.length; i++)
				pw.println(data[i]);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 追記
	public static void WriteDataAdd(String path, String data) {
		File file = new File(path);
		try {
			PrintWriter pw = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "unicode")));
			pw.println(data);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<String> readData(String path){
		File file = new File(path);
		ArrayList<String> ar = new ArrayList<>();
		try{
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "unicode"));
			String tmp;
			while((tmp = bfr.readLine()) != null){
				ar.add(tmp);
			}
			bfr.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return ar;
	}
}
