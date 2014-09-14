(defproject project-mercury "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [clojurewerkz/route-one "1.1.0"]
                 [liberator "0.12.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [org.clojure/tools.logging "0.3.0"]
                 [puppetlabs/trapperkeeper "0.4.3"]
                 [puppetlabs/trapperkeeper-webserver-jetty9 "0.5.2"]]
  :aliases {"tk" ["trampoline" "run" "--config" "resources/config/config.conf" "--bootstrap-config" "resources/config/bootstrap.cfg"]}
  :main puppetlabs.trapperkeeper.main)
