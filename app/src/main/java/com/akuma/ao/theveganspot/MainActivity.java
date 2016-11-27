package com.akuma.ao.theveganspot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static String EXTRA_MESSAGE="default_message";
    SharedPreferences prefs = null;
    protected ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.akuma.ao.theveganspot", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean firstrun = prefs.getBoolean("firstrun", true);
        int updatedb = prefs.getInt("dbv", 1);

        if( firstrun) {
            populatedDB(DBHandler.DATABASE_VERSION);
            prefs.edit().putBoolean("firstrun", false).commit();
            prefs.edit().putInt("dbv", DBHandler.DATABASE_VERSION);
        } else if(updatedb != DBHandler.DATABASE_VERSION) {
            populatedDB(updatedb);
            prefs.edit().putInt("dbv", DBHandler.DATABASE_VERSION);
        }
    }

    protected void populatedDB(int olddb) {
        DBHandler db = new DBHandler(this);
        db.onUpgrade(db.getWritableDatabase(), olddb, DBHandler.DATABASE_VERSION);

        Log.d("Insert: ", "Inserting..");

        db.addType(new Type(1, "Snacks"));
        db.addType(new Type(2, "Cheeses"));
        db.addType(new Type(3, "Chips and Dips"));
        db.addType(new Type(4, "Spreads"));

        db.addBrand(new Brand(1, "MyLife"));
        db.addBrand(new Brand(2, "Coles"));
        db.addBrand(new Brand(3, "Deli Original"));
        db.addBrand(new Brand(4, "Sprinters"));

        // new Food ( id, Brand, Type, Name, Ingredients )
        db.addFood(new Food(0, 1, 2, "BioCheese", "Water, Coconut Oil(non-Hydrogenated)(23%), Modified Starch (E1404, E1450), Starch, Sea Salt, Vegan Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(1, 2, 1, "Cracked Pepper - Water Crackers", "Wheat Flour, Palm Oil [Antioxidant(307)], Salt, Cracked Black Pepper (1%), Poppy Seeds, Raising Agent(500)."));
        db.addFood(new Food(2, 3, 3, "Hommus Dip", "Chickpeas (47%), Water, Vegetable Oil, Tahini (Contains Sesame Seeds), Acidity Regulators (260, 330, 575), Salt, Garlic, Preservative(202)."));
        db.addFood(new Food(3, 4, 3, "Salt and Vinegar chips", "Potatoes, Vegetable Oil, Acidicty Regulators(262, 330, 260), Mineral Salts(508, 340), Salt(1.5%), Corn Starch, Sugar, Maltodextrin, Natural Flavours."));
        db.addFood(new Food(4, 1, 2, "BioCheese - Cheddar", "Water, Coconut Oil(non-Hydrogenated)(23%), Modified Starch (E1404, E1450), Starch, Sea Salt, Cheddar Vegan Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(5, 1, 2, "BioCheese Slices", "Water, Coconut Oil(non-Hydrogenated)(23%), Modified Starch (E1404, E1450), Starch, Sea Salt, Vegan Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(6, 1, 2, "BioCheese Slices - Cheddar", "Water, Coconut Oil(non-Hydrogenated)(23%), Modified Starch (E1404, E1450), Starch, Sea Salt, Cheddar Vegan Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(7, 1, 2, "BioCheese Shred - Cheddar", "Water, Coconut Oil(non-Hydrogenated)(23%), Modified Starch (E1404, E1450), Starch, Sea Salt, Anti-caking Agent (Tapioca Starch), Cheddar Vegan Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(8, 1, 2, "BioCheese Shred - Pizza", "Water, Coconut Oil(non-Hydrogenated)(21%), Modified Starch (E1404, E1450), Starch, Sea Salt, Anti-caking Agent (Tapioca Starch), Vegan Mozzarella Flavours, Olive Extract, Colour: B-Carotene."));
        db.addFood(new Food(9, 1, 4, "BioButtery", "(All From Vegetable Sources) Canola Oil, Water, Coconut Oil, Salt Emulsifiers (471 [Non-palm], Sunflower Lecithn), Vegan Flavour, Vitamins D, E."));

        db.close();
    }

    public void search_food(View view) {
        Intent intent = new Intent(this, searchActivity.class);
        startActivity(intent);
    }
}

