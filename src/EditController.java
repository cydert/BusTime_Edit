import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

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
				view.getBottomButton()[1].setOnAction(ev -> save());
				view.getMenuItem()[1][0].setOnAction(ev -> sameBusStopNameJP());
				view.getMenuItem()[1][1].setOnAction(ev -> removeTime());
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
		if( model.extruct(filePath) ){
			//ファイル展開成功か
			view.showModelData();
		}
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void sameBusStopNameJP(){
		EditData ed = view.getEditData();
		String tmp = ed.hour;
		int cnt = tmp.split("\n").length;
		tmp = view.getShowBusStopName();
		ed.stand = "";
		for(int i=0; i<cnt; i++){
			ed.stand += tmp + "\n";
		}
		model.setEditData(ed);
		view.showModelData();
	}
	private void removeTime(){
		EditData ed = view.getEditData();
		String[] tmp = ed.hour.split("\n");
		int cnt = tmp.length;
		for(int i=0; i<cnt; i++){
			while(!Public.isNumber(tmp[i])){
				if(!Public.isNumber(tmp[i].charAt(0)+""))
					tmp[i] = tmp[i].substring(1);
				else{
					tmp[i] = tmp[i].substring(0, tmp[i].length()-1);
				}
			}
		}
		ed.hour = String.join("\n", tmp);

		tmp = ed.min.split("\n");
		cnt = tmp.length;
		for(int i=0; i<cnt; i++){
			while(!Public.isNumber(tmp[i])){
				if(!Public.isNumber(tmp[i].charAt(0)+""))
					tmp[i] = tmp[i].substring(1);
				else{
					tmp[i] = tmp[i].substring(0, tmp[i].length()-1);
				}
			}
		}
		ed.min = String.join("\n", tmp);
		model.setEditData(ed);
		view.showModelData();
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

	private void save() {
		view.saveView();
		view.getWriteSaveBt().setOnAction(ev -> writeSave());
	}

	private void writeSave() {
		SaveInfo svi = view.getSaveInfo();
		String nameListPath = model.getPath() + "\\" + Public.nameListDirName + "\\" + svi.gyou + ".txt";
		String[] option = { "", "" };
		if (!svi.toIndex.equals("")) {
			option[0] = svi.toIndex + "_";
		}
		if (svi.youbi > 0)
			option[1] = "_" + svi.youbi;
		String busTimeDataPath = model.getPath() + "\\" + Public.dataDirName + "\\" + option[0] + svi.fileName
				+ option[1] + ".csv";

		String[] data = model.makeBusTimeData(view.getTextArea());
		Files.WriteData(busTimeDataPath, data); // ファイル書き込み
		String tmp = model.makeBusTimeDataOption(view.getOptionData());
		Files.WriteDataAdd(busTimeDataPath, tmp); // 特殊運行の追記
		Files.WriteDataAdd(busTimeDataPath, "/i," + svi.newDay); // 更新日の追記
		model.writeNameListData(Files.readData(nameListPath), svi, view.getShowBusStopName(), nameListPath);
	}
}
