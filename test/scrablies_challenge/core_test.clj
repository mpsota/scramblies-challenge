(ns scrablies-challenge.core-test
  (:require [clojure.test :refer :all]
            [scrablies-challenge.algorithms :refer [scramble?]]
            [clojure.test.check.generators :as gen]
            [clojure.string :as str]))

(deftest challenge-tests
  (testing "Tests from the challenge descriptions"
    (is (= (scramble? "rekqodlw" "world")
           true))
    (is (= (scramble? "cedewaraaossoqqyt" "codewars")
           true))
    (is (= (scramble? "katas" "steak")
           false))))

(def test-string (str (gen/generate (gen/fmap str/join (gen/vector (gen/elements [\a]) 1e6)))
                      "test"))

(deftest big-test
  (testing "Testing 1M long string"
    (is (= (scramble? test-string "test")
           true))))