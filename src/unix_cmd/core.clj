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

;13. col1.txtとcol2.txtをマージ
; 12で作ったcol1.txtとcol2.txtを結合し，
; 元のファイルの1列目と2列目をタブ区切りで並べたテキストファイルを作成せよ．確認にはpasteコマンドを用いよ．
(defn col1_txtとcol_txtをマージ []
  (with-open [col1-rdr (io/reader "out/col1.txt")
              col2-rdr (io/reader "out/col2.txt")]
    (let [col1-lines (doall (line-seq col1-rdr))
          col2-lines (doall (line-seq col2-rdr))]
      (->> (map vector col1-lines (repeat "\t") col2-lines (repeat "\n"))
           (map str/join)
           (str/join)
           (spit "out/col1+col2.txt")))))

;14. 先頭からN行を出力
; 自然数Nをコマンドライン引数などの手段で受け取り，入力のうち先頭のN行だけを表示せよ．確認にはheadコマンドを用いよ．
(defn 先頭からN行を出力 [n]
  (with-open [rdr (io/reader data-file)]
    (->> (line-seq rdr)
         (take n)
         (str/join "\n")
         (println)
         (doall))))

;15. 末尾のN行を出力
; 自然数Nをコマンドライン引数などの手段で受け取り，入力のうち末尾のN行だけを表示せよ．確認にはtailコマンドを用いよ．
(defn 末尾のN行を出力 [n]
  (with-open [rdr (io/reader data-file)]
    (->> (line-seq rdr)
         (take-last n)
         (str/join "\n")
         (println)
         (doall))))

;16. ファイルをN分割する
;自然数Nをコマンドライン引数などの手段で受け取り，入力のファイルを行単位でN分割せよ．同様の処理をsplitコマンドで実現せよ．
(defn ファイルをN分割する [n]
  (with-open [rdr (io/reader data-file)]
    (let [全行 (doall (line-seq rdr))
          全行数 (count 全行)
          分割行数 (->> (/ 全行数 n) (double) (Math/ceil) (int))
          分割ファイル内容リスト (->> (partition-all 分割行数 全行) (map #(str/join "\n" %)))]
      (doseq [[idx item] (map-indexed vector 分割ファイル内容リスト)]
        (spit (format "out/split-%s.txt" idx) item)))))

;17. １列目の文字列の異なり
; 1列目の文字列の種類（異なる文字列の集合）を求めよ．確認にはcut, sort, uniqコマンドを用いよ．
(defn _１列目の文字列の異なり []
  (with-open [rdr (io/reader data-file)]
    (let [lines (doall (line-seq rdr))]
      (->> lines
           (map #(str/split % #"\t"))
           (map first)
           (str/join)
           (set)))))

