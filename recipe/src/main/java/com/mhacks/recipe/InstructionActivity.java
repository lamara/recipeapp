package com.mhacks.recipe;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhacks.recipe.parser.src.Recipe;

import java.util.List;

/**
 * Created by Alex on 9/22/13.
 */
public class InstructionActivity extends Activity {

    private InstructionActivity instructionActivity;

    private ListView listView;
    private Recipe recipe;
    private TextView header;
    private Button launchRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        ActionBar actionBar = getActionBar();
        actionBar.show();

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        this.instructionActivity = this;

        this.launchRecipe = (Button) findViewById(R.id.launchRecipe);
        launchRecipe.setVisibility(View.INVISIBLE);
        launchRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> instructions = recipe.getInstructions();
                RecipeAdapter adapter = new RecipeAdapter(instructionActivity, instructions.toArray(new String[0]));
                launchRecipe.setVisibility(View.GONE);
                header.setText("Instructions");
                listView.setAdapter(adapter);
            }
        });

        this.listView = (ListView) findViewById(R.id.instructionList);
        this.header = (TextView) findViewById(R.id.mainHeader);
        header.setText("Ingredients");

        Object[] params = new Object[2];
        params[0] = (Object) this;
        params[1] = (Object) title;

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
        this.recipe = recipe;
        System.out.println("callback recieved");
        RecipeAdapter adapter = new RecipeAdapter(this, recipe.getIngredientStrings().toArray(new String[0]));
        listView.setAdapter(adapter);
        System.out.println("adapter set!");
        launchRecipe.setVisibility(View.VISIBLE);
    }
}
