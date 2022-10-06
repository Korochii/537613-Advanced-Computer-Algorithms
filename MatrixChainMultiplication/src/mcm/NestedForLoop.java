package mcm;

import static mcm.Input.NUMBER_OF_MATRICES;

/**
 * @author Terng Yan Long (唐延龍）
 */
public class NestedForLoop {
    // Default value for each element in the 2D Array is 0
    public static int[][] iterativeTable = new int[NUMBER_OF_MATRICES + 1][NUMBER_OF_MATRICES + 1];

    public static int iterate(int start, int end, int[] arr) {
        // Let diff be the difference between the value of the current row and column
        for (int diff = 1; diff <= NUMBER_OF_MATRICES - 1; diff++) {
            for (int i = start; i <= NUMBER_OF_MATRICES - diff; i++) {
                int j = i + diff;
                iterativeTable[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int currOperations = iterativeTable[i][k] + iterativeTable[k+1][j] + (arr[i-1] * arr[k] * arr[j]);

                    if (currOperations < iterativeTable[i][j]) {
                        iterativeTable[i][j] = currOperations;
                    }
                }
            }
        }
        return iterativeTable[1][NUMBER_OF_MATRICES];
    }


}
