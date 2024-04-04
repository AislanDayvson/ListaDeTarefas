package com.aislandayvson.applistatarefas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aislandayvson.applistatarefas.adapter.TarefaAdapter
import com.aislandayvson.applistatarefas.database.TarefaDAO
import com.aislandayvson.applistatarefas.databinding.ActivityMainBinding
import com.aislandayvson.applistatarefas.model.Tarefa

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var listaTarefas = emptyList<Tarefa>()
    private var tarefaAdapter: TarefaAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabAdicionar.setOnClickListener {
            val intent = Intent(this, AdicionarTarefaActivity::class.java)
            startActivity(intent)
        }

        tarefaAdapter = TarefaAdapter(
            { id -> confirmarExclusao(id)},
            { tarefa -> editar(tarefa) }
        )
        tarefaAdapter?.adicionarLista(listaTarefas)
        binding.rvTarefas.adapter = tarefaAdapter

        binding.rvTarefas.layoutManager = LinearLayoutManager(this)

    }

    private fun editar(tarefa: Tarefa) {
        val intent = Intent(this, AdicionarTarefaActivity::class.java)
        intent.putExtra("tarefa", tarefa)
        startActivity(intent)

    }

    private fun confirmarExclusao(id: Int) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Confirmar exclusão")
        alertBuilder.setMessage("Deseja realmente excluir essa tarefa?")

        alertBuilder.setPositiveButton("Sim"){ _, _ ->
            val tarefaDAO = TarefaDAO(this)
            if ( tarefaDAO.remover(id)){
                atualizarListaTarefas()
                Toast.makeText(this, "Tarefa deletada", Toast.LENGTH_SHORT).show()
            }
        }

        alertBuilder.setNegativeButton("Não"){ _, _ -> }

        alertBuilder.create().show()
    }

    private fun atualizarListaTarefas(){
        val tarefaDAO = TarefaDAO(this )
        listaTarefas = tarefaDAO.listar()
        tarefaAdapter?.adicionarLista(listaTarefas)
    }

    override fun onStart() {
        super.onStart()
        atualizarListaTarefas()
    }
}