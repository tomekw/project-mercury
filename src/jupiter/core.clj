(ns jupiter.core
  (:import [java.sql.SQLException])
  (:require [clojure.tools.logging :as log]
            [clojure.java.jdbc :as jdbc]))

(def ^{:private true} schema-migrations-table-ddl
  ["CREATE TABLE IF NOT EXISTS schema_migrations (version text NOT NULL);
   CREATE UNIQUE INDEX index_schema_migrations_on_version
   ON schema_migrations (version);"])

(def ^{:private true} migration-pattern
  #"\A\d+_.+\.sql\z")

(defn- migration-file? [file]
  (re-matches migration-pattern (.getName file)))

(defn- all-migrations [migrations-path]
  (filter migration-file? (.listFiles (clojure.java.io/file migrations-path))))

(defn- migration-id [migration]
  (first (clojure.string/split (.getName migration) #"_")))

(defn- migration-ids [migrations]
  (set (map migration-id migrations)))

(defn- all-migration-ids [migrations-path]
  (migration-ids (all-migrations migrations-path)))

(defn- applied-migration-ids [datasource]
  (set (map :version (jdbc/query datasource
                                 ["SELECT version FROM schema_migrations"]))))

(defn- pending-migration-ids [datasource migrations-path]
  (clojure.set/difference (all-migration-ids migrations-path)
                          (applied-migration-ids datasource)))

(defn- pending-migrations [datasource migrations-path]
  (filter #((pending-migration-ids datasource migrations-path) (migration-id %)) (all-migrations migrations-path)))

(defn- migration-body [migration]
  [(slurp migration)])

(defn- apply-migration [datasource migration-body]
  (try
    (jdbc/execute! datasource migration-body)
    (catch java.sql.SQLException e
      (log/error (.getMessage e))
      (throw e))))

(defn- apply-migrations [datasource migrations]
  (doall (map #(apply-migration datasource (migration-body %)) migrations)))

(defn ensure-schema-migrations-table [datasource]
  (try
    (jdbc/execute! datasource schema-migrations-table-ddl)
    (catch java.sql.SQLException _)))

(defn migrate [datasource migrations-path]
  (apply-migrations datasource (pending-migrations datasource migrations-path)))
