public class Polynomial {
    double[]  coefficients;
    public Polynomial() {
        this.coefficients = new double[] {0};
    }
    public Polynomial(double[] polynomial) {
        coefficients = new double[polynomial.length];
        for (int i = 0; i < polynomial.length; i++) {
            coefficients[i] = polynomial[i];
        }
    }

    public Polynomial add(Polynomial new_pol) {
        double[] new_coef = new_pol.coefficients;
        Polynomial result = new Polynomial();
        result.coefficients = new double[Math.max(this.coefficients.length, new_coef.length)];
        for (int i = 0; i < this.coefficients.length; i++) {
            result.coefficients[i] = this.coefficients[i];
        }
        for (int i = 0; i < new_coef.length; i++) {
            result.coefficients[i] += new_coef[i];
        }
        return result;
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, i) * this.coefficients[i];
        }
        return result;
    }

    public boolean hasRoot(double x) {
        double result = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            result += Math.pow(x, i) * this.coefficients[i];
        }
        return (result == 0);
    }

}
