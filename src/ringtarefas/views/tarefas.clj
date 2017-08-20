(ns ringtarefas.views.tarefas
    (:require [hiccup.element :refer :all]
              [hiccup.form :refer :all]
              [ringtarefas.views.layout :as layout]
              [ringtarefas.storage.db :as db]))

(defn exibir-tarefas [& [mensagem]]
  (layout/common 
    (list
        [:table.tarefas.ui.striped.table
        [:thead
            [:tr
            [:th "ID"]
            [:th "Nome"]
            [:th "Descrição"]
            [:th "Última Alteração"]
            [:th "Informações"]
            [:th "Alterar"]
            [:th "Excluir"]]]
        [:tbody
        (for [{:keys [id nome descricao timestamp]} (db/ler-tarefas)]
            [:tr
            [:td id]
            [:td nome]
            [:td descricao]
            [:td [:time (layout/formatar-data timestamp)]]
            [:td [:div (link-to (str "/tarefas/" id) "+Info")]]
            [:td [:div (link-to (str "/tarefas/" id "/editar") "Editar")]]
            [:td [:div (link-to (str "/tarefas/" id "/excluir") "Excluir")]]])]]
        [:br] [:br])))

(defn exibir-tarefa [id &[optional]]
    (layout/common
      [:table.ui.definition.table
        [:thead
          [:tr
            [:th]
            [:th "Valor da propriedade"]]]
        [:tbody
          (for [[key val] (db/obter-tarefa id)]
            (list
              [:tr
                [:td [:p key]]
                [:td [:p val]]]))]] optional))

(defn editar-tarefa [id & [mensagem]]
    (layout/common
        (let [tarefa (db/obter-tarefa id)]
            (form-to {:class "ui form"} [:put (str "/tarefas/" id)]
            [:div.field [:p "Nome:"] (text-field "nome" (:nome tarefa))]
            [:div.field [:p "Descrição:"] (text-area "descricao" (:descricao tarefa))]
            [:br]
            (submit-button {:class "ui button"} "Editar")))))

(defn confirmar-exclusao-tarefa [id]
    (exibir-tarefa id
        (list
        [:button#excluir.ui.button.red "Excluir tarefa"]
        [:div.ui.tiny.test.modal.transition
            [:div.header "Excluir tarefa"]
            [:div.content "Você tem certeza de que desejas excluir a tarefa?"]
            [:div.actions.horizontal
            [:div.ui.negative.left.floated.button "Não"]
            (form-to [:delete (str "/tarefas/" id)]
                (hidden-field "id" id)
                [:button#excluir-botao.ui.positive.right.labeled.icon.button
                        {:type "submit"} "Sim" [:i.checkmark.icon]])]])))

(defn inserir-tarefa [& [id]]
    (layout/common
        (form-to {:class "ui form"} [:post "/tarefas/nova"]
        [:div.field [:p "Nome:"] (text-field "nome" "")]
        [:div.field [:p "Descrição:"] (text-area "descricao" "")]
        [:br]
        (submit-button {:class "ui button"} "Gravar"))))
