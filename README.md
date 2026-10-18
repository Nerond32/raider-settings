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

Build against Songs of Syx **71.76** using Java 21 or newer. The game JAR and
`info/SongsOfSyx-sources.jar` must come from that installation. Override the
installation path with `-Dgame.install.directory="/path/to/Songs of Syx"` if needed.

On the first build for a game version, run `mvn validate` separately to register
the game JAR in Maven's local repository before dependency resolution.

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

To build without installing, run `mvn package`. The complete mod is written to
`target/out/Raider Settings`. For Wine, copy this folder into the Windows game's
`%APPDATA%\songsofsyx\mods` directory inside the Wine prefix.

## 71.76 loading crash

The game moved `Profiler` from `game.debug` to `game.profile`. Older builds of
this mod therefore provide the wrong signature for `RaidingCurrent.update`,
causing `NoSuchMethodError` when 71.76 calls it. The import is updated here;
rebuild and replace the installed mod JAR to apply the fix.

The game's generic “Failed loading assets” / code-mod dialog hides this linkage
error. In 71.76, that error path prints the stack trace to the console but does
not write the usual error dump. Logs normally live in
`%APPDATA%\songsofsyx\logs` (typically
`$WINEPREFIX/drive_c/users/<user>/AppData/Roaming/songsofsyx/logs` under Wine).
If no trace is there, start the game from a terminal using your usual Wine prefix
and executable, capturing both streams:

```bash
wine "/path/to/game.exe" > "$HOME/songsofsyx-crash.log" 2>&1
```
