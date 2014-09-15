(ns project-mercury.resources.users
  (:require [liberator.core :refer [defresource]]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [yesql.core :refer [defqueries]]))

(defqueries "project_mercury/queries/users.sql")

(defn users [datasource]
  (all-users datasource))

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
