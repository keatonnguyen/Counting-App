package keaton.counterapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import keaton.counterapp.helpers.PrefsStore;
import keaton.counterapp.models.AppConfig;

public class MainActivity extends AppCompatActivity
{
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonSettings;
    private Button buttonDatabase;
    private TextView total;
    private PrefsStore prefs;
    private AppConfig settings;
    private int sessionTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        prefs = new PrefsStore(this);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonSettings = findViewById(R.id.buttonSettings);
        buttonDatabase = findViewById(R.id.buttonDatabase);
        total = findViewById(R.id.total);

        // increment buttons
        buttonA.setOnClickListener(v -> increment(1));
        buttonB.setOnClickListener(v -> increment(2));
        buttonC.setOnClickListener(v -> increment(3));

        // navigation buttons
        buttonSettings.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
        buttonDatabase.setOnClickListener(v -> startActivity(new Intent(this, DatabaseActivity.class)));

        updateTotal();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        settings = prefs.loadAppConfig();
        if (settings == null)
        {
            startActivity(new Intent(this, SettingsActivity.class)); // launch settings if none saved
            return;
        }
        buttonA.setText(settings.getNameOneName());
        buttonB.setText(settings.getNameTwoName());
        buttonC.setText(settings.getNameThreeName());
        updateTotal();
    }

    private void increment(int which)
    {
        int nextTotal = sessionTotal + 1;
        int maxCap = (settings != null)
                ? settings.getCapEvents()
                : (prefs.loadAppConfig() != null ? prefs.loadAppConfig().getCapEvents() : Integer.MAX_VALUE);

        if (nextTotal > maxCap)
        {
            Toast.makeText(this, "Max reached (" + maxCap + ")", Toast.LENGTH_SHORT).show();
            return;
        }
        prefs.incrementEvent(which);
        sessionTotal = nextTotal;
        updateTotal();
    }

    private void updateTotal()
    {
        total.setText(getString(R.string.total_prefix, sessionTotal));
    }
}