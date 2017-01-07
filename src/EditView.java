import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditView {
	private Stage stage;
	private Stage listStage;
	private EditModel model;
	private BorderPane root;
	private GridPane centerGrid;
	private BorderPane bottomBorder;

	private TextArea[] txArea;
	private Button[] button;
	private MenuBar menuBar;
	private Button[] extButton;
	private ComboBox<String> extListBox;

	EditView(Stage stage, EditModel model) {
		this.stage = stage;
		this.model = model;
	}

	public void showView() {
		root = new BorderPane();
		HBox topBox = new HBox();
		centerGrid = new GridPane();
		bottomBorder = new BorderPane();
		int gridI = 0;

		menuBar = new MenuBar();
		Menu[] menu = new Menu[2];
		menu[0] = new Menu("ファイル");
		menu[1] = new Menu("編集");

		MenuItem[][] item = new MenuItem[2][5];
		item[0][0] = new MenuItem("会社名ファイルを開く");
		item[0][1] = new MenuItem("会社ファイル新規作成");
		item[0][2] = new MenuItem("トップ画面に戻る");
		item[0][3] = new MenuItem("終了");
		item[1][0] = new MenuItem("テンプレートの作成");
		item[1][1] = new MenuItem("テンプレートの編集");
		item[1][2] = new MenuItem("式の作成");
		item[1][3] = new MenuItem("式の編集");

		topBox.setAlignment(Pos.CENTER);
		topBox.setSpacing(10);
		TextField busNameTx = new TextField();
		topBox.getChildren().addAll(new Label("表示バス停名:"), busNameTx);

		String[] listSt = { "時間[時]", "時間[分]", "経由地", "終点", "乗り場所", "(特殊運行のマーク)" };
		Label[] txF = new Label[listSt.length];
		txArea = new TextArea[listSt.length]; // 入力欄
		for (int i = 0; i < listSt.length; i++) {
			txF[i] = new Label(listSt[i]);
			centerGrid.add(txF[i], i, gridI);
			txArea[i] = new TextArea();
			centerGrid.add(txArea[i], i, gridI + 1);
		}
		gridI += 2;

		button = new Button[2]; // TODO
		button[1] = new Button("保存");
		BorderPane bottomGrid = new BorderPane();
		// bottomGrid.setLeft(button[0]);
		bottomGrid.setRight(button[1]);
		bottomBorder.setBottom(bottomGrid);

		root.setTop(topBox);
		root.setCenter(centerGrid);
		root.setBottom(bottomBorder);
		stage.setScene(new Scene(root));
	}

	public void showExtMode() {
		extButton = new Button[3];
		extButton[0] = new Button("←");
		extButton[1] = new Button("→");
		extButton[2] = new Button("抽出");

		extListBox = new ComboBox<>();
		extListBox.getItems().addAll(model.getExtList());
		HBox topBox = new HBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.getChildren().addAll(extButton[0], extListBox, extButton[1], extButton[2]);
		bottomBorder.setTop(topBox);
	}

	public ListView<String> showList(ArrayList<String> itemList) {
		// window初期設定
		listStage = new Stage();
		listStage.setWidth(700);
		listStage.setHeight(600);
		listStage.setTitle("ファイルを選択");
		listStage.initModality(Modality.APPLICATION_MODAL);// 他画面選択不可
		listStage.show();

		VBox root = new VBox();

		ListView<String> listView = new ListView<>();// リストビュー
		listView.setEditable(false); // 編集不可
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // 単体のみ選択可

		root.getChildren().addAll(listView);
		listStage.setScene(new Scene(root));// 表示

		ObservableList<String> list = FXCollections.observableArrayList(itemList);// 一覧内容
		listView.setItems(list);
		return listView;
	}

	public Button[] getExtButton(){
		return extButton;
	}
	public ComboBox<String> getExtListBox(){
		return extListBox;
	}

	public void closeListV() {
		listStage.hide();
	}
}
