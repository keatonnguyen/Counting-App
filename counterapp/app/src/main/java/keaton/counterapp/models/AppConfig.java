package keaton.counterapp.models;

public class AppConfig {
    private String button1Name;
    private String button2Name;
    private String button3Name;
    private int maxEvents;

    public AppConfig() {}

    public AppConfig(String b1, String b2, String b3, int max) {
        this.button1Name = b1;
        this.button2Name = b2;
        this.button3Name = b3;
        this.maxEvents  = max;
    }

    public String getNameOneName() { return button1Name; }
    public String getNameTwoName() { return button2Name; }
    public String getNameThreeName() { return button3Name; }
    public int getCapEvents() { return maxEvents; }

    public void setNameOneName(String s) { this.button1Name = s; }
    public void setNameTwoName(String s) { this.button2Name = s; }
    public void setNameThreeName(String s) { this.button3Name = s; }
    public void setCapEvents(int m) { this.maxEvents = m; }

    /** True if *any* name is missing or empty (used to force redirect to AppConfig). */
    public boolean isIncomplete() {
        return isEmpty(button1Name) || isEmpty(button2Name) || isEmpty(button3Name) || maxEvents <= 0;
    }
    private boolean isEmpty(String s) { return s == null || s.trim().isEmpty(); }
}
