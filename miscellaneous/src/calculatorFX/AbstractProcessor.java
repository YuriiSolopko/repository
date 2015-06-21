package calculatorFX;

/**
 * Created by Jura on 21.06.2015.
 */
public abstract class AbstractProcessor implements Processorable {
    private double result;
    private double temp;

    @Override
    public void printResult() {
        System.out.println("Result = " + result);
    }

    public abstract String showMem();

    public double getResult() {
        return result;
    }

    public double getTemp() {
        return temp;
    }

    public void setResult(double r) {
        result = r;
    }

    public void setTemp(double t) {
        temp = t;
    }

}