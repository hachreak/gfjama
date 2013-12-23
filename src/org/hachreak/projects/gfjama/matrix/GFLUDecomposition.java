/**
 * 
 */
package org.hachreak.projects.gfjama.matrix;

/**
 * @author hachreak
 *
 */
public class GFLUDecomposition implements java.io.Serializable {

/* ------------------------
   Class variables
 * ------------------------ */

   /** Array for internal storage of decomposition.
   @serial internal array storage.
   */
   private char[][] LU;

   /** Row and column dimensions, and pivot sign.
   @serial column dimension.
   @serial row dimension.
   @serial pivot sign.
   */
   private int m, n;//, pivsign; 

   /** Internal storage of pivot vector.
   @serial pivot vector.
   */
   private int[] piv;

private GaloisField galoisField;

/* ------------------------
   Constructor
 * ------------------------ */

   /** LU Decomposition
       Structure to access L, U and piv.
   @param  A Rectangular GFMatrix
   */

   public GFLUDecomposition (GFMatrix A) {

	  galoisField = A.getGaloisField();
	   
   // Use a "left-looking", dot-product, Crout/Doolittle algorithm.
	   
      LU = A.getArrayCopy();
      m = A.getRowDimension();
      n = A.getColumnDimension();
      piv = new int[m];
      for (int i = 0; i < m; i++) {
         piv[i] = i;
      }
      //pivsign = 1;
      char[] LUrowi;
      char[] LUcolj = new char[m];

      // Outer loop.

      for (int j = 0; j < n; j++) {

         // Make a copy of the j-th column to localize references.

         for (int i = 0; i < m; i++) {
            LUcolj[i] = LU[i][j];
         }

         // Apply previous transformations.

         for (int i = 0; i < m; i++) {
            LUrowi = LU[i];

            // Most of the time is spent in the following dot product.

            int kmax = Math.min(i,j);
            char s = GaloisField.getZero();
            for (int k = 0; k < kmax; k++) {
//               s += LUrowi[k]*LUcolj[k];
            	s = galoisField.sum(s, galoisField.product(LUrowi[k], LUcolj[k]));
            }

//            LUrowi[j] = LUcolj[i] -= s;
            LUcolj[i] = galoisField.minus(LUcolj[i], s);
            LUrowi[j] = LUcolj[i];
         }
   
         // Find pivot and exchange if necessary.

         int p = j;
         for (int i = j+1; i < m; i++) {
            //if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
        	if(LUcolj[i] > LUcolj[p]){
               p = i;
            }
         }
         if (p != j) {
            for (int k = 0; k < n; k++) {
               char t = LU[p][k]; LU[p][k] = LU[j][k]; LU[j][k] = t;
            }
            int k = piv[p]; piv[p] = piv[j]; piv[j] = k;
            //pivsign = -pivsign;
         }

         // Compute multipliers.
         
         if (j < m & LU[j][j] != GaloisField.getZero()) {
            for (int i = j+1; i < m; i++) {
//               LU[i][j] /= LU[j][j];
            	LU[i][j] = galoisField.divide(LU[i][j], LU[j][j]);
            }
         }
      }
   }

/* ------------------------
   Temporary, experimental code.
   ------------------------ *\

   \** LU Decomposition, computed by Gaussian elimination.
   <P>
   This constructor computes L and U with the "daxpy"-based elimination
   algorithm used in LINPACK and MATLAB.  In Java, we suspect the dot-product,
   Crout algorithm will be faster.  We have temporarily included this
   constructor until timing experiments confirm this suspicion.
   <P>
   @param  A             Rectangular GFMatrix
   @param  linpackflag   Use Gaussian elimination.  Actual value ignored.
   @return               Structure to access L, U and piv.
   *\

   public LUDecomposition (GFMatrix A, int linpackflag) {
      // Initialize.
      LU = A.getArrayCopy();
      m = A.getRowDimension();
      n = A.getColumnDimension();
      piv = new int[m];
      for (int i = 0; i < m; i++) {
         piv[i] = i;
      }
      pivsign = 1;
      // Main loop.
      for (int k = 0; k < n; k++) {
         // Find pivot.
         int p = k;
         for (int i = k+1; i < m; i++) {
            if (Math.abs(LU[i][k]) > Math.abs(LU[p][k])) {
               p = i;
            }
         }
         // Exchange if necessary.
         if (p != k) {
            for (int j = 0; j < n; j++) {
               char t = LU[p][j]; LU[p][j] = LU[k][j]; LU[k][j] = t;
            }
            int t = piv[p]; piv[p] = piv[k]; piv[k] = t;
            pivsign = -pivsign;
         }
         // Compute multipliers and eliminate k-th column.
         if (LU[k][k] != 0.0) {
            for (int i = k+1; i < m; i++) {
               LU[i][k] /= LU[k][k];
               for (int j = k+1; j < n; j++) {
                  LU[i][j] -= LU[i][k]*LU[k][j];
               }
            }
         }
      }
   }

\* ------------------------
   End of temporary code.
 * ------------------------ */

/* ------------------------
   Public Methods
 * ------------------------ */

   /** Is the GFMatrix nonsingular?
   @return     true if U, and hence A, is nonsingular.
   */

   public boolean isNonsingular () {
      for (int j = 0; j < n; j++) {
//    	  System.out.println((int)LU[j][j]+" "+(int)GaloisField.getZero());
         if (LU[j][j] == GaloisField.getZero())
            return false;
      }
      return true;
   }

   /** Return lower triangular factor
   @return     L
   */

   public GFMatrix getL (GaloisField galoisField) {
      GFMatrix X = new GFMatrix(m,n, galoisField);
      char[][] L = X.getArray();
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++) {
            if (i > j) {
               L[i][j] = LU[i][j];
            } else if (i == j) {
               L[i][j] = GaloisField.getOne();
            } else {
               L[i][j] = GaloisField.getZero();
            }
         }
      }
      return X;
   }

   /** Return upper triangular factor
   @return     U
   */

   public GFMatrix getU (GaloisField galoisField) {
      GFMatrix X = new GFMatrix(n, n, galoisField);
      char[][] U = X.getArray();
      for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
            if (i <= j) {
               U[i][j] = LU[i][j];
            } else {
               U[i][j] = GaloisField.getZero();
            }
         }
      }
      return X;
   }

   /** Return pivot permutation vector
   @return     piv
   */

   public int[] getPivot () {
      int[] p = new int[m];
      for (int i = 0; i < m; i++) {
         p[i] = piv[i];
      }
      return p;
   }

   /** Return pivot permutation vector as a one-dimensional char array
   @return     (char) piv
   */

   public char[] getcharPivot () {
      char[] vals = new char[m];
      for (int i = 0; i < m; i++) {
         vals[i] = (char) piv[i];
      }
      return vals;
   }

   /** Determinant
   @return     det(A)
   @exception  IllegalArgumentException  GFMatrix must be square
   */

   public char det () {
      if (m != n) {
         throw new IllegalArgumentException("GFMatrix must be square.");
      }
      char d = LU[0][0]; //(char) pivsign;
      for (int j = 0; j < n; j++) {
//         d *= LU[j][j];
    	  d = galoisField.product(d, LU[j][j]);
      }
      return d;
   }

   /** Solve A*X = B
   @param  B   A GFMatrix with as many rows as A and any number of columns.
   @return     X so that L*U*X = B(piv,:)
   @exception  IllegalArgumentException GFMatrix row dimensions must agree.
   @exception  RuntimeException  GFMatrix is singular.
   */

   public GFMatrix solve (GFMatrix B) {
      if (B.getRowDimension() != m) {
         throw new IllegalArgumentException("GFMatrix row dimensions must agree.");
      }
      if (!this.isNonsingular()) {
         throw new RuntimeException("GFMatrix is singular.");
      }

      // Copy right hand side with pivoting
      int nx = B.getColumnDimension();
      GFMatrix Xmat = B.getGFMatrix(piv,0,nx-1);
      char[][] X = Xmat.getArray();

      GaloisField galoisField = B.getGaloisField();
      
      // Solve L*Y = B(piv,:)
      for (int k = 0; k < n; k++) {
         for (int i = k+1; i < n; i++) {
            for (int j = 0; j < nx; j++) {
//               X[i][j] -= X[k][j]*LU[i][k];
            	X[i][j] = galoisField.minus(X[i][j], galoisField.product(X[k][j], LU[i][k]));
            }
         }
      }
      // Solve U*X = Y;
      for (int k = n-1; k >= 0; k--) {
         for (int j = 0; j < nx; j++) {
//            X[k][j] /= LU[k][k];
        	 X[k][j] = galoisField.divide(X[k][j], LU[k][k]);
         }
         for (int i = 0; i < k; i++) {
            for (int j = 0; j < nx; j++) {
//               X[i][j] -= X[k][j]*LU[i][k];
            	X[i][j] = galoisField.minus(X[i][j], galoisField.product(X[k][j], LU[i][k]));
            }
         }
      }
      return Xmat;
   }
  private static final long serialVersionUID = 1;
}
