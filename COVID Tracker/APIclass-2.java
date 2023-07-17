package covid19analysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class APIclass {

	static HashMap<Integer, String> dateStatistics = new HashMap<>();
	static HashMap<Integer, String> statSearch = new HashMap<>();
	static HashMap<Integer, String> compare = new HashMap<>();
	//static JSONObject that has parsed the API, so every method can access it
	static JSONObject myObject;

	
	//Method to parse API once only, so I don't need to do it for each method
	public static JSONObject parseCountry() {
		try {

			URL url = new URL("https://pomber.github.io/covid19/timeseries.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			JSONParser jsonParser = new JSONParser();
			myObject = (JSONObject) jsonParser.parse(br);

		} catch (Exception ex) {

		}
		return myObject;

	}

	//Validate user input for Country method
	public static boolean validateCountry(String countryName) {

		boolean validCountryName = false;

		Set<String> keys = myObject.keySet();
		Set<String> s = new TreeSet<String>();
		s.addAll(keys);
		try {
			// TreeSet.contains method is O(log n) time complexity, which should be faster
			// than looping through Array
			if (s.contains(countryName)) {
				validCountryName = true;

			} else if (!s.contains(countryName)) {
				System.out.println("\nInvalid Country, please try again\n");
				validCountryName = false;
			}
		} catch (Exception e) {

		}

		return validCountryName;

	}
	
	
	//Search by date for two country (Binary Search)

	public static void compareCountry(String country1, String country2, String userDate) {

		JSONArray countryArray1 = (JSONArray) myObject.get(country1);

		int left = 0, right = countryArray1.size() - 1, pivot = -1;

		while (left <= right) {
			pivot = left + (right - left) / 2;
			JSONObject c = (JSONObject) countryArray1.get(pivot);
			String date = (String) c.get("date");
			if (userDate.compareTo(date) == 0) {

				break;
			}
			if (userDate.compareTo(date) < 0) {
				left = pivot + 1;
			} else if (userDate.compareTo(date) > 0) {
				right = pivot - 1;
			}

		}
		JSONObject specData = (JSONObject) countryArray1.get(pivot);
		Long confirmed1 = (Long) specData.get("confirmed");
		Long deaths1 = (Long) specData.get("deaths");
		Long recovered1 = (Long) specData.get("recovered");

		JSONArray countryArray2 = (JSONArray) myObject.get(country2);
		int left1 = 0, right1 = countryArray2.size() - 1, pivot1 = -1;

		while (left1 <= right1) {
			pivot1 = left1 + (right1 - left1) / 2;
			JSONObject b = (JSONObject) countryArray2.get(pivot1);
			String date = (String) b.get("date");
			if (userDate.compareTo(date) == 0) {

				break;
			}
			if (userDate.compareTo(date) < 0) {
				left1 = pivot1 + 1;
			} else if (userDate.compareTo(date) > 0) {
				right1 = pivot1 - 1;
			}

		}

		specData = (JSONObject) countryArray2.get(pivot1);
		Long confirmed2 = (Long) specData.get("confirmed");
		Long deaths2 = (Long) specData.get("deaths");
		Long recovered2 = (Long) specData.get("recovered");

		compare.put(0, "\n" + country1 + " had " + confirmed1 + " confirmed cases");
		compare.put(1, country2 + " had " + confirmed2 + " confirmed cases");
		compare.put(2, "\n" + country1 + " had " + deaths1 + " deaths");
		compare.put(3, country2 + " had " + deaths2 + " deaths");
		compare.put(4, "\n" + country1 + " had " + recovered1 + " recoveries");
		compare.put(5, country2 + " had " + recovered2 + " recoveries");

	}

	//Search by date for specified country (Binary Search)
	public static void dateStat(String countryName, String userDate) {

		JSONArray countryArray = (JSONArray) myObject.get(countryName);
		int left = 0, right = countryArray.size() - 1, pivot = -1;

		while (left <= right) {
			pivot = left + (right - left) / 2;
			JSONObject c = (JSONObject) countryArray.get(pivot);
			String date = (String) c.get("date");
			if (userDate.compareTo(userDate) == 0) {

				break;
			} else if (userDate.compareTo(date) < 0) {
				left = pivot + 1;
			} else if (userDate.compareTo(date) > 0) {
				right = pivot - 1;
			}

		}

		JSONObject specData = (JSONObject) countryArray.get(pivot);
		Long confirmed = (Long) specData.get("confirmed");
		Long deaths = (Long) specData.get("deaths");
		Long recovered = (Long) specData.get("recovered");
		dateStatistics.put(0, "\nOn " + userDate + ", " + countryName + " had:");
		dateStatistics.put(1, confirmed + " confirmed cases");
		dateStatistics.put(2, deaths + " deaths");
		dateStatistics.put(3, recovered + " recoveries");

	}

	//Search method for statistic, using JSONArray
	public static void statSearch(String countryName, String statistic, Long data) {

		String hashMapInput = null;
		if (statistic.equals("confirmed")) {
			hashMapInput = " confirmed cases";
		} else if (statistic.equals("deaths")) {
			hashMapInput = " deaths";
		} else if (statistic.equals("recovered")) {
			hashMapInput = " recoveries";
		}
		int index = -1;

		JSONArray countryArray = (JSONArray) myObject.get(countryName);
		for (int i = 0; i < countryArray.size(); i++) {
			JSONObject c = (JSONObject) countryArray.get(i);
			Long userStat = (Long) c.get(statistic);

			if (userStat.equals(data)) {
				index = i;
			}

		}
		if (index < 0) {
			System.out.println("\nNo results returned for " + data + hashMapInput);
		} else if (index > 0) {
			JSONObject specData = (JSONObject) countryArray.get(index);
			String date = (String) specData.get("date");
			Long confirmed = (Long) specData.get("confirmed");
			Long deaths = (Long) specData.get("deaths");
			Long recovered = (Long) specData.get("recovered");
			statSearch.put(0, "\nIt was " + date + " when " + countryName + " reached " + data + hashMapInput);
			statSearch.put(1, "\nOn this date, " + countryName + " also reached:");
			statSearch.put(2, confirmed + " confirmed cases");
			statSearch.put(3, deaths + " deaths");
			statSearch.put(4, recovered + " recoveries");
		}

	}

	public static HashMap<Integer, String> getDateStatistics() {
		return dateStatistics;
	}

	public static HashMap<Integer, String> getStatSearch() {
		return statSearch;
	}

	public static HashMap<Integer, String> getCompare() {
		return compare;
	}

}
