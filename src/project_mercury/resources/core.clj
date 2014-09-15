(ns project-mercury.resources.core
  (:require [bidi.bidi :as routes]
            [clojure.tools.logging :as log]
            [project-mercury.resources.users :as users]))

(defn make-handlers [datasource]
  (let [p (promise)]
    @(deliver p {:users (users/list-resource datasource p)
                 :user (users/entry-resource datasource p)})))

(defn make-routes [handlers]
  ["/" [["users" (:users handlers)]
        [["users/" :id] (:user handlers)]]])

(defn handler [datasource]
  (routes/make-handler (make-routes (make-handlers datasource))))
