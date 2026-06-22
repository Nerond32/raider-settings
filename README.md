# Raider Settings

Raider Settings is a Songs of Syx code mod that exposes a small set of configurable raider behavior tweaks.

## Settings file

The mod creates and reads this shared settings file from the game's local settings directory:

```text
EmiTheWitch.txt
```

The file name is kept for compatibility with the external in-game properties editor.

Supported properties:

- `Raid_Settings_Frequency`: Multiplier for how quickly raiders progress toward a raid.
- `Raid_Settings_Wealth_Scaling`: Multiplier for current ransom value when checking whether raiders are interested.
- `Raid_Settings_Fearless_Raiders`: Set to `1` to prevent raiders from backing off because of army strength.
- `Raid_Settings_Leave_My_Raiders_Alone`: Set to `1` to prevent raiders from passing through another faction.
- `Raid_Settings_Ruthless_Raiders`: Set to `1` to let raiders stay interested regardless of wealth.
- `Raid_Settings_Show_All_Raider_Info`: Set to `1` to show raider information before the normal reveal conditions.
- `Raid_Settings_Bounty_Multiplier`: Multiplier for bounty rewards when raiders are defeated.

Invalid or missing values are replaced with defaults when the mod loads.

## Build and install

Use Maven to build the mod and copy it into the local Songs of Syx mod directory:

```bash
mvn clean install
```

The installed mod is written to:

```text
${game.mod.directory}/Raider Settings
```

On Linux, the default path is:

```text
~/.local/share/songsofsyx/mods/Raider Settings
```
