import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Network {
	public static String getHtml(String urlSt){
		StringBuilder str = new StringBuilder();
		try{
			URL url = new URL(urlSt);
			URLConnection co = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(co.getInputStream(),"Shift_JIS"));
			String tmp;
			while ((tmp = in.readLine()) != null)
				str.append(tmp);
			in.close();
		}catch(IOException e){
			System.out.println(e);
		}
		return str.toString();
	}

	public static String getParentUrl(String urlSt){	//http://aaa/b → http://aaa
		String[] tmpAr = urlSt.split("/");
		String urlHost = "";
		for(int i=0; i<tmpAr.length-1; i++){
			if(i > 0)
				urlHost += "/";
			urlHost += tmpAr[i];
		}
		return urlHost;
	}
}
