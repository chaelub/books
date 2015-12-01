(ns books.models.dbw
  (:require [clojure.java.jdbc :as sql]))

(def spec "postgresql://localhost:5432/books?user=vadim&password=olesya")

(defn books []
  (into [] (sql/query spec ["select * from books by id desc"])))

(defn get-book [book]
  (into [] (sql/query spec [(str "select * from books where book='" book "'")])))

(defn add-book [book]
  (sql/insert! spec :books [:book] [book]))

(defn created? []
  (-> (sql/query spec
    ["select count(*) from information_schema.tables where table_name='books'"])
    first :count pos?))

(defn cr-tb-books []
  (when (not (created?))
    (print "Creating...")
    (sql/db-do-commands spec
      (sql/create-table-ddl
        :books
        [:id :serial "PRIMARY KEY"]
        [:book :varchar "NOT NULL"]
        [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))
    (print "\nDone!")))