(ns encrypted-config.core-test
  (:require [clojure.test :refer :all]
            [encrypted-config.core :refer [str->encrypted
                                           str->decrypted] :as ecc]))

(deftest encryption->decryption
  (testing "encrypte a given text and immediately decrypted it back."
    (let [input-text "MyP@ssword!"
          secret-key "MySec$tKey@"
          {:keys [encrypted-key
                  encrypted-text]}
           (ecc/str->encrypted input-text secret-key)
          ;; Now let's decrypt the text we have just encrypted
          decrypted-text (ecc/str->decrypted encrypted-text
                                             encrypted-key
                                             secret-key)]
      (is (= input-text decrypted-text)))))
