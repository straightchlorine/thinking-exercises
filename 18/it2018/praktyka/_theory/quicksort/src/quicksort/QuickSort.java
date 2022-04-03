package quicksort;

import java.util.Random;

public class QuickSort {
	
	private static void swap(int[] A, int[] B, int i1, int i2) {
		int ba = A[i1];
		int bb = B[i1];
		A[i1] = A[i2];
		B[i1] = B[i2];
		A[i2] = ba;
		B[i2] = bb;
	}
		
	private static int partition(int[] A, int[] B, int l, int r) {
		int b = l - 1;
		int i = l;
		
		for(;i < r; i++) {
			if((float)A[i]/B[i] >= (float)A[r]/B[r]) {
				b++;
				swap(A, B, i, b);
			}
		}
		
		b++;
		swap(A, B, b, r);
		
		return b;
		
	}
	
	private static void quicksort(int[] A, int[] B, int l, int r) {
		if(l >= r || l < 0)
			return;
					
		int p = partition(A, B, l, r);
		
		quicksort(A, B, l, p - 1);
		quicksort(A, B, p + 1, r);
	}
	
	public static void main(String[] args) {
		Random rand = new Random();
		int[] Z = new int[1000];
		int[] X = new int[1000];	
		
		for(int i = 0; i < Z.length; i++) {
			Z[i] = rand.nextInt(100 + 100) + 100;
			X[i] = rand.nextInt(100 + 100) + 100;
			
		}
		
		for(int i = 0; i < Z.length; i++) {
			System.out.print(Z[i] + " ");
			
		}
		
		System.out.println();
		
		for(int i = 0; i < Z.length; i++) {
			System.out.print(X[i] + " ");
			
		}

		System.out.println();
		
		for(int i = 0; i < Z.length; i++) {
			System.out.print((float) Z[i]/X[i] + " ");
			
		}
		
		
		System.out.println();
		System.out.println();

		
		
		quicksort(Z, X, 0, 1000-1);

		
		
		System.out.println();
		System.out.println();

		for(int i = 0; i < Z.length; i++) {
			System.out.print(Z[i] + " ");
			
		}
		
		System.out.println();
		
		for(int i = 0; i < Z.length; i++) {
			System.out.print(X[i] + " ");
			
		}

		System.out.println();
		
		for(int i = 0; i < Z.length; i++) {
			System.out.print((float) Z[i]/X[i] + " ");
			
		}
	}

}
