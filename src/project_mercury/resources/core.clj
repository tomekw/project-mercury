(ns project-mercury.resources.core
  (:require [bidi.bidi :as routes]
            [ring.middleware.secure-headers :refer [wrap-secure-headers]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.nested-params :refer [wrap-nested-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [clojure.tools.logging :as log]
            [prone.middleware :refer [wrap-exceptions]]
            [project-mercury.resources.client-app :as client-app]
            [project-mercury.resources.posts :as posts]
            [project-mercury.resources.users :as users]))

(defn make-handlers [datasource]
  (let [p (promise)]
    @(deliver p {:client-app (client-app/app-resource datasource p)
                 :posts (posts/list-resource datasource p)
                 :users (users/list-resource datasource p)
                 :user (users/entry-resource datasource p)})))

(defn make-routes [handlers]
  ["/" [["" (:client-app handlers)]
        ["posts" (:posts handlers)]
        ["users" (:users handlers)]
        [["users/" :id] (:user handlers)]
        [[#".+"] (fn [req] {:status 404 :body "Not found"})]]])

(defn handler [datasource options]
  (->
    (routes/make-handler (make-routes (make-handlers datasource)))
    wrap-secure-headers
    wrap-anti-forgery
    wrap-session
    wrap-keyword-params
    wrap-nested-params
    wrap-multipart-params
    wrap-params
    wrap-cookies
    (wrap-resource "public")
    wrap-content-type
    wrap-not-modified
    (cond-> (:prone-enabled? options) wrap-exceptions)))
