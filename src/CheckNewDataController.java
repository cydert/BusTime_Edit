import javafx.stage.Stage;

public class CheckNewDataController extends MainController{
	private static CheckNewDataView cdv;
	public CheckNewDataController(Stage stage) {
		cdv = new CheckNewDataView(stage);
	}
	public static void toSearch(){
		new CheckNewDataModel();
	}
	public static void error(String massage){
		cdv.errorShow(massage);
	}
}
