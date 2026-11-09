package com.example.mercadinho.data

import android.content.Context
import android.content.SharedPreferences
import com.example.mercadinho.ui.screens.ShoppingItem
import org.json.JSONArray
import org.json.JSONObject

data class PurchaseRecord(
    val id: String = java.util.UUID.randomUUID().toString(),
    val marketName: String,
    val amount: String,
    val date: String,
    val itemsSummary: String
)

class ShoppingRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("mercadinho_storage", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ITEMS = "key_shopping_items"
        private const val KEY_PURCHASES = "key_purchases_history"
    }

    fun loadShoppingItems(): List<ShoppingItem> {
        val jsonString = prefs.getString(KEY_ITEMS, null)
        if (jsonString.isNullOrEmpty()) {
            val defaultItems = listOf(
                ShoppingItem(1, "Arroz 5kg", false),
                ShoppingItem(2, "Feijão Preto 1kg", false),
                ShoppingItem(3, "Café Torrado", true),
                ShoppingItem(4, "Leite Integral", false),
                ShoppingItem(5, "Pão Francês", false),
                ShoppingItem(6, "Maçã Gala", false),
                ShoppingItem(7, "Sabão em Pó", false)
            )
            saveShoppingItems(defaultItems)
            return defaultItems
        }

        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<ShoppingItem>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    ShoppingItem(
                        id = obj.getInt("id"),
                        name = obj.getString("name"),
                        isChecked = obj.getBoolean("isChecked")
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun saveShoppingItems(items: List<ShoppingItem>) {
        val jsonArray = JSONArray()
        for (item in items) {
            val obj = JSONObject().apply {
                put("id", item.id)
                put("name", item.name)
                put("isChecked", item.isChecked)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_ITEMS, jsonArray.toString()).apply()
    }

    fun clearShoppingItems() {
        saveShoppingItems(emptyList())
    }

    fun savePurchase(purchase: PurchaseRecord) {
        val current = loadPurchases().toMutableList()
        current.add(0, purchase)
        val jsonArray = JSONArray()
        for (p in current) {
            val obj = JSONObject().apply {
                put("id", p.id)
                put("marketName", p.marketName)
                put("amount", p.amount)
                put("date", p.date)
                put("itemsSummary", p.itemsSummary)
            }
            jsonArray.put(obj)
        }
        prefs.edit().putString(KEY_PURCHASES, jsonArray.toString()).apply()
    }

    fun loadPurchases(): List<PurchaseRecord> {
        val jsonString = prefs.getString(KEY_PURCHASES, null)
        if (jsonString.isNullOrEmpty()) {
            return emptyList()
        }
        return try {
            val jsonArray = JSONArray(jsonString)
            val list = mutableListOf<PurchaseRecord>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                list.add(
                    PurchaseRecord(
                        id = obj.optString("id", i.toString()),
                        marketName = obj.getString("marketName"),
                        amount = obj.optString("amount", ""),
                        date = obj.getString("date"),
                        itemsSummary = obj.optString("itemsSummary", "")
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun loadLastPurchase(): PurchaseRecord? {
        return loadPurchases().firstOrNull()
    }

    fun addItemsFromRecipe(ingredientNames: List<String>): Int {
        val current = loadShoppingItems().toMutableList()
        var maxId = current.maxOfOrNull { it.id } ?: 0
        var addedCount = 0
        for (name in ingredientNames) {
            val trimmed = name.trim()
            if (trimmed.isNotBlank() && current.none { it.name.equals(trimmed, ignoreCase = true) }) {
                maxId++
                current.add(ShoppingItem(maxId, trimmed, false))
                addedCount++
            }
        }
        if (addedCount > 0) {
            saveShoppingItems(current)
        }
        return addedCount
    }
}
