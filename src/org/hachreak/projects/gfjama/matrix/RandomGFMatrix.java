package org.hachreak.projects.gfjama.matrix;

import java.util.Random;

public class RandomGFMatrix extends GFMatrix {

	private Random randomizer;
	
	public RandomGFMatrix(int m, int n, GaloisField gf) {
		super(m, n, gf);
		// init randomizer
		randomizer = new Random();
		// init 
		fillWithRandomValues(m, n);
	}
	
	public RandomGFMatrix(int m, int n, GaloisField field, long seed) {
		super(m, n, field);
		// init randomizer
		randomizer = new Random(seed);
		// init
		fillWithRandomValues(m, n);
	}
	
	/**
	 * Fill the matrix with random values
	 * TODO Test!!!
	 */
	protected void fillWithRandomValues(int m, int n){
		char A[][] = this.getArray();
//		System.out.println("prima");
//		this.print();
		// generate random values
		for(int i=0; i<m; i++){
			for(int j=0; j<n; j++){
//				int r = (int) Math.round(Math.random() * 254);//
				int r = ( randomizer.nextInt(254));
				A[i][j] = (char)(r % 254);
//				char c = A[i][j];
//				System.out.print("random "+(int)r+" ");//+" - "+c+" - "+(int)c % 254);
			}
//			System.out.println();
		}
//		System.out.println("dopo");
//		this.print();
//		System.out.println();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
