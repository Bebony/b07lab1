import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Polynomial {
    double[]  coefficients;
    int[] exponents;
    public Polynomial() {
        this.coefficients = new double[] {0};
        this.exponents = new int[] {0};
    }
    public Polynomial(double[] polynomial_coef, int[] polynomial_exp) {
        this.coefficients = new double[polynomial_coef.length];
        this.exponents = new int[polynomial_exp.length];
        for (int i = 0; i < polynomial_coef.length; i++) {
            this.coefficients[i] = polynomial_coef[i];
        }
        for (int i = 0; i < polynomial_exp.length; i++) {
            this.exponents[i] = polynomial_exp[i];
        }
    }

    public Polynomial(File f) throws FileNotFoundException, IOException {
        BufferedReader input = new BufferedReader(new FileReader(f));
        input.close();
        String polynomial = input.readLine();
        polynomial = polynomial.replace("-", "+-");
        String[] elements = polynomial.split("+");
        for (int i = 0; i < elements.length; i++) {
            String[] element = elements[i].split("x");
            if (element.length == 0) {
                this.exponents[i] = 1;
                this.coefficients[i] = 1;
                continue;
            }
            this.coefficients[i] = Double.parseDouble(element[0]);
            if (element.length > 1) {
                this.exponents[i] = Integer.parseInt(element[1]);
            }
            else {
                this.exponents[i] = 0;
            }
        }

    }


    public Polynomial add(Polynomial new_polynomial) {
        double[] new_coefficients = new_polynomial.coefficients;
        int[] new_exponents = new_polynomial.exponents;
        Polynomial result = new Polynomial();
        Polynomial smaller_degree = new Polynomial();
        if (new_exponents.length > this.exponents.length) {
            result.coefficients = new_coefficients;
            result.exponents = new_exponents;
            smaller_degree = this;
        }
        else {
            result.coefficients = this.coefficients;
            result.exponents = this.exponents;
            smaller_degree = new_polynomial;
        }
        for (int i = 0; i < result.exponents.length; i++) {
            for (int j = 0; j < smaller_degree.exponents.length; j++) {
                if (smaller_degree.exponents[j] == result.exponents[i]) {
                    result.coefficients[i] += smaller_degree.coefficients[j];
                    continue; // I assumed that each exponent is unique in given polynomials
                }
            }
        }
        return result;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, this.exponents[i]) * this.coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, this.exponents[i]) * this.coefficients[i];
        }
        return (result == 0);
    }

    public Polynomial multiply(Polynomial new_polynomial) {
        double[] new_coefficients = new_polynomial.coefficients;
        int[] new_exponents = new_polynomial.exponents;
        double[] result_coefficients = new double[1000];
        for (int i = 0; i < this.exponents.length; i++) {
            for (int j = 0; j < new_exponents.length; j++) {
                result_coefficients[this.exponents[i] + new_exponents[j]] += this.coefficients[i] * new_coefficients[j];
            }
        }
        Polynomial result = new Polynomial();
        int t = 0;
        for (int i = 0; i < result_coefficients.length; i++) {
            if (result_coefficients[i] != 0) {
                result.coefficients[t] = result_coefficients[i];
                result.exponents[t] = i;
                t++;
            }
        }
        return result;
    }

    public void saveToFile(String file) throws FileNotFoundException {
        String polynomial = "";
        String operator = "";
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] == 0) {
                continue;
            }
            if (polynomial.length() != 0 && coefficients[i] > 0) {
                operator = "+";
            }
            else if (coefficients[i] < 0) {
                operator = "";
            }
            polynomial = polynomial + operator + coefficients[i];
            if (exponents[i] != 0) {
                polynomial = polynomial + "x" + exponents[i];
            }
        }
        java.io.PrintStream output = new PrintStream(file);
        output.print(polynomial);
        output.close();
    }


}
