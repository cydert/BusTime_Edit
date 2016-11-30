import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class CheckNewDataView extends MainController {
	public CheckNewDataView(Stage stage) {
		// TODO 自動生成されたコンストラクター・スタブ
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("確認");
		alert.setHeaderText("データ更新の確認をします");
		alert.setContentText("BusCompanyのフォルダを\n" + System.getProperty("user.dir") + "\nに設置してからOKを選択してください");
		Optional<ButtonType> isCheck = alert.showAndWait();
		if (isCheck.isPresent() && isCheck.get() == ButtonType.OK) {
			CheckNewDataController.toSearch();
		}
	}

	public void errorShow(String msg){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("エラー");
		alert.setContentText(msg);
	}



}
