package mcm;

public class Recursion {
    public static int recurse(int i, int j, int[] arr) {
        // i = index of first matrix
        // j = index of last matrix
        // Base Case: Only 1 matrix left -> number of operations = 0
        if (i == j) {
            return 0;
        }
        int count = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            int recurseOperations = recurse(i, k, arr) + recurse(k+1, j, arr);
            int currOperations = arr[i-1] * arr[k] * arr[j];
            int totalOperations = currOperations + recurseOperations;

            if (totalOperations < count) {
                count = totalOperations;
            }
        }
        return count;
    }
}
