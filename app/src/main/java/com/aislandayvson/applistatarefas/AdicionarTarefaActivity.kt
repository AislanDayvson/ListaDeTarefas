package com.aislandayvson.applistatarefas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aislandayvson.applistatarefas.database.TarefaDAO
import com.aislandayvson.applistatarefas.databinding.ActivityAdicionarTarefaBinding
import com.aislandayvson.applistatarefas.databinding.ActivityMainBinding
import com.aislandayvson.applistatarefas.model.Tarefa

class AdicionarTarefaActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAdicionarTarefaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var tarefa: Tarefa? = null
        val bundle = intent.extras
        if (bundle != null){
            tarefa = bundle.getSerializable("tarefa") as Tarefa
            binding.editTarefa.setText(tarefa.descricao)
        }

        binding.btnSalvar.setOnClickListener {
            if(binding.editTarefa.text.isNotEmpty()){
                if (tarefa != null){
                    editar(tarefa)
                }else{
                    salvar()
                }
            }else{
                Toast.makeText(this, "Preencha uma tarefa", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun editar(tarefa: Tarefa) {
        val descricaoTarefa = binding.editTarefa.text.toString()
        val tarefaAtualizar = Tarefa(tarefa.idTarefa, descricaoTarefa, "default")

        val tarefaDAO = TarefaDAO(this)
        tarefaDAO.atualizar(tarefaAtualizar)

        if (tarefaDAO.atualizar(tarefaAtualizar)){
                Toast.makeText(this, "Tarefa atualizada com sucesso", Toast.LENGTH_SHORT).show()
                finish()
        }
    }

    private fun salvar() {
        val descricaoTarefa = binding.editTarefa.text.toString()
        val tarefa = Tarefa(
            -1, descricaoTarefa, "default"
        )

        val tarefaDAO = TarefaDAO(this)
        if (tarefaDAO.salvar(tarefa)) {
            Toast.makeText(this, "Tarefa criada com sucesso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}