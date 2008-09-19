(in-ns 'pcl.chap_11)
(clojure/refer 'clojure)

(defmacro print-examples [& forms]
   `(do
      ~@(map (fn [f]
	       `(println (format "input=> %s\n%s"
				 (pr-str '~f)
				 (pr-str ~f)))) forms)))

(print-examples (count '(1 2 3))
		(count [1 2 3])
		(count #{1 2 3})
		(count #{:name "John"}))

(print-examples (:A #{:A :B :C})
		(:D #{:A :B :C})
		(#{:A :B :C} :A)
		(#{:A :B :C} :D))

(print-examples (:lname {:lname "Doe", :fname "John"})
		(:mname {:lname "Doe", :fname "John"}))



