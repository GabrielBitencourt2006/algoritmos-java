public class Contagem {
    public static int contar(int[] dados, int n) {
        if (dados == null || dados.length == 0) return 0;
        
        int primeiroDado = dados[0];
        int min = Math.min(primeiroDado, n);
        int max = Math.max(primeiroDado, n);
        
        int contador = 0;
        
        for (int i = 0; i < dados.length; i++) {
            if (dados[i] >= min && dados[i] <= max) {
                contador++;
            }
        }
        
        return contador;
    }
}