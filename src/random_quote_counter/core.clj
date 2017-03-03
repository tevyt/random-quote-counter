(ns random-quote-counter.core
  (:require [random-quote-counter.quotes :refer [multi-quote-frequencies]])  
  (:gen-class))

(defn print-results
  [results]
  (doseq [[word frequency] results]
         (println word " :" frequency)))

(defn prompt
  ([] (println "Welcome to the Random Quote Counter")
      (prompt true))
  ([continue?]
    (if continue?
        (do (print "How many quotes to measure?: ")
            (flush)
            (let [number-of-quotes (read-string (read-line))]
                 (print-results (multi-quote-frequencies number-of-quotes)))
            (print "Try another? (Y/N): ")
            (flush)
            (if (= (clojure.string/upper-case (read-line)) "Y")
                (recur true)
                (recur false)))
        (println "Thank you come again!"))))
               

(defn -main
  [& args]
  (prompt))
