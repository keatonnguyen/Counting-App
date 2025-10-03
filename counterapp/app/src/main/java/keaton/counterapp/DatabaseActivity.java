package keaton.counterapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import keaton.counterapp.adapters.HistoryAdapter;
import keaton.counterapp.helpers.PrefsStore;
import keaton.counterapp.models.AppConfig;
import java.util.ArrayList;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity
{
    private TextView countA;
    private TextView countB;
    private TextView countC;
    private TextView totalCount;
    private ListView list;
    private PrefsStore prefs;
    private AppConfig settings;
    private boolean showNames = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databasepage);
        prefs = new PrefsStore(this);
        settings = prefs.loadAppConfig();
        countA = findViewById(R.id.countA);
        countB = findViewById(R.id.countB);
        countC = findViewById(R.id.countC);
        totalCount = findViewById(R.id.totalCount);
        list = findViewById(R.id.list);
        refresh();
    }

    private void refresh()
    {
        int c1 = prefs.getEventCount(1);
        int c2 = prefs.getEventCount(2);
        int c3 = prefs.getEventCount(3);
        int total = c1 + c2 + c3;

        countA.setText(getString(R.string.count_item, "Counter 1", c1));
        countB.setText(getString(R.string.count_item, "Counter 2", c2));
        countC.setText(getString(R.string.count_item, "Counter 3", c3));
        totalCount.setText(getString(R.string.total_label, total));

        List<Integer> history = prefs.getHistory();
        List<String> display = new ArrayList<>();
        for (int v : history)
        {
            if (showNames)
            {
                display.add(v == 1 ? settings.getNameOneName()
                        : v == 2 ? settings.getNameTwoName()
                        : settings.getNameThreeName());
            }

            else
            {
                display.add(getString(R.string.button_num, v));
            }
        }
        list.setAdapter(new HistoryAdapter(this, display));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_switch_labels)
        {
            showNames = !showNames;
            refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
