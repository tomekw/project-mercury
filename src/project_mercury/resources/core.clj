(ns project-mercury.resources.core
  (:require [bidi.bidi :as routing]
            [project-mercury.resources.users :as users]))

(def ^{:private true} routes
  ["/"
   {"users" users/list-resource}])

(def ^{:private true} compiled-routes (routing/compile-route routes))

(def handler
  (routing/make-handler compiled-routes))
