(in-ns 'pcl.chap_05)
(clojure/refer 'clojure)

; use destructuring bind instead of nil
(defn foo [a b & [c d]]
  (list a b c d))

; use keys and or directives for optional values
(defn bar [{:keys [a b] :or {b 10}}] (list a b))

; couldn't find a way for destructure to refer to other parts
(defn make-rectangle 
  ([width] (make-rectangle width width))
  ([width height] {:width width :height height}))

; not obvious how to get the var-supplied idiom from CL
(defn which-args-supplied [{:keys [a b c] :as all :or {c 4}}]
  (let [c-supplied (contains? all :c)]
    (list a b c c-supplied)))

; can't write return-from, because defn does not wrap in named entity

; do this instead
(defn pair-with-product-greater-than [n]
  (take 1 (for [i (range 10) j (range 10) :when (> (* i j) n)] [i j])))

; using dotimes instead of repeat from cl
(defn plot [f min max step]
  (doseq i (range min max step)
    (dotimes _ (apply f [i]) (print "*"))
    (println)))

(defn stars [ct]
  (apply str (take ct (repeat "*"))))

; more idiomatic ?
(defn plot2 [f min max step]
  (doseq i (range min max step)
    (print (stars (apply f [i])))
    (println)))

; anonymous function examples
; (plot (fn [x] (* x 2)) 1 10)

; shorter (like Groovy's 'it')
; (plot #(* 2 %) 1 10 1)
