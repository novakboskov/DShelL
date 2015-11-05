(ns dsl.core-test
  (:require [clojure.test :refer :all]
            [dsl.core :refer :all]))

(deftest test-bash
  (testing "FIXME"
    (is (= (with-implementation :dsl.core/bash
             (script "a"))
           "a"))
    (is (= (with-implementation :dsl.core/bash
             (script (println "someVar")))
           "echo someVar"))))

(deftest test-batch
  (testing "FIXME"
    (is (= (with-implementation :dsl.core/batch
             (script "a"))
           "a"))
    (is (= (with-implementation :dsl.core/batch
             (script (println "someVar")))
           "ECHO someVar"))))
