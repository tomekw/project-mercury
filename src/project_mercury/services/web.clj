(ns project-mercury.services.web
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :refer [defservice]]
            [project-mercury.services.database]
            [project-mercury.resources.core :as web]))

(defservice service
  [[:ConfigService get-in-config]
   [:DatabaseService query datasource]
   [:WebserverService add-ring-handler]]
  (init [this context]
        (log/info "Initializing web service.")
        context)
  (start [this context]
         (log/info "Starting web service.")
         (log/info (query "SELECT * FROM users"))
         (let [context-path (get-in-config [:web :context-path] "/")]
           (log/info (format "Setting context path to %s." context-path))
           (add-ring-handler (web/handler (datasource)) context-path))
         context)
  (stop [this context]
        (log/info "Shutting down web service.")
        context))
