(ns migrator.core
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]))

(def ^{:private true} schema-migrations-table-ddl
  ["CREATE TABLE IF NOT EXISTS schema_migrations (version text NOT NULL)"])
   ;"CREATE UNIQUE INDEX index_schema_migrations_on_version ON schema_migrations (version)"])

; TODO: handle existing index
(defn- ensure-schema-migrations-table [datasource]
  (jdbc/execute! datasource schema-migrations-table-ddl))

(defn migrate [datasource migrations-path]
  (log/info "Running migrations.")
  (ensure-schema-migrations-table datasource))
