package covid19analysis;

import org.json.JSONObject;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TestClass {

	// Main test class for COVID-19 Analysis Application
	// Stanley Wang
	// 5/14/2022
	// SID: 950724572

	static Scanner sc = new Scanner(System.in);
	private static HashMap<Integer, String> testDateStat = new HashMap<>();
	private static HashMap<Integer, String> testStatSearch = new HashMap<>();
	private static HashMap<Integer, String> testCompare = new HashMap<>();

	public static void compareCountry() throws Exception {
		String country1 = null;
		String country2 = null;
		boolean validCountryName = false;
		System.out.println("\nPlease enter your first country (Case Sensitive)\n");
		while (validCountryName == false) {
			country1 = sc.nextLine();

			validCountryName = APIclass.validateCountry(country1);

		}
		validCountryName = false;
		System.out.println("\nPlease enter your second country (Case Sensitive)\n");
		while (validCountryName == false) {

			country2 = sc.nextLine();

			validCountryName = APIclass.validateCountry(country2);

		}
		System.out.println("\nNow please enter a date in this format (YYYY-MM-DD)\n"
				+ "Omit zeros if inputting singular digit months and dates\n" + "For ex. YYYY-5-6 NOT YYYY-05-06!\n");

		String date = sc.nextLine();
		
		APIclass.compareCountry(country1, country2, date);
		testCompare.putAll(APIclass.getCompare());

		System.out.println("\nOn " + date + ":");
		for (int i = 0; i < testCompare.size(); i++) {
			System.out.println(testCompare.get(i));

		}
		

	}

	public static void countryCovidInfo() throws Exception {

		boolean validCountryName = false;
		String country = null;
		boolean validDateStat = false;
		while (validCountryName == false) {

			System.out.println("\nWhat country would you like to check data for? (Case Sensitive)\n");
			country = sc.nextLine(); // "New Zealand"

			// country = temp.substring(0, 1).toUpperCase() +
			// temp.substring(1).toLowerCase();
			// Above code doesn't work with multiple word Countries (i.e New Zealand)
			validCountryName = APIclass.validateCountry(country);
		}
		System.out.println("\nWould you like to search by date or by a statistic?\n");
		String search = sc.nextLine();

		while (!validDateStat) {
			if (search.equalsIgnoreCase("date")) {
				System.out.println("\nPlease enter a date in this format (YYYY-MM-DD)\n"
						+ "Omit zeros if inputting singular digit months and dates\n"
						+ "For ex. YYYY-5-6 NOT YYYY-05-06!\n");

				String date = sc.nextLine();

				APIclass.dateStat(country, date);
				testDateStat.putAll(APIclass.getDateStatistics());

				// print text version
				for (int i = 0; i < testDateStat.size(); i++) {
					System.out.println(testDateStat.get(i));

					validDateStat = true;
				}
			}

			else if (search.equalsIgnoreCase("statistic") || search.equalsIgnoreCase("stat")) {
				System.out.println("\nSearch by\n1. Confirmed Cases\n2. Death Number\n3. Recovery Number\n");
				String stat = null;
				String userChoice = null;
				Long userLong = null;

				boolean validStatOption = false;
				validDateStat = true;
				while (!validStatOption) {

					stat = sc.nextLine();

					// add error handling

					if (stat.equals("1")) {

						userChoice = "confirmed";
						System.out.println("\nFind the date and corresponding information for when " + country
								+ " had _______ confirmed cases\n");
						userLong = (long) -1;
						do {
							try {
								userLong = sc.nextLong();

							} catch (InputMismatchException e) {
								System.out.println("\nPlease enter a number\n");

							}
							sc.nextLine();
						} while (userLong < 0);

						validStatOption = true;
					}

					else if (stat.equals("2")) {

						userChoice = "deaths";
						System.out.println("\nFind the date and corresponding information for when " + country
								+ " had _______ deaths\n");
						userLong = (long) -1;
						do {
							try {
								userLong = sc.nextLong();

							} catch (InputMismatchException e) {
								System.out.println("\nPlease enter a number\n");

							}
							sc.nextLine();
						} while (userLong < 0);

						validStatOption = true;
					}

					else if (stat.equals("3")) {

						userChoice = "recovered";
						System.out.println("\nFind the date and corresponding information for when " + country
								+ " had _______ recoveries\n");
						userLong = (long) -1;
						do {
							try {
								userLong = sc.nextLong();

							} catch (InputMismatchException e) {
								System.out.println("\nPlease enter a number\n");

							}
							sc.nextLine();
						} while (userLong < 0);

						validStatOption = true;
					}

					else {
						System.out.println("\nPlease select an option between 1-3\n");
						validStatOption = false;

					}
				}

				APIclass.statSearch(country, userChoice, userLong);

				testStatSearch.putAll(APIclass.getStatSearch());
				// print text version

				for (int i = 0; i < testStatSearch.size(); i++) {
					System.out.println(testStatSearch.get(i));

				}

			} else {

				validDateStat = false;
				System.out.println("\nPlease select date or statistic\n");
				search = sc.nextLine();
			}

		}
	}

	public static void main(String[] args) throws Exception {
		APIclass.parseCountry();
		System.out.println("Welcome to Stanley's COVID-19 Analysis App!");
		boolean program = true;
		boolean rerun = true;
		while (program) {
			System.out.println("\nWould you like to:\n\n1. Compare two country's COVID statistics based off date"
					+ "\n2. Search by statistic or date for one country\n");
			String firstChoice = sc.nextLine();
			if (firstChoice.equals("1")) {
				compareCountry();
				System.out.println("\nWould you like to run the program again?\n1. Yes\n2. No\n");
				while (rerun) {
					
					String repeat = sc.nextLine();
					if (repeat.equals("1")) {
						program = true;
						rerun = false;

					} else if (repeat.equals("2")) {
						program = false;
						rerun = false;

					} else {
						System.out.println("\nPlease select:\n1. Yes\n2. No\n");
						rerun = true;

					}
				}

			}

			else if (firstChoice.equals("2")) {
				countryCovidInfo();
				
				System.out.println("\nWould you like to run the program again?\n1. Yes\n2. No\n");
				while (rerun) {

					String repeat = sc.nextLine();
					if (repeat.equals("1")) {
						program = true;
						rerun = false;

					} else if (repeat.equals("2")) {
						program = false;
						rerun = false;

					} else {
						System.out.println("\nPlease select:\n1. Yes\n2. No\n");
						rerun = true;

					}
				}
				// program = false;

				// sc.nextLine();
			} else {
				System.out.println("\nPlease select a valid option");
				program = true;

			}
		}

	}
}