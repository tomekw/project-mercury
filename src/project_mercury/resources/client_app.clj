(ns project-mercury.resources.client-app
  (:require [liberator.core :refer [defresource]]
            [clojure.tools.logging :as log]))

(defresource app-resource [datasource handlers]
  :allowed-methods [:get]
  :available-media-types ["text/html"]
  :handle-ok (slurp "src/project_mercury/templates/index.html"))
