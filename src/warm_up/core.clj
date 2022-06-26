(ns warm-up.core
  (:require [clojure.string :as str]
            [clojure.set :as set]))

;00. 文字列の逆順
;文字列”stressed”の文字を逆に（末尾から先頭に向かって）並べた文字列を得よ．
(defn 文字列の逆順 []
  (->> (reverse "stressed")
       (apply str)))

;01. 「パタトクカシーー」
;「パタトクカシーー」という文字列の1,3,5,7文字目を取り出して連結した文字列を得よ．
(defn パタトクカシー []
  (->> "パタトクカシーー"
       (take-nth 2)
       (apply str)))

;02. 「パトカー」＋「タクシー」＝「パタトクカシーー」
;「パトカー」＋「タクシー」の文字を先頭から交互に連結して文字列「パタトクカシーー」を得よ．
(defn 「パトカー」＋「タクシー」＝「パタトクカシーー」 []
  (->> (interleave "パトカー" "タクシー")
       (apply str)))

;03. 円周率
; “Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.”
; という文を単語に分解し，各単語の（アルファベットの）文字数を先頭から出現順に並べたリストを作成せよ．
(defn 円周率 []
  (->> (str/split "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics." #" ")
       (map count)))

;04. 元素記号
; “Hi He Lied Because Boron Could Not Oxidize Fluorine. New Nations Might Also Sign Peace Security Clause. Arthur King Can.”
; という文を単語に分解し，1, 5, 6, 7, 8, 9, 15, 16, 19番目の単語は先頭の1文字，
; それ以外の単語は先頭の2文字を取り出し，取り出した文字列から単語の位置（先頭から何番目の単語か）への連想配列（辞書型もしくはマップ型）を作成せよ．
(defn 元素記号 []
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

(defn 単語bi-gram []
  (->> (str/split "I am an NLPer" #" ")
       (n-gram 2)))

(defn 文字bi-gram []
  (n-gram 2 "I am an NLPer"))

;06. 集合
;“paraparaparadise”と”paragraph”に含まれる文字bi-gramの集合を，それぞれ, XとYとして求め，
; XとYの和集合，積集合，差集合を求めよ．さらに，’se’というbi-gramがXおよびYに含まれるかどうかを調べよ．
(defn 集合X []
  (->> (n-gram 2 "paraparaparadise")
       (map #(apply str %))
       (set)))

(defn 集合Y []
  (->> (n-gram 2 "paragraph")
       (map #(apply str %))
       (set)))

(defn XとYの和集合 []
  (set/union (集合X) (集合Y)))

(defn XとYの積集合 []
  (set/intersection (集合X) (集合Y)))

(defn X-Yの差集合 []
  (set/difference (集合X) (集合Y)))

(defn Y-Xの差集合 []
  (set/difference (集合Y) (集合X)))

(defn seがXおよびYに含まれるか []
  (->> (some #{"se"} (XとYの和集合))
       (some?)))

;07. テンプレートによる文生成
;引数x, y, zを受け取り「x時のyはz」という文字列を返す関数を実装せよ．さらに，x=12, y=”気温”, z=22.4として，実行結果を確認せよ．
(defn x時のyはz [x y z]
  (format "%s時の%sは%s" x y z))

;08. 暗号文
;与えられた文字列の各文字を，以下の仕様で変換する関数cipherを実装せよ．
; 英小文字ならば(219 - 文字コード)の文字に置換
; その他の文字はそのまま出力
;この関数を用い，英語のメッセージを暗号化・復号化せよ．
(defn cipher [txt]
  (str/replace txt #"[a-z]" #(->> (.charAt % 0)
                                  (int)
                                  (- 219)
                                  (char)
                                  (str))))

;09. Typoglycemia
; スペースで区切られた単語列に対して，各単語の先頭と末尾の文字は残し，
; それ以外の文字の順序をランダムに並び替えるプログラムを作成せよ．
; ただし，長さが４以下の単語は並び替えないこととする．適当な英語の文
; （例えば”I couldn’t believe that I could actually understand what I was reading : the phenomenal power of the human mind .”）を与え，
; その実行結果を確認せよ．
(defn- typo [word]
  (if (<= (count word) 4) word
                          (let [first (first word)
                                last (last word)
                                shuffled (->> (drop 1 word)
                                              (drop-last)
                                              (shuffle))
                                ]
                            (->> (cons first shuffled)
                                 (reverse)
                                 (cons last)
                                 (reverse)
                                 (apply str)))))

(defn typoglycemia [txt]
  (->> (str/split txt #" ")
       (map typo)
       (str/join " ")))
