(ns reg-exp.core
  (:require [clojure.java.io :as io]
            [cheshire.core :as cheshire]
            [clojure.string :as str]
            [portal.api :as p])
  (:import (java.util.zip GZIPInputStream)))

(def p (p/open {:launcher :intellij}))
(add-tap #'p/submit)

;第3章: 正規表現
;Wikipediaの記事を以下のフォーマットで書き出したファイルjawiki-country.json.gzがある．
;
;1行に1記事の情報がJSON形式で格納される
;各行には記事名が”title”キーに，記事本文が”text”キーの辞書オブジェクトに格納され，そのオブジェクトがJSON形式で書き出される
;ファイル全体はgzipで圧縮される
;以下の処理を行うプログラムを作成せよ．
(def data-file (io/resource "jawiki-country.json.gz"))
(defn data-reader []
  (-> data-file
      (io/input-stream)
      (GZIPInputStream.)
      (io/reader)))

;20. JSONデータの読み込み
; Wikipedia記事のJSONファイルを読み込み，
; 「イギリス」に関する記事本文を表示せよ．問題21-29では，ここで抽出した記事本文に対して実行せよ．
(defn JSONデータの読み込み []
  (with-open [rdr (data-reader)]
    (->> (line-seq rdr)
         (map #(cheshire/parse-string % (fn [k] (keyword k))))
         (filter #(= "イギリス" (:title %)))
         (first)
         (:text)
         (doall))))

;21. カテゴリ名を含む行を抽出
; 記事中でカテゴリ名を宣言している行を抽出せよ．
(defn カテゴリ名を含む行を抽出 []
  (->> (str/split (JSONデータの読み込み) #"\n")
       (filter #(str/starts-with? % "[[Category:"))))

;22. カテゴリ名の抽出
;記事のカテゴリ名を（行単位ではなく名前で）抽出せよ．
(defn カテゴリ名の抽出 []
  (->> (JSONデータの読み込み)
       (re-seq #"\[\[Category:(.+)\]\]")
       (map second)))

;23. セクション構造
;記事中に含まれるセクション名とそのレベル（例えば”== セクション名 ==”なら1）を表示せよ．
(defn セクション構造 []
  (->> (JSONデータの読み込み)
       (re-seq #"(==+)(.+?)==+")
       (map #(hash-map :name (nth % 2)
                       :level (-> %
                                  (second)
                                  (count)
                                  (- 1))))))

