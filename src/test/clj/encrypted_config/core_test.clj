(ns encrypted-config.core-test
  (:require [clojure.test :refer :all]
            [encrypted-config.core :refer [str->encrypted
                                           str->decrypted] :as ecc]))

;; See: https://github.com/clojure-emacs/cider/blob/master/doc/running_tests.md
;; To run the test C-c C-t n

(deftest encryption->decryption
  (testing "Simple decryption"
    (let [input-text "MyP@ssword!"
          secret-key "MySec$tKey@"
          [random-hex encrypted-text] (ecc/str->encrypted input-text secret-key)
          ;; Now let's decrypt the text we have just encrypted
          decrypted-text (ecc/str->decrypted encrypted-text
                                             secret-key
                                             random-hex)]
      (is (= input-text decrypted-text)))))
