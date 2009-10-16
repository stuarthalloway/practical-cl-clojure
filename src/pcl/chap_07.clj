(clojure/in-ns 'pcl.chap_07)
(clojure/refer 'clojure)

; demo unless instead of when, since when is built in already
(defmacro unless [condition & body]
  `(when (not ~condition)
     ~@body))

(defn demo-if-and-when []
  (let [happy true]
    (if happy
      (println "happy")
      (println "sad"))
    (when happy
      (println "so happy")
      (println "really happy")))
  (let [sad false]
    (unless sad
      (println "not sad")
      (println "no, not sad at all"))))	    

; fewer parentheses than CL dolist
(defn demo-doseq []
  (doseq x '(1 2 3) (println x))
  (doseq [_ v] {:fn "John" :ln "Doe"} (println v))
  (doseq x (take 5 (iterate inc 1)) (println x)))

; fewer parentheses than CL dotimes
(defn times-table []
  (dotimes x 10
    (dotimes y 20
      (print (format "%3d " (* (inc x) (inc y)))))
    (println)))
      
    
  
