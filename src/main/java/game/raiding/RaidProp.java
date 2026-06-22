package game.raiding;
import init.paths.PATHS;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class RaidProp {
    private static final Path PropPath = PATHS.local().SETTINGS.get().resolve("EmiTheWitch.txt");
    private static final Properties PropFile = new Properties();

    public static double frequencyMultiplier = 5.0;
    public static double wealthScaling = 1.0;
    public static boolean fearlessRaiders = false;
    public static boolean leaveMyRaidersAlone = false;
    public static boolean ruthlessRaiders = false;
    public static boolean showAllRaidsInfo = false;
    public static double bountyMultiplier = 1.0;

    private RaidProp() {
        {
            reloadValues();
        }
    }

    private static void loadPropertiesFile() {
        try {
            PropFile.load(Files.newInputStream(Paths.get(String.valueOf(PropPath))));
        } catch (Exception e) {
            try {
                PropFile.store(new FileWriter(String.valueOf(PropPath)), "store to properties file");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void reloadValues() {
        loadPropertiesFile();
        frequencyMultiplier = ((Number )varCheck("Raid_Settings_Frequency","Raid Settings", 5.0, 0, 1000, false )).doubleValue();
        wealthScaling = ((Number )varCheck("Raid_Settings_Wealth_Scaling","Raid Settings", 1.0, 0, 100, false )).doubleValue();
        fearlessRaiders = ((Number) varCheck("Raid_Settings_Fearless_Raiders","Raid Settings", 0, 0, 1, true )).intValue() == 1;
        leaveMyRaidersAlone = ((Number) varCheck("Raid_Settings_Leave_My_Raiders_Alone","Raid Settings", 0, 0, 1, true )).intValue() == 1;
        ruthlessRaiders = ((Number) varCheck("Raid_Settings_Ruthless_Raiders","Raid Settings", 0, 0, 1, true )).intValue() == 1;
        showAllRaidsInfo = ((Number) varCheck("Raid_Settings_Show_All_Raider_Info","Raid Settings", 0, 0, 1, true )).intValue() == 1;
        bountyMultiplier = ((Number )varCheck("Raid_Settings_Bounty_Multiplier","Raid Settings", 1.0, 0, 100, false )).doubleValue();
    }

    private static Object varCheck(String varName, String modName, double defaultVal, double min, double max, boolean IntDouble) {
        double value = 0;
        boolean valid = true;
        try {
            if (IntDouble){
                value = Integer.parseInt(PropFile.getProperty(varName));
            }
            else{
                value = Double.parseDouble(PropFile.getProperty(varName));
            }

            if (value < min || value > max) {
                valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        if (!valid) {
            value = defaultVal;
        }
        if (IntDouble) {
            PropFile.setProperty(varName, String.valueOf((int) value));
        } else {PropFile.setProperty(varName, String.valueOf(value));}

        try {
            PropFile.store(new FileWriter(String.valueOf(PropPath)), modName);
        } catch (Exception f) {
            return "Can't store in the property file";
        }
        return value;
    }
}

