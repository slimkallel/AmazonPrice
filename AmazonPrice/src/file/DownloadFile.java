package file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * The DownloadFile program implements a methods that download the
 * Json file and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */
public class DownloadFile {

	public static final String HOST = "https://pricing.us-east-1.amazonaws.com/offers/v1.0/aws/AmazonEC2/current/index.json";

	public static void getFile(URL u) throws IOException {
		URLConnection uc = u.openConnection();
		int FileLenght = uc.getContentLength();
		if (FileLenght == -1) {
			throw new IOException("File not available...");
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
			System.out.println("The file is well downloaded...");
		} catch (MalformedURLException e) {
			System.err.println(HOST + " : URL no-comprise.");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
