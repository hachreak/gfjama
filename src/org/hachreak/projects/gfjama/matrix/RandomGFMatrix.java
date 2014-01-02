/**
 * Copyright (C) 2013 Leonardo Rossi <leonardo.rossi@studenti.unipr.it>
 *
 * This source code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

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
				int r = ( randomizer.nextInt(255));
				A[i][j] = (char)(r % 255);
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
