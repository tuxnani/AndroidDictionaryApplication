package inducesmile.com.androiddictionaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private EditText filterText;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterText = (EditText)findViewById(R.id.editText);
        ListView itemList = (ListView)findViewById(R.id.listView);

        final DbBackend dbBackend = new DbBackend(MainActivity.this);
        String[] terms = dbBackend.dictionaryWords();

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, terms);
        itemList.setAdapter(listAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
// make Toast when click
                String word = listAdapter.getItem(position);
// Toast.makeText(MainActivity.this, terms2, Toast.LENGTH_SHORT).show();
                final String[] words = dbBackend.dictionaryWords();
                for(int i=0; i < words.length; i++)
                    if(words[i].contains(word))
                        position = i;
                Toast.makeText(MainActivity.this, word + " " +position , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                intent.putExtra("DICTIONARY_ID", position);
                startActivity(intent);
            }
        });

        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.listAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
