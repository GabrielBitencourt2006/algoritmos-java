import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        System.out.println("--- 1. Numero Primo ---");
        System.out.println("7 e primo? " + Primo.verificar(7));
        System.out.println("10 e primo? " + Primo.verificar(10));

        System.out.println("\n--- 2. Somatorio ---");
        int[] nums = {1, 2, 3, 4, 5};
        System.out.println("Soma: " + Somatorio.calcular(nums));

        System.out.println("\n--- 3. Fibonacci ---");
        System.out.println("6 primeiros termos: " + Arrays.toString(Fibonacci.gerar(6)));

        System.out.println("\n--- 4. MDC ---");
        System.out.println("MDC de 48 e 18: " + Mdc.calcular(48, 18));

        System.out.println("\n--- 5. Quicksort ---");
        int[] desordenado = {10, 5, 2, 3, 7};
        System.out.println("Original: " + Arrays.toString(desordenado));
        Quicksort.ordenar(desordenado);
        System.out.println("Ordenado: " + Arrays.toString(desordenado));

        System.out.println("\n--- 6. Contagem ---");
        int[] dados = {5, 8, 12, 18, 20};
        System.out.println("Valores entre 5 e 15: " + Contagem.contar(dados, 15));
    }
}