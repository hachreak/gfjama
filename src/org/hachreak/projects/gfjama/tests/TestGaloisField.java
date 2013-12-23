package org.hachreak.projects.gfjama.tests;

import static org.junit.Assert.*;

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
		char output[] = "ﬂ‘ﬁÿ”".toCharArray();
		for(int i=0; i<c1.length; i++){
			//System.out.print(gf.product(c1[i], c2[i]));
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
		char output[] = "1z,ˇZ".toCharArray();
		for(int i=0; i<c1.length; i++){
			//System.out.print(gf.sum(c1[i], c2[i]));
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
		
		char c3[] = new char[c1.length];
		char output = gf.getZero();
		char result = 't';
		for(int i=0; i<c1.length; i++){
			output = gf.sum(output, gf.product(c1[i], c2[i]));
		}
		System.out.println(output);
		assertTrue(output == result);
		char output2 = gf.product(c1[0], c2[0]);
		for(int i=1; i<c1.length; i++){
			//System.out.println(i);
			output2 = gf.sum(output2, gf.product(c1[i], c2[i]));
		}
		System.out.println(output);
		System.out.println(output2);
		assertTrue(output == result);
		
	}

}
