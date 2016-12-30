import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Network {
	public static String getHtml(String urlSt) {
		StringBuilder str = new StringBuilder();
		try {
			URL url = new URL(urlSt);
			URLConnection co = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(co.getInputStream(), "Shift_JIS"));
			String tmp;
			while ((tmp = in.readLine()) != null)
				str.append(tmp);
			in.close();
		} catch (IOException e) {
			System.out.println(e);
		}
		return str.toString();
	}

	public static String getParentUrl(String urlSt) { // http://aaa/b →
														// http://aaa
		String[] tmpAr = urlSt.split("/");
		String urlHost = "";
		for (int i = 0; i < tmpAr.length - 1; i++) {
			if (i > 0)
				urlHost += "/";
			urlHost += tmpAr[i];
		}
		return urlHost;
	}

	public static void saveFile(String urlSt, String fileLocation) {
		try {
			URL url = new URL(urlSt);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();
			int httpStatusCode = conn.getResponseCode();
			if (httpStatusCode != HttpURLConnection.HTTP_OK) {
				return;
			}

			DataInputStream dataInStream = new DataInputStream(conn.getInputStream());
			// Output Stream
			DataOutputStream dataOutStream = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileLocation)));
			byte[] b = new byte[4096];
			int readByte = 0;
			while(-1 != (readByte = dataInStream.read(b))){
				dataOutStream.write(b, 0, readByte);
			}
			// Close Stream
			dataInStream.close();
			dataOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
