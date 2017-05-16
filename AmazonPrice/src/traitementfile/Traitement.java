package traitementfile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Traitement program implements many methods that make the treatment of the
 * Json file and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI
 * @version 1.0
 * @since 2017-04-26
 */
public class Traitement {
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
	public static JSONObject ReadFile() throws FileNotFoundException,
			IOException, ParseException {
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
	public static ArrayList<Object> getListOnDemandShared()
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
	 * The AfficheListOnDemandShared method is used to get the list of all the
	 * product shared with their cpu,memory,sku,instance,location,OS,
	 * Licence,preInstalled
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing
	 */
	public static void AfficheListOnDemandShared()
			throws FileNotFoundException, IOException, ParseException {
		// get the size of the demandShared list
		ArrayList<Object> OnDemandShared = getListOnDemandShared();
		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		int i = 0;
		System.out.println("Les produit à la demnade");
		System.out
				.println("sku\t\t\t instance \tvcpu\tlocation\t\tmemory\t\toperatingSystem\tlicenseModel\tpreInstalledSw\tprix");
		// print all the dmandShared Instance
		for (i = 0; i < OnDemandShared.size(); i++) {
			float prix = OnDemandInstanceSharedPrice(OnDemandShared.get(i),
					jsonObject);

			JSONObject prod = (JSONObject) products.get(OnDemandShared.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			// get the instancetype
			String instante = (String) attributes.get("instanceType");
			// get the cpu
			String cpu = (String) attributes.get("vcpu");
			// get the location
			String location = (String) attributes.get("location");
			// get the memory
			String memoire = (String) attributes.get("memory");
			// get the operating system
			String system = (String) attributes.get("operatingSystem");
			// get the license model
			String licenseModel = (String) attributes.get("licenseModel");
			// get the preInstalledSw
			String pre = (String) attributes.get("preInstalledSw");
			// get the sku
			String sku = (String) prod.get("sku");

			System.out.println(sku + "\t" + instante + "\t" + cpu + "\t"
					+ location + "\t" + memoire + "\t" + system + "\t" + system
					+ "\t" + licenseModel + "\t" + pre + "\t" + prix);
		}

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
	public static float OnDemandInstanceSharedPrice(Object object,
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
	 * The getMinCpu method is used to get a cpu
	 * 
	 * @param jsonObject
	 *            this is the json file .
	 * 
	 * @param demandShared
	 *            this is the list of the shared product .
	 * @param cpu
	 *            this is the cpu of the instance .
	 * @param memory
	 *            this is the memory of the instance .
	 * @return int this returns the minimum cpu
	 */

	public static int getMinCpu(JSONObject json,
			ArrayList<Object> demandShared, int cpu) {
		JSONObject posts = (JSONObject) json.get("products");
		ArrayList<Object> cpus = new ArrayList<>();
		int n = 0, k = 0;
		int min = 0;
		// get all the cpu values wich are superior than the cpu enter
		for (n = 0; n < demandShared.size(); n++) {

			JSONObject prod = (JSONObject) posts.get(demandShared.get(n));
			JSONObject attr = (JSONObject) prod.get("attributes");
			int vcpu = Integer.parseInt((String) attr.get("vcpu"));
			if (cpu <= vcpu) {
				cpus.add(vcpu);

			}
		}
		// get the min cpu from the list below
		min = (int) cpus.get(0);
		for (k = 1; k < cpus.size(); k++) {
			if (((int) cpus.get(k)) < min) {
				min = (int) cpus.get(k);
			}
		}
		return min;
	}

	/**
	 * The getInstanceMinCpu method is used to get the list of the shared
	 * product which their cpu is just superior of the enter cpu
	 * 
	 * @param jsonObject
	 *            this is the json file .
	 * @param demandShared
	 *            this is the list of the shared product .
	 * @param cpu
	 *            this is the cpu of the instance .
	 * @return ArrayList this returns list of the instances that has their
	 *         cpu=min
	 */
	public static ArrayList<Object> getInstanceMinCpu(JSONObject json,
			ArrayList<Object> demandShared, int cpu, String memory) {
		JSONObject posts = (JSONObject) json.get("products");
		int min = getMinCpu(json, demandShared, cpu);
		ArrayList<Object> instances = new ArrayList<>();
		int n = 0;

		for (n = 0; n < demandShared.size(); n++) {

			JSONObject prod = (JSONObject) posts.get(demandShared.get(n));
			JSONObject attr = (JSONObject) prod.get("attributes");

			int vcpu = Integer.parseInt((String) attr.get("vcpu"));

			String ram = (String) attr.get("memory");
			if ((vcpu == min) && (memory.equals(ram))) {
				instances.add(demandShared.get(n));

			}
		}
		// get the instances that have their cpu and memory equal to the enter
		// param
		return instances;

	}

	/**
	 * The getInformationInstance method is used to get the list of the shared
	 * product which their cpu=dcpu or juste superios and their memory=dmemory
	 * 
	 * @param dcpu
	 *            we have to find the instances that have cpu=dcpu or just
	 *            superior .
	 * @param dmemory
	 *            we have to find the instances that have memory=dmemory .
	 * @return Nothing
	 */
	public static void getInformationInstance(int dcpu, String dmemory)
			throws FileNotFoundException, IOException, ParseException {
		ArrayList<Object> demandShared = getListOnDemandShared();
		JSONObject json = ReadFile();
		ArrayList<Object> instances = new ArrayList<>();
		JSONObject posts = (JSONObject) json.get("products");

		int n = 0;
		int min = getMinCpu(json, demandShared, dcpu);
		for (n = 0; n < demandShared.size(); n++) {

			JSONObject prod = (JSONObject) posts.get(demandShared.get(n));
			JSONObject attr = (JSONObject) prod.get("attributes");

			int cpu = Integer.parseInt((String) attr.get("vcpu"));
			String memoire = (String) attr.get("memory");

			if ((cpu == dcpu) && (memoire.equals(dmemory))) {
				instances.add(demandShared.get(n));
			}

		}
		// if instances.size ==0 then the dcpu is not existed we pass to get the
		// min cpu
		if (instances.size() == 0) {

			instances = getInstanceMinCpu(json, demandShared, dcpu, dmemory);
		}

		if (instances.size() == 0) {
			// if after getting the min cpu instances.size still equal 0 than
			// the
			// product with these param dosen't exist
			System.out.println("The product with this memory is not existed");

		} else {
			System.out.println("The instances with memory= " + dmemory
					+ " and cpu=" + min);
			System.out
					.println("sku\t\t\t instance \tvcpu\tlocation\tmemory\t\toperatingSystem\tlicenseModel\t\tpreInstalledSw\tprix");
			for (n = 0; n < instances.size(); n++) {

				float prix = OnDemandInstanceSharedPrice(instances.get(n), json);

				JSONObject prod = (JSONObject) posts.get(instances.get(n));
				JSONObject attr = (JSONObject) prod.get("attributes");
				String instante = (String) attr.get("instanceType");
				String cpu = (String) attr.get("vcpu");
				String location = (String) attr.get("location");
				String memoire = (String) attr.get("memory");
				String system = (String) attr.get("operatingSystem");
				String licenseModel = (String) attr.get("licenseModel");
				String pre = (String) attr.get("preInstalledSw");
				String sku = (String) prod.get("sku");
				if ((location.equals("US East (Ohio)"))
						&& (system.equals("Linux"))) {
					System.out.println(sku + "\t" + instante + "\t" + cpu
							+ "\t" + location + "\t" + memoire + "\t" + system
							+ "\t" + "\t" + licenseModel + "\t" + pre + "\t"
							+ "\t" + prix);
				}
			}
		}

	}

	// /////////////////////////////////////////////////////////////////:
	/**
	 * The getListIpAdressProduct method is used to get the list of Elastic IP
	 * addresses product
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList of the Elastic IP addresses skus
	 */
	public static ArrayList<Object> getListIpAdressProduct()
			throws FileNotFoundException, IOException, ParseException {
		ArrayList<Object> ipadress = new ArrayList<>();
		JSONObject jsonObject = ReadFile();
		JSONObject terms = (JSONObject) jsonObject.get("terms");
		JSONObject onDemand = (JSONObject) terms.get("OnDemand");
		JSONObject products = (JSONObject) jsonObject.get("products");

		Set<?> s = onDemand.keySet();
		Iterator<?> i = s.iterator();

		do {
			String k = i.next().toString();
			JSONObject sku = (JSONObject) products.get(k);
			String prodfam = (String) sku.get("productFamily");
			if (prodfam.equals("IP Address")) {

				ipadress.add(k);
			}
		} while (i.hasNext());
		return ipadress;
	}

	/**
	 * The getIpAdressPrice method is used to get the list of Elastic IP
	 * addresses price
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its prices
	 * @return ArrayList of the price of the product
	 */
	public static ArrayList<Object> getIpAdressPrice(JSONObject jsonObject,
			Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						JSONObject priceperunit = (JSONObject) despri
								.get("pricePerUnit");
						String res = (String) priceperunit.get("USD");
						result.add(res);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The getIpAdressDescription method is used to get the list of Elastic IP
	 * addresses Description.
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its Description
	 * @return ArrayList of the Description of the product
	 */
	public static ArrayList<Object> getIpAdressDescription(
			JSONObject jsonObject, Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						String description = (String) despri.get("description");
						result.add(description);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The AfficheIpAdress() method is used to get the list of Elastic IP
	 * addresses with their sku,location,description,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheIpAdress() throws FileNotFoundException,
			IOException, ParseException {

		ArrayList<Object> ipadress = getListIpAdressProduct();
		JSONObject jsonObject = ReadFile();
		int i = 0;

		JSONObject products = (JSONObject) jsonObject.get("products");

		System.out.println("Les produit IP Adress");
		System.out.println("sku\t\t\tlocation\t\tgroupDescription\t\tprix");

		for (i = 0; i < ipadress.size(); i++) {
			ArrayList<Object> prix = getIpAdressPrice(jsonObject,
					ipadress.get(i));
			ArrayList<Object> description = getIpAdressDescription(jsonObject,
					ipadress.get(i));
			for (int o = 0; o < prix.size(); o++) {

				JSONObject prod = (JSONObject) products.get(ipadress.get(i));
				JSONObject attributes = (JSONObject) prod.get("attributes");

				String location = (String) attributes.get("location");
				String groupDescription = (String) attributes
						.get("groupDescription");
				String sku = (String) prod.get("sku");

				System.out.println(sku + "\t" + location + "\t"
						+ groupDescription + "\t" + "\t" + prix.get(o)
						+ description.get(o));
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////
	/**
	 * The getListDataTransfert method is used to get the list of Data Transfert
	 * product
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList of the Data Transfert skus
	 */
	public static ArrayList<Object> getListDataTransfert()
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransfert = new ArrayList<>();
		JSONObject jsonObject = ReadFile();
		JSONObject terms = (JSONObject) jsonObject.get("terms");
		JSONObject onDemand = (JSONObject) terms.get("OnDemand");
		JSONObject products = (JSONObject) jsonObject.get("products");

		Set<?> s = onDemand.keySet();
		Iterator<?> i = s.iterator();

		do {
			String k = i.next().toString();
			JSONObject sku = (JSONObject) products.get(k);
			String prodfam = (String) sku.get("productFamily");
			if (prodfam.equals("Data Transfer")) {

				datatransfert.add(k);
			}
		} while (i.hasNext());
		return datatransfert;
	}

	/**
	 * The getDataTransfertPrice method is used to get the list of Data
	 * Transfert product price
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its prices
	 * @return ArrayList of the price of the product
	 */
	public static ArrayList<Object> getDataTransfertPrice(
			JSONObject jsonObject, Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						JSONObject priceperunit = (JSONObject) despri
								.get("pricePerUnit");
						String res = (String) priceperunit.get("USD");
						result.add(res);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The getDataTrasfertDescription method is used to get the list of Data
	 * Transfert product Description.
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its Description
	 * @return ArrayList of the Description of the product
	 */
	public static ArrayList<Object> getDataTransfertDescription(
			JSONObject jsonObject, Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						String description = (String) despri.get("description");
						result.add(description);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The AfficheDataTransfert() method is used to get the list of the Data
	 * Transfert products with their
	 * sku,fromlocation,tolocation,description,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheDataTransfert() throws FileNotFoundException,
			IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = ReadFile();

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Les produit Data Transfert");
		System.out
				.println("sku\t\t\tfromLocation\t\ttransfertType\t\ttoLocation\t\tdescription\t\t\t\tprix");

		for (i = 0; i < datatransfert.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfert.get(i));
			ArrayList<Object> description = getDataTransfertDescription(
					jsonObject, datatransfert.get(i));
			for (int o = 0; o < prix.size(); o++) {

				JSONObject prod = (JSONObject) products.get(datatransfert
						.get(i));
				JSONObject attributes = (JSONObject) prod.get("attributes");

				String fromLocation = (String) attributes.get("fromLocation");
				String toLocation = (String) attributes.get("toLocation");
				String sku = (String) prod.get("sku");
				String transferType = (String) attributes.get("transferType");

				System.out.println(sku + "\t" + fromLocation + "\t"
						+ transferType + "\t" + toLocation + "\t" + "\t"
						+ description.get(o) + "\t" + "\t" + "\t" + "\t" + "\t"
						+ "\t" + "\t" + prix.get(o));

			}
		}
	}

	/**
	 * The getDataTransfertIntraRegion method is used to get the list of Data
	 * Transfer Intra Region.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	public static ArrayList<Object> getDataTransfertIntraRegion()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = ReadFile();
		ArrayList<Object> datatransfertintraregion = new ArrayList<>();

		JSONObject products = (JSONObject) jsonObject.get("products");

		for (i = 0; i < datatransfert.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransfert.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String transferType = (String) attributes.get("transferType");
			if (transferType.equals("IntraRegion")) {
				datatransfertintraregion.add(datatransfert.get(i));
			}
		}
		return datatransfertintraregion;
	}

	/**
	 * The getDataTransfertInterRegion method is used to get the list of Data
	 * Transfer Inter region.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	public static ArrayList<Object> getDataTransfertInterRegion()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = ReadFile();
		ArrayList<Object> datatransfertinterregion = new ArrayList<>();

		JSONObject products = (JSONObject) jsonObject.get("products");

		for (i = 0; i < datatransfert.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransfert.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String transferType = (String) attributes.get("transferType");
			if (transferType.equals("InterRegion Outbound")) {
				datatransfertinterregion.add(datatransfert.get(i));
			}
		}
		return datatransfertinterregion;
	}

	/**
	 * The getDataTransfertIn method is used to get the list of Data Transfer
	 * In.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	public static ArrayList<Object> getDataTransfertIn()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = ReadFile();
		ArrayList<Object> datatransfertin = new ArrayList<>();

		JSONObject products = (JSONObject) jsonObject.get("products");

		for (i = 0; i < datatransfert.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransfert.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String transferType = (String) attributes.get("transferType");
			if (transferType.equals("AWS Inbound")) {
				datatransfertin.add(datatransfert.get(i));
			}
		}
		return datatransfertin;
	}

	/**
	 * The getDataTransfertOut method is used to get the list of Data Transfer
	 * In.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	public static ArrayList<Object> getDataTransfertOut()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = ReadFile();
		ArrayList<Object> datatransfertout = new ArrayList<>();

		JSONObject products = (JSONObject) jsonObject.get("products");

		for (i = 0; i < datatransfert.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransfert.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String transferType = (String) attributes.get("transferType");
			if (transferType.equals("AWS Outbound")) {
				datatransfertout.add(datatransfert.get(i));
			}
		}
		return datatransfertout;
	}

	/**
	 * The AfficheDataTransfertInterRegionOut method is used to get the list of
	 * the Data Transfert Inter Region fromlocation,tolocation,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheDataTransfertInterRegionOut()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfertinterregion = getDataTransfertInterRegion();
		JSONObject jsonObject = ReadFile();

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Inter Region Data Transfer Out");
		System.out.println("fromLocation\t\t\t\ttoLocation\t\tprix");

		for (i = 0; i < datatransfertinterregion.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfertinterregion.get(i));

			JSONObject prod = (JSONObject) products
					.get(datatransfertinterregion.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String fromLocation = (String) attributes.get("fromLocation");
			String toLocation = (String) attributes.get("toLocation");

			System.out.println(fromLocation + "\t" + "\t" + toLocation + "\t"
					+ "\t" + prix.get(0));

		}

	}

	/**
	 * The AfficheDataTransfertOut method is used to get the list of the Data
	 * Transfert out tolocation,description,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheDataTransfertOut() throws FileNotFoundException,
			IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfertout = getDataTransfertOut();
		JSONObject jsonObject = ReadFile();

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Data Transfer Out");
		System.out.println("fromLocation\t\t\tdescription\t\t\t\t\t\tprix");

		for (i = 0; i < datatransfertout.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfertout.get(i));
			ArrayList<Object> description = getDataTransfertDescription(
					jsonObject, datatransfertout.get(i));
			for (int o = 0; o < description.size(); o++) {
				JSONObject prod = (JSONObject) products.get(datatransfertout
						.get(i));
				JSONObject attributes = (JSONObject) prod.get("attributes");

				String fromLocation = (String) attributes.get("fromLocation");

				System.out.println(fromLocation + "\t" + "\t"
						+ description.get(o) + "\t" + prix.get(o));
			}

		}
	}

	/**
	 * The AfficheDataTransfertIn method is used to get the list of the Data
	 * Transfert out fromlocation,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheDataTransfertIn() throws FileNotFoundException,
			IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfertin = getDataTransfertIn();
		JSONObject jsonObject = ReadFile();

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Data Transfer In");
		System.out.println("toLocation\t\tprix");

		for (i = 0; i < datatransfertin.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfertin.get(i));

			JSONObject prod = (JSONObject) products.get(datatransfertin.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");

			String toLocation = (String) attributes.get("toLocation");
			System.out.println(toLocation + "\t" + "\t" + prix.get(0));

		}
	}

	/**
	 * The AfficheDataTransfertIntraRegion method is used to get the list of the
	 * Data Transfer Intra Region fromlocation,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheDataTransfertIntraRegion()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfertintraregion = getDataTransfertIntraRegion();
		JSONObject jsonObject = ReadFile();

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Intra Region Data Transfer");
		System.out.println("Location\t\tprix");

		for (i = 0; i < datatransfertintraregion.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfertintraregion.get(i));
			for (int o = 0; o < prix.size(); o++) {

				JSONObject prod = (JSONObject) products
						.get(datatransfertintraregion.get(i));
				JSONObject attributes = (JSONObject) prod.get("attributes");

				String Location = (String) attributes.get("fromLocation");
				System.out.println(Location + "\t" + "\t" + prix.get(o));

			}
		}
	}

	/**
	 * The getInstancePrice method is used to get the price of the instance
	 * 
	 * @param instance
	 *            this is the instance Type .
	 * @param location
	 *            this is the location of the instance .
	 * @param system
	 *            this is the operating system of the instance .
	 * @return float this returns the price of the instance per hour
	 */
	public static float getInstancePrice(String instance, String location,
			String system) throws FileNotFoundException, IOException,
			ParseException {
		ArrayList<Object> demandShared = getListOnDemandShared();

		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		int i;
		float price = 0;
		for (i = 0; i < demandShared.size(); i++) {

			JSONObject prod = (JSONObject) products.get(demandShared.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");

			String flocation = (String) attributes.get("location");
			String fsystem = (String) attributes.get("operatingSystem");
			String finstance = (String) attributes.get("instanceType");
			if ((finstance.equals(instance)) && (flocation.equals(location))
					&& (fsystem.equals(system))) {

				price = OnDemandInstanceSharedPrice(demandShared.get(i),
						jsonObject);
			}

		}
		return price;

	}

	/**
	 * The getPriceInstanceIntra method is used to get the price of the instance
	 * with data transfer Intra Region
	 * 
	 * @param instance
	 *            this is the instance Type .
	 * @param location
	 *            this is the location of the instance .
	 * @param system
	 *            this is the operating system of the instance .
	 * @param locationintra
	 *            this is the location of the inta region .
	 * @return Nothing
	 */
	public static void getPriceInstanceIntra(String instance, String location,
			String system, String locationintra) throws FileNotFoundException,
			IOException, ParseException {

		ArrayList<Object> datatransfertintraregion = getDataTransfertIntraRegion();

		Float price = getInstancePrice(instance, location, system);
		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		for (int i = 0; i < datatransfertintraregion.size(); i++) {
			JSONObject prod = (JSONObject) products
					.get(datatransfertintraregion.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String flocation = (String) attributes.get("fromLocation");
			if (flocation.equals(locationintra)) {

				prix = getDataTransfertPrice(jsonObject,
						datatransfertintraregion.get(i));
				String p = (String) prix.get(0);
				System.out.println(p + " GB/month");
				System.out.println(price + " per hour");
			}

		}

	}

	/**
	 * The getPriceInstanceIn method is used to get the price of the instance
	 * with data transfer In
	 * 
	 * @param instance
	 *            this is the instance Type .
	 * @param location
	 *            this is the location of the instance .
	 * @param system
	 *            this is the operating system of the instance .
	 * @param tolocation
	 *            this is the location of the in data transfer in .
	 * @return Nothing
	 */
	public static void getPriceInstanceIn(String instance, String location,
			String system, String tolocation) throws FileNotFoundException,
			IOException, ParseException {

		ArrayList<Object> datatransfertin = getDataTransfertIn();

		Float price = getInstancePrice(instance, location, system);
		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		for (int i = 0; i < datatransfertin.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransfertin.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String flocation = (String) attributes.get("toLocation");
			if (flocation.equals(tolocation)) {

				prix = getDataTransfertPrice(jsonObject, datatransfertin.get(i));
				String p = (String) prix.get(0);
				System.out.println(p + " GB/month");
				System.out.println(price + " per hour");
			}

		}

	}

	/**
	 * The getPriceInstanceInter method is used to get the price of the instance
	 * with the inter region data transfert
	 * 
	 * @param instance
	 *            this is the instance Type .
	 * @param location
	 *            this is the location of the instance .
	 * @param system
	 *            this is the operating system of the instance .
	 * @param fromlocation
	 *            this is the fromlocation of the inter region data transfer .
	 * @param tolocation
	 *            this is the tolocation of the inter region data transfer .
	 * @return Nothing
	 */
	public static void getPriceInstanceInter(String instance, String location,
			String system, String fromlocation, String tolocation)
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransfertinterregion = getDataTransfertInterRegion();

		Float price = getInstancePrice(instance, location, system);
		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		for (int i = 0; i < datatransfertinterregion.size(); i++) {
			JSONObject prod = (JSONObject) products
					.get(datatransfertinterregion.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String ffromlocation = (String) attributes.get("fromLocation");
			String ftolocation = (String) attributes.get("toLocation");
			if (ffromlocation.equals(fromlocation)
					&& (ftolocation.equals(tolocation))) {

				prix = getDataTransfertPrice(jsonObject,
						datatransfertinterregion.get(i));
				String p = (String) prix.get(0);
				System.out.println(p + " GB/month");
				System.out.println(price + " per hour");
			}

		}

	}

	/**
	 * The getPriceInstanceOut method is used to get the price of the instance
	 * with the data transfer out
	 * 
	 * @param instance
	 *            this is the instance Type .
	 * @param location
	 *            this is the location of the instance .
	 * @param system
	 *            this is the operating system of the instance .
	 * @param fromlocation
	 *            this is the location of the data transfert out .
	 * @return Nothing
	 */
	public static void getPriceInstanceOut(String instance, String location,
			String system, String fromlocation) throws FileNotFoundException,
			IOException, ParseException {

		ArrayList<Object> datatransfertout = getDataTransfertOut();
		int i = 0;
		Float price = getInstancePrice(instance, location, system);
		JSONObject jsonObject = ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		for (i = 0; i < datatransfertout.size(); i++) {
			JSONObject prod = (JSONObject) products
					.get(datatransfertout.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String ffromlocation = (String) attributes.get("fromLocation");
			if (ffromlocation.equals(fromlocation)) {

				prix = getDataTransfertPrice(jsonObject,
						datatransfertout.get(i));

				for (i = 0; i < prix.size(); i++) {
					System.out.println(prix.get(i));
				}
				System.out.println(price + " per hour");
			}

		}

	}

	// ////////////////////////////////////////////////////////////////

	/**
	 * The getListDataTransfert method is used to get the list of Load Balancing
	 * product
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList of the Load Balancing skus
	 */
	public static ArrayList<Object> getListLoadBalance()
			throws FileNotFoundException, IOException, ParseException {
		JSONObject jsonObject = ReadFile();
		ArrayList<Object> loadBalance = new ArrayList<>();
		JSONObject terms = (JSONObject) jsonObject.get("terms");
		JSONObject onDemand = (JSONObject) terms.get("OnDemand");
		JSONObject products = (JSONObject) jsonObject.get("products");

		Set<?> s = onDemand.keySet();
		Iterator<?> i = s.iterator();

		do {
			String k = i.next().toString();
			JSONObject sku = (JSONObject) products.get(k);
			String prodfam = (String) sku.get("productFamily");
			if (prodfam.equals("Load Balancer")) {

				loadBalance.add(k);
			}
		} while (i.hasNext());
		return loadBalance;

	}

	/**
	 * The getLoadBalancePrice method is used to get the list of Load Balancing
	 * product price
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its prices
	 * @return ArrayList of the price of the product
	 */
	public static ArrayList<Object> getLoadBalancePrice(JSONObject jsonObject,
			Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						JSONObject priceperunit = (JSONObject) despri
								.get("pricePerUnit");
						String res = (String) priceperunit.get("USD");
						result.add(res);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The getLoadBalanceDescription method is used to get the list of Data
	 * Transfert product Description.
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its Description
	 * @return ArrayList of the Description of the product
	 */
	public static ArrayList<Object> getLoadBalanceDescription(
			JSONObject jsonObject, Object object) {

		JSONObject posts = (JSONObject) jsonObject.get("terms");
		JSONObject DemandPord = (JSONObject) posts.get("OnDemand");

		JSONObject id = (JSONObject) DemandPord.get(object);
		ArrayList<Object> result = new ArrayList<>();
		Set<?> s1 = id.keySet();

		Iterator<?> i1 = s1.iterator();

		do {
			String l = i1.next().toString();
			{

				JSONObject euff = (JSONObject) id.get(l);
				JSONObject priceDim = (JSONObject) euff.get("priceDimensions");

				Set<?> s2 = priceDim.keySet();

				Iterator<?> i2 = s2.iterator();

				do {
					String k = i2.next().toString();
					{
						JSONObject despri = (JSONObject) priceDim.get(k);
						String description = (String) despri.get("description");
						result.add(description);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The AfficheLoadBalance method is used to get the list of the Load
	 * Balancing products with their sku,location,description,price.
	 * 
	 * @param Nothing
	 *            .
	 * @return Nothing.
	 */
	public static void AfficheLoadBalance() throws FileNotFoundException,
			IOException, ParseException {
		ArrayList<Object> loadBalance = getListLoadBalance();
		JSONObject jsonObject = ReadFile();
		int i = 0;

		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out.println("Les produit Load Balancing");

		System.out.println("sku\t\t\tLocation\t\t\t\t\tdescription\t\t\tprix");

		for (i = 0; i < loadBalance.size(); i++) {
			ArrayList<Object> prix = getLoadBalancePrice(jsonObject,
					loadBalance.get(i));
			ArrayList<Object> description = getLoadBalanceDescription(
					jsonObject, loadBalance.get(i));
			for (int o = 0; o < prix.size(); o++) {

				JSONObject prod = (JSONObject) products.get(loadBalance.get(i));
				JSONObject attributes = (JSONObject) prod.get("attributes");

				String Location = (String) attributes.get("location");
				String sku = (String) prod.get("sku");

				System.out.println(sku + "\t" + Location + "\t" + "\t"
						+ description.get(o) + "\t" + "\t" + prix.get(o));
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////

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
	 * @see FileNotFoundException
	 * @see ParseException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException {

		// AfficheListOnDemandShared();
		// getInformationInstance(16, "30.5 GiB");
		// AfficheIpAdress();
		// AfficheDataTransfert();
		// AfficheLoadBalance();
		
		// AfficheDataTransfertIntraRegion();
		// AfficheDataTransfertIn();
		// AfficheDataTransfertOut();
		// AfficheDataTransfertInterRegionOut();
	
		// getPriceInstanceIntra("t1.micro", "US East (N. Virginia)", "Linux",
		// "US East (N. Virginia)");
		// getPriceInstanceInter("t1.micro", "US East (N. Virginia)", "Linux",
		// "EU (Ireland)", "US East (N. Virginia)");
		getPriceInstanceIn("t1.micro", "US East (N. Virginia)", "Linux",
				"US East (N. Virginia)");
		// getPriceInstanceOut("t1.micro", "US East (N. Virginia)", "Linux",
		// "US East (N. Virginia)");
	}
}
