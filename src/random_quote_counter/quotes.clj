(ns random-quote-counter.quotes
    (:require [random-quote-counter.formatting :refer :all])
  (:gen-class))

(def ^:private random-quote-url "http://www.braveclojure.com/random-quote")

(def ^:private random-quote #(slurp random-quote-url))

(def ^:private word-frequencies (atom {}))

(defn- update-word-frequencies
  [word-count]
  (doseq [word (keys word-count)]
         (swap! word-frequencies #(merge-with + % {word (get word-count word)}))))
     
(defn- quote-word-frequencies
  [quote]
  (-> quote
      remove-author
      remove-punctuation
      string/lower-case
      (string/split #"\s")
      frequencies))

(defn multi-quote-frequencies
  [number-of-quotes]
  
  (let [responses (atom 0)
        block-till-done #(if (= number-of-quotes @responses)
                             nil
                             (do (Thread/sleep 10)
                                 (recur)))]
        (doseq [_ (range number-of-quotes)]
               (future (do (-> (random-quote)
                               quote-word-frequencies
                               update-word-frequencies)
                           (swap! responses inc))))
        
        (block-till-done))
  (let [result @word-frequencies]
       (reset! word-frequencies {})
       (sort-by first result)))