import javafx.application.Application;
import javafx.stage.Stage;

public class MainController extends Application {
	static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		MainView main = new MainView(primaryStage);

	}

	public static void toCheckNewDataView(){
		new CheckNewDataController(stage);
	}
	public static void toEditView(){

	}

	public static void main(String[] args) {
		launch(args);
	}
}
