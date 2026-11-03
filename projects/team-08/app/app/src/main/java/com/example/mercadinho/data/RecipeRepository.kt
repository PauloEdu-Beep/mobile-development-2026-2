package com.example.mercadinho.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class RecipeItem(
    val id: String,
    val title: String,
    val category: String,
    val area: String,
    val imageUrl: String,
    val ingredients: List<String>
)

class RecipeRepository(private val context: Context) {

    companion object {
        // Endpoint REST público de receitas brasileiras no GitHub
        private const val BRAZILIAN_RECIPES_URL =
            "https://raw.githubusercontent.com/itimt/mobile-development-2026-2/team-08/sprint-06/projects/team-08/receitas_brasileiras.json"
    }

    suspend fun fetchRecipes(
        categoryFilter: String = "todas",
        simulateError: Boolean = false
    ): Result<List<RecipeItem>> {
        return withContext(Dispatchers.IO) {
            if (simulateError) {
                return@withContext Result.failure(
                    Exception("Falha na conexão com o servidor de receitas brasileiras (Erro 503 / Sem Internet).")
                )
            }

            try {
                var jsonContent: String? = null

                // 1. Tenta buscar da API REST externa na internet
                try {
                    val url = URL(BRAZILIAN_RECIPES_URL)
                    val connection = (url.openConnection() as HttpURLConnection).apply {
                        requestMethod = "GET"
                        connectTimeout = 4000
                        readTimeout = 4000
                        setRequestProperty("Accept", "application/json")
                    }

                    if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val reader = BufferedReader(InputStreamReader(connection.inputStream))
                        jsonContent = reader.readText()
                        reader.close()
                    }
                    connection.disconnect()
                } catch (netEx: Exception) {
                    // Se a branch ainda não tiver sido enviada para o GitHub remoto ou sem internet,
                    // usa os dados do catálogo brasileiro embarcado
                }

                // 2. Se a requisição online ainda não estiver no ar, lê o catálogo brasileiro dos assets
                if (jsonContent.isNullOrBlank()) {
                    jsonContent = context.assets.open("receitas_brasileiras.json")
                        .bufferedReader()
                        .use { it.readText() }
                }

                val jsonObject = JSONObject(jsonContent)
                val receitasArray = jsonObject.getJSONArray("receitas")
                val list = mutableListOf<RecipeItem>()

                for (i in 0 until receitasArray.length()) {
                    val item = receitasArray.getJSONObject(i)
                    val cat = item.getString("categoria")

                    if (categoryFilter != "todas" && !cat.equals(categoryFilter, ignoreCase = true)) {
                        continue
                    }

                    val ingredientsJson = item.getJSONArray("ingredientes")
                    val ingredientsList = mutableListOf<String>()
                    for (j in 0 until ingredientsJson.length()) {
                        ingredientsList.add(ingredientsJson.getString(j))
                    }

                    list.add(
                        RecipeItem(
                            id = item.getString("id"),
                            title = item.getString("titulo"),
                            category = formatCategory(cat),
                            area = item.getString("origem"),
                            imageUrl = item.getString("imagemUrl"),
                            ingredients = ingredientsList
                        )
                    )
                }

                Result.success(list)
            } catch (e: Exception) {
                Result.failure(
                    Exception(e.localizedMessage ?: "Erro ao carregar receitas brasileiras.")
                )
            }
        }
    }

    private fun formatCategory(raw: String): String {
        return when (raw.lowercase()) {
            "massas" -> "Massas"
            "tipicos" -> "Pratos Típicos"
            "carnes" -> "Carnes & Aves"
            "sobremesas" -> "Sobremesas"
            "lanches" -> "Lanches"
            else -> raw.replaceFirstChar { it.uppercase() }
        }
    }
}
