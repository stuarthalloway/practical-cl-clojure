(clojure/in-ns 'pcl.chap_09b)
(clojure/refer 'clojure)

(defn report-result [result form]
  (println (format "%s: %s" (if result "pass" "fail") (pr-str form)))
  result)

; don't need to loop
; could use auto-gensyms even if we did loop
(defmacro combine-results [& forms]
  `(every? identity (list ~@forms)))

(defmacro check [& forms]
  `(combine-results
    ~@(map (fn [f] `(report-result ~f '~f)) forms)))

(defn test-* []
  (check (= (* 2 4) 8)
	 (= (* 3 3) 9)))
     