package treatment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * The ReservedTreatment program implements many methods that make the treatment
 * of the Reserved instance from Json file and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */
public class ReservedTreatment {
	/**
	 * The getListReserved method is used to get a list
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList This returns list of the skus of all the instances that
	 *         are reserved
	 */
	static ArrayList<Object> getListReserved() throws FileNotFoundException,
			IOException, ParseException {
		ArrayList<Object> shared = OnDemandTreatment.getListOnDemandShared();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
		ArrayList<Object> reservedins = new ArrayList<>();
		JSONObject terms = (JSONObject) jsonObject.get("terms");
		JSONObject reserved = (JSONObject) terms.get("Reserved");

		Set<?> s = reserved.keySet();
		Iterator<?> i = s.iterator();

		do {
			String k = i.next().toString();
			if (shared.contains(k))
				reservedins.add(k);

		} while (i.hasNext());

		return reservedins;
	}

	/**
	 * The getReservedPrice method is used to get the list of the price of a
	 * reserved instance with the file
	 * 
	 * @param jsonObject
	 *            this is the file.
	 * @param object
	 *            this is the id of the reserved instance.
	 * @return list
	 */
	static ArrayList<Object> getReservedPrice(JSONObject jsonObject,
			Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject ReservedPord = (JSONObject) posts.get("Reserved");

		JSONObject id = (JSONObject) ReservedPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");
				JSONObject term = (JSONObject) euff.get("termAttributes");

				String contractLength = (String) term
						.get("LeaseContractLength");
				String offre = (String) term.get("OfferingClass");
				String opt = (String) term.get("PurchaseOption");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						JSONObject priceperunit = (JSONObject) despri
								.get("pricePerUnit");
						String res = (String) priceperunit.get("USD");
						result.add(contractLength);
						result.add(offre);
						result.add(opt);
						result.add(res);

					}

				} while (i2.hasNext());

			}
		} while (i1.hasNext());
		return result;
	}

	/**
	 * The getInstancePriceReserved method is used to get the list of the price
	 * of a reserved instance with the id of the instance
	 * 
	 * @param object
	 *            this is the id of the reserved instance.
	 * @return list
	 */
	static void getInstancePriceReserved(Object id)
			throws FileNotFoundException, IOException, ParseException {
		Boolean test = false;
		JSONObject jsonObject = OnDemandTreatment.ReadFile();

		ArrayList<Object> prix = getReservedPrice(jsonObject, id);
		test = true;
		int k = 0;
		while (k < prix.size() - 4) {

			if (prix.get(k + 3).equals("0.0000000000")) {
				k = k + 4;
			}
			System.out.println(prix.get(k) + "\t" + prix.get(k + 1) + "\t"
					+ prix.get(k + 2) + "\t" + prix.get(k + 3));
			k = k + 4;

		}

		if (!test) {
			System.out.println("Instance not resrved");
		}
	}

}
