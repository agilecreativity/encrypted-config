(ns encrypted-config.core
  (:require [buddy.core.crypto :as crypto]
            [buddy.core.codecs :as codecs]
            [buddy.core.nonce :as nonce]
            [buddy.core.hash :as hash]
            [crypto.random :as ctr])
  (:gen-class))

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

(defn- encrypted-to-hex
  [input-text secret salt-key]
  (codecs/bytes->hex (complex-encrypted input-text secret salt-key)))

(defn- encrypted-wrapper
  "Take the input that has been encrypted with a given secret key"
  [hex-input]
  (codecs/hex->bytes hex-input))

(defn- decrypted-wrapper
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

;; Public APIs

(defn str->encrypted
  "Encrypted a given `input-text' using a given `secret-key'.
Return the map of :encrypted-key :encrypted-text that can be used"
  [input-text
   secret-key]
  (let [encrypted-key (ctr/hex 16)
        salt-key (codecs/hex->bytes encrypted-key)
        encrypted-text (encrypted-to-hex input-text secret-key salt-key)]
    ;; Return as a map instead of list
    {:encrypted-key  encrypted-key
     :encrypted-text encrypted-text}))

(defn str->decrypted
  "Decrypted previously encrypted text using secret-key and random-hex."
  [encrypted-text
   encrypted-key
   secret-key]
  (let [salt-key (codecs/hex->bytes encrypted-key)]
    (decrypted-wrapper encrypted-text secret-key salt-key)))
