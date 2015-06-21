package calculatorFX;

/**
 * Created by Jura on 21.06.2015.
 */
public class MyProcessor extends AbstractProcessor {

    private double value2 = 0; // второе значение
    private int valStatus = 0; // 0 - пишем первое значение, 1 - пишем воторое значение
    private int operation; // 0 - плюс, 1 - минус, 2 - умножить, 3 - поделить, 4 - возведение в степень
    private boolean oper = false; // true в случае ввода "5 +(-,*,/) ="
    private boolean add = false; // true - если после вывода результата следует оператор '+', '-', '*' или '/' для продолжения работы с полученным результатом
    private boolean fraction = false; // true - если вводится дробное число
    private int pow = -1;
    private double memory = 0; // память
    private boolean mem = false; // true - если вызвана память (MR)
    private boolean addmem = false; // true - если вызвана память+ (М+)

    private char[] symbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '*', '/', '=', '.','n','s','r','c','m','M','p','l'};

    {
        setTemp(0);
    }

    @Override
    public String showMem() {
        return "" + memory;
    }

    @Override
    public void inputChar(char c) {
        //System.out.println(c);

        for (int i = 0; i < symbols.length; i++) {

            if(c == symbols[i]) {

                if(c == '+' || c == '-' || c == '*' || c == '/' || c == 'p' ) {

                    if(add) {
                        if (mem) {
                            setTemp(getResult());
                            mem = false;
                        } else {
                            setTemp(getResult());
                        }
                    }
                    if (valStatus == 1) {

                        if(mem) {

                            if (operation == 0) {
                                setTemp(getTemp() + getResult());
                            } else if (operation == 1) {
                                setTemp(getTemp() - getResult());
                            } else if (operation == 2) {
                                setTemp(getTemp() * getResult());
                            } else if (operation == 3){
                                setTemp( getTemp() / getResult() );
                            } else {
                                setTemp( Math.pow(getTemp(), getResult()) );
                            }

                            setOperation(c);
                            value2 = 0;
                            mem = false;
                        }

                        if (operation == 0) {
                            setTemp(getTemp() + value2);
                        } else if (operation == 1) {
                            setTemp(getTemp() - value2);
                        } else if (operation == 2) {
                            setTemp(getTemp() * value2);
                        } else if (operation == 3){
                            setTemp( getTemp() / value2 );
                        } else {
                            setTemp( Math.pow(getTemp(), value2) );
                        }

                        setOperation(c);
                        value2 = 0;

                    } else {
                        if (mem) {
                            setTemp(getResult());
                            mem = false;
                        }
                        valStatus = 1;
                        setOperation(c);
                        value2 = 0;
                    }
                    add = false;
                    oper = true;
                    pow = -1;
                    fraction = false;

                } else if (c == '=') {

                    if (oper) {
                        if (mem) {
                            value2 = getResult();
                            mem = false;
                        } else {
                            value2 = getTemp();
                        }
                    }

                    if (add) {
                        if (operation == 0) {
                            setResult(getResult() + value2);
                        } else if (operation == 1) {
                            setResult(getResult() - value2);
                        } else if (operation == 2) {
                            setResult(getResult() * value2);
                        } else if (operation == 3){
                            setResult( getResult() / value2 );
                        } else {
                            setResult( Math.pow(getResult(), value2) );
                        }
                    } else {
                        if (operation == 0) {
                            setResult(getTemp() + value2);
                        } else if (operation == 1) {
                            setResult(getTemp() - value2);
                        } else if (operation == 2) {
                            setResult(getTemp() * value2);
                        } else if (operation == 3) {
                            setResult( getTemp() / value2 );
                        } else {
                            setResult( Math.pow(getTemp(), value2) );
                        }
                    }

                    setTemp(0);
                    valStatus = 0;
                    add = true;
                    pow = -1;
                    fraction = false;
                    oper = false;

                } else if (c == 'n') {
                    if (add | mem) {
                        setResult(getResult()*(-1));
                    } else {
                        if (valStatus == 0) {
                            setTemp(getTemp()*(-1));
                            setResult(getTemp());
                        } else {
                            value2 *= -1;
                            setResult(value2);
                        }
                    }
                } else if (c == 'r') {
                    if (add | mem) {
                        setResult( 1 / getResult() );
                    } else {
                        if (valStatus == 0) {
                            setTemp( 1 / getTemp() );
                            setResult(getTemp());
                        } else {
                            value2 = 1 / value2;
                            setResult(value2);
                        }
                    }
                    fraction = false;
                    pow = -1;
                    mem = true;
                } else if(c == 's') {
                    if (add | mem) {
                        setResult( Math.sqrt(getResult() ) );
                    } else {
                        if (valStatus == 0) {
                            setTemp( Math.sqrt(getTemp() ) );
                            setResult(getTemp());
                        } else {
                            value2 = Math.sqrt(value2);
                            setResult(value2);
                        }
                    }
                    fraction = false;
                    pow = -1;
                    mem = true;
                } else if(c == 'c') {
                    setTemp(0);
                    value2 = 0;
                    setResult(0);
                    valStatus = 0;
                    pow = -1;
                    add = false;
                    fraction = false;
                    oper = false;
                } else if (c == 'm') {
                    memory += getResult();
                    addmem = true;
                } else if (c == 'M') {
                    if (memory != 0) {
                        setResult(memory);
                        mem = true;
                    }
                } else if (c == 'l') {
                    memory = 0;
                    addmem = false;
                    mem = false;
                } else {

                    if(addmem | mem) {
                        if (valStatus == 0) {
                            setTemp(0);
                        } else {
                            value2 = 0;
                        }
                    }
                    addmem = false;
                    mem = false;
                    oper = false;

                    if (c == '.') {
                        fraction = true;
                    } else {
                        add = false;

                        if (valStatus == 0) {
                            if (getTemp() >= 0) {
                                inputTemp(c, 1);
                            } else {
                                inputTemp(c, -1);
                            }
                        } else {
                            if(value2 >= 0) {
                                inputValue2(c, 1);
                            } else {
                                inputValue2(c, -1);
                            }
                        }
                    }
                }
            }
        }
    }
    private void setOperation(char c) {
        if (c == '+') {
            operation = 0;
        } else if (c == '-') {
            operation = 1;
        } else if (c == '*') {
            operation = 2;
        } else if (c == '/'){
            operation = 3;
        } else {
            operation = 4;
        }
    }

    private void inputTemp(char c, int i) {
        if (fraction) {
            setTemp( getTemp() + i*( (int) (c) - 48 )*Math.pow(10, pow) );
            setResult(getTemp());
            pow--;
        } else {
            setTemp( getTemp()*10 + i*( (int) (c) - 48) );
            setResult(getTemp());
        }
    }

    private void inputValue2(char c, int i) {
        if (fraction) {
            value2 = value2 + i*( (int) (c) - 48 )*Math.pow(10, pow);
            setResult(value2);
            pow--;
        } else {
            value2 = value2*10 + i*( (int) (c) - 48 );
            setResult(value2);
        }
    }
}

