; use destructuring bind instead of nil
(defn foo [a b & [c d]]
  (list a b c d))

; use keys and or directives for optional values
(defn foo [{:keys [a b] :or {b 10}}] (list a b))

; couldn't find a way for destructure to refer to other parts
(defn make-rectangle 
  ([width] (make-rectangle width width))
  ([width height] {:width width :height height}))
