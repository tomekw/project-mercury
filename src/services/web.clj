(ns services.web
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :refer [defservice]]
            [project-mercury.resources.core :as web]))

(defservice service
  [[:ConfigService get-in-config]
   [:DatabaseService datasource]
   [:MigrationService]
   [:WebserverService add-ring-handler]]
  (start [this context]
         (log/info "Starting web service.")
         (let [context-path (get-in-config [:web :context-path] "/")
               options      {:prone-enabled? (get-in-config [:prone :enabled])}]
           (log/info (format "Setting context path to %s." context-path))
           (add-ring-handler (web/handler (datasource) options) context-path))
         context)
  (stop [this context]
        (log/info "Shutting down web service.")
        context))
