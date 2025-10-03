package keaton.counterapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import keaton.counterapp.models.AppConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefsStore {

    private static final String PREFS = "event_prefs";

    private static final String K_BTN1 = "button1";
    private static final String K_BTN2 = "button2";
    private static final String K_BTN3 = "button3";
    private static final String K_MAX  = "maxEvents";

    private static final String K_COUNT1 = "count1";
    private static final String K_COUNT2 = "count2";
    private static final String K_COUNT3 = "count3";

    // history stored as CSV like "1,2,3,1,2"
    private static final String K_HISTORY = "history_csv";

    private final SharedPreferences sp;

    public PrefsStore(Context ctx) {
        sp = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    /* ---------- AppConfig ---------- */

    public void saveAppConfig(AppConfig s) {
        sp.edit()
                .putString(K_BTN1, s.getNameOneName())
                .putString(K_BTN2, s.getNameTwoName())
                .putString(K_BTN3, s.getNameThreeName())
                .putInt(K_MAX, s.getCapEvents())
                .apply();
    }

    public AppConfig loadAppConfig() {
        String b1 = sp.getString(K_BTN1, null);
        String b2 = sp.getString(K_BTN2, null);
        String b3 = sp.getString(K_BTN3, null);
        int max = sp.getInt(K_MAX, 0);
        if (b1 == null || b2 == null || b3 == null || max <= 0) return null;
        return new AppConfig(b1, b2, b3, max);
    }

    /* ---------- Counts + History ---------- */

    public void incrementEvent(int eventNum) {
        // bump per-event counters
        String key = eventNum == 1 ? K_COUNT1 : (eventNum == 2 ? K_COUNT2 : K_COUNT3);
        int cur = sp.getInt(key, 0);
        sp.edit().putInt(key, cur + 1).apply();

        // append to CSV history, trimming to max size
        int max = Math.max(5, sp.getInt(K_MAX, 50)); // fallback if max not set yet
        String csv = sp.getString(K_HISTORY, "");
        if (csv == null || csv.isEmpty()) csv = String.valueOf(eventNum);
        else csv = csv + "," + eventNum;

        List<String> items = new ArrayList<>(Arrays.asList(csv.split(",")));
        while (items.size() > max) items.remove(0);           // drop oldest first
        csv = String.join(",", items);

        sp.edit().putString(K_HISTORY, csv).apply();
    }

    public int getEventCount(int eventNum) {
        return sp.getInt(eventNum == 1 ? K_COUNT1 : eventNum == 2 ? K_COUNT2 : K_COUNT3, 0);
    }

    public List<Integer> getHistory() {
        String csv = sp.getString(K_HISTORY, "");
        List<Integer> out = new ArrayList<>();
        if (csv == null || csv.trim().isEmpty()) return out;
        for (String token : csv.split(",")) {
            try { out.add(Integer.parseInt(token.trim())); } catch (Exception ignored) {}
        }
        return out;
    }

    /* ---------- Optional helpers ---------- */

    public void resetAll() {
        sp.edit()
                .remove(K_BTN1).remove(K_BTN2).remove(K_BTN3).remove(K_MAX)
                .remove(K_COUNT1).remove(K_COUNT2).remove(K_COUNT3)
                .remove(K_HISTORY)
                .apply();
    }
}
