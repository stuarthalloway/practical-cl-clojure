(ns pcl.chap_16
    (refer-clojure))

; multimethods
(defmulti draw :shape)
(defmethod draw :square [shape] "TBD: draw a sqaure")
(defmethod draw :circle [shape] "TBD: draw a circle")

; using multimethods
(defn raw-withdraw [account amount]
  (when (< (:balance account) amount)
    (throw (IllegalArgumentException. "Account overdrawn")))
  (assoc account :balance (- (:balance account) amount)))

(defmulti withdraw :account-type)

(defmethod withdraw :bank [account amount]
  (raw-withdraw account amount))

; note the implicit deposit: problem?
(defmethod withdraw :checking [account amount]
  (let [overdraft 
	(- amount (:balance account))
	safe-account 
	(if (> overdraft 0)
	  (merge account
		 {:overdraft-account (withdraw (account :overdraft-account) overdraft)
		  :balance amount})
	  account)]
    (raw-withdraw safe-account amount)))

; dispatching on more than one argument    
(defmulti beat (fn [d s] [(:drum d)(:stick s)]))
(defmethod beat [:snare-drum :brush] [drum stick] "snare drum and brush")
(defmethod beat [:snare-drum :soft-mallet] [drum stick] "snare drum and soft mallet")
(defmethod beat :default [drum stick] "default value, if you want one")
