(defproject project-mercury "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[bidi "1.10.5"]
                 [org.clojure/clojure "1.6.0"]
                 [hikari-cp "0.6.0"]
                 [liberator "0.12.1"]
                 [postgresql "9.3-1102.jdbc41"]
                 [org.clojure/tools.logging "0.3.1"]
                 [puppetlabs/trapperkeeper "0.5.1"]
                 [puppetlabs/trapperkeeper-webserver-jetty9 "0.7.5"]
                 [yesql "0.4.0"]]
  :aliases {"tk" ["trampoline" "run" "--config" "resources/config/config.conf" "--bootstrap-config" "resources/config/bootstrap.cfg"]}
  :main puppetlabs.trapperkeeper.main)
