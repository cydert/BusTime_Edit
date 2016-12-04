
import javafx.stage.Stage;

public class MainController {
	private MainView mv;
	private Stage stage;
	public MainController(Stage stage) {
		this.stage = stage;
		mv = new MainView(stage);


		mv.getButton(0).setOnAction(e -> toEdit());
		mv.getButton(1).setOnAction(e -> toCheckNewData());
	}

	void toEdit(){

	}

	void toCheckNewData(){
		new CheckNewDataController(stage);
	}
}
