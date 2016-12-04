import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class CheckNewDataView{
	private CheckNewDataModel cndM;
	private Optional<ButtonType> isCheck;
	public CheckNewDataView(Stage stage) {
		// TODO 自動生成されたコンストラクター・スタブ
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("確認");
		alert.setHeaderText("データ更新の確認をします");
		alert.setContentText("BusCompanyのフォルダを\n" + System.getProperty("user.dir") + "\nに設置してからOKを選択してください");
		isCheck = alert.showAndWait();
	}

	Optional<ButtonType> getOptionalButton(){
		return isCheck;
	}



	void bindModel(CheckNewDataModel cndM){
		this.cndM = cndM;
	}


}
