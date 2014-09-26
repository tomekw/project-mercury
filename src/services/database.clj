(ns services.database
  (:require [clojure.tools.logging :as log]
            [hikari-cp.core :as cp]
            [puppetlabs.trapperkeeper.core :refer [defservice]]
            [puppetlabs.trapperkeeper.services :refer [service-context]]))

(defprotocol DatabaseService
  (datasource [this]))

(defservice service
  DatabaseService
  [[:ConfigService get-in-config]]
  (start [this context]
         (log/info "Starting database service.")
         (assoc context :datasource
                (cp/datasource-from-config
                  (cp/datasource-config (get-in-config [:database])))))
  (stop [this context]
        (log/info "Shutting down database service.")
        (cp/close-datasource (:datasource context))
        (dissoc context :datasource))
  (datasource [this]
              (let [context (service-context this)]
                {:datasource (:datasource context)})))
