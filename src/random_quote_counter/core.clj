(ns random-quote-counter.core
    (:require [clojure.string :as string])
  (:gen-class))

(def random-quote-url "http://www.braveclojure.com/random-quote")

(def random-quote #(slurp random-quote-url))

(def total-word-counts (atom {}))

(defn remove-author
  [quote]
  (first (string/split quote #"--")))

(defn remove-punctuation
  [quote]
  (string/replace quote #"[,.!\"\';?]" ""))

(defn update-total-word-counts
  [word-count]
  (doseq [word (keys word-count)]
         (swap! total-word-counts #(merge-with + % {word (get word-count word)}))))

(defn count-random-quote-words
  []
  (-> (random-quote)
      remove-author
      remove-punctuation
      string/lower-case
      (string/split #"\s")
      frequencies
      update-total-word-counts)
      true)

(defn count-multi-quote-word
  [number-of-quotes]
  
  (let [responses (atom 0)
        block-till-done #(if (= number-of-quotes @responses)
                             nil
                             (do (Thread/sleep 10)
                                 (recur)))]
        (doseq [_ (range number-of-quotes)]
               (future (do (count-random-quote-words)
                           (swap! responses inc))))
        
        (block-till-done))
  @total-word-counts)
       
                                 
       
                 
           
(defn -main
  [& args]
  (println "Hello, World!"))
