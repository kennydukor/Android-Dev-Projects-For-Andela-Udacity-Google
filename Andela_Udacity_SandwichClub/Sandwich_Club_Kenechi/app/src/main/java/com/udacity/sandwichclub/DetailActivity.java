package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;



    private ImageView sandwichIv;
    private Sandwich sandwich;
    private TextView alsoKnownAsValue;
    private TextView descriptionValue;
    private TextView originValue;
    private TextView ingredientValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAsValue = findViewById(R.id.also_known_tv_value);
        descriptionValue = findViewById(R.id.description_tv_value);
        originValue = findViewById(R.id.origin_tv_value);
        ingredientValue = findViewById(R.id.ingredients_tv_value);
        sandwichIv = findViewById(R.id.image_iv);
        //ImageView ingredientsIv = findViewById(R.id.image_iv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(sandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            originValue.setText(R.string.data_error_message);
        } else {
            originValue.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsValue.setText(R.string.data_error_message);
        } else {
            alsoKnownAsValue.setText(listModel(sandwich.getAlsoKnownAs()));
        }


        descriptionValue.setText(sandwich.getDescription());
        ingredientValue.setText(listModel(sandwich.getIngredients()));

    }

    public StringBuilder listModel(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }
}
