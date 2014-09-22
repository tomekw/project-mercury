(ns project-mercury.resources.posts
  (:require [liberator.core :refer [defresource]]
            [clojure.java.jdbc :as jdbc]
            [clojure.tools.logging :as log]
            [yesql.core :refer [defqueries]]))

(defresource list-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [_] "Posts"))

(defresource entry-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [context]
               (let [id (get-in context [:request :params :id])]
                 (format "Post: %s" id))))
