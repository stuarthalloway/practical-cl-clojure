(ns pcl.chap-03-test
  (:use clojure.test pcl.chap-03))

(deftest loading-and-saving-db
  (testing "read matches write"
           (let [data {:a "foo"}]
             (save-db data "test/output/test.db")
             (is (= data (load-db "test/output/test.db"))))))