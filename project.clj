(defproject encrypted-config "0.1.0-SNAPSHOT"
  :description "Encrypted configuration"
  :url "https://github.com/agilecreativity/encrypted-config"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[lein-bin "0.3.4"]]}
             :uberjar {:aot :all}}
  :source-paths ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [easy-config "0.1.2"]
                 [buddy "1.2.0"]
                 [org.bouncycastle/bcprov-jdk15on "1.56"]
                 [me.raynes/fs "1.4.6"]]
  :bin {:name "encrypted-config"
        :bin-path "~/bin"
        :bootclasspath true}
  :plugins [[lein-bin "0.3.4"]
            [lein-cljfmt "0.5.3"]]
  :main encrypted-config.core)