(ns books.web
  (:require [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]
            [books.models.dbw :as dbw]))

(defroutes routes
  (GET "/" [] "<h2>Hello from web.</h2>")
  (GET "/add/:book" [book]
    (dbw/add-book book)
    (str "<h2>Added.</h2><br><a href='/get-book/"book"'>Show it now!</a>"))
  (GET "/get-book/:book" [book]
    (let [all_books (dbw/get-book book)]
      (str "<h2>"(first all_books)"</h2>"))))

(defn -main []
  (dbw/cr-tb-books)
  (ring/run-jetty #'routes {:port 8080 :join? false}))