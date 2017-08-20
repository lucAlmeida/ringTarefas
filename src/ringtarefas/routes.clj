(ns ringtarefas.routes
    (:require [compojure.core :refer :all]
              [compojure.route :as route]
              [ringtarefas.handler :as handler]
              [ring.middleware.content-type :refer [wrap-content-type]]
              [ring.middleware.params :refer [wrap-params]]))

(defn tarefa-routes [id]
  (routes
    (GET "/" [] (handler/exibir-tarefa id))
    (PUT "/" [] handler/atualizar-tarefa)
    (GET "/editar" [] (handler/editar-tarefa id))
    (GET "/excluir" [] (handler/confirmar-exclusao-tarefa id))
    (DELETE "/" [] (handler/excluir-tarefa id))))

(defn tarefas-routes []
  (routes
    (GET "/" [] handler/exibir-tarefas)
    (GET "/nova" [] handler/inserir-tarefa)
    (POST "/nova" [] (wrap-params handler/salvar-tarefa))))

(defroutes home-routes
  (GET "/" [] handler/home-page)
  (context "/tarefas" []
    (tarefas-routes)
      (context "/:id" [id]
        (tarefa-routes id))))

(defroutes app-routes
    home-routes
    (route/resources "/")
    (route/not-found "Not Found"))
