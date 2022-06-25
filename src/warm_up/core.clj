(ns warm-up.core
  (:require [clojure.string :as str]))

;00. 文字列の逆順
(defn 文字列の逆順
  "文字列”stressed”の文字を逆に（末尾から先頭に向かって）並べた文字列を得よ．"
  []
  (->> (reverse "stressed")
       (apply str)))

;01. 「パタトクカシーー」
(defn パタトクカシー
  "「パタトクカシーー」という文字列の1,3,5,7文字目を取り出して連結した文字列を得よ．"
  []
  (->> "パタトクカシーー"
       (take-nth 2)
       (apply str)))

;02. 「パトカー」＋「タクシー」＝「パタトクカシーー」
(defn 「パトカー」＋「タクシー」＝「パタトクカシーー」
  "「パトカー」＋「タクシー」の文字を先頭から交互に連結して文字列「パタトクカシーー」を得よ．"
  []
  (->> (interleave "パトカー" "タクシー")
       (apply str)))

;03. 円周率
(defn 円周率
  "“Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics.”
  という文を単語に分解し，各単語の（アルファベットの）文字数を先頭から出現順に並べたリストを作成せよ．"
  []
  (->> (str/split "Now I need a drink, alcoholic of course, after the heavy lectures involving quantum mechanics." #" ")
       (map count)))

