(ns project-mercury.resources.users
  (:require [liberator.core :refer [defresource]]))

(defresource list-resource
  :allowed-methods [:get]
  :available-media-types ["text/plain"]
  :handle-ok (fn [_] "Hello users"))
