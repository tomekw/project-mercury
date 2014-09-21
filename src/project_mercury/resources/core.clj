(ns project-mercury.resources.core
  (:require [bidi.bidi :as routes]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.secure-headers :refer [wrap-secure-headers]]
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
  (->
    (routes/make-handler (make-routes (make-handlers datasource)))
    wrap-secure-headers
    wrap-anti-forgery
    wrap-session))
