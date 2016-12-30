import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CheckNewDataView {
	private CheckNewDataModel cndM;
	private Optional<ButtonType> isCheck;
	private Stage dlListStage;

	public CheckNewDataView(Stage stage) {
		// TODO 自動生成されたコンストラクター・スタブ
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("確認");
		alert.setHeaderText("データ更新の確認をします");
		alert.setContentText("BusCompanyのフォルダを\n" + System.getProperty("user.dir") + "\nに設置してからOKを選択してください");
		isCheck = alert.showAndWait();
	}

	public ListView<String> showDlList(ArrayList<String> itemList) {
		// window初期設定
		dlListStage = new Stage();
		dlListStage.setWidth(700);
		dlListStage.setHeight(600);
		dlListStage.setTitle("ファイルを選択");
		dlListStage.initModality(Modality.APPLICATION_MODAL);// 他画面選択不可
		dlListStage.show();

		VBox root = new VBox();

		ListView<String> listView = new ListView<>();// リストビュー
		listView.setEditable(false); // 編集不可
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // 単体のみ選択可

		root.getChildren().addAll(listView);
		dlListStage.setScene(new Scene(root));// 表示

		ObservableList<String> list = FXCollections.observableArrayList(itemList);// 一覧内容
		listView.setItems(list);
		return listView;
	}
	public void closeDlList(){
		dlListStage.hide();
	}

	public Optional<ButtonType> alertConfi(String hdrMsg, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("確認");
		alert.setHeaderText(hdrMsg);
		alert.setContentText(msg);
		return alert.showAndWait();
	}

	Optional<ButtonType> getOptionalButton() {
		return isCheck;
	}

	void bindModel(CheckNewDataModel cndM) {
		this.cndM = cndM;
	}

}
