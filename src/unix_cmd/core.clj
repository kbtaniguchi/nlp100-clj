(ns unix-cmd.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

;第2章: UNIXコマンド
; popular-names.txtは，アメリカで生まれた赤ちゃんの「名前」「性別」「人数」「年」をタブ区切り形式で格納したファイルである．
; 以下の処理を行うプログラムを作成し，popular-names.txtを入力ファイルとして実行せよ．
; さらに，同様の処理をUNIXコマンドでも実行し，プログラムの実行結果を確認せよ．
(def data-file (io/resource "popular-names.txt"))

;10. 行数のカウント
;行数をカウントせよ．確認にはwcコマンドを用いよ．
(defn 行数のカウント []
  (with-open [rdr (io/reader data-file)]
    (->> (line-seq rdr)
         (count))))

;11. タブをスペースに置換
;タブ1文字につきスペース1文字に置換せよ．確認にはsedコマンド，trコマンド，もしくはexpandコマンドを用いよ．
(defn タブをスペースに置換 []
  (with-open [rdr (io/reader data-file)]
    (->> (line-seq rdr)
         (map #(str/replace % #"\t" " "))
         (doall))))

;12. 1列目をcol1.txtに，2列目をcol2.txtに保存
; 各行の1列目だけを抜き出したものをcol1.txtに，2列目だけを抜き出したものをcol2.txtとしてファイルに保存せよ．
; 確認にはcutコマンドを用いよ．
(defn _1列目をcol1_txtに_2列目をcol2_txtに保存 []
  (with-open [rdr (io/reader data-file)]
    (let [lines (doall (line-seq rdr))]
      (->> lines
           (map #(str/split % #"\t"))
           (map first)
           (str/join "\n")
           (spit "out/col1.txt"))
      (->> lines
           (map #(str/split % #"\t"))
           (map second)
           (str/join "\n")
           (spit "out/col2.txt")))))

