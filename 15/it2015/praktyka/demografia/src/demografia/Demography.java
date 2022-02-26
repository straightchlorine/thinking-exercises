package demografia;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RecursionFinish extends Exception {
	private int lastPopulation = 0;

	RecursionFinish(int population) {
		this.lastPopulation = population;
	}

	public int getPopulation() {
		return lastPopulation;
	}
}

class ThirdExercise extends Demography {

	private static HashMap<String, HashMap<Integer, Boolean>> indexedForecast = new HashMap<>();
	private static double pace = 0.0;
	private static int population2013 = 0;
	private static int population2014 = 0;
	private static int nationalPopulation2025 = 0;

	private static boolean overpopulationFlag = false;

	private static int getPopulation2013(ArrayList<Integer> population) {
		ThirdExercise.population2013 = population.get(0) + population.get(1);
		return population2013;
	}

	private static int getPopulation2014(ArrayList<Integer> population) {
		ThirdExercise.population2014 = population.get(2) + population.get(3);
		return population2014;
	}

	private static double getPace() {
		double toRound = ((double) population2014 / population2013 * 10000) / 10000;

		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.DOWN);

		ThirdExercise.pace = Double.parseDouble(df.format(toRound).replace(',', '.'));
		return pace;
	}

	private static void basicCalculations(ArrayList<Integer> population) {
		getPopulation2013(population);
		getPopulation2014(population);
	}

	protected static int countOverpopulated() {
		int overpopulatedProvinces = 1;
		for (var entry : indexedForecast.entrySet()) {

			for (var internalEntry : entry.getValue().entrySet())
				if (internalEntry.getValue()) {
					overpopulatedProvinces++;
				}
		}

		return overpopulatedProvinces;
	}

	protected static String findMostPopulated() {
		String mostPopulated = null;
		ArrayList<Integer> populationOnly = new ArrayList<>();

		for (var entry : indexedForecast.entrySet()) {
			for (var internalEntry : entry.getValue().entrySet()) {
				populationOnly.add(internalEntry.getKey());
			}
		}

		Collections.sort(populationOnly, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 > o2)
					return -1;
				else if (o2 > o1)
					return 1;
				else
					return 0;
			}
		});

		int theLargestPopulation = populationOnly.get(0);

		for (var entry : indexedForecast.entrySet()) {
			for (var internalEntry : entry.getValue().entrySet()) {
				if (internalEntry.getKey() == theLargestPopulation)
					mostPopulated = entry.getKey();
			}
		}

		return "w" + mostPopulated;
	}

	protected static int getNationalPopulation() {
		return nationalPopulation2025;
	}

	private static int getPopulationForecast(int desiredYear) throws RecursionFinish {
		if (desiredYear == 2015)
			return (int) Math.floor(population2014 * pace);
		else {
			int currentPopulation = (int) Math.floor(getPopulationForecast(desiredYear - 1));
			double ratio = (double) currentPopulation / population2013;
			if (ratio >= 2.0) {
				overpopulationFlag = true;
				throw new RecursionFinish(currentPopulation);
			} else
				return (int) Math.floor(getPopulationForecast(desiredYear - 1) * pace);
		}
	}

	protected static HashMap<String, HashMap<Integer, Boolean>> provincialForecast(
			HashMap<String, ArrayList<Integer>> provinces) {

		for (var entry : provinces.entrySet()) {
			HashMap<Integer, Boolean> forecast = new HashMap<>();
			basicCalculations(entry.getValue());

			int expectedPopulation = 0;
			try {
				double ratio = (double) population2014 / population2013;
				if (ratio >= 2.0) {
					overpopulationFlag = true;
					expectedPopulation = population2014;
				} else {
					getPace();
					expectedPopulation = getPopulationForecast(2025);
				}
			} catch (RecursionFinish e) {
				expectedPopulation = e.getPopulation();
			}

			forecast.put(expectedPopulation, overpopulationFlag);
			indexedForecast.put(entry.getKey(), forecast);

			nationalPopulation2025 += expectedPopulation;
			overpopulationFlag = false;
		}

		return indexedForecast;
	}

}

class SecondExercise extends Demography {

	private static boolean checkGrowth(ArrayList<Integer> population) {
		return (population.get(0) < population.get(2) && population.get(1) < population.get(3));
	}

	/*
	 * 1. Creation of the array, containing output data 2. Iterating through every
	 * single entry of the map 2.1 for each iteration, check the condition (2014 >
	 * 2013) do helper function 2.1.1 if satisfied, retain older value in the buffer
	 * and set the cell to the sum of those
	 * 
	 */
	protected static ArrayList<Integer> regionalMaleAndFemalePopulationGrowth(
			HashMap<String, ArrayList<Integer>> provinces) {
		ArrayList<Integer> regionalGrowth = new ArrayList<>();

		regionalGrowth.add(0); // cell for region A
		regionalGrowth.add(0); // cell for region B
		regionalGrowth.add(0); // cell for region C
		regionalGrowth.add(0); // cell for region D

		for (var entry : provinces.entrySet()) {
			if (checkGrowth(entry.getValue())) {

				int buffer = 0;
				if (entry.getKey().contains("A")) {
					buffer = regionalGrowth.get(0);
					regionalGrowth.set(0, (buffer + 1));
					buffer = 0;
				} else if (entry.getKey().contains("B")) {
					buffer = regionalGrowth.get(1);
					regionalGrowth.set(1, (buffer + 1));
					buffer = 0;
				} else if (entry.getKey().contains("C")) {
					buffer = regionalGrowth.get(2);
					regionalGrowth.set(2, (buffer + 1));
					buffer = 0;
				} else if (entry.getKey().contains("D")) {
					buffer = regionalGrowth.get(3);
					regionalGrowth.set(3, (buffer + 1));
					buffer = 0;
				}
			} else
				continue;

		}

		return regionalGrowth;
	}
}

class FirstExercise extends Demography {

	/*
	 * 1. Creation of the array containing all the data. (in alphabetical order)
	 * 
	 * 2. Iterating through every element of the map 2.1 Another loop, which loops
	 * over internal ArrayList with population data a) if the key contains A - add
	 * the population to the first cell, and so on 3. Return the array
	 */
	protected static ArrayList<Integer> regionalPopulation(HashMap<String, ArrayList<Integer>> provinces) {
		ArrayList<Integer> regional = new ArrayList<>();

		regional.add(0); // cell for region A
		regional.add(0); // cell for region B
		regional.add(0); // cell for region C
		regional.add(0); // cell for region D

		for (var entry : provinces.entrySet()) {
			int population = 0;

			// looping over and adding first two records - for men and women in 2013
			for (int i = 0; i < 2; i++)
				population += entry.getValue().get(i);

			if (entry.getKey().contains("A")) {
				// holding onto the previous value to prevent data loss
				int buffer = regional.get(0);
				regional.set(0, (population + buffer));
			} else if (entry.getKey().contains("B")) {
				int buffer = regional.get(1);
				regional.set(1, (population + buffer));
			} else if (entry.getKey().contains("C")) {
				int buffer = regional.get(2);
				regional.set(2, (population + buffer));
			} else if (entry.getKey().contains("D")) {
				int buffer = regional.get(3);
				regional.set(3, (population + buffer));
			} else
				continue;
		}

		return regional;
	}

}

class DataExtraction extends Demography {

	/*
	 * The function extracts data from each string. First matches all the sequences
	 * of numbers preceded by a semicolon, then parses and adds the data into
	 * ArrayList.
	 * 
	 * After than matches for the prefix, which figures as a key to the HashMap
	 * containing the data.
	 */
	protected static HashMap<String, ArrayList<Integer>> extractDecimalData(ArrayList<String> provinces) {
		HashMap<String, ArrayList<Integer>> extracted = new HashMap<>();

		for (String province : provinces) {
			Pattern pattern = Pattern.compile(";(\\d+)");
			Matcher matcher = pattern.matcher(province);

			ArrayList<Integer> extractedDecimal = new ArrayList<>();
			while (matcher.find())
				extractedDecimal.add(Integer.parseInt(matcher.group(1)));

			// matching for the index
			matcher = Pattern.compile("^w([^;]+)").matcher(province);

			matcher.find();
			extracted.put(matcher.group(1), extractedDecimal);
		}

		return extracted;
	}
}

public class Demography {

	protected static void displayMap(HashMap<String, ArrayList<Integer>> format) {
		for (var entry : format.entrySet()) {
			System.out.println(entry.getKey() + ": ");
			display(entry.getValue());
		}
	}

	protected static <T> void display(ArrayList<T> list) {
		for (T currentElement : list) {
			System.out.println(currentElement);
		}
	}

	private static ArrayList<String> loadData() {
		// setting up the array and path of the resource file
		ArrayList<String> provinces = new ArrayList<String>();
		Path path = Path.of("./resources", "kraina.txt");

		// setting the buffered reader up
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				provinces.add(line); // adding to the general array
			}

		} catch (IOException e) {
			System.err.println("Exception caught: ");
			e.printStackTrace();
		}

		return provinces;
	}

	public static void main(String[] args) {
		ArrayList<String> provinces = loadData();
		// display(provinces);
		HashMap<String, ArrayList<Integer>> data = DataExtraction.extractDecimalData(provinces);

		ArrayList<Integer> regionalGenderGrowth = SecondExercise.regionalMaleAndFemalePopulationGrowth(data);
		int nationalGenderGrowth = 0;
		for (int region : regionalGenderGrowth)
			nationalGenderGrowth += region;

		System.out.println("\n--- Regional population ---\n");
		display(FirstExercise.regionalPopulation(data));

		System.out.println("\n\t--- Regional gender population growth ---\n");
		display(regionalGenderGrowth);

		System.out.println("\n\t--- National gender population growth ---\n");
		System.out.println(nationalGenderGrowth);

		System.out.println("\n\t--- Forecast ---\n");
		ThirdExercise.provincialForecast(data);
		System.out.println("\n\t\t >> national population: " + ThirdExercise.getNationalPopulation());
		System.out.println("\n\t\t >> most populated province: " + ThirdExercise.findMostPopulated());
		System.out.println("\n\t\t >> overpopulated provinces by 2025: " + ThirdExercise.countOverpopulated());

	}

}
