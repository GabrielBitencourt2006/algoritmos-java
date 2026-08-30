public class Quicksort {
    // Método principal que a gente vai chamar
    public static void ordenar(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        executarQuicksort(arr, 0, arr.length - 1);
    }

    private static void executarQuicksort(int[] arr, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = particionar(arr, inicio, fim);
            executarQuicksort(arr, inicio, posicaoPivo - 1);
            executarQuicksort(arr, posicaoPivo + 1, fim);
        }
    }

    private static int particionar(int[] arr, int inicio, int fim) {
        int pivo = arr[fim];
        int i = (inicio - 1);

        for (int j = inicio; j < fim; j++) {
            if (arr[j] <= pivo) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[fim];
        arr[fim] = temp;

        return i + 1;
    }
}