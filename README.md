# ファイル名一括変更ツール（RenameTool）

**日本語** | [English](README_EN.md)

## <img src="images/sparkles.svg" width="20" height="20" valign="middle"> はじめに

新しい職場に入る準備をする中で、以前の職場で「ファイル名の変更」が頻繁に発生していたことを思い出しました。
同じような場面に備え、あらかじめファイル名の変更にすぐ対応できるツールをJavaで作っておくことにしました。

まずはシンプルな「フォルダ直下のファイルだけを対象にした名前変換」から着手し、必要に応じて機能を増やしていく方針です。

## <img src="images/list-checks.svg" width="20" height="20" valign="middle"> できること

- 指定したフォルダの直下にあるファイル名の一部を、指定した文字に一括で置き換える
- 変更した内容を元に戻すための`Restore.bat`を自動作成する
- フォルダ（ディレクトリ）はリネーム対象にしない

## <img src="images/folder-tree.svg" width="20" height="20" valign="middle"> ファイル構成

| ファイル名 | 役割 |
|---|---|
| `RenameTool.java` | 本体プログラム |
| `RenameTool.class` | `run.bat`の実行時に生成されるファイル |
| `rename.txt` | 変換ルールの設定ファイル（1行目＝変更前、2行目＝変更後） |
| `run.bat` | Javaをコンパイルして実行する起動用バッチ |
| `Restore.bat` | 実行後に自動生成される、元に戻すためのバッチ |

## <img src="images/mouse-pointer-click.svg" width="20" height="20" valign="middle"> 使い方

1. JDKをインストールし、`java`と`javac`を使えるようにする
2. `rename.txt`を開き、1行目に「変更したい文字」、2行目に「変更後の文字」を書いて保存する
3. リネームしたいファイルと`RenameTool.java`・`rename.txt`・`run.bat`を同じフォルダに置く
4. `run.bat`をダブルクリックする
5. 画面に処理結果（何を何に変えたか）が表示される
6. やり直したい場合は、同じフォルダにできた`Restore.bat`をダブルクリックすると元の名前に戻る

## <img src="images/info.svg" width="20" height="20" valign="middle"> 仕組みの補足

- 対象になるのは、起動用バッチと同じフォルダの中にある「ファイル」だけです。サブフォルダの中や、フォルダそのものは対象外です
- ファイル名の中に「変更前の文字」が含まれていれば、その部分だけを置き換えます（ファイル名全体が完全一致している必要はありません）
- 変更後と同じ名前のファイルが既に存在する場合は、上書き事故を防ぐためスキップされます
- 画面表示の文字化けを防ぐため、プログラム側の出力をUTF-8に固定し、`run.bat`側でも画面の文字コードをUTF-8に切り替えています

## <img src="images/shield-check.svg" width="20" height="20" valign="middle"> 動作確認済みの内容

- テスト用フォルダを作成し、複数のファイルを対象にリネームが正しく行われることを確認済み
- 対象外のファイル・フォルダ・設定ファイル自体が変更されないことを確認済み
- `Restore.bat`を実行し、リネーム前の状態に正しく戻ることを確認済み
- 実行フォルダの外（親フォルダなど）には影響が及ばないことを確認済み

## <img src="images/rocket.svg" width="20" height="20" valign="middle"> 今後の拡張予定（今回は未対応）

- サブフォルダの中まで対象にするオプション
- ファイル名の先頭に特定の文字を追加する機能
- 拡張子だけを変更する機能

## クレジットとライセンス

見出しのアイコンは [Lucide](https://lucide.dev/icons/) の SVG を使用しています。画像は `images` フォルダに格納し、見やすいように線の色を赤（`#dc2626`）に変更しました。取得元と変更内容は [images/SOURCE.txt](images/SOURCE.txt) に記載しています。

Lucide のアイコンは ISC ライセンスです。一部のアイコンには Feather 由来の MIT ライセンスも適用されます。著作権表示とライセンス全文は [images/LICENSE](images/LICENSE) をご覧ください。

Lucide の制作者・メンテナー・貢献者の皆さまへ。たびたび使用させて頂いており、いつも感謝しています。
