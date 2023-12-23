import java.util.Scanner;


public class NewtonRaphson {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a aproximação inicial (x0):");
        double x0 = scanner.nextDouble();

        System.out.println("Digite o número máximo de iterações:");
        int maxIterations = scanner.nextInt();

        double root = calculateNewtonRaphson(x0, maxIterations);

        System.out.println("A raiz aproximada é: " + root);
    }

    private static double calculateNewtonRaphson(double x0, int maxIterations) {
        double x = x0;
        int iterations = 0;

        while (iterations < maxIterations) {
            double fx = f(x);
            double fPrimeX = fPrime(x);

            if (fPrimeX == 0) {
                System.out.println("A derivada é zero. O método de Newton-Raphson não pode continuar.");
                return Double.NaN;
            }

            x = x - fx / fPrimeX;

            if (Math.abs(fx) < 1e-8) {
                System.out.println("Convergiu após " + iterations + " iterações.");
                return x;
            }

            iterations++;
        }

        System.out.println("Não convergiu após " + maxIterations + " iterações.");
        return Double.NaN;
    }

    private static double f(double x) {
        // Função f(x) = x^2 - 5
        return x * x - 5;
    }

    private static double fPrime(double x) {
        // Derivada de f(x) = 2x
        return 2 * x;
    }
}
