package keaton.counterapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import keaton.counterapp.helpers.PrefsStore;
import keaton.counterapp.models.AppConfig;

public class SettingsActivity extends AppCompatActivity
{
    private EditText name1;
    private EditText name2;
    private EditText name3;
    private EditText max;
    private Button save;
    private PrefsStore prefs;
    private boolean editing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingspage);
        prefs = new PrefsStore(this);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        max = findViewById(R.id.max);
        save = findViewById(R.id.save);

        //if no set name, you edit
        AppConfig s = prefs.loadAppConfig();
        if (s != null)
        {
            name1.setText(s.getNameOneName());
            name2.setText(s.getNameTwoName());
            name3.setText(s.getNameThreeName());
            max.setText(String.valueOf(s.getCapEvents()));
            setEditing(false);
        }
        else
        {
            setEditing(true);
        }

        //button that saves the changes
        save.setOnClickListener(v -> saveAppConfig());
    }

    private void setEditing(boolean enable)
    {
        editing = enable;
        name1.setEnabled(enable);
        name2.setEnabled(enable);
        name3.setEnabled(enable);
        max.setEnabled(enable);
        save.setVisibility(enable ? Button.VISIBLE : Button.GONE);
        invalidateOptionsMenu();
    }
    private void saveAppConfig()
    {

        String b1 = name1.getText().toString().trim();
        String b2 = name2.getText().toString().trim();
        String b3 = name3.getText().toString().trim();
        String maxStr = max.getText().toString().trim();

        if (b1.isEmpty() || b2.isEmpty() || b3.isEmpty())
        {
            Toast.makeText(this, "missing name(s)", Toast.LENGTH_SHORT).show();
            return;
        }

        int max;

        try
        {
            max = Integer.parseInt(maxStr);
        }

        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Enter a valid number for maximum events", Toast.LENGTH_SHORT).show();
            return;
        }

        if (max < 5 || max > 100)
        {
            Toast.makeText(this, "Maximum must be between 5 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        prefs.saveAppConfig(new AppConfig(b1, b2, b3, max));
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        setEditing(false);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // Only show Edit action when we’re in display mode
        menu.findItem(R.id.action_toggle_edit).setVisible(!editing);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_toggle_edit)
        {
            setEditing(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
