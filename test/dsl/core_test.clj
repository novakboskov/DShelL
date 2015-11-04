(ns dsl.core-test
  (:require [clojure.test :refer :all]
            [dsl.core :refer :all]))

(deftest test-bash
  (testing "FIXME"
    (is (= (emit-bash "a") "a"))
    (is (= (emit-bash '(println "a")) "echo a"))))

(deftest test-batch
  (testing "FIXME"
    (is (= (emit-batch "a") "a"))
    (is (= (emit-batch '(println "a")) "ECHO a"))))
