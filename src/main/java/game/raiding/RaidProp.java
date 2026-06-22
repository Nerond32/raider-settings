package game.raiding;
import init.paths.PATHS;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class RaidProp {
    private static final String MOD_NAME = "Raider Settings";
    private static final String SETTINGS_FILE_NAME = "EmiTheWitch.txt";
    private static final Properties SETTINGS_PROPERTIES = new Properties();
    private static boolean settingsChanged = false;

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
        SETTINGS_PROPERTIES.clear();
        Path settingsFile = settingsFile();
        try {
            Files.createDirectories(settingsFile.getParent());
            if (Files.exists(settingsFile)) {
                try (Reader reader = Files.newBufferedReader(settingsFile)) {
                    SETTINGS_PROPERTIES.load(reader);
                }
            } else {
                settingsChanged = true;
            }
        } catch (Exception e) {
            SETTINGS_PROPERTIES.clear();
            settingsChanged = true;
        }
    }

    public static void reloadValues() {
        settingsChanged = false;
        loadPropertiesFile();
        frequencyMultiplier = validateSetting("Raid_Settings_Frequency", 5.0, 0, 1000, false);
        wealthScaling = validateSetting("Raid_Settings_Wealth_Scaling", 1.0, 0, 100, false);
        fearlessRaiders = (int) validateSetting("Raid_Settings_Fearless_Raiders", 0, 0, 1, true) == 1;
        leaveMyRaidersAlone = (int) validateSetting("Raid_Settings_Leave_My_Raiders_Alone", 0, 0, 1, true) == 1;
        ruthlessRaiders = (int) validateSetting("Raid_Settings_Ruthless_Raiders", 0, 0, 1, true) == 1;
        showAllRaidsInfo = (int) validateSetting("Raid_Settings_Show_All_Raider_Info", 0, 0, 1, true) == 1;
        bountyMultiplier = validateSetting("Raid_Settings_Bounty_Multiplier", 1.0, 0, 100, false);
        storePropertiesFile();
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
        String originalValue = SETTINGS_PROPERTIES.getProperty(settingName);
        try {
            if (parseAsInteger) {
                value = Integer.parseInt(originalValue);
            }
            else {
                value = Double.parseDouble(originalValue);
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
        String normalizedValue;
        if (parseAsInteger) {
            normalizedValue = String.valueOf((int) value);
        } else {
            normalizedValue = String.valueOf(value);
        }

        if (!normalizedValue.equals(originalValue)) {
            SETTINGS_PROPERTIES.setProperty(settingName, normalizedValue);
            settingsChanged = true;
        }
        return value;
    }

    private static void storePropertiesFile() {
        if (!settingsChanged) {
            return;
        }

        Path settingsFile = settingsFile();
        try {
            try (Writer writer = Files.newBufferedWriter(settingsFile)) {
                SETTINGS_PROPERTIES.store(writer, MOD_NAME);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't store Raider Settings properties file", e);
        }
    }

    private static Path settingsFile() {
        return PATHS.local().SETTINGS.get().resolve(SETTINGS_FILE_NAME);
    }
}
