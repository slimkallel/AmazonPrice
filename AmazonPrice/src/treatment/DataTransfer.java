package treatment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * The DataTransfer program implements many methods that make the treatment of the
 * data trasnfer from Json file and Prints the output on the screen.
 * 
 * 
 * @author Imen NEJI and Slim KALLEL
 * @version 1.0
 * @since 2017-04-26
 */
public class DataTransfer {

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
	static ArrayList<Object> getDataTransfertDescription(JSONObject jsonObject,
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
						String description = (String) despri.get("description");
						result.add(description);

					}
				} while (i2.hasNext());

			}
		} while (i1.hasNext());

		return result;
	}

	/**
	 * The getDataTransfertIn method is used to get the list of Data Transfer
	 * In.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	static ArrayList<Object> getDataTransfertIn() throws FileNotFoundException,
			IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
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
	 * The getDataTransfertInterRegion method is used to get the list of Data
	 * Transfer Inter region.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	static ArrayList<Object> getDataTransfertInterRegion()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
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
	 * The getDataTransfertIntraRegion method is used to get the list of Data
	 * Transfer Intra Region.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	static ArrayList<Object> getDataTransfertIntraRegion()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
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
	 * The getDataTransfertOut method is used to get the list of Data Transfer
	 * In.
	 * 
	 * @param Nothing
	 * @return ArrayList
	 */
	static ArrayList<Object> getDataTransfertOut()
			throws FileNotFoundException, IOException, ParseException {
		int i = 0;
		ArrayList<Object> datatransfert = getListDataTransfert();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
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
	 * The getDataTransfertPrice method is used to get the list of Data
	 * Transfert product price
	 * 
	 * @param jsonObject
	 *            this is the json file.
	 * @param object
	 *            this is the sku of the product that we want its prices
	 * @return ArrayList of the price of the product
	 */
	static ArrayList<Object> getDataTransfertPrice(JSONObject jsonObject,
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
	 * The getListDataTransfert method is used to get the list of Data Transfert
	 * product
	 * 
	 * @param Nothing
	 *            .
	 * @return ArrayList of the Data Transfert skus
	 */
	static ArrayList<Object> getListDataTransfert()
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransfert = new ArrayList<>();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
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
	 * The getPriceIn method is used to get the price of the instance with data
	 * transfer In
	 * 
	 * @param location
	 *            this is the location .
	 * @param qte
	 *            this is the amount of GB per month .
	 * @return float price.
	 */
	static float getPriceIn(String location, int qte)
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransferin = getDataTransfertIn();

		JSONObject jsonObject = OnDemandTreatment.ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		float price = 0;
		for (int i = 0; i < datatransferin.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransferin.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String ftolocation = (String) attributes.get("toLocation");
			prix = getDataTransfertPrice(jsonObject, datatransferin.get(i));
			String p = (String) prix.get(0);
			if (ftolocation.equals(location)) {

				System.out.println("the price of the data transfert In: "
						+ Float.parseFloat(p) * qte + " $ GB/month");
				price = Float.parseFloat(p) * qte;
			}

		}
		return price;
	}

	/**
	 * The getPriceInter method is used to get the price of the instance with
	 * data transfer Inter region
	 * 
	 * @param fromlocation
	 *            this is the location wich data transfret is from .
	 * @param tolocation
	 *            this is the location wich data transfret is to.
	 * @param qte
	 *            this is the amount of GB per month .
	 * @return float price.
	 */
	static float getPriceInter(String fromlocation, String tolocation, int qte)
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransfertinterregion = getDataTransfertInterRegion();

		JSONObject jsonObject = OnDemandTreatment.ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		float price = 0;
		for (int i = 0; i < datatransfertinterregion.size(); i++) {
			JSONObject prod = (JSONObject) products
					.get(datatransfertinterregion.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String ffromlocation = (String) attributes.get("fromLocation");
			String ftolocation = (String) attributes.get("toLocation");
			prix = getDataTransfertPrice(jsonObject,
					datatransfertinterregion.get(i));
			String p = (String) prix.get(0);
			if (ffromlocation.equals(fromlocation)
					&& (ftolocation.equals(tolocation))) {

				System.out
						.println("the price of the data transfert Inter region: "
								+ Float.parseFloat(p) * qte + " $ GB/month");
				price = Float.parseFloat(p) * qte;

			}

		}
		return price;

	}

	/**
	 * The getPriceIntra method is used to get the price of the instance with
	 * data transfer Intra region
	 * 
	 * @param location
	 *            this is the location .
	 * @param qte
	 *            this is the amount of GB per month .
	 * @return float price.
	 */
	static float getPriceIntra(String location, int qte)
			throws FileNotFoundException, IOException, ParseException {

		ArrayList<Object> datatransferintra = getDataTransfertIntraRegion();

		JSONObject jsonObject = OnDemandTreatment.ReadFile();
		JSONObject products = (JSONObject) jsonObject.get("products");
		ArrayList<Object> prix = new ArrayList<>();
		float price = 0;
		for (int i = 0; i < datatransferintra.size(); i++) {
			JSONObject prod = (JSONObject) products.get(datatransferintra
					.get(i));
			JSONObject attributes = (JSONObject) prod.get("attributes");
			String ffromlocation = (String) attributes.get("fromLocation");
			prix = getDataTransfertPrice(jsonObject, datatransferintra.get(i));
			String p = (String) prix.get(0);
			if (ffromlocation.equals(location)) {

				System.out
						.println("the price of the data transfert intra region: "
								+ Float.parseFloat(p) * qte + " $ GB/month");
				price = Float.parseFloat(p) * qte;
			}

		}
		return price;
	}

	/**
	 * The getPriceOut method is used to get the price of the instance with data
	 * transfer Out
	 * 
	 * @param fromlocation
	 *            this is the location where data transfret is from .
	 * @param qte
	 *            this is the amount of GB per month .
	 * @return float price.
	 */
	static float getPriceOut(String fromlocation, int qte)
			throws FileNotFoundException, IOException, ParseException {

		int i = 0;
		ArrayList<Object> datatransfertout = getDataTransfertOut();
		JSONObject jsonObject = OnDemandTreatment.ReadFile();
		float price = 0;
		JSONObject products = (JSONObject) jsonObject.get("products");
		System.out
				.println("The data transfert out is supposed to be less than 10TB/Month");
		for (i = 0; i < datatransfertout.size(); i++) {
			ArrayList<Object> prix = getDataTransfertPrice(jsonObject,
					datatransfertout.get(i));
			ArrayList<Object> description = getDataTransfertDescription(
					jsonObject, datatransfertout.get(i));
			for (int o = 0; o < description.size(); o++) {
				if (description.get(o).toString().contains("first 10 TB")) {
					JSONObject prod = (JSONObject) products
							.get(datatransfertout.get(i));
					JSONObject attributes = (JSONObject) prod.get("attributes");

					String ffromLocation = (String) attributes
							.get("fromLocation");
					if (fromlocation.equals(ffromLocation)) {
						price = Float.parseFloat(prix.get(o).toString());
						System.out
								.println("the price of the data transfert Out: "
										+ price * qte + " $ GB/month");
						price = price * qte;
					}

				}
			}
		}
		return price;
	}

	/**
	 * The SommeDataTransfert method is used to get the price of the Data
	 * Transfer
	 * 
	 * @param location
	 *            this is the location of the instance
	 * @return float.
	 */
	static float SommeDataTransfert(String location, int qte1, int qte2,
			int qte3, int qte4) throws FileNotFoundException, IOException,
			ParseException {
		float price = 0;
		price = getPriceIn(location, qte1);
		// we should indicate the toLocation in the inter region
		price = price + getPriceInter(location, "EU (Ireland)", qte2);
		price = price + getPriceIntra(location, qte3);
		price = price + getPriceOut(location, qte4);
		return price;
	}

}
