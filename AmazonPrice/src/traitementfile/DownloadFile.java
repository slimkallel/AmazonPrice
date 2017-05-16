package traitementfile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFile {

	public static final String HOST = "https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/AmazonEC2/current/index.json";

	public static void getFile(URL u) throws IOException {
		URLConnection uc = u.openConnection();
		String FileType = uc.getContentType();
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("Fichier non valide.");
		}
		InputStream in = uc.getInputStream();
		String FileName = u.getFile();
		FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
		FileOutputStream WritenFile = new FileOutputStream(FileName);
		byte[] buff = new byte[1024];
		int l = in.read(buff);
		while (l > 0) {
			WritenFile.write(buff, 0, l);
			l = in.read(buff);
		}
		WritenFile.flush();
		WritenFile.close();

	}

	public static void main(String[] args) {
		try {
			URL racine = new URL(HOST);
			getFile(racine);
			System.out.println("Le fichier est bien téléchargé");
		} catch (MalformedURLException e) {
			System.err.println(HOST + " : URL non comprise.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
