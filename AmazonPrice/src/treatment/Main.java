package treatment;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {

	/**
	 * This is the main method which makes use of the methods.
	 * 
	 * @param args
	 *            Unused.
	 * @return Nothing.
	 * @exception IOException
	 *                On input error.
	 * @exception FileNotFoundException
	 *                On input error.
	 * @exception ParseException
	 *                On input error.
	 * @see IOException
	 * @see ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {

		// DataTransfer.SommeDataTransfert("Asia Pacific (Tokyo)", 100, 100,
		// 100,
		// 100);
		Price.getInstancePrice("US East (N. Virginia)", "Linux", 8, 32, 1);
		

	}

}
