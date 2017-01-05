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

public class EditModel {
	private String path;

	// 会社名一覧 取得
	ArrayList<String> getCompanysInfo() {
		String fileName[] = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
		String filePath = Public.rootPath + "\\BusCompany";
		File file = new File(filePath);
		if (file.exists()) { // フォルダ存在していたら
			ArrayList<String> text = new ArrayList<>(); // 全文
			for (int i = 0; i < fileName.length; i++) {
				String tmpPath = filePath + "\\" + fileName[i] + ".txt"; // 読み込み元
				String tmp;
				try {
					BufferedReader bfr = new BufferedReader(
							new InputStreamReader(new FileInputStream(tmpPath), "unicode"));
					while ((tmp = bfr.readLine()) != null) {
						text.add(tmp);
					}
					bfr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return text;
		} else {
			System.out.println("ファイル未存在");
			return null;
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void check() {
		String path = this.path + "\\" + Public.dataDirName;
		File file = new File(path);
		file.mkdirs();
		path = this.path + "\\" + Public.nameListDirName;
		file = new File(path);
		file.mkdirs();

		String fileName[] = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
		try {
			PrintWriter pw;
			for (int i = 0; i < fileName.length; i++) {
				pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(path +"\\"+ fileName[i] + ".txt"), "Unicode")));
				pw.flush();
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
