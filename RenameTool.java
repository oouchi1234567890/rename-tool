import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RenameTool {

    private static final String CONFIG_FILE_NAME = "rename.txt";
    private static final String RESTORE_FILE_NAME = "Restore.bat";

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        try {
            File baseDir = getExecutableDirectory();
            System.out.println("対象ディレクトリ: " + baseDir.getAbsolutePath());

            File configFile = new File(baseDir, CONFIG_FILE_NAME);
            String[] rule = readRule(configFile);
            String from = rule[0];
            String to = rule[1];
            System.out.println("置き換えルール: \"" + from + "\" -> \"" + to + "\"");

            File[] files = baseDir.listFiles(File::isFile);
            if (files == null) {
                System.out.println("ファイル一覧を取得できませんでした。");
                return;
            }

            List<String> restoreCommands = new ArrayList<>();
            int renamedCount = 0;
            for (File file : files) {
                String name = file.getName();
                if (name.equals(CONFIG_FILE_NAME) || name.equals(RESTORE_FILE_NAME)) {
                    continue;
                }
                if (name.contains(from)) {
                    String newName = name.replace(from, to);
                    File dest = new File(baseDir, newName);
                    if (dest.exists()) {
                        System.out.println("スキップ（同名ファイルが既に存在）: " + newName);
                        continue;
                    }
                    if (file.renameTo(dest)) {
                        System.out.println("リネーム: " + name + " -> " + newName);
                        renamedCount++;
                        restoreCommands.add("ren \"" + newName + "\" \"" + name + "\"");
                    } else {
                        System.out.println("リネーム失敗: " + name);
                    }
                }
            }
            System.out.println("完了。リネームしたファイル数: " + renamedCount);

            if (!restoreCommands.isEmpty()) {
                writeRestoreBatch(baseDir, restoreCommands);
                System.out.println(RESTORE_FILE_NAME + " を作成しました（元に戻す場合はこれを実行してください）。");
            }

        } catch (Exception e) {
            System.out.println("エラーが発生しました: " + e.getMessage());
        }
    }

    /** リネーム内容を元に戻すRestore.batを作成する */
    private static void writeRestoreBatch(File baseDir, List<String> restoreCommands) throws IOException {
        File restoreFile = new File(baseDir, RESTORE_FILE_NAME);
        try (BufferedWriter writer = Files.newBufferedWriter(restoreFile.toPath(), StandardCharsets.UTF_8)) {
            writer.write("@echo off");
            writer.newLine();
            writer.write("chcp 65001 >nul");
            writer.newLine();
            writer.write("cd /d %~dp0");
            writer.newLine();
            for (String command : restoreCommands) {
                writer.write(command);
                writer.newLine();
            }
            writer.write("pause");
            writer.newLine();
        }
    }

    /** rename.txt から1行目（検索文字）・2行目（置換文字）を読み込む */
    private static String[] readRule(File configFile) throws IOException {
        if (!configFile.exists()) {
            throw new IOException(CONFIG_FILE_NAME + " が見つかりません: " + configFile.getAbsolutePath());
        }
        List<String> lines = Files.readAllLines(configFile.toPath(), StandardCharsets.UTF_8);
        if (lines.size() < 2) {
            throw new IOException(CONFIG_FILE_NAME + " には最低2行（検索文字・置換文字）が必要です。");
        }
        return new String[] { lines.get(0), lines.get(1) };
    }

    /** 実行中のjar（またはクラスファイル）が置かれているディレクトリを取得する */
    private static File getExecutableDirectory() throws URISyntaxException {
        Path path = Path.of(
                RenameTool.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File location = path.toFile();
        return location.isDirectory() ? location : location.getParentFile();
    }
}
