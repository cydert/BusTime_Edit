import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Public {
	public static String rootPath = "data\\";
	public static void errorShow(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("エラー");
		alert.setContentText(msg);
	}
}
