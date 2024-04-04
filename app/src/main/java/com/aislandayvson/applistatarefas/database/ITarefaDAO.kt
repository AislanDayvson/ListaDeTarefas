package com.aislandayvson.applistatarefas.database

import com.aislandayvson.applistatarefas.model.Tarefa

interface ITarefaDAO {

    fun salvar(tarefa: Tarefa): Boolean
    fun atualizar(tarefa: Tarefa): Boolean
    fun remover(idTarefa: Int): Boolean
    fun listar(): List<Tarefa>

}