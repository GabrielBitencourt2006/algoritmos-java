public class Fibonacci {
    public static int[] gerar(int n) {
        if (n <= 0) return new int[0]; 
        if (n == 1) return new int[]{0}; 
        
        int[] seq = new int[n];
        seq[0] = 0;
        seq[1] = 1;
        
        for (int i = 2; i < n; i++) {
            seq[i] = seq[i - 1] + seq[i - 2];
        }
        
        return seq;
    }
}