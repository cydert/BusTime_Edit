import java.util.ArrayList;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class CheckNewDataController{
	private CheckNewDataView cndV;
	private CheckNewDataModel cndM;

	public CheckNewDataController(Stage stage) {
		cndV = new CheckNewDataView(stage);
		cndM = new CheckNewDataModel();
		cndV.bindModel(cndM);


		//更新確認ボタンが押されたら
		if (cndV.getOptionalButton().isPresent() && cndV.getOptionalButton().get() == ButtonType.OK) {
			toSearch();
		}


	}
	public void toSearch(){
		ArrayList<String> infoAr = cndM.getCompanysInfo();
		if(infoAr != null){

		}else{
			Public.errorShow("記述されたファイルが見つかりません。");
		}
	}
	public void error(String massage){
		Public.errorShow(massage);
	}
}
