import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EditView {
	private Stage stage;
	private TextArea[] txArea;
	private Button[] button;

	EditView(Stage stage){
		this.stage = stage;
		GridPane grid = new GridPane();

		String[] listSt = {"時間[時]","時間[分]","経由地","終点","乗り場所","(特殊運行のマーク)"};
		Label[] txF = new Label[listSt.length];
		txArea = new TextArea[listSt.length];	//入力欄
		for(int i=0; i<listSt.length; i++){
			txF[i] = new Label(listSt[i]);
			grid.add(txF[i], i, 0);
			txArea[i] = new TextArea();
			grid.add(txArea[i], i, 1);
		}

		button = new Button[2];
		//TODO
		stage.setScene(new Scene(grid));


	}


}
