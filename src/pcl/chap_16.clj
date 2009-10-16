(ns pcl.chap_16
    (refer-clojure))

; multimethods
(defmulti draw :shape)
(defmethod draw :square [shape] "TBD: draw a square")
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
  (let [over-account (account :overdraft-account)
	over-amount (- amount (:balance account))
	withdrawal-account 
	(if (> over-amount 0)
	  (merge account
		 {:overdraft-account (withdraw over-account over-amount)
		  :balance amount})
	  account)]
    (raw-withdraw withdrawal-account amount)))

; dispatching on more than one argument    
(defmulti beat (fn [d s] [(:drum d)(:stick s)]))
(defmethod beat [:snare-drum :brush] [drum stick] "snare drum and brush")
(defmethod beat [:snare-drum :soft-mallet] [drum stick] "snare drum and soft mallet")
(defmethod beat :default [drum stick] "default value, if you want one")

(let [original-state {:account-type :bank :balance 100}
      updated-state (withdraw original-state 50)]
  (println original-state updated-state))

(let [overdraft {:account-type :checking, :balance 1000}
      original-state {:account-type :checking
		      :balance 100
		      :overdraft-account overdraft}
      updated-state (withdraw original-state 500)]
  (println original-state)
  (println updated-state))