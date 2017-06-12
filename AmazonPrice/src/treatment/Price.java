package treatment;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

/**
 * The Price program implements a methods that Prints the price of an instance
 * in the three type and print it on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */
public class Price {

	/**
	 * The getInstancePrice method is used to get the price of the instance in
	 * the 3 types
	 * 
	 * @param location
	 *            we have to find instance in a specific location
	 * @param os
	 *            we have to find instances with a specific operating system
	 * @param dcpu
	 *            we have to find the instances that have cpu=dcpu or just
	 *            superior .
	 * @param memory
	 *            we have to find the instances that have memory=dmemory .
	 * @param hour
	 *            the number of hour
	 * @param object
	 *            this is the sku of the shared product .
	 * @return float this returns price of the object
	 */
	 static void getInstancePrice(String location, String os, int dcpu,
			double d, int hour) throws IOException, ParseException {

		ArrayList<Object> list = OnDemandTreatment.getInformationInstanceLoS(location, os, dcpu, d, hour);
		
		System.out.println("The instance: " + list.get(0));
		
		float prix = OnDemandTreatment.OnDemandInstancePrice(list.get(3));
		System.out.println("\n--- Price On-Demand : " + prix+"$");
		
		System.out.println("\n--- Price Reserved : ");
		ReservedTreatment.getInstancePriceReserved(list.get(3));
		
		System.out.println("\n---Price SpotHistory : ");
		SpotHistory.RunSpotPrice(list.get(0).toString());
	}

}
