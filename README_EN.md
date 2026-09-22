# Batch File Renaming Tool (RenameTool)

[日本語](README.md) | **English**

## <img src="images/sparkles.svg" width="20" height="20" valign="middle"> Introduction

On a personal note, I'll be starting at a new workplace in October.
While preparing, I remembered how often I had needed to rename files at my previous workplace. I decided to make a Java tool so I would be ready to handle the same task again.

The tool starts with a simple operation: renaming files directly inside one folder. I may add more features as needed.

## <img src="images/list-checks.svg" width="20" height="20" valign="middle"> Features

- Replace part of the names of files directly inside a specified folder in one run.
- Automatically create `Restore.bat` to undo the changes.
- Leave folders unchanged.

## <img src="images/folder-tree.svg" width="20" height="20" valign="middle"> Files

| File | Purpose |
|---|---|
| `RenameTool.jar` | Executable JAR that runs with a JRE |
| `RenameTool.java` | Java source code |
| `rename.txt` | Rename rule: text to find on the first line and replacement text on the second line |
| `run.bat` | Batch file that starts the JAR |
| `build.bat` | Batch file that rebuilds the JAR from source (requires a JDK) |
| `Restore.bat` | Batch file generated after renaming to undo the changes |

## <img src="images/mouse-pointer-click.svg" width="20" height="20" valign="middle"> How to use

1. Install a JRE version 8 or newer and make sure `java` is available.
2. Open `rename.txt`. Put the text to find on the first line and its replacement on the second line, then save the file.
3. Place `RenameTool.jar`, `rename.txt`, and `run.bat` in the same folder as the files you want to rename.
4. Double-click `run.bat`.
5. Review the results shown in the console, including each old and new filename.
6. To undo the changes, double-click the `Restore.bat` created in that folder.

To rebuild the JAR from source, run `build.bat` with a JDK version 9 or newer. A JDK is not needed for normal use.

## <img src="images/info.svg" width="20" height="20" valign="middle"> How it works

- Only files in the same folder as the JAR are processed. Subfolders, folders themselves, and the tool's own files are left unchanged.
- If a filename contains the text from the first line of `rename.txt`, that text is replaced. The whole filename does not need to match.
- If the new filename already exists, the file is skipped to avoid overwriting it.
- The program uses UTF-8 for its output, and `run.bat` switches the console to UTF-8 to prevent garbled text.

## Java techniques used

- **File operations**: `File`, `Path`, and `Files` locate the JAR and read `rename.txt`. `listFiles(File::isFile)` selects files directly in the same folder, and `File.renameTo()` changes their names.
- **Finding and replacing text**: `String.contains()` checks whether a filename contains the search text, and `String.replace()` replaces matching text. These methods treat the search text literally, without regular expressions.
- **Batch file for undoing renames**: The program stores reverse `ren` commands for successfully renamed files in a `List<String>`. It writes them to `Restore.bat` with `Files.newBufferedWriter()`, so running the batch file restores the original names.
- **Ternary operator**: `location.isDirectory() ? location : location.getParentFile()` selects the directory itself when running from class files, or the parent directory when running from a JAR.
- **Character encoding and cleanup**: `StandardCharsets.UTF_8` is used when reading the configuration and writing the restore batch file. `try-with-resources` closes the writer after use.
- **Exception handling**: `try-catch` handles read and write errors and displays the error message in the console.

## <img src="images/shield-check.svg" width="20" height="20" valign="middle"> Verified behavior

- Confirmed that multiple files in a test folder are renamed correctly.
- Confirmed that unrelated files, folders, and the configuration file remain unchanged.
- Confirmed that `Restore.bat` restores the original filenames.
- Confirmed that files outside the folder where the tool runs, including those in its parent folder, remain unchanged.

## <img src="images/rocket.svg" width="20" height="20" valign="middle"> Planned features (not yet implemented)

- An option to process files in subfolders.
- Adding specified text to the beginning of filenames.
- Changing file extensions only.

## Credits and licenses

The heading icons are SVGs from [Lucide](https://lucide.dev/icons/). They are stored in the `images` folder, and their stroke color was changed to red (`#dc2626`) for better visibility. See [images/SOURCE.txt](images/SOURCE.txt) for the source and modifications.

Lucide icons are licensed under the ISC License. Some icons derived from Feather are also covered by the MIT License. See [images/LICENSE](images/LICENSE) for the full copyright notices and license terms.

To the creators, maintainers, and contributors of Lucide: I use your icons often, and I am always grateful for your work.
