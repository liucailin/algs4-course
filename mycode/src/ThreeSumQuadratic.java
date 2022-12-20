import java.util.Arrays;

/**
 * Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case.
 */
public class ThreeSumQuadratic {
    public int count(int[] a) {
        Arrays.sort(a);
        int n = 0;
        for (int i = 0; i < a.length - 2; i++) {
            int k = a[i];
            int start = i + 1;
            int end = a.length -  1;
            while (start < end) {
                int b = a[start];
                int c = a[end];
                if (k + b + c == 0) {
                  n += 1;
                  start += 1;
                  end -= 1;
                } else if (k + b + c > 0) {
                    end -= 1;
                } else {
                    start += 1;
                }
            }
        }
        return n;
    }
}
