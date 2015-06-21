package calculatorFX;

/**
 * Created by Jura on 21.06.2015.
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class CalcFX extends Application {
    AbstractProcessor proc = new MyProcessor();
    Calc calc = new Calc(proc);
    TextField text = new TextField();
    Label mem = new Label();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Calculator");
        stage.setScene(createScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Scene createScene() {
        GridPane buttons = new GridPane();
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setVgap(5);
        buttons.setHgap(10);
        buttons.setPadding(new Insets(5, 5, 5, 5));

        buttons.add(createButton("7", '7'), 0, 0);
        buttons.add(createButton("8", '8'), 1, 0);
        buttons.add(createButton("9", '9'), 2, 0);
        buttons.add(createButton("/", '/'), 3, 0);
        buttons.add(createButton("±", 'n'), 4, 0);
        buttons.add(createButton("sqrt", 's'), 5, 0);

        buttons.add(createButton("4", '4'), 0, 1);
        buttons.add(createButton("5", '5'), 1, 1);
        buttons.add(createButton("6", '6'), 2, 1);
        buttons.add(createButton("*", '*'), 3, 1);
        buttons.add(createButton("1/x", 'r'), 4, 1);
        buttons.add(createButton("x^y", 'p'), 5, 1);

        buttons.add(createButton("1", '1'), 0, 2);
        buttons.add(createButton("2", '2'), 1, 2);
        buttons.add(createButton("3", '3'), 2, 2);
        buttons.add(createButton("-", '-'), 3, 2);
        buttons.add(createButton("M+", 'm'), 4, 2);
        buttons.add(createButton("MR", 'M'), 5, 2);

        buttons.add(createButton("0", '0'), 0, 3);
        buttons.add(createButton(".", '.'), 1, 3);
        buttons.add(createButton("=", '='), 2, 3);
        buttons.add(createButton("+", '+'), 3, 3);
        buttons.add(createButton("MC", 'l'), 4, 3);
        buttons.add(createButton("C", 'c'), 5, 3);

        VBox main = new VBox(1);
        main.setAlignment(Pos.CENTER_RIGHT);
        text.setText("0");
        text.setDisable(true);
        //text.setEditable(false);
        text.setAlignment(Pos.CENTER_RIGHT);
        Font f = new Font("SansSerif", 26);
        text.setFont(f);
        mem.setText("Mem = 0");
        mem.setAlignment(Pos.CENTER_LEFT);
        mem.setMinWidth(80);
        main.getChildren().add(text);
        main.getChildren().add(mem);
        main.getChildren().add(buttons);

        Scene scene = new Scene(main, 330, 270);
        scene.setOnKeyPressed(new KeyInsert<KeyEvent>());
        return scene;
    }

    public Button createButton(String name, char c) {
        Button b = new Button(name);
        b.setOnAction(new Insert<ActionEvent>(c));
        b.setMinSize(45, 35);
        return b;
    }

    public class KeyInsert<T> implements EventHandler<KeyEvent> {

        private char[] symbols = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '+', '-', '*', '/', '=', '.'};

        @Override
        public void handle(KeyEvent e) {

            for (int i = 0; i < symbols.length; i++) {
                if (e.getText().charAt(0) == symbols[i]) {
                    calc.inSymb(symbols[i]);
                    text.setText(calc.showResult());
                }
            }
            if (e.getCode() == KeyCode.ENTER) {
                calc.inSymb('=');
                text.setText(calc.showResult());
            }
        }
    }

    public class Insert<T> implements EventHandler {

        char label;

        @Override
        public void handle(Event arg0) {
            calc.inSymb(label);
            text.setText(calc.showResult());
            mem.setText("Mem = " + calc.showMem());
        }

        public Insert(char c) {
            label = c;
        }

    }

}
