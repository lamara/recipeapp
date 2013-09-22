package com.mhacks.recipe;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mhacks.recipe.parser.src.Recipe;

public class MainActivity extends Activity {

    RecipeAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.show();

        this.listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        Object[] params = new Object[2];
        params[0] = (Object) this;
        params[1] = (Object) "Flying_saucer_salad";
        new AsyncRecipeRetriever().execute(params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Called back by the recipe async retriever.
     * @param recipe
     */
    public void recipeCallBack(Recipe recipe) {
        if (recipe == null) {
            Toast.makeText(getApplicationContext(),
                    "Recipe Retrieval Failed!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        adapter = new RecipeAdapter(this, recipe.getIngredientStrings().toArray(new String[0]));
        listView.setAdapter(adapter);
    }
}
