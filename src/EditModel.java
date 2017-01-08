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

public class EditModel {
	private String path;
	private String extPath;	//時刻表　抽出元データpat
	private SaveInfo saveInfo;


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
	public String[] getExtList(){
		File file = new File(extPath);
		return file.list();
	}

	public SaveInfo getSaveInfo(){
		return saveInfo;
	}
	public String getPath(){
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		System.out.println(path);
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
						new OutputStreamWriter(new FileOutputStream(path +"\\"+ fileName[i] + ".txt"), "Unicode")));
				pw.flush();
				pw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setInfo(){
		if(path != null){

		}
	}

	public void setExtPath(String path){
		this.extPath = path;
	}

	public String[] makeBusTimeData(String[] ar){
		String spAr[] = ar[0].split("\n",-1);
		int cnt = spAr.length;	//行数
		if(spAr[spAr.length-1].equals(""))	cnt--;	//無駄な改行があれば引く
		String[] tmp = new String[cnt];
		for(int i=0; i<ar.length; i++){
			if(i != 0)
				spAr = ar[i].split("\n",-1);
			for(int j=0; j<cnt; j++){
				if(i==0){
					tmp[j] = spAr[j];
				}else{
					tmp[j] += ","+spAr[j];
				}
			}
		}
		return tmp;
	}
}

class SaveInfo{
	public String newDay;
	public String toIndex;
	public String toString;
	public String fileName;
	public char gyou;
	public int youbi;//(0を平日),1が土曜,2が日曜,3が土日
	void clear(){
		newDay = null;
		toIndex = null;
		toString = null;
		fileName = null;
		gyou = 0;
		youbi = 0;
	}
}
