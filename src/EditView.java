import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
	private VBox centerBox;
	private GridPane centerGrid;
	private BorderPane bottomBorder;

	private TextArea[] txArea;//"時間[時]", "時間[分]", "経由地", "終点", "乗り場所", "(特殊運行のマーク)"
	private TextArea specialArea;
	private Button[] button;
	private MenuBar menuBar;
	private Button[] extButton;
	private Button saveBt;// ファイル生成ボタン
	private TextField busNameTx;
	private TextField[] saveFileInfoTx; // 更新日,行先番号,行先,ファイル名
	private ChoiceBox<String> saveGyouBox;
	private ChoiceBox<String> saveChBox;
	private ComboBox<String> extListBox;

	EditView(Stage stage, EditModel model) {
		this.stage = stage;
		this.model = model;
	}

	public void showView() {
		root = new BorderPane();
		HBox topBox = new HBox();
		centerGrid = new GridPane();
		centerBox = new VBox();
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
		busNameTx = new TextField();
		topBox.getChildren().addAll(new Label("表示バス停名:"), busNameTx);

		String[] listSt = { "時間[時]", "時間[分]", "経由地", "終点", "乗り場所", "(特殊運行のマーク)" };
		Label[] txF = new Label[listSt.length];
		txArea = new TextArea[listSt.length + 1]; // 入力欄
		for (int i = 0; i < listSt.length; i++) {
			txF[i] = new Label(listSt[i]);
			centerGrid.add(txF[i], i, gridI);
			txArea[i] = new TextArea();
			centerGrid.add(txArea[i], i, gridI + 1);
		}
		gridI += 2;

		specialArea = new TextArea();
		specialArea.setPrefHeight(60);
		HBox howInfoBox = new HBox();
		howInfoBox.setSpacing(20);
		howInfoBox.getChildren().add(new Label("特殊運行情報"));
		howInfoBox.getChildren().add(specialArea);
		centerBox.getChildren().addAll(centerGrid, howInfoBox);

		button = new Button[2];
		button[0] = new Button("画面クリア");
		button[1] = new Button("保存");
		BorderPane bottomGrid = new BorderPane();
		bottomGrid.setLeft(button[0]);
		bottomGrid.setRight(button[1]);
		bottomBorder.setBottom(bottomGrid);

		root.setTop(topBox);
		root.setCenter(centerBox);
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
	public void showModelData(){
		txArea[0].setText(model.getEditData().hour);
		txArea[1].setText(model.getEditData().min);
		txArea[2].setText(model.getEditData().via);
		txArea[3].setText(model.getEditData().end);
		txArea[4].setText(model.getEditData().stand);
		txArea[5].setText(model.getEditData().special);
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

	public void saveView() {
		Stage stage = new Stage();
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setTitle("保存");
		stage.initModality(Modality.APPLICATION_MODAL);// 他画面選択不可
		stage.show();

		SaveInfo svi = model.getSaveInfo(); // 設定済みの内容取得
		saveBt = new Button("保存");
		BorderPane root = new BorderPane();
		VBox centerBox = new VBox();
		GridPane gdp = new GridPane();
		gdp.setAlignment(Pos.CENTER);
		int gdpIndex = 0;

		String[] txHint = { "", "数字のみ,行先が複数ある場合のみ入力", "行き先が複数ある時のみ入力", "" };
		String[] txLabel = { "更新日", "行先番号", "行き先", "ファイル名" };
		saveFileInfoTx = new TextField[txLabel.length]; // 更新日,行先番号,行先,ファイル名
		Label[] label = new Label[txLabel.length];
		for (int i = 0; i < saveFileInfoTx.length; i++) {
			label[i] = new Label(txLabel[i]);
			label[i].setAlignment(Pos.CENTER);
			gdp.add(label[i], 0, i);
			saveFileInfoTx[i] = new TextField();
			saveFileInfoTx[i].setPromptText(txHint[i]);// hint
			gdp.add(saveFileInfoTx[i], 1, i);
			gdpIndex++;
			if (svi != null) { // 既に指定があれば
				switch (i) {
				case 0:
					if (svi.newDay != null)
						saveFileInfoTx[i].setText(svi.newDay);
					break;
				case 1:
					if (svi.toIndex != null)
						saveFileInfoTx[i].setText("" + svi.toIndex);
					break;
				case 2:
					if (svi.toString != null)
						saveFileInfoTx[i].setText(svi.toString);
					break;
				case 3:
					if (svi.fileName != null)
						saveFileInfoTx[i].setText(svi.fileName);
					break;
				}
			}
		}
		saveGyouBox = new ChoiceBox<>(); // 行, A,K,S,T,N
		saveGyouBox.getItems().addAll(Public.gyouS);
		if (svi != null && svi.gyou != 0)// 指定あれば
			saveGyouBox.getSelectionModel().select(Public.searchIndex(Public.gyouC, svi.gyou));
		gdp.add(new Label("行"), 0, gdpIndex);
		gdp.add(saveGyouBox, 1, gdpIndex);
		gdpIndex++;
		saveChBox = new ChoiceBox<>(); // 曜日選択
		String[] youbi = { "平日", "土曜", "日曜", "土日" };
		saveChBox.getItems().addAll(youbi);
		if (svi != null && svi.youbi != -1)// 指定あれば
			saveChBox.getSelectionModel().select(Public.searchIndex(youbi, youbi[svi.youbi]));
		gdp.add(new Label("曜日選択"), 0, gdpIndex);
		gdp.add(saveChBox, 1, gdpIndex);

		centerBox.getChildren().add(gdp);
		BorderPane.setAlignment(centerBox, Pos.CENTER);
		BorderPane.setAlignment(saveBt, Pos.BOTTOM_CENTER);
		root.setCenter(centerBox);
		root.setBottom(saveBt);

		stage.setScene(new Scene(root));
	}

	public Button[] getBottomButton() {
		return button;
		// 画面クリア, 保存
	}

	public Button[] getExtButton() {
		return extButton;
	}

	public ComboBox<String> getExtListBox() {
		return extListBox;
	}

	public Button getWriteSaveBt() {
		return saveBt;
	}

	public SaveInfo getSaveInfo() {
		SaveInfo sviTmp = new SaveInfo();// 更新日,行先番号,行先,ファイル名
		sviTmp.newDay = saveFileInfoTx[0].getText();
		sviTmp.toIndex = saveFileInfoTx[1].getText();
		sviTmp.toString = saveFileInfoTx[2].getText();
		sviTmp.fileName = saveFileInfoTx[3].getText();
		sviTmp.gyou = saveGyouBox.getSelectionModel().getSelectedItem().charAt(0);
		sviTmp.youbi = saveChBox.getSelectionModel().getSelectedIndex();
		return sviTmp;
	}

	public String[] getTextArea() {
		// "時間[時]", "時間[分]", "経由地", "終点", "乗り場所", "(特殊運行のマーク)"
		String[] ar = new String[txArea.length - 1];
		for (int i = 0; i < txArea.length - 1; i++) {
			ar[i] = txArea[i].getText();
		}
		return ar;
	}

	public String getOptionData() {
		return specialArea.getText();
	}
	public String getShowBusStopName(){
		return busNameTx.getText();
	}

	public void closeListV() {
		listStage.hide();
	}
}
