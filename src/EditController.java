import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditController {
	private Stage stage;
	private EditView view;
	private EditModel model;

	private ListView<String> listV;
	public EditController(Stage stage) {
		this.stage = stage;
		view = new EditView(stage);
		model = new EditModel();
		listV = view.showList(model.getCompanysInfo());
		listV.setOnMouseClicked(e -> select(e,listV));
	}

	private void select(MouseEvent e, ListView<String> list) {
		// ダブルクリックされたら
		boolean doubleClick = e.getButton().equals(MouseButton.PRIMARY) && (e.getClickCount() == 2);
		if (doubleClick) {
			int selectId = list.getSelectionModel().getSelectedIndex();// 選択されたもの
			String item = list.getSelectionModel().getSelectedItem();
			if(item.contains("Boucho")){
				model.setPath(Public.rootPath + "\\Boucho");
				model.check();
				view.showView();
			}
			view.closeListV(); // Window閉じる
		}
	}

}
