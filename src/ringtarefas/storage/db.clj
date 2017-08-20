(ns ringtarefas.storage.db
    (:require [clojure.java.jdbc :as sql])
    (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "db.sq3"})

(defn criar-tabela-tarefas []
    (sql/with-connection
        db
        (sql/create-table
            :tarefas
            [:id "INTEGER PRIMARY KEY AUTOINCREMENT"]
            [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"]
            [:nome "TEXT"]
            [:descricao "TEXT"])
        (sql/do-commands "CREATE INDEX timestamp_index ON tarefas (timestamp)")))

(defn ler-tarefas []
    (sql/with-connection
     db
     (sql/with-query-results res
        ["SELECT * FROM tarefas ORDER BY timestamp DESC"]
        (doall res))))

(defn salvar-tarefa [nome descricao]
    (sql/with-connection
        db
        (sql/insert-values
            :tarefas
            [:nome :descricao :timestamp]
            [nome descricao (new java.util.Date)])))

(defn obter-tarefa [id]
    (sql/with-connection db
        (sql/with-query-results
            res ["select * from tarefas where id = ?" id] (first res))))

(defn atualizar-tarefa [id nome descricao]
    (sql/with-connection db
        (sql/update-values :tarefas ["id=?" id]
            {:nome nome 
             :descricao descricao 
             :timestamp (new java.util.Date)})))

(defn excluir-tarefa [id]
    (sql/with-connection db
        (sql/delete-rows :tarefas ["id=?" id])))
