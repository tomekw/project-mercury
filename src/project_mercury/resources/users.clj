(ns project-mercury.resources.users
  (:require [liberator.core :refer [defresource]]
            [clojure.tools.logging :as log]))

(defresource list-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [_] "Hello users"))

(defresource entry-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [context]
               (let [id (get-in context [:request :params :id])]
                 (format "Hello user: %s" id))))
