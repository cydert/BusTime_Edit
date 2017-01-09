import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class EditModel {
	private String path;
	private String extPath; // 時刻表 抽出元データpat
	private SaveInfo saveInfo;
	private EditData ed;

	// 会社名一覧 取得
	ArrayList<String> getCompanysInfo() {
		String fileName[] = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
		String filePath = Public.rootPath + "\\BusCompany";
		File file = new File(filePath);
		if (file.exists()) { // フォルダ存在していたら
			ArrayList<String> text = new ArrayList<>(); // 全文
			for (int i = 0; i < fileName.length; i++) {
				String tmpPath = filePath + "\\" + fileName[i] + ".txt"; // 読み込み元
				String tmp;
				try {
					BufferedReader bfr = new BufferedReader(
							new InputStreamReader(new FileInputStream(tmpPath), "unicode"));
					while ((tmp = bfr.readLine()) != null) {
						text.add(tmp);
					}
					bfr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return text;
		} else {
			System.out.println("ファイル未存在");
			return null;
		}
	}

	public String[] getExtList() {
		File file = new File(extPath);
		return file.list();
	}

	public SaveInfo getSaveInfo() {
		return saveInfo;
	}

	public String getPath() {
		return path;
	}
	public EditData getEditData(){
		return ed;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void check() {
		String path = this.path + "\\" + Public.dataDirName;
		File file = new File(path);
		file.mkdirs();
		path = this.path + "\\" + Public.nameListDirName;
		file = new File(path);
		file.mkdirs();

		String fileName[] = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
		try {
			PrintWriter pw;
			for (int i = 0; i < fileName.length; i++) {
				pw = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(path + "\\" + fileName[i] + ".txt"), "Unicode")));
				pw.flush();
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean extruct(String filePath){
		ed = new EditData();
		saveInfo = new SaveInfo();
		saveInfo.gyou = Public.changeFirstGyou(Public.cutTwoStringSecondL(filePath, "\\"));
		String tmpS =  Public.cutTwoStringFirstL(filePath, ".pdf");
		char youbiC =tmpS.charAt(tmpS.length()-1);
		if(youbiC == 'a') saveInfo.youbi = 0;
		else if(youbiC == 'b') saveInfo.youbi = 1;
		else saveInfo.youbi = 3;


		try {
			FileInputStream pdfStream = new FileInputStream(filePath);
			PDDocument pdf = PDDocument.load(pdfStream);
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(pdf);
			System.out.println(text);
			int page = pdf.getNumberOfPages();
			String[] textAr = text.split("\r\n");

			boolean isRead = false;
			for(int i=0; i<textAr.length; i++){
				if(!isRead){
					if(textAr[i].contains("日改正")){
						isRead = true;
						String tmp = Public.cutTwoStringFirstL(textAr[i], "改正");
						saveInfo.newDay = Public.cutTwoStringSecondL(tmp, " ");
					}
				}else{
					String tmp;
					if(textAr[i].contains(":")){//取り込むデータなら
						tmp = Public.cutTwoStringFirst(textAr[i], " ");	//時刻か注意マーク取り出し
						if(!Public.isNumber(tmp.charAt(0)+"")){//注意マークあるなら
							ed.special += tmp.charAt(0)+"\n";
							tmp = Public.cutTwoStringSecond(textAr[i], tmp+" ");	//時刻以降取り出し
						}else{
							tmp = textAr[i];//時刻以降
							ed.special += "\n";
						}
						ed.hour += Public.cutTwoStringFirst(tmp, ":") + "\n";
						ed.min += Public.cutTwoStringFirst(Public.cutTwoStringSecond(tmp, ":"), " ") + "\n";
						tmp = Public.cutTwoStringSecond(tmp, " ");
						ed.end += Public.cutTwoStringSecondL(tmp, " ")+ "\n";	//目的地
						tmp = Public.cutTwoStringFirstL(tmp, " ");
						tmp = tmp.replaceAll("\\) ", "\\)");
						tmp = tmp.replaceAll(" ", "・");
						if(tmp == "" || tmp == " ")	tmp = "不明";
						ed.via += tmp+"\n";	//経由地
					}
				}
				pdf.close();
				pdfStream.close();
			}
			return isRead;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setInfo() {
		if (path != null) {

		}
	}

	public void setExtPath(String path) {
		this.extPath = path;
	}

	public void setEditData(EditData ed){
		this.ed = ed;
	}

	public String[] makeBusTimeData(String[] ar) {
		String spAr[] = ar[0].split("\n", -1);
		int cnt = spAr.length; // 行数
		if (spAr[spAr.length - 1].equals(""))
			cnt--; // 無駄な改行があれば引く
		String[] tmp = new String[cnt];
		for (int i = 0; i < ar.length; i++) {
			if (i != 0)
				spAr = ar[i].split("\n", -1);
			for (int j = 0; j < cnt; j++) {
				if (i == 0) {
					if (spAr.length < j)
						tmp[j] = "";
					else
						tmp[j] = spAr[j];
				} else {
					if (spAr.length < j)
						tmp[j] = "";
					else
						tmp[j] += "," + spAr[j];
				}
			}
		}
		return tmp;
	}

	public String makeBusTimeDataOption(String data) {
		return "//," + data.replaceAll("\n", ",");
	}

	public void writeNameListData(ArrayList<String> oldData, SaveInfo svi, String busStopName, String dataPath) {
		String tmp;
		for (int i = 0; i < oldData.size(); i++) {
			if (oldData.get(i).split(",")[1].equals(svi.fileName)) {
				// 既にデータがあったら
				if (svi.toIndex.equals("")) { // 行先複数なし
					oldData.remove(i);
					oldData.add(i, busStopName + "," + svi.fileName + ",");
					Files.WriteData(dataPath, oldData.toArray(new String[oldData.size()]));	//書き込み
					return;
				}
				ArrayList<String> ar = new ArrayList<>(Arrays.asList(oldData.get(i).split(",")));
				for (int j = 2; j < ar.size(); j++) {
					if (ar.get(j).equals(svi.toString)) { // 既に同じ行先が書かれてた
						return; // 何もしない
					}
				}
				int index = Integer.parseInt(svi.toIndex) + 2;
				if (ar.size() <= index)
					index = ar.size();
				ar.add(index, svi.toString);
				oldData.remove(i);
				oldData.add(i, String.join(",", ar));
				Files.WriteData(dataPath, oldData.toArray(new String[oldData.size()]));	//書き込み
				return;
			}
		}
		//データなし
		int index = binarySearch2Mode(oldData, svi.fileName);	//2分探索
		tmp = busStopName + "," + svi.fileName+ ","+svi.toString;
		oldData.add(index, tmp);
		Files.WriteData(dataPath, oldData.toArray(new String[oldData.size()]));	//書き込み
	}

	public int binarySearch2Mode(ArrayList<String> ar, String key) {
		int a = 0, b = 0, c = ar.size();
		while (a < c) {
			b = (a + c) / 2;
			int compare = ar.get(b).split(",")[1].compareToIgnoreCase(key);
			if (compare == 0)
				return b;
			else if (compare < 0) { // ar > key
				c = b;
			} else {
				a = b + 1;
			}
		}
		return b;
	}
}

class SaveInfo {
	public String newDay;
	public String toIndex;
	public String toString;
	public String fileName;
	public char gyou;
	public int youbi;// (0を平日),1が土曜,2が日曜,3が土日

	void clear() {
		newDay = null;
		toIndex = null;
		toString = null;
		fileName = null;
		gyou = 0;
		youbi = 0;
	}
}
//"時間[時]", "時間[分]", "経由地", "終点", "乗り場所", "(特殊運行のマーク)"
class EditData{
	public String hour="";
	public String min="";
	public String via="";
	public String end="";
	public String stand="";
	public String special="";
}
