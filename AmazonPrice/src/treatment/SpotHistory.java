package treatment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The SpotHistory program implements methods that pass a command line to the
 * cmd that concern spot instance and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */
public class SpotHistory {
	/**
	 * The RunSpotPrice method is used to get the description of the spot
	 * history price
	 * 
	 * @param instance
	 *            the name of the instance that we are searching.
	 * @return Nothing
	 */
	static void RunSpotPrice(String instance) throws IOException {

		Runtime runtime = Runtime.getRuntime();
		final Process process = runtime
				.exec("aws ec2 describe-spot-price-history --instance-types "
						+ instance
						+ "  --product-description Linux/UNIX --start-time 2017-06-02T14:08:09 --end-time 2017-06-02T14:09:10");

		// Consommation de la sortie standard de l'application externe dans un
		// Thread separe
		new Thread() {
			public void run() {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line = "";
					BufferedWriter out = new BufferedWriter(new FileWriter(
							"SpotInstance.json"));
					try {
						while ((line = reader.readLine()) != null) {
							// Traitement du flux de sortie de l'application si
							// besoin est
							System.out.println(line);
							out.write(line);
						}
					} finally {
						reader.close();
						out.close();
					}
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * The RunSpotPrice method is used to get the description of the spot
	 * history price
	 * 
	 * @param instance
	 *            the name of the instance that we are searching.
	 * @param os
	 *            the operating system that we we want
	 * @return Nothing
	 */
	static void RunSpotPrice(String instance, String os) throws IOException {

		Runtime runtime = Runtime.getRuntime();
		if (os.equals("linux")) {

			final Process process = runtime
					.exec("aws ec2 describe-spot-price-history --instance-types "
							+ instance
							+ "  --product-description Linux/UNIX  --start-time 2017-06-02T14:08:09 --end-time 2017-06-02T14:09:10");
			// Consommation de la sortie standard de l'application externe dans
			// un
			// Thread separe
			new Thread() {
				public void run() {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						String line = "";
						BufferedWriter out = new BufferedWriter(new FileWriter(
								"SpotInstance.json"));
						try {
							while ((line = reader.readLine()) != null) {
								// Traitement du flux de sortie de l'application
								// si
								// besoin est
								System.out.println(line);
								out.write(line);
							}
						} finally {
							reader.close();
							out.close();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}.start();

		} else if (os.equals("windows")) {
			final Process process = runtime
					.exec("aws ec2 describe-spot-price-history --instance-types "
							+ instance
							+ "  --product-description Windows  --start-time 2017-03-04T14:08:09 --end-time 2017-03-04T14:09:10");
			new Thread() {
				public void run() {
					try {
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						String line = "";
						BufferedWriter out = new BufferedWriter(new FileWriter(
								"SpotInstance.json"));
						try {
							while ((line = reader.readLine()) != null) {
								// Traitement du flux de sortie de l'application
								// si
								// besoin est
								System.out.println(line);
								out.write(line);
							}
						} finally {
							reader.close();
							out.close();
						}
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}.start();
		}

	}
}
