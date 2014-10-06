(ns repl
  (:require [puppetlabs.trapperkeeper.services.webserver.jetty9-service
             :refer [jetty9-service]]
            [clojure.edn :as edn]
            [clojure.java.jdbc :as jdbc]
            [services.database :as database]
            [services.migration :as migration]
            [services.web :as web]
            [puppetlabs.trapperkeeper.core :as tk]
            [puppetlabs.trapperkeeper.app :as tka]
            [clojure.tools.namespace.repl :refer (refresh)]))

(def system nil)

(defn init []
  (alter-var-root #'system
                  (fn [_] (tk/build-app
                            [jetty9-service database/service migration/service web/service]
                            (edn/read-string (slurp "resources/config/config.edn")))))
  (alter-var-root #'system tka/init)
  (tka/check-for-errors! system))

(defn start []
  (alter-var-root #'system (fn [s] (if s (tka/start s))))
  (tka/check-for-errors! system))

(defn stop []
  (alter-var-root #'system (fn [s] (when s (tka/stop s)))))

(defn go []
  (init)
  (start))

(defn context []
  @(tka/app-context system))

(defn print-context []
  (clojure.pprint/pprint (context)))

(defn reload []
  (stop)
  (refresh :after 'repl/go))

(defn reset-db []
  (let [datasource {:datasource (:datasource (:DatabaseService (context)))}]
    (jdbc/execute! datasource ["DROP SCHEMA public CASCADE"])
    (jdbc/execute! datasource ["CREATE SCHEMA public"]))
  (reload))
