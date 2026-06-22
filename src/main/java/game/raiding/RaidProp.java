package game.raiding;
import init.paths.PATHS;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class RaidProp {
    private static final String MOD_NAME = "Raider Settings";
    private static final Path SETTINGS_FILE = PATHS.local().SETTINGS.get().resolve("RaiderSettings.txt");
    private static final Properties SETTINGS_PROPERTIES = new Properties();

    public static double frequencyMultiplier = 5.0;
    public static double wealthScaling = 1.0;
    public static boolean fearlessRaiders = false;
    public static boolean leaveMyRaidersAlone = false;
    public static boolean ruthlessRaiders = false;
    public static boolean showAllRaidsInfo = false;
    public static double bountyMultiplier = 1.0;

    private RaidProp() {
    }

    private static void loadPropertiesFile() {
        try {
            SETTINGS_PROPERTIES.load(Files.newInputStream(SETTINGS_FILE));
        } catch (Exception e) {
            try {
                SETTINGS_PROPERTIES.store(Files.newBufferedWriter(SETTINGS_FILE), MOD_NAME);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void reloadValues() {
        loadPropertiesFile();
        frequencyMultiplier = validateSetting("Raid_Settings_Frequency", 5.0, 0, 1000, false);
        wealthScaling = validateSetting("Raid_Settings_Wealth_Scaling", 1.0, 0, 100, false);
        fearlessRaiders = (int) validateSetting("Raid_Settings_Fearless_Raiders", 0, 0, 1, true) == 1;
        leaveMyRaidersAlone = (int) validateSetting("Raid_Settings_Leave_My_Raiders_Alone", 0, 0, 1, true) == 1;
        ruthlessRaiders = (int) validateSetting("Raid_Settings_Ruthless_Raiders", 0, 0, 1, true) == 1;
        showAllRaidsInfo = (int) validateSetting("Raid_Settings_Show_All_Raider_Info", 0, 0, 1, true) == 1;
        bountyMultiplier = validateSetting("Raid_Settings_Bounty_Multiplier", 1.0, 0, 100, false);
    }

    private static double validateSetting(
            String settingName,
            double defaultValue,
            double minimumValue,
            double maximumValue,
            boolean parseAsInteger
    ) {
        double value = 0;
        boolean valid = true;
        try {
            if (parseAsInteger) {
                value = Integer.parseInt(SETTINGS_PROPERTIES.getProperty(settingName));
            }
            else {
                value = Double.parseDouble(SETTINGS_PROPERTIES.getProperty(settingName));
            }

            if (value < minimumValue || value > maximumValue) {
                valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        if (!valid) {
            value = defaultValue;
        }
        if (parseAsInteger) {
            SETTINGS_PROPERTIES.setProperty(settingName, String.valueOf((int) value));
        } else {
            SETTINGS_PROPERTIES.setProperty(settingName, String.valueOf(value));
        }

        try {
            SETTINGS_PROPERTIES.store(Files.newBufferedWriter(SETTINGS_FILE), MOD_NAME);
        } catch (Exception f) {
            throw new RuntimeException("Can't store Raider Settings properties file", f);
        }
        return value;
    }
}
