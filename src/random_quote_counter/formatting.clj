(ns random-quote-counter.formatting
    (:require [clojure.string :as string])
  (:gen-class))

(defn remove-author
  [quote]
  (first (string/split quote #"--")))

(defn remove-punctuation
  [quote]
  (string/replace quote #"[,.!\"\';?]" ""))