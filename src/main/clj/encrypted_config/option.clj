(ns encrypted-config.option
  (:require [clojure.string :as string])
  (:gen-class))

(def default-config "~/Dropbox/github.edn")

(def options
  [["-c" "--config CONFIG" :default default-config]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["Login to Github from a command line"
        ""
        "Usage: web-login [options]"
        options-summary
        "Options:"
        ""
        "--config CONFIG path to the configuration file e.g. ~/Dropbox/github.edn"
        ""]
       (string/join \newline)))

(defn error-message [errors]
  (str "The following error occured while parsing your commands: \n\n"
       (string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))
