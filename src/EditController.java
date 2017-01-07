import javafx.scene.control.ComboBox;
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
		model = new EditModel();
		view = new EditView(stage, model);

		listV = view.showList(model.getCompanysInfo());
		listV.setOnMouseClicked(e -> select(e, listV));
	}

	private void select(MouseEvent e, ListView<String> list) {
		// ダブルクリックされたら
		boolean doubleClick = e.getButton().equals(MouseButton.PRIMARY) && (e.getClickCount() == 2);
		if (doubleClick) {
			int selectId = list.getSelectionModel().getSelectedIndex();// 選択されたもの
			String item = list.getSelectionModel().getSelectedItem();
			if (item.contains("Boucho")) {
				model.setPath(Public.rootPath + "\\Boucho");
				model.check(); // ファイルが正常にあるか
				model.setExtPath(Public.bouchoPdfPath);
				model.setInfo();
				view.showView();
				view.showExtMode();
				view.getExtButton()[0].setOnAction(ev -> changeNextBox(false));
				view.getExtButton()[1].setOnAction(ev -> changeNextBox(true));
				view.getExtButton()[2].setOnAction(ev -> extruct());

			}
			view.closeListV(); // Window閉じる
		}
	}

	private void extruct() {
		ComboBox<String> box = view.getExtListBox();
		int selectId = box.getSelectionModel().getSelectedIndex();
		if (selectId == -1)
			return;
		String[] exList = model.getExtList();
		
		String filePath = Public.bouchoPdfPath + "\\" + exList[selectId];
		

	}

	private void changeNextBox(boolean right) {
		ComboBox<String> tmpBox = view.getExtListBox();
		int index = tmpBox.getSelectionModel().getSelectedIndex();
		if (right)
			index++;
		else
			index--;
		tmpBox.getSelectionModel().select(index);
	}
}
