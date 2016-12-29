import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage stage;
	private MainView mv;
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		mv = new MainView(stage);

		mv.getButton(0).setOnAction(e -> toEdit());
		mv.getButton(1).setOnAction(e -> toCheckNewData());
	}
	public static void main(String[] args) {
		launch(args);
	}

	void toCheckNewData(){
		new CheckNewDataController(stage);
	}

	void toEdit(){

	}
}
