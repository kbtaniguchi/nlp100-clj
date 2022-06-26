(ns warm-up.core
  (:require [clojure.string :as str]))

;00. 文字列の逆順
;文字列”stressed”の文字を逆に（末尾から先頭に向かって）並べた文字列を得よ．
(defn 文字列の逆順
  []
  (->> (reverse "stressed")
       (apply str)))

;01. 「パタトクカシーー」
;「パタトクカシーー」という文字列の1,3,5,7文字目を取り出して連結した文字列を得よ．
(defn パタトクカシー
  []
  (->> "パタトクカシーー"
       (take-nth 2)
       (apply str)))

;02. 「パトカー」＋「タクシー」＝「パタトクカシーー」
;「パトカー」＋「タクシー」の文字を先頭から交互に連結して文字列「パタトクカシーー」を得よ．
(defn 「パトカー」＋「タクシー」＝「パタトクカシーー」
  []
  (->> (interleave "パトカー" "タクシー")
       (apply str)))

;03. 円周率
; “Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.”
; という文を単語に分解し，各単語の（アルファベットの）文字数を先頭から出現順に並べたリストを作成せよ．
(defn 円周率
  []
  (->> (str/split "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics." #" ")
       (map count)))

;04. 元素記号
; “Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can.”
; という文を単語に分解し，1, 5, 6, 7, 8, 9, 15, 16, 19番目の単語は先頭の1文字，
; それ以外の単語は先頭の2文字を取り出し，取り出した文字列から単語の位置（先頭から何番目の単語か）への連想配列（辞書型もしくはマップ型）を作成せよ．
(defn 元素記号
  ""
  []
  (->> (str/split "Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can." #" ")
       (map-indexed (fn [idx item] (if (some #{(+ idx 1)} [1, 5, 6, 7, 8, 9, 15, 16, 19]) (take 1 item) (take 2 item))))
       (map #(apply str %))))

;05. n-gram
;与えられたシーケンス（文字列やリストなど）からn-gramを作る関数を作成せよ．
; この関数を用い，”I am an NLPer”という文から単語bi-gram，文字bi-gramを得よ．
(defn n-gram [n seq]
  (letfn [(ng [result current n]
            (let [taken (take n current)]
              (if (< (count taken) n)
                result
                (recur (conj result taken) (drop 1 current) n))))]
    (ng [] seq n)))

(defn 単語bi-gram
  []
  (->> (str/split "I am an NLPer" #" ")
       (n-gram 2)))

(defn 文字bi-gram
  []
  (n-gram 2 "I am an NLPer"))

;06. 集合
;