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
