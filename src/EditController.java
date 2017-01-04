import javafx.stage.Stage;

public class EditController {
	private Stage stage;
	private EditView view;
	private EditModel model;
	public EditController(Stage stage) {
		this.stage = stage;
		view = new EditView(stage);
		model = new EditModel();
	}
	
}
