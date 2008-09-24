(clojure/in-ns 'pcl.chap_09c)
(clojure/refer 'clojure)

(def *test-name* [])

; using pr-str since using Java format
(defn report-result [result form]
  (println (format "%s: %s %s" 
		   (if result "pass" "fail") 
		   (reduce #(format "%s->%s" %1 %2) *test-name*) 
		   (pr-str form)))
  result)

; don't need to loop
; could use auto-gensyms even if we did loop
; every? doesn't promise to run the forms in order ?
(defmacro combine-results [& forms]
  `(every? identity (list ~@forms)))

(defmacro check [& forms]
  `(combine-results
    ~@(map (fn [f] `(report-result ~f '~f)) forms)))

; (name '~name) was trial and error
; using conj to stack tests
(defmacro deftest [name & forms]
  `(defn ~name []
     (binding [*test-name* (conj *test-name* (str '~name))]
       ~@forms)))
     
(deftest test-*
  (check (= (* 2 4) 8)
	 (= (* 3 3) 9)))

(deftest test-math
  ; TODO: rest of math
  (test-*))

(deftest test-all-of-nature
  ; TODO: rest of nature
  (test-math))


; tests are just functions (deftest is a convenience)
; suites are just functions that call a collection of other functions (could write a defsuite convenience)
  