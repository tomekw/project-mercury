(ns project-mercury.resources.core
  (:require [compojure.core :as compojure]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.tools.logging :as log]
            [project-mercury.resources.users :as users])
  (:use [clojurewerkz.route-one.core]))

(defroute users "/users")
(defroute user "/users/:id")

(compojure/defroutes routes
  (compojure/ANY users-template request users/list-resource)
  (compojure/ANY user-template request users/entry-resource))

(def handler
  (handler/site routes))
