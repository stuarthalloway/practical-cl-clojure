(clojure/in-ns 'prcl.ch06)
(clojure/refer 'clojure)

; closure demo ends up demoing ref/dosync and anon lambda
(def counter (let [count (ref 0)] #(dosync (alter count inc))))

; multiple fns closed on same value
(defn counters []
  (let [count (ref 0)]
    (list #(dosync (alter count inc))
	  #(dosync (alter count dec))
	  #(deref count))))