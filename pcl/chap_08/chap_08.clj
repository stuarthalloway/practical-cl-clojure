(clojure/in-ns 'pcl.chap_08)
(clojure/refer 'clojure)

(defn divides? [candidate-divisor dividend]
  (zero? (rem dividend candidate-divisor)))

(defn prime? [num]
  (when (> num 1)
    (every? (fn [x] (not (divides? x num)))
	    (range 2 (inc (int (Math/sqrt num)))))))

; why ever do something once, when you can do it infinitely?
(defn primes-from [number]
  (filter prime? (iterate inc number)))

; new helper function
(defn primes-in-range [start end]
  (for [x (primes-from start) :while (<= x end)] x))

; omit the destructuring
(defmacro do-primes [var start end & body]
  `(doseq ~var (primes-in-range ~start ~end) ~@body))

