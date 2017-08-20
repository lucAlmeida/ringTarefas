(ns ringtarefas.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.page :refer [html5 include-css include-js]]
            [ringtarefas.views.layout :as layout]
            [ringtarefas.views.tarefas :as vtarefas]
            [ring.util.response :as res]
            [ringtarefas.storage.db :as db]))

(defn home-page [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (layout/home-page)})

(defn exibir-tarefas [request &[mensagem]]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (vtarefas/exibir-tarefas)})

(defn exibir-tarefa [id]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (vtarefas/exibir-tarefa id)})

(defn inserir-tarefa [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (vtarefas/inserir-tarefa request)})

(defn editar-tarefa [id &[mensagem]]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (vtarefas/editar-tarefa id)})

(defn salvar-tarefa [request]
  (let [nome (get-in request [:params :nome])
        descricao (get-in request [:params :descricao])]
    (cond
      (empty? nome)
      (exibir-tarefas request "Nome da tarefa não inserido")
      :else
      (do
        (db/salvar-tarefa nome descricao)
        (res/redirect "/tarefas")))))

(defn atualizar-tarefa [request]
  (let [id (get-in request [:params :id])
        nome (get-in request [:params :nome])
        descricao (get-in request [:params :descricao])]
    (cond
      (empty? nome)
      (editar-tarefa request "Nome da tarefa não inserido")
      :else
      (do
        (db/atualizar-tarefa id nome descricao)
        (res/redirect "/tarefas")))))

(defn confirmar-exclusao-tarefa [id]
  (do
    (res/redirect (str "/tarefas/" id))
    {:status 200
    :headers {"Content-Type" "text/html"}
    :body (vtarefas/confirmar-exclusao-tarefa id)}))

(defn excluir-tarefa [id]
  (do
    (db/excluir-tarefa id)
    (res/redirect "/tarefas")))
