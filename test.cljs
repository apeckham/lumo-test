(ns my-project.tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]))

(deftest test-numbers
  (is (= 1 1)))

(run-tests)