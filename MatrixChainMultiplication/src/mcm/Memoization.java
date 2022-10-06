package mcm;

import static mcm.Input.NUMBER_OF_MATRICES;

/**
 * @author Terng Yan Long (唐延龍）
 */
public class Memoization {
    // Creation of a 2D Array that acts as a table in order to store the intermediate results.
    // The first row and column (index 0) are not used and are simply added due to the effect of zero-based numbering.
    public static int[][] memoizedTable = new int[NUMBER_OF_MATRICES +1][NUMBER_OF_MATRICES +1];

    // Fill all elements in the table with -1 (since the default value for int arrays is 0, which is a possible value
    // and thus will affect the intermediate calculations)
    public static void initMemoizedTable() {
        for (int i = 1; i <= NUMBER_OF_MATRICES; i++) {
            for (int j = 1; j <= NUMBER_OF_MATRICES; j++) {
                memoizedTable[i][j] = -1;
            }
        }
    }

    public static int memoize(int i, int j, int[] arr) {
        // i = index of first matrix
        // j = index of last matrix
        // Base Case: Only 1 matrix left -> number of operations = 0
        if (i == j) {
            return 0;
        }

        // Check if stored value exists in the table
        if (memoizedTable[i][j] != -1) {
            return memoizedTable[i][j];
        }

        memoizedTable[i][j] = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int recurseOperations = memoize(i, k, arr) + memoize(k+1, j, arr);
            int currOperations = arr[i-1] * arr[k] * arr[j];
            int totalOperations = currOperations + recurseOperations;

            if (totalOperations < memoizedTable[i][j]) {
                memoizedTable[i][j] = totalOperations;
            }
        }
        return memoizedTable[i][j];
    }
}
