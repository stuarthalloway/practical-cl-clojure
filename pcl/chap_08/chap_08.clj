(clojure/in-ns 'pcl.chap_08)
(clojure/refer 'clojure)

(defn primep [num]
  (when (> num 1)
    (empty? (for [x (range 2 (inc (int (Math/sqrt num)))) :when (zero? (rem num x))] x))))

; why ever do something once, when you can do it infinitely?
(defn primes-from [number]
  (filter primep (iterate inc number)))

; new helper function
(defn primes-in-range [start end]
  (for [x (primes-from start) :while (<= x end)] x))

; omit the destructuring
(defmacro do-primes [var start end & body]
  `(doseq ~var (primes-in-range ~start ~end) ~@body))