(ns jupiter.core
  (:import [java.sql.SQLException])
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]))

(def ^{:private true} schema-migrations-table-ddl
  ["CREATE TABLE IF NOT EXISTS schema_migrations (version text NOT NULL)"
   "CREATE UNIQUE INDEX index_schema_migrations_on_version ON schema_migrations (version)"])

(def ^{:private true} applied-versions-query
  ["SELECT version FROM schema_migrations"])

(def ^{:private true} migration-pattern
  #"\A(\d+)_(.+)\.sql\z")

(defn- migration-file? [file]
  (re-matches migration-pattern (.getName file)))

(defn- migration [migration-file]
  (let [[_ version name] (re-find migration-pattern (.getName migration-file))]
    {:file    migration-file
     :version version
     :name    name}))

(defn- all-migrations [migrations-path]
  (map #(migration %)
       (filter migration-file?
               (.listFiles (clojure.java.io/file migrations-path)))))

(defn- pending-migrations [datasource all-migrations]
  (let [applied-versions (set (jdbc/query datasource applied-versions-query))]
    (sort-by #(:version %)
             (filter #(not (applied-versions (select-keys % [:version]))) all-migrations))))

(defn- migration-body [migration]
  [(slurp (:file migration))])

(defn- register-migration-query [migration]
  ["INSERT INTO schema_migrations (version) VALUES (?)" (:version migration)])

(defn- apply-migration [datasource migration]
  (log/info (str "Applying migration " (:name migration) "..."))
  (try
    (jdbc/with-db-transaction [conn datasource]
      (jdbc/execute! conn (register-migration-query migration))
      (jdbc/execute! conn (migration-body migration)))
    (catch java.sql.SQLException e
      (log/error (.getMessage e))
      (throw e))))

(defn- apply-migrations [datasource migrations]
  (doall (map #(apply-migration datasource %) migrations)))

(defn ensure-schema-migrations-table [datasource]
  (try
    (apply jdbc/db-do-commands datasource schema-migrations-table-ddl)
    (catch java.sql.SQLException _)))

(defn migrate [datasource migrations-path]
  (apply-migrations datasource
                    (pending-migrations datasource
                                        (all-migrations migrations-path))))
