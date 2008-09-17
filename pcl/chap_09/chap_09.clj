(clojure/in-ns 'pcl.chap_09)
(clojure/refer 'clojure)

(defn report-result [result form]
  (println (format "%s: %s" (if result "pass" "FAIL") (pr-str form))))

(defn test-+ []
  (report-result (= (+ 1 2) 3) '(= (+ 1 2) 3))
  (report-result (= (+ 1 2 3) 6) '(= (+ 1 2 3) 6))
  (report-result (= (+ -1 -3) -4) '(= (+ -1 -3) -4)))

(defmacro check [form]
  `(report-result ~form '~form))

; deliberate fails
(defn test-* []
  (check (= (* 1 2) 3))
  (check (= (* 1 2 3) 6))
  (check (= (* -1 -3) -4)))

(defmacro check [& forms]
  `(do
     ~@(map (fn [f] `(report-result ~f '~f))  forms)))

(defn test-rem []
  (check (= (rem 10 3) 1)
	 (= (rem 6 2) 0)
	 (= (rem 7 4) 3)))




    