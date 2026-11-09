package com.team05.sarc.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// FR-01: a tabela "acionamentos". Cada objeto desta classe é uma linha da tabela.
@Entity(tableName = "acionamentos")
data class AcionamentoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Número da linha: o Room preenche sozinho.
    val mensagem: String,                             // A mensagem pronta da Sprint 04.
    val dataHora: Long,                               // Momento do envio, em milissegundos.
    val resolvido: Boolean = false                    // FR-05: começa como "não resolvido".
)

// Os comandos do banco (o "cardápio" de operações).
@Dao
interface AcionamentoDao {
    // FR-02: escreve uma linha nova. "suspend" = roda nos bastidores, sem travar a tela.
    @Insert
    suspend fun salvar(item: AcionamentoEntity)

    // FR-03: lê tudo, do mais novo para o mais antigo. O Flow avisa a tela sempre que a tabela muda.
    @Query("SELECT * FROM acionamentos ORDER BY id DESC")
    fun listarTodos(): Flow<List<AcionamentoEntity>>

    // FR-05: marca uma linha como resolvida.
    @Query("UPDATE acionamentos SET resolvido = 1 WHERE id = :id")
    suspend fun marcarResolvido(id: Int)
}

// O banco em si: um arquivo "sarc.db" guardado dentro do aparelho (é isso que cumpre o FR-04).
@Database(entities = [AcionamentoEntity::class], version = 1, exportSchema = false)
abstract class AcionamentoDatabase : RoomDatabase() {
    abstract fun dao(): AcionamentoDao

    companion object {
        private var instancia: AcionamentoDatabase? = null

        // Abre o banco uma única vez e reaproveita nas próximas chamadas.
        fun abrir(context: Context): AcionamentoDatabase = instancia
            ?: Room.databaseBuilder(context, AcionamentoDatabase::class.java, "sarc.db").build()
                .also { instancia = it }
    }
}
