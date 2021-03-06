(defproject project-mercury "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[bidi "1.10.5"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [hikari-cp "0.9.1"]
                 [liberator "0.12.2"]
                 [postgresql "9.3-1102.jdbc41"]
                 [prone "0.6.0"]
                 [ring/ring-anti-forgery "1.0.0"]
                 [dhruv/ring-secure-headers "0.3.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [puppetlabs/trapperkeeper "0.5.2"]
                 [puppetlabs/trapperkeeper-webserver-jetty9 "0.9.0"]
                 [yesql "0.4.0"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.7"]]}}
  :plugins [[lein-jupiter "0.1.0"]]
  :aliases {"tk" ["trampoline" "run" "--config" "resources/config/config.edn" "--bootstrap-config" "resources/config/bootstrap.cfg"]}
  :main puppetlabs.trapperkeeper.main)
