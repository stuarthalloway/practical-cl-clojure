(ns test
  (:use clojure.test))

(def tests
     ['pcl.chap-03-test])

(doseq [test tests] (require test))

(apply run-tests tests)

(shutdown-agents)