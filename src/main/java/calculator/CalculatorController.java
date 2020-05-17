package calculator;

import calculator.model.Calculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculatorController {

    @FXML
    private TextField display;

    private Calculator calculator;
    private boolean startNumber = true;
    private double number1;
    private String operator = "";

    @FXML
    private void initialize() {
        calculator = new Calculator();
    }

    @FXML
    public void processDigit(ActionEvent event) {
        String digitPressed = ((Button) event.getSource()).getText();
        System.out.println(digitPressed);
        if (startNumber || display.getText().equals("0")) {
            display.setText(digitPressed);
        } else {
            display.setText(display.getText() + digitPressed);
        }
        startNumber = false;
    }

    @FXML
    public void processOperator(ActionEvent event) {
        String operatorPressed = ((Button) event.getSource()).getText();
        System.out.println(operatorPressed);
        if (operatorPressed.equals("=")) {
           if (operator.isEmpty()) {
               return;
           }
           double number2 = Double.parseDouble(display.getText());
           double result = calculator.calculate(number1, number2, operator);
            setDisplayContent(result);
            operator = "";
        } else {
            if (! operator.isEmpty()) {
                return;
            }
            number1 = Double.parseDouble(display.getText());
            operator = operatorPressed;
            startNumber = true;
        }
    }

    /**
     * Sets the content of the display while paying attention
     * not to print unnecessary decimal dots.
     *
     * @param num the new content
     */
    private void setDisplayContent(double num) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

        display.setText(df.format(num));
    }

    @FXML
    public void resetCalculator(ActionEvent event) {
        number1 = 0;
        operator = "";
        startNumber = true;
        display.setText("0");
        System.out.println("AC");
    }

    public void decimalDot(ActionEvent event) {
        String s = display.getText();
        if ( ! s.contains(".")) {
            System.out.println(".");
            double current = Double.parseDouble(display.getText());
            display.setText(String.format("%.0f.", current));
        }
    }

    public void numberSignChange(ActionEvent event) {
        double current = Double.parseDouble(display.getText());
        setDisplayContent(- current);
    }

}
