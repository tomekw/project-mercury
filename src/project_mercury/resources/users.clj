(ns project-mercury.resources.users
  (:require [liberator.core :refer [defresource]]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]))

(defn users [datasource]
  (jdbc/with-db-connection [conn datasource]
    (jdbc/query conn "SELECT * FROM users")))

(defresource list-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [_] (users datasource)))

(defresource entry-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [context]
               (let [id (get-in context [:request :params :id])]
                 (format "Hello user: %s" id))))
