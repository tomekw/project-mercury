(ns project-mercury.services.database
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :refer [defservice]]))

(defprotocol DatabaseService
  (foo [this]))

(defservice service
  DatabaseService
  [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing database service.")
        context)
  (start [this context]
         (log/info "Starting database service.")
         context)
  (stop [this context]
        (log/info "Shutting down database service.")
        context)
  (foo [this]
       "Hello!"))
