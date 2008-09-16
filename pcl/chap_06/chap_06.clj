(clojure/in-ns 'pcl.chap_06)
(clojure/refer 'clojure)
(clojure.contrib.lib/use 'clojure.contrib.def)
(clojure.contrib.lib/use 'clojure.contrib.string)

; closure demo ends up demoing ref/dosync and anon lambda
(def counter (let [count (ref 0)] #(dosync (alter count inc))))

; multiple fns closed on same value
(defn counters []
  (let [count (ref 0)]
    (list #(dosync (alter count inc))
	  #(dosync (alter count dec))
	  #(deref count))))

; don't use *foo* convention unless var is to be dynamically rebound
(def
 #^{:doc "Count of widgets made so far"}
 widget-count)
(init-once widget-count 0)

(defvar gap-tolerance 0.0001
  "Tolerance to be allowed in widget gaps")

(def a "global a")
(def b "global b")

(def print-a-and-b)

(defn demo-bindings []
  (let [a "let a" b "let b"]
    (print-a-and-b "let"))
  (binding [a "bound a" b "bound b"]
    (print-a-and-b "binding")))

(defn print-a-and-b [from]
  (println (format "From %s: [a=%s] [b=%s]" from a b)))
     
(defmacro print-eval [form]
  `(do
     (println (format "%30s    =>   %s"
		      (pr-str (quote ~form))
		      (pr-str ~form)))))

(defn demo-assoc []
  (let [a [1 2 3 4 5]
	o {:foo 5}]
    (print-eval a)
    (print-eval (assoc a 0 10))
    (print-eval a)
    (print-eval o)
    (print-eval (assoc o :bar 10))
    (print-eval o)))
		
(defn demo-assoc-on-ref []
  (let [a (ref [1 2 3 4 5])]
    (print-eval a)
    (print-eval @a)
    (print-eval (dosync (alter a #(assoc % 0 10))))
    (print-eval @a)))

; no obvious equivalent of rotate/shift, but could write them on top of STM
