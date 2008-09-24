(in-ns 'pcl.chap_11)
(clojure/refer 'clojure)

(defmacro print-examples [& forms]
   `(do
      ~@(map (fn [f]
	       `(println (format "user=> %s\n%s"
				 (pr-str '~f)
				 (pr-str ~f)))) forms)))

(print-examples (count '(1 2 3))
		(count [1 2 3])
		(count #{1 2 3})
		(count "characters")
		(count #{:name "John"}))

(print-examples (find {:lname "Doe", :fname "John"} :fname)
		({:lname "Doe", :fname "John"} :fname)
		(some #(= % 2) [1 2 3]))

(use 'clojure.contrib.str-utils)
(def days (re-split #" " "Sun Mon Tues Wed Thurs Fri Sat"))

(print-examples (filter #(.startsWith % "S") days)
		(count (filter #(.startsWith % "S") days))
		(filter (complement #(.startsWith % "S")) days)
		(map #(if (.startsWith % "S") "Weekend!" %) days)
		(sort days))

(use 'clojure.contrib.seq-utils)
(print-examples (take 5 (concat '(1/4 1/2) powers-of-2)))

(print-examples (every? #(.startsWith % "S") days)
		(some #(.startsWith % "M") days))






