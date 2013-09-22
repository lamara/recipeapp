package com.mhacks.recipe;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhacks.recipe.parser.src.Recipe;

public class MainActivity extends Activity {

    ArrayAdapter<String> adapter;
    ListView listView;
    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ActionBar actionBar = getActionBar();
        actionBar.show();

        mainActivity = this;

        final EditText search = (EditText) findViewById(R.id.search);


        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Object[] params = new Object[2];
                    params[0] = (Object) mainActivity;
                    params[1] = (Object) search.getText();
                    new AsyncSearch().execute(params);
                    return true;
                } else
                    return false;
            }
        });

        this.listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String title = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(mainActivity, InstructionActivity.class);
                intent.putExtra("title", title);
                mainActivity.startActivity(intent);
            }
        });

        //new AsyncRecipeRetriever().execute(params);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Call back used by the async search
     * @param results
     */
    public void searchCallBack(String[] results) {
        if (results == null || results.length == 0 ) {
            Toast.makeText(getApplicationContext(),
                    "Recipe Retrieval Failed!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        System.out.println("callback recieved");
        adapter = new ArrayAdapter<String>(this, R.layout.list_text, results);
        listView.setAdapter(adapter);
        System.out.println("adapter set!");
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
        System.out.println("callback recieved");
        adapter = new RecipeAdapter(this, recipe.getIngredientStrings().toArray(new String[0]));
        listView.setAdapter(adapter);
        System.out.println("adapter set!");
    }
}
