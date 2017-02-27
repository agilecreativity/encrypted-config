(ns encrypted-config.core
  (:require [buddy.core.crypto :as crypto]
            [buddy.core.codecs :as codecs]
            [buddy.core.nonce :as nonce]
            [buddy.core.hash :as hash]))

(defn- hash-sha256
  [secret-text]
  (hash/sha256 secret-text))

(defn- complex-encrypted
  [input-text secret-text salt-key]
  (let [encrypted-key (hash-sha256 secret-text)]
    (crypto/encrypt (codecs/to-bytes input-text)
                    encrypted-key
                    salt-key
                    {:algorithm :aes128-cbc-hmac-sha256})))

(defn encrypted-to-hex
  [input-text secret salt-key]
  (codecs/bytes->hex (complex-encrypted input-text secret salt-key)))

(defn encrypted-wrapper
  "Take the input that has been encrypted with a given secret key"
  [hex-input]
  (codecs/hex->bytes hex-input))

(defn decrypted-wrapper
  "Decrypt the given input (hex) using a given secret key.
salt-key should be generated by =(nonce/random-bytes 16)="
  [hex-input secret-key salt-key]
  (->
   (crypto/decrypt
    (encrypted-wrapper hex-input)
                   (hash-sha256 secret-key)
                   salt-key
                   {:algorithm :aes128-cbc-hmac-sha256})
   (codecs/bytes->str)))

(def eg-decrypted-usage
  (let [secret-key "secret"
        salt-key (nonce/random-bytes 16)
        hex-input (encrypted-to-hex "S0m$p@assw0rD!" secret-key salt-key)]
    (println (str "FYI: hex-input: " hex-input))
    (decrypted-wrapper hex-input secret-key salt-key)))

(defn -main [& args]
  (println "Welcome to main()'s world!")
  ;; (let [{:keys [options arguments errors summary]}
  ;;       (cli/parse-opts args opt/options)]
  ;;   (cond
  ;;     (:help options)
  ;;     (exit 0 (usage summary))
  ;;     (:config options)
  ;;     (create-new-repo! options)))
  )
