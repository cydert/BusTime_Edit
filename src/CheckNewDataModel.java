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

	void checkCompany(String fileName, String date) {
		switch (fileName) {
		case "Boucho":
			bcM = new BouchoM();
			bcM.serachNewDateList();

			break;
		}

	}
}

class BouchoM {
	private String topUrl = "http://www.bochobus.co.jp/";
	private String currentUrl;
	private String currentHtml;
	private String[] newDateSt;
	private String[] newDateUrl;

	BouchoM() {

	}

	void serachNewDateList() {
		currentUrl = topUrl;
		loadUrl();
		String tmpHtml = currentHtml;
		try {
			tmpHtml = Public.cutTwoStringSecond(tmpHtml, "（Route Bus/TimeTable）");
			tmpHtml = Public.cutTwoStringFirst(tmpHtml, "\">時刻表/路線図");
			tmpHtml = Public.cutTwoStringSecondL(tmpHtml, "<a href=\"");
			if (!tmpHtml.contains("htm"))
				Public.errorShow("html取得エラー 防長バス BouchoM searchNewDateList\n" + tmpHtml);
			setCurrentUrl(topUrl + tmpHtml);
		} catch (NullPointerException e) {
			// url取得エラー
			Public.errorShow("null html取得エラー 防長バス BouchoM searchNewDateList");
			e.printStackTrace();
			return;
		}
		// 時刻表リンク取得完了
		loadUrl();

		tmpHtml = currentHtml;
		tmpHtml= Public.cutTwoStringSecond(tmpHtml, "時刻表検索");
		tmpHtml = Public.cutTwoStringFirst(tmpHtml, "※ＰＤＦファイルです");
		//tmpHtml内に日付、urlあり

		String[] newDataHtml = tmpHtml.split("改正時刻");
		if(newDataHtml.length <= 1){
			//失敗
			Public.errorShow("BouchoM 防長バスの改正時刻が見つけれません\n" + tmpHtml);
			return;
		}else if(newDataHtml.length != 2){
			//TODO 複数ある
			Public.errorShow("改正時刻　複数");
		}else{
			//1つ
			newDateSt = new String[1];
			newDateSt[0] = Public.cutTwoStringSecondL(newDataHtml[0], "<b>");
			System.out.println("newDataSt:"+newDateSt[0]);
			//日付取得完了 String

			newDateUrl = new String[1];
			tmpHtml = Public.cutTwoStringSecond(newDataHtml[0], "<a href=\"");
			newDateUrl[0] = Public.cutTwoStringFirst(tmpHtml, "\"");

			currentUrl = Public.cutTwoStringFirstL(currentUrl, "/") + "/" +newDateUrl[0];
			currentUrl = Public.cutTwoStringFirstL(currentUrl, "/") + "/00.htm";
		}
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
}