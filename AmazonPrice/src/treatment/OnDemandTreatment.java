package treatment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The OnDemandTreatment program implements many methods that make the treatment
 * of the OnDemand instance from Json file and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */

public class OnDemandTreatment {

	/**
	 * The ReadFile() method simply read the json file
	 * 
	 * @param Nothing
	 *            .
	 * @return JSONObject This returns json from file.
	 * @exception IOException
	 *                On input error.
	 * @exception FileNotFoundException
	 *                On input error.
	 * @exception ParseException
	 *                On input error.
	 * @see IOException
	 * @see FileNotFoundException
	 * @see ParseException
	 */
	static JSONObject ReadFile() throws FileNotFoundException, IOException,
			ParseException {
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new FileReader("index.json"));

		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;

	}

	/**
	 * The getListOnDemandShared method is used to get a list
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList This returns list of the skus of all products which are
	 *         shared.
	 */
	static ArrayList<Object> getListOnDemandShared()
			throws FileNotFoundException, IOException, ParseException {

		JSONObject jsonObject = ReadFile();
		ArrayList<Object> demandShared = new ArrayList<>();
		JSONObject posts = (JSONObject) jsonObject.get("products");
		Set<?> s1 = posts.keySet();
		Iterator<?> i1 = s1.iterator();

		do {
			String k = i1.next().toString();
			JSONObject sku = (JSONObject) posts.get(k);
			JSONObject attr = (JSONObject) sku.get("attributes");
			// in this part KeySet is a list of the keys in the attributes part
			Set<?> s = attr.keySet();
			Iterator<?> i = s.iterator();

			do {
				String k1 = i.next().toString();
				// if the Key equal tenancy
				if (k1.equals("tenancy")) {
					String tenancy = (String) attr.get("tenancy");
					// if the value of the Key equal shared
					if (tenancy.equals("Shared")) {
						demandShared.add(k);

					}
				}
			} while (i.hasNext());

		} while (i1.hasNext());
		// demandShared is the list of all the product that have in their
		// attribute "tenancy" the value "shared"
		return demandShared;

	}

	/**
	 * The OnDemandInstanceSharedPrice method is used to get the price of a
	 * shared product
	 * 
	 * @param object
	 *            this is the sku of the shared product .
	 * @param jsonObject
	 *            this is the json file .
	 * @return float this returns price of the object
	 */
	static float OnDemandInstanceSharedPrice(Object object,
			JSONObject jsonObject) throws FileNotFoundException, IOException,
			ParseException {
		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");
		// the object is equal to the sku in the file
		JSONObject id = (JSONObject) DemandPord.get(object);
		Set<?> s1 = id.keySet();
		String res = null;
		float prix = 1.23f;
		Iterator<?> i1 = s1.iterator();
		// go the product attributes
		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");
				// go to the priceDimensions attributes
				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						// go to the pricePerUnit attributes and get the price
						JSONObject priceperunit = (JSONObject) despri
								.get("pricePerUnit");
						res = (String) priceperunit.get("USD");

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());
		// every sharedProduct has a price
		prix = Float.parseFloat(res);
		return prix;

	}

	/**
	 * The getInformationInstanceLoS method is used to get the list of the
	 * instances which their cpu=dcpu and memory=dmemory or juste superior
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
	 *            number of hour
	 * @return ArrayList
	 */
	static ArrayList<Object> getInformationInstanceLoS(String location,
			String os, int dcpu, double memory, int hour)
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> instances = getInstanceInformation(dcpu, memory);
		JSONObject json = ReadFile();
		ArrayList<Object> tab = new ArrayList<>();
		JSONObject posts = (JSONObject) json.get("products");

		for (int n = 0; n < instances.size(); n++) {
			JSONObject prod = (JSONObject) posts.get(instances.get(n));
			String sku = (String) prod.get("sku");
			JSONObject attr = (JSONObject) prod.get("attributes");
			String instance = (String) attr.get("instanceType");
			String cpu = (String) attr.get("vcpu");
			String flocation = (String) attr.get("location");
			String memoire = (String) attr.get("memory");
			String system = (String) attr.get("operatingSystem");
			if (flocation.equals(location) && (os.equals(system))) {

				tab.add(instance);
				tab.add(cpu);
				tab.add(memoire);
				tab.add(sku);
			}
		}

		return tab;

	}

	/**
	 * The getInstanceInformation method is used to get the list of the
	 * instances which their cpu=dcpu and memory=dmemory or juste superior
	 * 
	 * @param dcpu
	 *            we have to find the instances that have cpu=dcpu or just
	 *            superior .
	 * @param memory
	 *            we have to find the instances that have memory=dmemory .
	 * @return ArrayList
	 */
	static ArrayList<Object> getInstanceInformation(int cpu, double memory)

	throws FileNotFoundException, IOException, ParseException {
		ArrayList<Object> demandShared = getListOnDemandShared();
		JSONObject json = ReadFile();
		ArrayList<Object> instances = new ArrayList<>();

		ArrayList<Integer> cpus = new ArrayList<>();
		ArrayList<Double> memories = new ArrayList<>();
		JSONObject posts = (JSONObject) json.get("products");

		int n = 0;
		int tcpu = 0, fin_cpu = 0;
		double tmemory = 0, fin_memory = 0;
		int i = 0;
		Boolean trouve = false, fin = false;
		for (n = 0; n < demandShared.size(); n++) {

			JSONObject prod = (JSONObject) posts.get(demandShared.get(n));
			JSONObject attr = (JSONObject) prod.get("attributes");

			int vcpu = Integer.parseInt((String) attr.get("vcpu"));

			String memoire = ((String) attr.get("memory")).replaceAll(
					"[\\s+a-zA-Z :]", "");

			if ((memoire.equals("1,952"))) {
				memoire = "1952";
			}
			double mem = Double.parseDouble(memoire);
			if ((vcpu >= cpu) && (!cpus.contains(vcpu))) {
				cpus.add(vcpu);
			}
			if ((mem >= memory) && (!memories.contains(mem))) {
				memories.add(mem);
			}

		}

		Collections.sort(cpus);
		Collections.sort(memories);
		for (int k = 0; k < cpus.size(); k++) {
			if (!fin) {
				tcpu = cpus.get(k);
				for (i = 0; i < memories.size(); i++) {

					tmemory = memories.get(i);
					if (!trouve) {
						for (n = 0; n < demandShared.size(); n++) {

							JSONObject prod = (JSONObject) posts
									.get(demandShared.get(n));
							JSONObject attr = (JSONObject) prod
									.get("attributes");

							int vcpu = Integer.parseInt((String) attr
									.get("vcpu"));

							String memoire = ((String) attr.get("memory"))
									.replaceAll("[\\s+a-zA-Z :]", "");

							if ((memoire.equals("1,952"))) {
								memoire = "1952";
							}
							double mem = Double.parseDouble(memoire);
							if ((tcpu == vcpu) && (mem == tmemory)) {
								instances.add(demandShared.get(n));
								fin_cpu = tcpu;
								fin_memory = tmemory;
								trouve = true;
								fin = true;
							}
						}
					}

				}
			}
		}
		System.out.println("cpu=" + fin_cpu);
		System.out.println("memory=" + fin_memory);
		return instances;
	}

	/**
	 * The OnDemandInstancePrice method is used to get the price of a shared
	 * product with its id
	 * 
	 * @param object
	 *            this is the id of the shared product .
	 * @return float this returns price of the object
	 */
	static float OnDemandInstancePrice(Object object)
			throws FileNotFoundException, IOException, ParseException {
		JSONObject jsonObject = ReadFile();
		float prix = OnDemandInstanceSharedPrice(object, jsonObject);
		return prix;

	}
}
