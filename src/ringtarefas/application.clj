(ns ringtarefas.application
    (:require [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
              [ringtarefas.routes :refer [app-routes]]
              [ringtarefas.storage.db :as db]
              [compojure.handler :as chandler]
              [hiccup.middleware :refer [wrap-base-url]]))

(defn init []
  (println "Lista de tarefas está iniciando...")
  (if-not (.exists (java.io.File. "./db.sq3"))
    (do
      (db/criar-tabela-tarefas))))

(defn destroy []
  (println "Lista de tarefas está terminando..."))

(def app
  (-> app-routes 
      (chandler/site)
      (wrap-base-url)))
