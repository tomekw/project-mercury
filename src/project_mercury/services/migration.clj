(ns project-mercury.services.migration
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :refer [defservice]]
            [jupiter.core :as migrator]))

(defprotocol MigrationService)

(defservice service
  MigrationService
  [[:ConfigService get-in-config]
   [:DatabaseService datasource]]
  (init [this context]
        (log/info "Initializing migration service.")
        context)
  (start [this context]
         (log/info "Running pending migrations (if any).")
         (migrator/ensure-schema-migrations-table (datasource))
         ;(migrator/migrate (datasource)
                           ;(get-in-config [:migrator :migrations-path]))
         context)
  (stop [this context]
        (log/info "Shutting down migration service.")
        context))
