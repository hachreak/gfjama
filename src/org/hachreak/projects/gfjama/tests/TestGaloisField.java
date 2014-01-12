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

import static org.junit.Assert.assertTrue;

import org.hachreak.projects.gfjama.matrix.GaloisField;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGaloisField {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGaloisFieldProduct() {
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		char c1[] = "hello".toCharArray();
		char c2[] = "world".toCharArray();
		
		char output[] = new char[c1.length];//"�����".toCharArray();
		output[0] = 223;
		output[1] = 212;
		output[2] = 222;
		output[3] = 216;
		output[4] = 211;
		
		for(int i=0; i<c1.length; i++){
//			System.out.print((int)gf.product(c1[i], c2[i])+" ");
			assertTrue(output[i] == gf.product(c1[i], c2[i]));
		}
		//System.out.println(gf.sum('a', (char)4));
	}

	@Test
	public void testGaloisFieldSum() {
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		char c1[] = "qello".toCharArray();
		char c2[] = "world".toCharArray();
		char output[] = new char[c1.length];//"1z,�Z".toCharArray();
		output[0] = 49;
		output[1] = 122;
		output[2] = 44;
		output[3] = 255;
		output[4] = 90;
		
		for(int i=0; i<c1.length; i++){
//			System.out.print((int)gf.sum(c1[i], c2[i])+" ");
			assertTrue(output[i] == gf.sum(c1[i], c2[i]));
		}
		//System.out.println(gf.sum('a', (char)4));
	}
	
	@Test
	public void testGaloisSumProduct(){
		byte b = 8;
		GaloisField gf = new GaloisField(b);
		
		char c1[] = "qwllo".toCharArray();
		char c2[] = "world".toCharArray();
		
//		char c3[] = new char[c1.length];
		char output = GaloisField.getZero();
		char result = 't';
		for(int i=0; i<c1.length; i++){
			output = gf.sum(output, gf.product(c1[i], c2[i]));
		}
//		System.out.println(output);
		assertTrue(output == result);
		char output2 = gf.product(c1[0], c2[0]);
		for(int i=1; i<c1.length; i++){
			//System.out.println(i);
			output2 = gf.sum(output2, gf.product(c1[i], c2[i]));
		}
//		System.out.println(output);
//		System.out.println(output2);
		assertTrue(output == result);
		
	}

}
