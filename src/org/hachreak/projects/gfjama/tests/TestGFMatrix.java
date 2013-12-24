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

import org.hachreak.projects.gfjama.matrix.GFMatrix;
import org.hachreak.projects.gfjama.matrix.GFMatrixException;
import org.hachreak.projects.gfjama.matrix.GaloisField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGFMatrix {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTimesChar() {
		char c[][] = new char[3][2];
		c[0][0] = 2;
		c[0][1] = 3;
		c[1][0] = 4;
		c[1][1] = 5;
		c[2][0] = 6;
		c[2][1] = 7;
		char mux = 4;
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		GFMatrix m = new GFMatrix(c, gf);
		GFMatrix ret = m.times(mux);
		assertTrue(gf.product((char)2, (char)4) == ret.get(0, 0));
		assertTrue(gf.product((char)3, (char)4) == ret.get(0, 1));
		assertTrue(gf.product((char)4, (char)4) == ret.get(1, 0));
		assertTrue(gf.product((char)5, (char)4) == ret.get(1, 1));
		assertTrue(gf.product((char)6, (char)4) == ret.get(2, 0));
		assertTrue(gf.product((char)7, (char)4) == ret.get(2, 1));
	}

	@Test
	public void testTimesGFMatrix() {
		char c[][] = new char[3][2];
		c[0][0] = (char)2;
		c[0][1] = (char)3;
		c[1][0] = (char)4;
		c[1][1] = 5;
		c[2][0] = 6;
		c[2][1] = 7;
		
		char d[][] = new char[2][3];
		d[0][0] = (char)8;
		d[0][1] = 9;
		d[0][2] = 10;
		d[1][0] = (char)11;
		d[1][1] = 12;
		d[1][2] = 13;
		
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		GFMatrix m1 = new GFMatrix(c, gf);
		GFMatrix m2 = new GFMatrix(d, gf);
		
		GFMatrix ret = m1.times(m2);
//		System.out.println((int)ret.get(0, 0)+ " - "+ (int)gf.product((char)2, (char)8)+ " - "+
//		   (int)gf.sum(gf.product((char)2, (char)8), gf.product((char)3, (char)11)));
//		char s = 0;
//		s = gf.sum(s, gf.product((char)2, (char)8));
//		s = gf.sum(s, gf.product((char)3, (char)11));
//		System.out.println((int)ret.get(0, 0)+ " - "+ (int)s);
		assertTrue(gf.sum(gf.product((char)2, (char)8), gf.product((char)3, (char)11)) == ret.get(0, 0));
		assertTrue(gf.sum(gf.product((char)2, (char)9), gf.product((char)3, (char)12)) == ret.get(0, 1));
		assertTrue(gf.sum(gf.product((char)2, (char)10), gf.product((char)3, (char)13)) == ret.get(0, 2));
		assertTrue(gf.sum(gf.product((char)4, (char)8), gf.product((char)5, (char)11)) == ret.get(1, 0));
		assertTrue(gf.sum(gf.product((char)4, (char)9), gf.product((char)5, (char)12)) == ret.get(1, 1));
		assertTrue(gf.sum(gf.product((char)4, (char)10), gf.product((char)5, (char)13)) == ret.get(1, 2));
		assertTrue(gf.sum(gf.product((char)6, (char)8), gf.product((char)7, (char)11)) == ret.get(2, 0));
		assertTrue(gf.sum(gf.product((char)6, (char)9), gf.product((char)7, (char)12)) == ret.get(2, 1));
		assertTrue(gf.sum(gf.product((char)6, (char)10), gf.product((char)7, (char)13)) == ret.get(2, 2));
	}

	@Test
	public void testIdentity() {
		byte b = 8;
		GaloisField gf = new GaloisField(b);
//		GFMatrix m = new GFMatrix(3,2,gf);
		GFMatrix m = GFMatrix.identity(3, 2, gf);
//		System.out.println(m.getRowDimension()+" - "+m.getColumnDimension());
		for(int i=0; i<m.getRowDimension(); i++){
			for(int j=0; j<m.getColumnDimension(); j++){
//				System.out.println("GET m["+i+"]["+j+"] "+(int)m.get(i, j));
				if(i == j){
					assertTrue(GaloisField.getOne() == m.get(i, j));
				}else{
					assertTrue(GaloisField.getZero() == m.get(i, j));
				}
					
			}
		}
	}
	
	@Test
	public void testInverse(){
		char c[][] = new char[3][3];
		c[0][0] = (char)7;
		c[0][1] = (char)2;
		c[0][2] = 4;
		c[1][0] = (char)4;
		c[1][1] = 43;
		c[1][2] = 10;
		c[2][0] = 6;
		c[2][1] = 7;
		c[2][2] = 10;
		
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		GFMatrix m1 = new GFMatrix(c, gf);
		try {
			GFMatrix m2 = m1.inverse().inverse();
			
			for(int i=0; i<m2.getColumnDimension(); i++){
				for(int j=0; j<m2.getRowDimension(); j++){
//					System.out.print((int)m2.get(i, j)+" ");
					assertTrue(c[i][j] == m2.get(i, j));
				}
				//System.out.print("\n");
			}
		} catch (GFMatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSubMatrix(){
		char c[][] = new char[3][3];
		c[0][0] = (char)7;
		c[0][1] = (char)2;
		c[0][2] = 4;
		c[1][0] = (char)4;
		c[1][1] = 43;
		c[1][2] = 10;
		c[2][0] = 6;
		c[2][1] = 7;
		c[2][2] = 10;
		
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		GFMatrix m1 = new GFMatrix(c, gf);
		
		GFMatrix m2 = m1.submatrix(2);
		
		assertTrue(m2.getRowDimension() == 2);
		assertTrue(m2.getColumnDimension() == m1.getColumnDimension());
		
		for(int i=0; i<m2.getRowDimension(); i++){
			for(int j=0; j<m2.getColumnDimension(); j++){
//				System.out.println(m2.get(i, j)+" == "+m1.get(i, j));
				assertTrue(m2.get(i, j) == m1.get(i, j));
			}	
		}
		
		m1.set(1, 1, (char) 70);
		assertFalse(m2.get(1, 1) == m1.get(1, 1));
	}
	
	@Test
	public void testSubMatrixRowsCols(){
		char c[][] = new char[3][3];
		c[0][0] = (char)7;
		c[0][1] = (char)2;
		c[0][2] = 4;
		c[1][0] = (char)4;
		c[1][1] = 43;
		c[1][2] = 10;
		c[2][0] = 6;
		c[2][1] = 7;
		c[2][2] = 10;
		
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		GFMatrix m1 = new GFMatrix(c, gf);
		
		GFMatrix m2 = m1.submatrix(2, 1);
		
		assertTrue(m2.getRowDimension() == 2);
		assertTrue(m2.getColumnDimension() == 1);
		
		for(int i=0; i<m2.getRowDimension(); i++){
			for(int j=0; j<m2.getColumnDimension(); j++){
//				System.out.println(m2.get(i, j)+" == "+m1.get(i, j));
				assertTrue(m2.get(i, j) == m1.get(i, j));
			}	
		}
		
		m1.set(1, 0, (char) 70);
		assertFalse(m2.get(1, 0) == m1.get(1, 0));
	}
}
