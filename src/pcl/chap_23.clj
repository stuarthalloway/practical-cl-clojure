(ns pcl.chap_23
    (refer-clojure))

; options
(def max-ham-score 0.4)
(def min-spam-score 0.6)

; refs
(def database (ref {}))
(def total-spams (ref 0))
(def total-hams (ref 0))

(defn classification [score]
  (cond
   (<= score max-ham-score) 'ham
   (>= score min-spam-score) 'spam
   true 'unsure))

(defstruct word-feature :word :spam-count :ham-count)

(defn intern-feature [db word]
  (merge {word (struct word-feature word 0 0)} db))
	     
(defn extract-words [text]
  (distinct (re-seq #"[a-zA-Z]{3,}" text)))

(defn extract-features [db text]
  (reduce intern-feature
	  (cons db (extract-words text))))

(defn score [& args] (throw (Error. "TBD")))

(defn classify [text]
  (classification (score (extract-features text))))

; could name this increment-count to & use defmulti. Overkill...
(defn increment-feature-count [feature type]
  (println feature)
  (assoc feature type (inc (feature type))))

; increment the total-count somewhere else?  
(defn increment-count [db word type]
  (let [db (intern-feature db word)]
    (merge db {word (increment-feature-count (db word) type)})))

; broken out into separate functions, unlike PCL
(defn spam-frequency [feature]
  (/ (feature :spam-count) (max 1 @total-spams)))
(defn ham-frequency [feature]
  (/ (feature :ham-count) (max 1 @total-hams)))

(defn spam-probability [feature]
  (/ (spam-frequency feature) 
     (+ (spam-frequency feature) (ham-frequency feature))))

(defn bayesian-spam-probability
  ([feature] 
     (bayesian-spam-probability feature 1/2))
  ([feature assumed-probability] 
     (bayesian-spam-probability feature assumed-probability 1))
  ([feature assumed-probability weight]
     (let [basic-probability (spam-probability feature)
	   data-points (+ (:spam-count feature) (:ham-count feature))]
       (/ (+ (* weight assumed-probability)
	     (* data-points basic-probability))
	  (+ weight data-points)))))

(defn untrained? [feature]
  (every? zero? [(:spam-count feature) (:ham-count feature)]))

(defn inverse-chi-square [value degrees-of-freedom]
  (reduce 
   +
   (loop [m (/ value 2)
	  i 1
	  probs (list (Math/exp (- m)))]
     (if (>= i (quot degrees-of-freedom 2))
       probs
       (recur m (inc i) (cons (* (first probs) (/ m i)) probs))))))

; using map instead of keyword argument to reduce
(defn fisher [probs number-of-probs]
  (inverse-chi-square
   (* -2 (reduce + (map #(Math/log %) probs)))
   (* 2 number-of-probs)))

; TODO: score function
; TODO: testing

; transactions
(defn clear-database! []
  (dosync (ref-set database #{})
	  (ref-set total-spams 0)
	  (ref-set total-hams 0)))

