import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CheckNewDataModel {
	BouchoM bcM;

	CheckNewDataModel() {

	}

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

	String[][] getCompanyNewDate(String fileName) {
		switch (fileName) {
		case "Boucho":
			bcM = new BouchoM();
			return bcM.serachNewDateList();
		}
		return null;
	}

	void dlBusTimeList(String url) {
		if (url.contains("bochobus")) {
			bcM.getPDF(url);
		}
	}

}

class BouchoM {
	private String topUrl = "http://www.bochobus.co.jp/";
	private String searchTopUrl;
	private String currentUrl;
	private String currentHtml;
	private String[][] newDate; // String, url

	BouchoM() {
		currentUrl = topUrl;
		currentHtml = Network.getHtml(currentUrl);
		String tmpHtml = currentHtml;
		try {
			tmpHtml = Public.cutTwoStringSecond(tmpHtml, "（Route Bus/TimeTable）");
			tmpHtml = Public.cutTwoStringFirst(tmpHtml, "\">時刻表/路線図");
			tmpHtml = Public.cutTwoStringSecondL(tmpHtml, "<a href=\"");
			if (!tmpHtml.contains("htm"))
				Public.errorShow("html取得エラー 防長バス BouchoM searchNewDateList\n" + tmpHtml);
			setCurrentUrl(topUrl + tmpHtml);
			searchTopUrl = currentUrl;
		} catch (NullPointerException e) {
			// url取得エラー
			Public.errorShow("null html取得エラー 防長バス BouchoM searchNewDateList");
			e.printStackTrace();
			return;
		}
		// 時刻表検索画面のURL取得
	}

	String[][] serachNewDateList() {
		setCurrentHtml(Network.getHtml(searchTopUrl));
		String tmpHtml = currentHtml;
		tmpHtml = currentHtml;
		tmpHtml = Public.cutTwoStringSecond(tmpHtml, "時刻表検索");
		tmpHtml = Public.cutTwoStringFirst(tmpHtml, "※ＰＤＦファイルです");
		// tmpHtml内に日付、urlあり

		String[] newDataHtml = tmpHtml.split("改正時刻");
		if (newDataHtml.length <= 1) {
			// 失敗
			Public.errorShow("BouchoM 防長バスの改正時刻が見つけれません\n" + tmpHtml);
			return null;
		} else {
			// TODO 複数ある
			newDate = new String[newDataHtml.length - 1][2];

			for (int i = 0; i < newDataHtml.length - 1; i++) {
				newDate[i][0] = Public.cutTwoStringSecondL(newDataHtml[i], "<b>"); // 改正時刻を取得

				tmpHtml = Public.cutTwoStringSecond(newDataHtml[i], "<a href=\""); // 改正時刻先のURL
				newDate[i][1] = Public.cutTwoStringFirst(tmpHtml, "\">");
				newDate[i][1] = Public.cutTwoStringFirstL(searchTopUrl, "/") + "/" + newDate[i][1];
				newDate[i][1] = Public.cutTwoStringFirstL(newDate[i][1], "/") + "/00.htm";
			}
		}
		return newDate;
	}

	void loadUrl() {
		currentHtml = Network.getHtml(currentUrl);
	}

	void setTopUrl(String url) {
		topUrl = url;
	}

	void setCurrentUrl(String url) {
		currentUrl = url;
	}

	void setCurrentHtml(String html) {
		currentHtml = html;
	}

	void getPDF(String url) {
		currentHtml = Network.getHtml(url);
		String hostUrl = Public.cutTwoStringFirstL(url, "/") + "/";
		String dlList[] = currentHtml.split("<a href=\"");
		String fileName;
		String tmpUrl;
		for (int i = 1; i < dlList.length; i++) {
			tmpUrl = hostUrl + Public.cutTwoStringFirst(dlList[i], "\"");
			fileName = Public.cutTwoStringSecondL(tmpUrl, "/");
			if (fileName.contains(".pdf")) {
				Network.saveFile(tmpUrl, Public.bouchoPdfPath + "\\" + fileName);
			}
		}
		Public.infoShow("DL完了しました");
	}
}