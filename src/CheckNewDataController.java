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
		System.out.println("toSerarch");
		ArrayList<String> infoAr = cndM.getCompanysInfo();

		if(infoAr != null){
			for(int i=0; i<infoAr.size(); i++){
				String fileName = infoAr.get(i).split(",")[1];
				String date = infoAr.get(i).split(",")[3];
				cndM.checkCompany(fileName, date);
			}
		}else{
			Public.errorShow("記述されたファイルが見つかりません。");
		}
	}
	public void error(String massage){
		Public.errorShow(massage);
	}
}
