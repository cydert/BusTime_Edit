
public class BouchoM {
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
			//複数ある

		}else{
			//1つ
			newDateSt = new String[1];
			newDateSt[0] = Public.cutTwoStringSecondL(newDataHtml[0], "<b>");
			//日付取得完了 String

			newDateUrl = new String[1];
			tmpHtml = Public.cutTwoStringSecond(newDataHtml[0], "<a href=\"");
			newDateUrl[0] = Public.cutTwoStringFirst(tmpHtml, "\"");
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
