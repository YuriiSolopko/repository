package calculatorFX;

/**
 * Created by Jura on 21.06.2015.
 */
public class Calc {
    AbstractProcessor proc;

    public Calc(AbstractProcessor pr) {
        proc = pr;
    }

    public String showResult() {
        return "" + proc.getResult();
    }
    public void inSymb(char c) {
        proc.inputChar(c);
        //proc.printResult();
    }

    public String showMem() {
        return proc.showMem();
    }

}