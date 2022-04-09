package partitioning;

import java.io.IOException;
import java.util.Random;

public class Partitioning {
	private static int[] a;
	
	private static int reversed() {
		int number = 0;
		
		for(int i = a.length - 1; i > 0; i--) {
			if(a[i - 1] % 2 != 0) 
				return a[i];
		}
		
		return number;
	}
	
	private static int partitioning(int p, int q) {
		int pivot = (p + q) / 2;
		
		if((a[pivot] % 2 != 0) && (a[pivot + 1] % 2 == 0)) {
			return a[pivot + 1];
		}
		
		if((a[pivot] % 2 == 0) && (a[pivot - 1] % 2 != 0)) {
			return a[pivot];
		}
		
		if((a[pivot] % 2 != 0) && (a[pivot + 1] % 2 != 0)) {
			return partitioning(pivot + 2, q);
		} else {
			return partitioning(p, pivot - 2);
		}
	}
	
	private static int findFirstEven() {
		int left = 0;
		int right = a.length - 1;
		
		int extracted = partitioning(left, right);
		
		
		return extracted;
	}
	
	public static void main(String[] args) {
		Random rand = new Random();

		int[] arr = new int[10];

		int p = rand.nextInt((arr.length - 2) - 1) + 1;

		for (int i = 0; i < p; i++) {
			while (true) {
				int number = rand.nextInt(100 - 1) + 1;
				if (number % 2 == 0)
					continue;
				else {
					arr[i] = number;
					break;
				}
			}
		}

		for (int i = p; i < arr.length; i++) {
			while (true) {
				int number = rand.nextInt(100 - 1) + 1;
				if (number % 2 != 0)
					continue;
				else {
					arr[i] = number;
					break;
				}
			}
		}

		a = arr;
		
		double start = System.nanoTime();
		
		System.out.println(reversed());
		
		double finish = System.nanoTime();
		
		System.out.println("reversed: " + (finish - start));
		
		start = System.nanoTime();
		
		System.out.println(findFirstEven());
		
		finish = System.nanoTime();
		
		System.out.println("partitioning: " + (finish - start));
		
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}

}
