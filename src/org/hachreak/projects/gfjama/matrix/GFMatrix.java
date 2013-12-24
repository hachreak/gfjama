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

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author hachreak
 * 
 */
public class GFMatrix implements Cloneable, java.io.Serializable {

	/*
	 * ------------------------ Class variables ------------------------
	 */

	/**
	 * Array for internal storage of elements.
	 * 
	 * @serial internal array storage.
	 */
	private char[][] A;

	/**
	 * Row and column dimensions.
	 * 
	 * @serial row dimension.
	 * @serial column dimension.
	 */
	private int m, n;

	/**
	 * Useful to implement operation in Galois Field space 
	 */
	protected GaloisField galoisField;

	/*
	 * ------------------------ Constructors ------------------------
	 */

	/**
	 * Construct an m-by-n GFMatrix of zeros.
	 * 
	 * @param m
	 *            Number of rows.
	 * @param n
	 *            Number of colums.
	 */

	public GFMatrix(int m, int n, GaloisField gf) {
		this.galoisField = gf;
		this.m = m;
		this.n = n;
		A = new char[m][n];
	}

	/**
	 * Construct an m-by-n constant GFMatrix.
	 * 
	 * @param m
	 *            Number of rows.
	 * @param n
	 *            Number of colums.
	 * @param s
	 *            Fill the GFMatrix with this scalar value.
	 */

//	public GFMatrix(int m, int n, double s) {
//		this.m = m;
//		this.n = n;
//		A = new double[m][n];
//		for (int i = 0; i < m; i++) {
//			for (int j = 0; j < n; j++) {
//				A[i][j] = s;
//			}
//		}
//	}

	/**
	 * Construct a GFMatrix from a 2-D array.
	 * 
	 * @param A
	 *            Two-dimensional array of doubles.
	 * @exception IllegalArgumentException
	 *                All rows must have the same length
	 * @see #constructWithCopy
	 */

	public GFMatrix(char[][] A, GaloisField gf) {
		this.galoisField = gf;
		m = A.length;
		n = A[0].length;
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException(
						"All rows must have the same length.");
			}
		}
		this.A = A;
	}
	
	/**
	 * Create a matrix with dimension 1xA.length
	 * 
	 * @param A vector to insert in matrix
	 * @return matrix
	 */
	public static char[][] vector2matrix(char[] A){
		char v[][] = new char[1][A.length];
		v[0] = A;
		return v;
	}

	/**
	 * Return a submatrix [numRows x n] 
	 * @param numRows number of rows to copy
	 * @return submatrix
	 */
	public GFMatrix submatrix(int numRows){
			GFMatrix X = new GFMatrix(numRows, n, galoisField);
			char[][] C = X.getArray();
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < n; j++) {
					C[i][j] = A[i][j];
				}
			}
			return X;
	}
	
	/**
	 * Return a submatrix [numRows x numCols] 
	 * @param numRows number of rows to copy
	 * @param numCols number of cols to copy
	 * @return submatrix
	 */
	public GFMatrix submatrix(int numRows, int numCols){
			GFMatrix X = new GFMatrix(numRows, numCols, galoisField);
			char[][] C = X.getArray();
			for (int i = 0; i < numRows; i++) {
				for (int j = 0; j < numCols; j++) {
					C[i][j] = A[i][j];
				}
			}
			return X;
	}
	
	/**
	 * Construct a GFMatrix quickly without checking arguments.
	 * 
	 * @param A
	 *            Two-dimensional array of doubles.
	 * @param m
	 *            Number of rows.
	 * @param n
	 *            Number of colums.
	 */

//	public GFMatrix(double[][] A, int m, int n) {
//		this.A = A;
//		this.m = m;
//		this.n = n;
//	}

	/**
	 * Construct a GFMatrix from a one-dimensional packed array
	 * 
	 * @param vals
	 *            One-dimensional array of doubles, packed by columns (ala
	 *            Fortran).
	 * @param m
	 *            Number of rows.
	 * @exception IllegalArgumentException
	 *                Array length must be a multiple of m.
	 */

//	public GFMatrix(double vals[], int m) {
//		this.m = m;
//		n = (m != 0 ? vals.length / m : 0);
//		if (m * n != vals.length) {
//			throw new IllegalArgumentException(
//					"Array length must be a multiple of m.");
//		}
//		A = new double[m][n];
//		for (int i = 0; i < m; i++) {
//			for (int j = 0; j < n; j++) {
//				A[i][j] = vals[i + j * m];
//			}
//		}
//	}

	/*
	 * ------------------------ Public Methods ------------------------
	 */

	/**
	 * Construct a GFMatrix from a copy of a 2-D array.
	 * 
	 * @param A
	 *            Two-dimensional array of doubles.
	 * @exception IllegalArgumentException
	 *                All rows must have the same length
	 */

	public static GFMatrix constructWithCopy(char[][] A, GaloisField gf) {
		int m = A.length;
		int n = A[0].length;
		GFMatrix X = new GFMatrix(m, n, gf);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException(
						"All rows must have the same length.");
			}
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}

	/**
	 * Make a deep copy of a GFMatrix
	 */

	public GFMatrix copy() {
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}

	/**
	 * Clone the GFMatrix object.
	 */

	public Object clone() {
		return this.copy();
	}

	/**
	 * Access the internal two-dimensional array.
	 * 
	 * @return Pointer to the two-dimensional array of GFMatrix elements.
	 */

	public char[][] getArray() {
		return A;
	}

	/**
	 * Copy the internal two-dimensional array.
	 * 
	 * @return Two-dimensional array copy of GFMatrix elements.
	 */

	public char[][] getArrayCopy() {
		char[][] C = new char[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return C;
	}

	/**
	 * Make a one-dimensional column packed copy of the internal array.
	 * 
	 * @return GFMatrix elements packed in a one-dimensional array by columns.
	 */

	public char[] getColumnPackedCopy() {
		char[] vals = new char[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i + j * m] = A[i][j];
			}
		}
		return vals;
	}

	/**
	 * Make a one-dimensional row packed copy of the internal array.
	 * 
	 * @return GFMatrix elements packed in a one-dimensional array by rows.
	 */

	public char[] getRowPackedCopy() {
		char[] vals = new char[m * n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				vals[i * n + j] = A[i][j];
			}
		}
		return vals;
	}

	/**
	 * Get row dimension.
	 * 
	 * @return m, the number of rows.
	 */

	public int getRowDimension() {
		return m;
	}

	/**
	 * Get column dimension.
	 * 
	 * @return n, the number of columns.
	 */

	public int getColumnDimension() {
		return n;
	}

	/**
	 * Get a single element.
	 * 
	 * @param i
	 *            Row index.
	 * @param j
	 *            Column index.
	 * @return A(i,j)
	 * @exception ArrayIndexOutOfBoundsException
	 */

	public char get(int i, int j) {
		return A[i][j];
	}

	/**
	 * Get a subGFMatrix.
	 * 
	 * @param i0
	 *            Initial row index
	 * @param i1
	 *            Final row index
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @return A(i0:i1,j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public GFMatrix getGFMatrix(int i0, int i1, int j0, int j1) {
		GFMatrix X = new GFMatrix(i1 - i0 + 1, j1 - j0 + 1, galoisField);
		char[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i - i0][j - j0] = A[i][j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
		return X;
	}

	/**
	 * Get a subGFMatrix.
	 * 
	 * @param r
	 *            Array of row indices.
	 * @param c
	 *            Array of column indices.
	 * @return A(r(:),c(:))
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public GFMatrix getGFMatrix(int[] r, int[] c) {
		GFMatrix X = new GFMatrix(r.length, c.length, galoisField);
		char[][] B = X.getArray();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < c.length; j++) {
					B[i][j] = A[r[i]][c[j]];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
		return X;
	}

	/**
	 * Get a subGFMatrix.
	 * 
	 * @param i0
	 *            Initial row index
	 * @param i1
	 *            Final row index
	 * @param c
	 *            Array of column indices.
	 * @return A(i0:i1,c(:))
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public GFMatrix getGFMatrix(int i0, int i1, int[] c) {
		GFMatrix X = new GFMatrix(i1 - i0 + 1, c.length, galoisField);
		char[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = 0; j < c.length; j++) {
					B[i - i0][j] = A[i][c[j]];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
		return X;
	}

	/**
	 * Get a subGFMatrix.
	 * 
	 * @param r
	 *            Array of row indices.
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @return A(r(:),j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public GFMatrix getGFMatrix(int[] r, int j0, int j1) {
		GFMatrix X = new GFMatrix(r.length, j1 - j0 + 1, galoisField);
		char[][] B = X.getArray();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i][j - j0] = A[r[i]][j];
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
		return X;
	}

	/**
	 * Set a single element.
	 * 
	 * @param i
	 *            Row index.
	 * @param j
	 *            Column index.
	 * @param s
	 *            A(i,j).
	 * @exception ArrayIndexOutOfBoundsException
	 */

	public void set(int i, int j, char s) {
		A[i][j] = s;
	}

	/**
	 * Set a subGFMatrix.
	 * 
	 * @param i0
	 *            Initial row index
	 * @param i1
	 *            Final row index
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @param X
	 *            A(i0:i1,j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public void setGFMatrix(int i0, int i1, int j0, int j1, GFMatrix X) {
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					A[i][j] = X.get(i - i0, j - j0);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
	}

	/**
	 * Set a subGFMatrix.
	 * 
	 * @param r
	 *            Array of row indices.
	 * @param c
	 *            Array of column indices.
	 * @param X
	 *            A(r(:),c(:))
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public void setGFMatrix(int[] r, int[] c, GFMatrix X) {
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = 0; j < c.length; j++) {
					A[r[i]][c[j]] = X.get(i, j);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
	}

	/**
	 * Set a subGFMatrix.
	 * 
	 * @param r
	 *            Array of row indices.
	 * @param j0
	 *            Initial column index
	 * @param j1
	 *            Final column index
	 * @param X
	 *            A(r(:),j0:j1)
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public void setGFMatrix(int[] r, int j0, int j1, GFMatrix X) {
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					A[r[i]][j] = X.get(i, j - j0);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
	}

	/**
	 * Set a subGFMatrix.
	 * 
	 * @param i0
	 *            Initial row index
	 * @param i1
	 *            Final row index
	 * @param c
	 *            Array of column indices.
	 * @param X
	 *            A(i0:i1,c(:))
	 * @exception ArrayIndexOutOfBoundsException
	 *                SubGFMatrix indices
	 */

	public void setGFMatrix(int i0, int i1, int[] c, GFMatrix X) {
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = 0; j < c.length; j++) {
					A[i][c[j]] = X.get(i - i0, j);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("SubGFMatrix indices");
		}
	}

	/**
	 * GFMatrix transpose.
	 * 
	 * @return A'
	 */

	public GFMatrix transpose() {
		GFMatrix X = new GFMatrix(n, m, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[j][i] = A[i][j];
			}
		}
		return X;
	}

	/**
	 * One norm
	 * 
	 * @return maximum column sum.
	 * TODO max function is usefull in GF?
	 */

	public char norm1() {
		char f = GaloisField.getZero();
		for (int j = 0; j < n; j++) {
			char s = GaloisField.getZero();
			for (int i = 0; i < m; i++) {
//				s += Math.abs(A[i][j]);
				s = galoisField.sum(s, A[i][j]);
			}
			f = (char) Math.max(f, s);
		}
		return f;
	}

	/**
	 * Two norm
	 * 
	 * @return maximum singular value.
	 */

//	public char norm2() {
//		return (new SingularValueDecomposition(this).norm2());
//	}

	/**
	 * Infinity norm
	 * 
	 * @return maximum row sum.
	 */

	public char normInf() {
		char f = GaloisField.getZero();
		for (int i = 0; i < m; i++) {
			char s = GaloisField.getZero();
			for (int j = 0; j < n; j++) {
//				s += Math.abs(A[i][j]);
				s = galoisField.sum(s, A[i][j]);
			}
			f = (char) Math.max(f, s);
		}
		return f;
	}

	/**
	 * Frobenius norm
	 * 
	 * @return sqrt of sum of squares of all elements.
	 */

//	public char normF() {
//		char f = 0;
//		for (int i = 0; i < m; i++) {
//			for (int j = 0; j < n; j++) {
//				f = Maths.hypot(f, A[i][j]);
//			}
//		}
//		return f;
//	}

	/**
	 * Unary minus
	 * 
	 * @return -A
	 */

	public GFMatrix uminus() {
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.minus(GaloisField.getZero(), A[i][j]);
			}
		}
		return X;
	}

	/**
	 * C = A + B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A + B
	 */

	public GFMatrix plus(GFMatrix B) {
		checkGFMatrixDimensions(B);
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.sum(A[i][j], B.A[i][j]);
			}
		}
		return X;
	}

	/**
	 * A = A + B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A + B
	 */

	public GFMatrix plusEquals(GFMatrix B) {
		checkGFMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.sum(A[i][j], B.A[i][j]);
			}
		}
		return this;
	}

	/**
	 * C = A - B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A - B
	 */

	public GFMatrix minus(GFMatrix B) {
		checkGFMatrixDimensions(B);
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.minus(A[i][j], B.A[i][j]);
			}
		}
		return X;
	}

	/**
	 * A = A - B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A - B
	 */

	public GFMatrix minusEquals(GFMatrix B) {
		checkGFMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.minus(A[i][j], B.A[i][j]);
			}
		}
		return this;
	}

	/**
	 * Element-by-element multiplication, C = A.*B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A.*B
	 */

	public GFMatrix arrayTimes(GFMatrix B) {
		checkGFMatrixDimensions(B);
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.product(A[i][j], B.A[i][j]);
			}
		}
		return X;
	}

	/**
	 * Element-by-element multiplication in place, A = A.*B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A.*B
	 */

	public GFMatrix arrayTimesEquals(GFMatrix B) {
		checkGFMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.product(A[i][j], B.A[i][j]);
			}
		}
		return this;
	}

	/**
	 * Element-by-element right division, C = A./B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A./B
	 */

	public GFMatrix arrayRightDivide(GFMatrix B) {
		checkGFMatrixDimensions(B);
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.divide(A[i][j], B.A[i][j]);
			}
		}
		return X;
	}

	/**
	 * Element-by-element right division in place, A = A./B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A./B
	 */

	public GFMatrix arrayRightDivideEquals(GFMatrix B) {
		checkGFMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.divide(A[i][j], B.A[i][j]);
			}
		}
		return this;
	}

	/**
	 * Element-by-element left division, C = A.\B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A.\B
	 */

	public GFMatrix arrayLeftDivide(GFMatrix B) {
		checkGFMatrixDimensions(B);
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.divide(B.A[i][j], A[i][j]);
			}
		}
		return X;
	}

	/**
	 * Element-by-element left division in place, A = A.\B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return A.\B
	 */

	public GFMatrix arrayLeftDivideEquals(GFMatrix B) {
		checkGFMatrixDimensions(B);
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.divide(B.A[i][j], A[i][j]);
			}
		}
		return this;
	}

	/**
	 * Multiply a GFMatrix by a scalar, C = s*A
	 * 
	 * @param s
	 *            scalar
	 * @return s*A
	 */

	public GFMatrix times(char s) {
		GFMatrix X = new GFMatrix(m, n, galoisField);
		char[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = galoisField.product(s, A[i][j]);
			}
		}
		return X;
	}

	/**
	 * Multiply a GFMatrix by a scalar in place, A = s*A
	 * 
	 * @param s
	 *            scalar
	 * @return replace A by s*A
	 */

	public GFMatrix timesEquals(char s) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = galoisField.product(s, A[i][j]);
			}
		}
		return this;
	}

	/**
	 * Linear algebraic GFMatrix multiplication, A * B
	 * 
	 * @param B
	 *            another GFMatrix
	 * @return GFMatrix product, A * B
	 * @exception IllegalArgumentException
	 *                GFMatrix inner dimensions must agree.
	 */

	public GFMatrix times(GFMatrix B) {
		if (B.m != n) {
			throw new IllegalArgumentException(
					"GFMatrix inner dimensions must agree.");
		}
		GFMatrix X = new GFMatrix(m, B.n, galoisField);
		char[][] C = X.getArray();
		char[] Bcolj = new char[n];
		for (int j = 0; j < B.n; j++) {
			for (int k = 0; k < n; k++) {
				Bcolj[k] = B.A[k][j];
			}
			for (int i = 0; i < m; i++) {
				char[] Arowi = A[i];
				char s = GaloisField.getZero();
//System.out.print("Z["+i+"]["+j+"] = ");
				for (int k = 0; k < n; k++) {
//					s += Arowi[k] * Bcolj[k];
//System.out.print("A["+i+"]["+k+"] * B["+k+"]["+j+"] + ");
//System.out.print((int)Arowi[k]+" * "+(int)Bcolj[k]+" + ");
//System.out.print((int)s+ " ");
					s = galoisField.sum(s, galoisField.product(Arowi[k], Bcolj[k]));
//System.out.print(" S("+(int)s+ ") ");
				}
//System.out.print("\n");				
				C[i][j] = s;
			}
		}
		return X;
	}

	/**
	 * LU Decomposition
	 * 
	 * @return LUDecomposition
	 * @see LUDecomposition
	 */

	public GFLUDecomposition lu() {
		return new GFLUDecomposition(this);
	}

	/**
	 * QR Decomposition
	 * 
	 * @return QRDecomposition
	 * @see QRDecomposition
	 */

//	public QRDecomposition qr() {
//		return new QRDecomposition(this);
//	}

	/**
	 * Cholesky Decomposition
	 * 
	 * @return CholeskyDecomposition
	 * @see CholeskyDecomposition
	 */

//	public CholeskyDecomposition chol() {
//		return new CholeskyDecomposition(this);
//	}

	/**
	 * Singular Value Decomposition
	 * 
	 * @return SingularValueDecomposition
	 * @see SingularValueDecomposition
	 */

//	public SingularValueDecomposition svd() {
//		return new SingularValueDecomposition(this);
//	}

	/**
	 * Eigenvalue Decomposition
	 * 
	 * @return EigenvalueDecomposition
	 * @see EigenvalueDecomposition
	 */

//	public EigenvalueDecomposition eig() {
//		return new EigenvalueDecomposition(this);
//	}

	/**
	 * Solve A*X = B
	 * 
	 * @param B
	 *            right hand side
	 * @return solution if A is square, least squares solution otherwise
	 * @throws GFMatrixException 
	 */

	public GFMatrix solve(GFMatrix B) throws GFMatrixException {
		if(m != n)
			throw new GFMatrixException("Solve not supported in matrix m x n, when m != n");
		
		return new GFLUDecomposition(this).solve(B);
	}

	/**
	 * Solve X*A = B, which is also A'*X' = B'
	 * 
	 * @param B
	 *            right hand side
	 * @return solution if A is square, least squares solution otherwise.
	 * @throws GFMatrixException 
	 */

	public GFMatrix solveTranspose(GFMatrix B) throws GFMatrixException {
		return transpose().solve(B.transpose());
	}

	/**
	 * GFMatrix inverse or pseudoinverse
	 * 
	 * @return inverse(A) if A is square, pseudoinverse otherwise.
	 * @throws GFMatrixException 
	 */

	public GFMatrix inverse() throws GFMatrixException {
		return solve(identity(m, m, galoisField));
	}

	/**
	 * GFMatrix determinant
	 * 
	 * @return determinant
	 */

	public char det() {
		return new GFLUDecomposition(this).det();
	}

	/**
	 * GFMatrix rank
	 * 
	 * @return effective numerical rank, obtained from SVD.
	 */

//	public int rank() {
//		return new SingularValueDecomposition(this).rank();
//	}

	/**
	 * GFMatrix condition (2 norm)
	 * 
	 * @return ratio of largest to smallest singular value.
	 */

//	public char cond() {
//		return new SingularValueDecomposition(this).cond();
//	}

	/**
	 * GFMatrix trace.
	 * 
	 * @return sum of the diagonal elements.
	 */

	public char trace() {
		char t = GaloisField.getZero();
		for (int i = 0; i < Math.min(m, n); i++) {
//			t += A[i][i];
			t = galoisField.sum(t, A[i][i]);
		}
		return t;
	}

	/**
	 * Generate GFMatrix with random elements
	 * 
	 * @param m
	 *            Number of rows.
	 * @param n
	 *            Number of colums.
	 * @return An m-by-n GFMatrix with uniformly distributed random elements.
	 */

//	public static GFMatrix random(int m, int n, GaloisField gf) {
//		GFMatrix A = new GFMatrix(m, n, gf);
//		char[][] X = A.getArray();
//		for (int i = 0; i < m; i++) {
//			for (int j = 0; j < n; j++) {
//				X[i][j] = (char) (Math.random() % 255);
//			}
//		}
//		return A;
//	}

	/**
	 * Generate identity GFMatrix
	 * 
	 * @param m
	 *            Number of rows.
	 * @param n
	 *            Number of colums.
	 * @return An m-by-n GFMatrix with ones on the diagonal and zeros elsewhere.
	 */

	public static GFMatrix identity(int m, int n, GaloisField gf) {
		GFMatrix A = new GFMatrix(m, n, gf);
		char[][] X = A.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X[i][j] = (i == j ? GaloisField.getOne() : GaloisField.getZero());
			}
		}
		return A;
	}

	/**
	 * Return the galois field used
	 * @return
	 */
	public GaloisField getGaloisField() {
		return galoisField;
	}
	
	/**
	 * Return a column of matrix
	 * 
	 * @param index which column
	 * @return column of values
	 */
	public char[] getColumnCopy(int index){
		char col[] = new char[m];

		//System.out.println((int)getColumnsNumber() +" - "+(int)getRowsNumber()+ " index: "+(int)index);
		for(char i=0; i<m; i++){
			//System.out.println("get["+(int)i+"]["+(int)index+"] = "+(int)matrix[i][index]);
			col[i] = A[i][index];
		}
		
		return col;
	}
	
	/**
	 * Print the GFMatrix to stdout. Line the elements up in columns with a
	 * Fortran-like 'Fw.d' style format.
	 * 
	 * @param w
	 *            Column width.
	 * @param d
	 *            Number of digits after the decimal.
	 */

	public void print(int w, int d) {
		print(new PrintWriter(System.out, true), w, d);
	}

	/**
	 * Print the GFMatrix to the output stream. Line the elements up in columns
	 * with a Fortran-like 'Fw.d' style format.
	 * 
	 * @param output
	 *            Output stream.
	 * @param w
	 *            Column width.
	 * @param d
	 *            Number of digits after the decimal.
	 */

	public void print(PrintWriter output, int w, int d) {
		DecimalFormat format = new DecimalFormat();
		format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ITALY));
		format.setMinimumIntegerDigits(1);
		format.setMaximumFractionDigits(d);
		format.setMinimumFractionDigits(d);
		format.setGroupingUsed(false);
		print(output, format, w + 2);
	}

	/**
	 * Print the GFMatrix to stdout. Line the elements up in columns. Use the
	 * format object, and right justify within columns of width characters. Note
	 * that is the GFMatrix is to be read back in, you probably will want to use a
	 * NumberFormat that is set to US Locale.
	 * 
	 * @param format
	 *            A Formatting object for individual elements.
	 * @param width
	 *            Field width for each column.
	 * @see java.text.DecimalFormat#setDecimalFormatSymbols
	 */

	public void print(NumberFormat format, int width) {
		print(new PrintWriter(System.out, true), format, width);
	}
	
	public void print(){
		for(int i=0; i<A.length; i++){
			for(int j=0; j<A[0].length; j++){
				System.out.print((int)A[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	
	public void printChar(){
		for(int i=0; i<A.length; i++){
			for(int j=0; j<A[0].length; j++){
				System.out.print(A[i][j]+" ");
			}
			System.out.print("\n");
		}
	}

	// DecimalFormat is a little disappointing coming from Fortran or C's
	// printf.
	// Since it doesn't pad on the left, the elements will come out different
	// widths. Consequently, we'll pass the desired column width in as an
	// argument and do the extra padding ourselves.

	/**
	 * Print the GFMatrix to the output stream. Line the elements up in columns.
	 * Use the format object, and right justify within columns of width
	 * characters. Note that is the GFMatrix is to be read back in, you probably
	 * will want to use a NumberFormat that is set to US Locale.
	 * 
	 * @param output
	 *            the output stream.
	 * @param format
	 *            A formatting object to format the GFMatrix elements
	 * @param width
	 *            Column width.
	 * @see java.text.DecimalFormat#setDecimalFormatSymbols
	 */

	public void print(PrintWriter output, NumberFormat format, int width) {
		output.println(); // start on new line.
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				String s = format.format(A[i][j]); // format the number
				int padding = Math.max(1, width - s.length()); // At _least_ 1
																// space
				for (int k = 0; k < padding; k++)
					output.print(' ');
				output.print(s);
			}
			output.println();
		}
		output.println(); // end with blank line.
	}

	/**
	 * Read a GFMatrix from a stream. The format is the same the print method, so
	 * printed matrices can be read back in (provided they were printed using US
	 * Locale). Elements are separated by whitespace, all the elements for each
	 * row appear on a single line, the last row is followed by a blank line.
	 * 
	 * @param input
	 *            the input stream.
	 */

//	public static GFMatrix read(BufferedReader input) throws java.io.IOException {
//		StreamTokenizer tokenizer = new StreamTokenizer(input);
//
//		// Although StreamTokenizer will parse numbers, it doesn't recognize
//		// scientific notation (E or D); however, Double.valueOf does.
//		// The strategy here is to disable StreamTokenizer's number parsing.
//		// We'll only get whitespace delimited words, EOL's and EOF's.
//		// These words should all be numbers, for Double.valueOf to parse.
//
//		tokenizer.resetSyntax();
//		tokenizer.wordChars(0, 255);
//		tokenizer.whitespaceChars(0, ' ');
//		tokenizer.eolIsSignificant(true);
//		java.util.Vector<Character> vD = new java.util.Vector<Character>();
//
//		// Ignore initial empty lines
//		while (tokenizer.nextToken() == StreamTokenizer.TT_EOL)
//			;
//		if (tokenizer.ttype == StreamTokenizer.TT_EOF)
//			throw new java.io.IOException("Unexpected EOF on GFMatrix read.");
//		do {
//			vD.addElement(Double.valueOf(tokenizer.sval)); // Read & store 1st
//															// row.
//		} while (tokenizer.nextToken() == StreamTokenizer.TT_WORD);
//
//		int n = vD.size(); // Now we've got the number of columns!
//		double row[] = new double[n];
//		for (int j = 0; j < n; j++)
//			// extract the elements of the 1st row.
//			row[j] = vD.elementAt(j).doubleValue();
//		java.util.Vector<double[]> v = new java.util.Vector<double[]>();
//		v.addElement(row); // Start storing rows instead of columns.
//		while (tokenizer.nextToken() == StreamTokenizer.TT_WORD) {
//			// While non-empty lines
//			v.addElement(row = new double[n]);
//			int j = 0;
//			do {
//				if (j >= n)
//					throw new java.io.IOException("Row " + v.size()
//							+ " is too long.");
//				row[j++] = Double.valueOf(tokenizer.sval).doubleValue();
//			} while (tokenizer.nextToken() == StreamTokenizer.TT_WORD);
//			if (j < n)
//				throw new java.io.IOException("Row " + v.size()
//						+ " is too short.");
//		}
//		int m = v.size(); // Now we've got the number of rows.
//		double[][] A = new double[m][];
//		v.copyInto(A); // copy the rows out of the vector
//		return new GFMatrix(A);
//	}

	/*
	 * ------------------------ Private Methods ------------------------
	 */

	/** Check if size(A) == size(B) **/

	private void checkGFMatrixDimensions(GFMatrix B) {
		if (B.m != m || B.n != n) {
			throw new IllegalArgumentException("GFMatrix dimensions must agree.");
		}
	}

	private static final long serialVersionUID = 1;
}
