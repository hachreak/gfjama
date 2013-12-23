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

package org.hachreak.projects.gfjama.tests;

import static org.junit.Assert.*;

import org.hachreak.projects.gfjama.matrix.GaloisField;
import org.hachreak.projects.gfjama.matrix.RandomGFMatrix;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRandomGFMatrix {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRandomGFMatrixIntIntGaloisFieldLong() {
		byte b = 8;
		int m = 3;
		int n = 2;
		int seed = 10;
		char randoms[][] = new char[m][n];
		
		randoms[0][0] = (char)207;
		randoms[0][1] = (char)136;
		randoms[1][0] = (char)3;
		randoms[1][1] = (char)146;
		randoms[2][0] = (char)118;
		randoms[2][1] = (char)190;
		
		GaloisField gf = new GaloisField(b);
		RandomGFMatrix r = new RandomGFMatrix(m, n, gf, seed);
		
		for(int i=0; i<m;i++){
			for(int j=0; j<n;j++){
				assertTrue(randoms[i][j] == r.getArray()[i][j]);
			}
		}

	}

}
