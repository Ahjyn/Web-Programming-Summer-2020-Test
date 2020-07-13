package com.example.pizzaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 5;
    final int CHEESE_PRICE = 1;
    final int EXTRA_CHEESE_PRICE = 2;
    final int GREEN_PEPPER_PRICE =2;
    final int ITALIAN_SAUSAGE_PRICE =3;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // cheese selection
        CheckBox cheesePizza = (CheckBox) findViewById(R.id.cheese_pizza_checked);
        boolean hasCheese = cheesePizza.isChecked();

        // check extra cheese
        CheckBox extraCheese = (CheckBox) findViewById(R.id.extra_cheese_checked);
        boolean hasExtraCheese = extraCheese.isChecked();

        // check peppers
        CheckBox greenPeppers = (CheckBox) findViewById(R.id.green_peppers_checked);
        boolean hasGreenPeppers = greenPeppers.isChecked();

        // check sausage
        CheckBox italianSausage = (CheckBox) findViewById(R.id.italian_sausage_checked);
        boolean hasItalianSausage = italianSausage.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasCheese, hasExtraCheese, hasGreenPeppers, hasItalianSausage);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasCheese, hasExtraCheese, hasGreenPeppers, hasItalianSausage, totalPrice);
        Intent redirect = new Intent(MainActivity.this, Summary.class);
        redirect.putExtra("summary", orderSummaryMessage);
        startActivity(redirect);
        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents

    }

    public void sendEmail(View view) {
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check cheese
        CheckBox cheesePizza = (CheckBox) findViewById(R.id.cheese_pizza_checked);
        boolean hasCheese = cheesePizza.isChecked();

        // check extra cheese
        CheckBox extraCheese = (CheckBox) findViewById(R.id.extra_cheese_checked);
        boolean hasExtraCheese = extraCheese.isChecked();

        // check if green peppers
        CheckBox greenPeppers = (CheckBox) findViewById(R.id.green_peppers_checked);
        boolean hasGreenPeppers = greenPeppers.isChecked();

        // check sausage
        CheckBox italianSausage = (CheckBox) findViewById(R.id.italian_sausage_checked);
        boolean hasItalianSausage = italianSausage.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasCheese, hasExtraCheese, hasGreenPeppers, hasItalianSausage);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasCheese, hasExtraCheese, hasGreenPeppers, hasItalianSausage, totalPrice);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sangjinhyun1@outlook.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Pizza Order");
        i.putExtra(Intent.EXTRA_TEXT   , orderSummaryMessage);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "No email matches that title.", Toast.LENGTH_SHORT).show();
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasCheese, boolean hasExtraCheese, boolean hasGreenPeppers, boolean hasItalianSausage, float price) {
        String orderSummaryMessage = "Name: "+userInputName + "\n" +
                "Your choices were from the following options:" +
                "Add Cheese Pizza "+boolToString(hasCheese) + "\n" +
                "Add Extra Cheese "+boolToString(hasExtraCheese) + "\n" +
                "Add Green Pepper "+boolToString(hasGreenPeppers) + "\n" +
                "Add Italian Sausage "+boolToString(hasItalianSausage) + "\n" +
                "Quantity: "+quantity + "\n" +
                "Total: $ "+ price + "\n" +
                "Thank You for your order! Please come again!";
        return orderSummaryMessage;
    }

    /**
     * Calculate and return price
     *
     *
     */
    private float calculatePrice(boolean hasCheese, boolean hasExtraCheese,boolean hasGreenPeppers,boolean hasItalianSausage) {
        int basePrice = PIZZA_PRICE;
        if (hasCheese) {
            basePrice += CHEESE_PRICE;
        }
        if (hasExtraCheese) {
            basePrice += EXTRA_CHEESE_PRICE;
        }
        if (hasGreenPeppers) {
            basePrice += GREEN_PEPPER_PRICE;
        }
        if (hasItalianSausage) {
            basePrice += ITALIAN_SAUSAGE_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Pizza
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.small_order);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select an option");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.small_order);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}