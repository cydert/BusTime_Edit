import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckNewDataModel {
	ArrayList<String> getCompanysInfo() {
		String fileName[] = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
		String filePath = Public.rootPath + "BusCompany";

		File file = new File(filePath);
		if (file.exists()) { // フォルダ存在していたら
			for (int i = 0; i < fileName.length; i++) {
				String tmpPath = filePath + "\\" + fileName[i] + ".txt"; // 読み込み元
				String tmp;
				ArrayList<String> text = new ArrayList<>(); // 全文
				try {
					BufferedReader bfr = new BufferedReader(
							new InputStreamReader(new FileInputStream(tmpPath), "unicode"));
					while ((tmp = bfr.readLine()) != null) {
						text.add(tmp);
					}
					return text;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("ファイル未存在");
		return null;
	}

	void checkCompany(String company, String date) {
		switch (company) {
		case "防長バス":
			String hostUrl;
			String html = Network.getHtml("http://www.bochobus.co.jp/");
			String[] tmpAr = html.split("\">時刻表/路線図")[0].split("<a href=\"");
			html = tmpAr[tmpAr.length - 1];

			html = Network.getHtml("http://www.bochobus.co.jp/");
			int first = html.indexOf("時刻表検索");
			int end = html.indexOf("※ＰＤＦファイルです");
			if (first == -1 || end == -1) {

			}
			html = html.substring(first, end);

			break;

		default:
			break;
		}
	}
}
