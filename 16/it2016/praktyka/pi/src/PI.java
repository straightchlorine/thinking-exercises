import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ThirdExercise extends PI {
	
	private static double absoluteError(double approximate) {
		return Math.abs(Math.PI - approximate);
	}
	
	
	private static double getApproximation(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords, Integer n) {
		double squareArea = Math.pow(400, 2);
		double circleK = Math.pow(200, 2);
	
		double approximate = (squareArea * (SecondExercise.getInside(xCoords, yCoords, n) / n)) / circleK;

		return approximate;
	}
	
	protected static void summary(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
		ArrayList<Double> absoluteErrors = new ArrayList<>();
		for(int i = 1; i <= 1700; i++) {
			absoluteErrors.add(absoluteError(getApproximation(xCoords, yCoords, i)));
			String approximation = Double.toString(absoluteErrors.get(i - 1)).replace('.', ',');
			System.out.println(approximation);
		}
		
		System.out.println("--- 1000 and 1700 ---");
		System.out.println(SecondExercise.roundUpToFour(absoluteErrors.get(999)));
		System.out.println(SecondExercise.roundUpToFour(absoluteErrors.get(1699)));
	}
	
}

class SecondExercise extends PI {
	
	protected static boolean inCircle(Integer xCoord, Integer yCoord) {
		boolean inside = false;

		if ((Math.pow(xCoord - 200, 2) + Math.pow(yCoord - 200, 2)) <= 40000)
			inside = true;

		return inside;
	}
	
	protected static double getInside(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords, int n) {
		int inside = 0;
			
		for(int i = 0; i < n; i++) {
			if(inCircle(xCoords.get(i), yCoords.get(i)))
				inside++;
		}
		
		return inside;
	}
	
	protected static double roundUpToFour(double toRound) {
		return Math.round(toRound * 10000.0) / 10000.0;
	}
	
	protected static void summary(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
		double squareArea = Math.pow(400, 2);
		double circleK = Math.pow(200, 2);
	
		System.out.println("--- Approximate value of PI for first 1000 ---");
		double approximate = (squareArea * (getInside(xCoords, yCoords, 1000) / 1000)) / circleK;
		System.out.println("\t" + roundUpToFour(approximate));
		
		System.out.println("--- Approximate value of PI for first 5000 ---");
		approximate = (squareArea * (getInside(xCoords, yCoords, 5000) / 5000)) / circleK;
		System.out.println("\t" + roundUpToFour(approximate));
		
		System.out.println("--- Approximate value of PI for all the data ---");
		approximate = (squareArea * (getInside(xCoords, yCoords, 10000) / 10000)) / circleK;
		System.out.println("\t" + roundUpToFour(approximate));
	}
}

class FirstExercise extends PI {

	protected static Integer inside = 0;

	private static boolean onCircle(Integer xCoord, Integer yCoord) {
		boolean liesOnCircle = false;

		// r^2 = 40000
		if ((Math.pow(xCoord - 200, 2) + Math.pow(yCoord - 200, 2)) == 40000)
			liesOnCircle = true;

		return liesOnCircle;
	}

	private static boolean inCircle(Integer xCoord, Integer yCoord) {
		boolean inside = false;

		if ((Math.pow(xCoord - 200, 2) + Math.pow(yCoord - 200, 2)) < 40000)
			inside = true;

		return inside;
	}

	protected static void summary(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
		int inside = 0;
		System.out.println("--- Points on the circle ---");
		for (int i = 0; i < xCoords.size(); i++) {
			if (onCircle(xCoords.get(i), yCoords.get(i))) {
				System.out.println("\t" + i + ": (" + xCoords.get(i) + ", " + yCoords.get(i) + ")");
				continue;
			}
			if (inCircle(xCoords.get(i), yCoords.get(i)))
				inside++;
		}

		System.out.println("--- Points in the circle ---");
		System.out.println("\t" + inside);
		FirstExercise.inside = inside;
	}
}

public class PI {

	private static void loadPoints(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {

		Path path = Path.of("./resources", "punkty.txt");
		try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				// regex
				Pattern pattern = Pattern.compile("[0-9]*\\S");
				Matcher matcher = pattern.matcher(line);

				// ArrayList containing all the matches
				ArrayList<String> temp = new ArrayList<String>();
				while (matcher.find())
					temp.add(matcher.group());

				xCoords.add(Integer.parseInt(temp.get(0)));
				yCoords.add(Integer.parseInt(temp.get(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ArrayList<Integer> xCoords = new ArrayList<Integer>();
		ArrayList<Integer> yCoords = new ArrayList<Integer>();

		loadPoints(xCoords, yCoords);

		System.out.println("### First Exercise ###");
		FirstExercise.summary(xCoords, yCoords);
		System.out.println("### First Exercise ###");
		SecondExercise.summary(xCoords, yCoords);
		System.out.println("### Thrid Exercise ###");
		ThirdExercise.summary(xCoords, yCoords);

	}

}
