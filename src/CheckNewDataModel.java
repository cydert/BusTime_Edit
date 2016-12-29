import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckNewDataModel {
	BouchoM bcM;

	CheckNewDataModel() {
		bcM = new BouchoM();
	}

	//会社名一覧　取得
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

	void checkCompany(String fileName, String date) {
		System.out.println("check");
		switch (fileName) {
		case "Boucho":
			bcM.serachNewDateList();
			break;
		}

	}

	// 文字分割
	String cutTwoStringFirst(String text, String cutString) {
		int tmp = text.indexOf(cutString);
		if (tmp == -1)
			return null;
		return text.substring(0, tmp);

	}

	String cutTwoStringSecond(String text, String cutString) {
		int tmp = text.indexOf(cutString);
		if (tmp == -1)
			return null;
		tmp += cutString.length();
		return text.substring(tmp);

	}

	// 後ろから
	String cutTwoStringFirstL(String text, String cutString) {
		int tmp = text.lastIndexOf(cutString);
		if (tmp == -1)
			return null;
		return text.substring(0, tmp);

	}

	String cutTwoStringSecondL(String text, String cutString) {
		int tmp = text.lastIndexOf(cutString);
		if (tmp == -1)
			return null;
		tmp += cutString.length();
		return text.substring(tmp);

	}
}
