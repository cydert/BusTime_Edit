import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainView extends MainController{
	MainView(Stage primaryStage){
		VBox vbox = new VBox();
		primaryStage.setWidth(800);
		primaryStage.setHeight(500);
		primaryStage.setTitle("BusTime edit");


		Button[] btn = new Button[2];
		btn[0] = new Button("データ編集");
		btn[1] = new Button("更新確認");
		btn[0].setFont(new Font(30));
		btn[1].setFont(new Font(30));
		btn[0].setOnAction(event -> MainController.toEditView());
		btn[1].setOnAction(event -> MainController.toCheckNewDataView());


		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(btn);
		vbox.setSpacing(30);

		primaryStage.setScene(new Scene(vbox));
		primaryStage.show();
	}
}
