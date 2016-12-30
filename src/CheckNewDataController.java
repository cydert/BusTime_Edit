import java.util.ArrayList;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CheckNewDataController {
	private CheckNewDataView cndV;
	private CheckNewDataModel cndM;

	public CheckNewDataController(Stage stage) {
		cndV = new CheckNewDataView(stage);
		cndM = new CheckNewDataModel();
		cndV.bindModel(cndM);

		// 更新確認ボタンが押されたら
		if (cndV.getOptionalButton().isPresent() && cndV.getOptionalButton().get() == ButtonType.OK) {
			toSearch();
		}

	}

	public void toSearch() {
		ArrayList<String> infoAr = cndM.getCompanysInfo(); // 会社一覧

		if (infoAr != null) {
			ArrayList<String> dateInfoAr = new ArrayList<>();
			ArrayList<String> dateInfoUrlAr = new ArrayList<>();
			for (int i = 0; i < infoAr.size(); i++) {
				String fileName = infoAr.get(i).split(",")[1];
				String date = infoAr.get(i).split(",")[3];
				String[][] infoTmp = cndM.getCompanyNewDate(fileName);
				for (int j = 0; j < infoTmp.length; j++) {
					dateInfoAr.add(infoTmp[i][0] + "\t\t作成ファイル:" + fileName + ",\t 更新日:" + date);
					dateInfoUrlAr.add(infoTmp[i][1]);
				}

			}
			ListView<String> dlList = cndV.showDlList(dateInfoAr);
			dlList.setOnMouseClicked(e -> select(e, dlList, dateInfoUrlAr));
		} else {
			Public.errorShow("記述されたファイルが見つかりません。");
		}
	}

	private void select(MouseEvent e, ListView<String> list, ArrayList<String> urlAr) {
		// ダブルクリックされたら
		boolean doubleClick = e.getButton().equals(MouseButton.PRIMARY) && (e.getClickCount() == 2);
		if (doubleClick) {
			int selectId = list.getSelectionModel().getSelectedIndex();// 選択されたもの
			cndM.dlBusTimeList(urlAr.get(selectId));
			cndV.closeDlList(); // Window閉じる
		}
	}

	public void error(String massage) {
		Public.errorShow(massage);
	}
}
