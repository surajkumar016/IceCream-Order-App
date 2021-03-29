package android.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this," You cannot have more than 100 coffees ", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity+1;
        displayQuantity(quantity);

    }

    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this," You cannot have less than 1 coffees ", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity-1;
        displayQuantity(quantity);

    }

    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculateprice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }

    private int calculateprice(boolean addWhippedCream, boolean addchocolate) {
        int baseprice = 5;
        if(addWhippedCream){
            baseprice += 1;
        }
        if(addchocolate){
            baseprice += 2;
        }
        return quantity * baseprice;

    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addchocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addchocolate;
        priceMessage += "\nQuantity: "+ quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }


    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+number);

    }



}