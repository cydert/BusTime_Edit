import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Public {
	public static String rootPath = "data";
	public static String bouchoPdfPath = rootPath + "\\Boucho\\BouchoPDF";
	public static String dataDirName = "BusTime";
	public static String nameListDirName = "Name_list";
	public static String[] gyouS = { "A", "K", "S", "T", "N", "H", "M", "R", "Y", "W" };
	public static char[] gyouC = { 'A', 'K', 'S', 'T', 'N', 'H', 'M', 'R', 'Y', 'W' };

	public static void errorShow(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("エラー");
		alert.setContentText(msg);
		alert.show();
	}

	public static void infoShow(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("通知");
		alert.setContentText(msg);
		alert.show();
	}

	// 文字分割
	public static String cutTwoStringFirst(String text, String cutString) {
		int tmp = text.indexOf(cutString);
		if (tmp == -1)
			return text;
		return text.substring(0, tmp);

	}

	public static String cutTwoStringSecond(String text, String cutString) {
		int tmp = text.indexOf(cutString);
		if (tmp == -1)
			return text;
		tmp += cutString.length();
		return text.substring(tmp);

	}

	// 後ろから
	public static String cutTwoStringFirstL(String text, String cutString) {
		int tmp = text.lastIndexOf(cutString);
		if (tmp == -1)
			return text;
		return text.substring(0, tmp);

	}

	public static String cutTwoStringSecondL(String text, String cutString) {
		System.out.println(text+cutString);
		int tmp = text.lastIndexOf(cutString);
		if (tmp == -1) {
			return text;
		}
		tmp += cutString.length();
		return text.substring(tmp);

	}

	public static int searchIndex(char[] list, char c) {
		for (int i = 0; i < list.length; i++) {
			if (list[i] == c) {
				return i;
			}
		}
		return -1;
	}

	public static int searchIndex(String[] list, String s) {
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(s)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isNumber(String num) {
		String regex = "^\\-?[0-9]*\\.?[0-9]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(num);
		return m.find();
	}
}
