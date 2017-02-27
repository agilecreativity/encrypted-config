(ns encrypted-config.core-test
  (:require [clojure.test :refer :all]
            [encrypted-config.core :refer :all]
            [encrypted-config.option :refer :all]))

;; See: https://github.com/clojure-emacs/cider/blob/master/doc/running_tests.md
;; To run the test C-c C-t n

(def sample-config
  (let [sample-config
        {:url "https://github.com/login"
         :expected-title "github"
         :u-type :id
         :u-expr "login_field"
         :username "agilecreativity"
         :p-type :id
         :p-expr "password"
         :password "some-password"
         :browser :chrome
         }]
    sample-config))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))
