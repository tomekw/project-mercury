(ns project-mercury.services.database
  (:require [clojure.tools.logging :as log]
            [hikari-cp.core :as cp]
            [clojure.java.jdbc :as jdbc]
            [puppetlabs.trapperkeeper.core :refer [defservice]]
            [puppetlabs.trapperkeeper.services :as svcs]))

(defprotocol DatabaseService
  (datasource [this])
  (query [this query]))

(defservice service
  DatabaseService
  [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing database service.")
        (let [config {:adapter (keyword (get-in-config [:database :adapter]))
                      :username (get-in-config [:database :username])
                      :password (get-in-config [:database :password])
                      :database-name (get-in-config [:database :database-name])}]
          (assoc context :dataseource-config (cp/datasource-config config))))
  (start [this context]
         (log/info "Starting database service.")
         (assoc context :datasource
                (cp/datasource-from-config (:dataseource-config context))))
  (stop [this context]
        (log/info "Shutting down database service.")
        (cp/close-datasource (:datasource context))
        (dissoc context :dataseource-config :datasource))
  (datasource [this]
              (let [context (svcs/service-context this)]
                {:datasource (:datasource context)}))
  (query [this query]
         (jdbc/with-db-connection [conn (datasource this)]
           (let [rows (jdbc/query conn query)]
             rows))))
