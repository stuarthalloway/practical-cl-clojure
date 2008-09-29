(ns pcl.chap_17
    (refer-clojure))

; basic structs
(defstruct bank-account :customer-name :balance)

; example for the REPL to access
(def example-account (struct bank-account "Example Customer" 1000))

; defaults
(def account-defaults {:balance 0})
(defn create-account [options]
  (merge account-defaults options))

; validation
(defn validate-account [account]
  (or (every? account [:customer-name :balance])
      (throw (IllegalArgumentException. "Not a valid account"))))

; accessors

; with slots