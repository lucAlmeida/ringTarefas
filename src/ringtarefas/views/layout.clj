(ns ringtarefas.views.layout
    (:require [hiccup.element :refer :all]
              [hiccup.page :refer [html5 include-css include-js]]
              [hiccup.form :refer :all]))

(defn header []
  (list
    [:br]
    [:div.ui.grid.container
      [:div.row
        [:div.column
          [:div.ui.message
            [:br]
            (link-to "/" [:div.ui.large.header {:align "center"} [:i.checked.calendar.icon] "Lista de Tarefas"])
            [:br]
            [:h3 {:align "center"}
                 "Esta é uma lista de tarefas simples escrita em Clojure e
                 baseada nas bibliotecas Ring e Compojure"]
            [:div.ui.four.column.grid
              [:div.row
                [:div.column]
                [:div.column (link-to {:class "ui blue button"} "/tarefas" "Exibir tarefas")]
                [:div.column (link-to {:class "ui green right floated button"} "/tarefas/nova" "Inserir nova tarefa")]
                [:div.column]]]
            [:br]]]]]))

(defn home []
    [:div.ui.header.medium {:align "center"} 
        [:i.send.outline.icon]
        "Selecione uma das opções acima para exibir suas tarefas ou inserir uma nova"])

(defn common [& body]
  (html5
    [:head
     [:title "Lista de Tarefas"]
     (include-css "/css/screen.css")
     (include-css "/css/semantic/semantic.min.css")
     (include-js "/js/jquery-3.1.1.min.js")
     (include-js "/js/semantic.min.js")
     (include-js "/js/main.js")]
    [:body.ui.container (header) body]))

(defn home-page []
    (common (home)))

(defn formatar-data [timestamp]
  (-> "dd/MM/yyyy HH:mm:ss"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))
