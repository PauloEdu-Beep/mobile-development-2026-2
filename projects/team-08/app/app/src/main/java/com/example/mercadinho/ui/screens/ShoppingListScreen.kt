package com.example.mercadinho.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mercadinho.data.PurchaseRecord
import com.example.mercadinho.data.ShoppingRepository
import com.example.mercadinho.ui.theme.MercadinhoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ShoppingItem(
    val id: Int,
    val name: String,
    val isChecked: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    onBackClick: () -> Unit,
    onNavigateToSuggestions: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val repository = remember { ShoppingRepository(context) }

    // Persistência local (Sprint 05): carregar dados salvos do disco
    var itemsList by remember { mutableStateOf(repository.loadShoppingItems()) }
    var purchasesList by remember { mutableStateOf(repository.loadPurchases()) }
    var lastPurchase by remember { mutableStateOf(repository.loadLastPurchase()) }

    // Atualiza a lista sempre que a tela for reexibida
    LaunchedEffect(Unit) {
        itemsList = repository.loadShoppingItems()
        purchasesList = repository.loadPurchases()
        lastPurchase = repository.loadLastPurchase()
    }

    var newItemName by remember { mutableStateOf("") }

    // Estado do diálogo "Minhas Compras Anteriores"
    var showPurchasesHistoryDialog by remember { mutableStateOf(false) }

    // Estado do diálogo "Limpar Carrinho"
    var showClearCartDialog by remember { mutableStateOf(false) }

    // Estado do formulário de finalização da compra (Sprint 04)
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var marketName by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var marketNameError by remember { mutableStateOf<String?>(null) }
    var totalAmountError by remember { mutableStateOf<String?>(null) }
    var checkoutSuccessMessage by remember { mutableStateOf<String?>(null) }

    val addItem = {
        val trimmed = newItemName.trim()
        if (trimmed.isNotEmpty()) {
            val newId = (itemsList.maxOfOrNull { it.id } ?: 0) + 1
            val updated = itemsList + ShoppingItem(
                id = newId,
                name = trimmed,
                isChecked = false
            )
            itemsList = updated
            repository.saveShoppingItems(updated)
            newItemName = ""
        }
    }

    val deleteItem: (Int) -> Unit = { itemId ->
        val updated = itemsList.filter { it.id != itemId }
        itemsList = updated
        repository.saveShoppingItems(updated)
    }

    val toggleItem: (Int) -> Unit = { itemId ->
        val updated = itemsList.map {
            if (it.id == itemId) it.copy(isChecked = !it.isChecked) else it
        }
        itemsList = updated
        repository.saveShoppingItems(updated)
    }

    val pendingCount = itemsList.count { !it.isChecked }

    // Diálogo "Minhas Compras" (Histórico completo de compras com itens e valores)
    if (showPurchasesHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showPurchasesHistoryDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text(
                    text = "Minhas Compras",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                ) {
                    if (purchasesList.isEmpty()) {
                        Text(
                            text = "Nenhuma compra registrada anteriormente.\nFinalize suas compras para salvar o histórico com os itens e valores!",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 24.dp)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(purchasesList, key = { it.id }) { purchase ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = purchase.marketName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = purchase.date,
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }

                                        if (purchase.amount.isNotEmpty()) {
                                            Text(
                                                text = "Valor Total: R$ ${purchase.amount}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                        }

                                        HorizontalDivider(
                                            modifier = Modifier.padding(vertical = 6.dp),
                                            color = DividerDefaults.color.copy(alpha = 0.5f)
                                        )

                                        Text(
                                            text = "Itens comprados:",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )

                                        Text(
                                            text = if (purchase.itemsSummary.isNotEmpty()) purchase.itemsSummary else "Itens gerais da lista",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showPurchasesHistoryDialog = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Fechar")
                }
            }
        )
    }

    // Diálogo de Confirmação para Limpar Carrinho
    if (showClearCartDialog) {
        AlertDialog(
            onDismissRequest = { showClearCartDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.DeleteSweep,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            },
            title = {
                Text(
                    text = "Limpar Carrinho?",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Tem certeza que deseja remover todos os ${itemsList.size} itens da sua lista de compras? Essa ação não pode ser desfeita.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        itemsList = emptyList()
                        repository.clearShoppingItems()
                        showClearCartDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sim, Limpar Tudo")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showClearCartDialog = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de Formulário com Validação para Finalizar Compra
    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Storefront,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text(
                    text = "Finalizar Compra",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Informe o nome do supermercado para registrar suas compras realizadas:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo 1: Nome do Supermercado (Obrigatório, mín. 3 letras)
                    OutlinedTextField(
                        value = marketName,
                        onValueChange = {
                            marketName = it
                            if (marketNameError != null) marketNameError = null
                        },
                        label = { Text("Nome do Supermercado *") },
                        placeholder = { Text("Ex: Supermercado Big Mais") },
                        isError = marketNameError != null,
                        supportingText = {
                            if (marketNameError != null) {
                                Text(
                                    text = marketNameError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo 2: Valor Total Gasto (Opcional, formato numérico decimal)
                    OutlinedTextField(
                        value = totalAmount,
                        onValueChange = {
                            totalAmount = it
                            if (totalAmountError != null) totalAmountError = null
                        },
                        label = { Text("Valor Gasto (R$) - Opcional") },
                        placeholder = { Text("Ex: 149.90") },
                        isError = totalAmountError != null,
                        supportingText = {
                            if (totalAmountError != null) {
                                Text(
                                    text = totalAmountError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        var hasError = false
                        val trimmedMarket = marketName.trim()
                        if (trimmedMarket.length < 3) {
                            marketNameError = "Informe o nome do supermercado (mínimo 3 caracteres)"
                            hasError = true
                        }

                        val trimmedAmount = totalAmount.trim()
                        if (trimmedAmount.isNotEmpty()) {
                            val parsed = trimmedAmount.replace(",", ".").toDoubleOrNull()
                            if (parsed == null || parsed < 0) {
                                totalAmountError = "Informe um valor numérico válido (ex: 150.50)"
                                hasError = true
                            }
                        }

                        if (!hasError) {
                            showCheckoutDialog = false
                            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                            // Monta o resumo dos itens comprados
                            val checkedItems = itemsList.filter { it.isChecked }
                            val summary = if (checkedItems.isNotEmpty()) {
                                checkedItems.joinToString(", ") { it.name }
                            } else {
                                itemsList.joinToString(", ") { it.name }
                            }

                            val newRecord = PurchaseRecord(
                                marketName = trimmedMarket,
                                amount = trimmedAmount,
                                date = currentDate,
                                itemsSummary = summary
                            )

                            // Salva a compra no histórico persistente (Sprint 05)
                            repository.savePurchase(newRecord)
                            purchasesList = repository.loadPurchases()
                            lastPurchase = newRecord

                            // Reinicia a lista desmarcando os itens e salva no disco
                            val updated = itemsList.map { it.copy(isChecked = false) }
                            itemsList = updated
                            repository.saveShoppingItems(updated)

                            checkoutSuccessMessage = "Compra no '$trimmedMarket' finalizada com sucesso! Nova lista criada."
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Confirmar Compra")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showCheckoutDialog = false },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Lista de Compras",
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar para tela inicial"
                        )
                    }
                },
                actions = {
                    // Botão "Sugestões de Receitas" (Integração REST API - Sprint 06)
                    IconButton(onClick = onNavigateToSuggestions) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = "Sugestões de Receitas",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botão "Minhas Compras" no topo
                    IconButton(onClick = { showPurchasesHistoryDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                            contentDescription = "Minhas Compras",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botão "Limpar Carrinho"
                    if (itemsList.isNotEmpty()) {
                        IconButton(onClick = { showClearCartDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Limpar Carrinho",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        bottomBar = {
            // Barra inferior com botões de Ação
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    // Botão de Minhas Compras
                    OutlinedButton(
                        onClick = { showPurchasesHistoryDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Minhas Compras (${purchasesList.size})",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botão de Finalizar Compra
                    Button(
                        onClick = {
                            marketName = ""
                            totalAmount = ""
                            marketNameError = null
                            totalAmountError = null
                            showCheckoutDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Finalizar Compra",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Card de Histórico da Última Compra (Persistência Sprint 05)
            lastPurchase?.let { purchase ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { showPurchasesHistoryDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Última compra salva (toque para ver todas):",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                            )
                            val valorTexto = if (purchase.amount.isNotEmpty()) " • R$ ${purchase.amount}" else ""
                            Text(
                                text = "${purchase.marketName}$valorTexto • ${purchase.date}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Banner de sucesso ao finalizar compra
            checkoutSuccessMessage?.let { msg ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = msg,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        IconButton(
                            onClick = { checkoutSuccessMessage = null },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar aviso",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Seção de Adicionar Item
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newItemName,
                    onValueChange = { newItemName = it },
                    placeholder = { Text("Ex: Detergente, Batata...") },
                    label = { Text("Adicionar item") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { addItem() })
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = addItem,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resumo de contagem
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Itens da Lista (${itemsList.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$pendingCount pendente(s)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (itemsList.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "•", color = MaterialTheme.colorScheme.outline)
                        TextButton(
                            onClick = { showClearCartDialog = true }
                        ) {
                            Text(
                                text = "Limpar",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Lista de Itens
            if (itemsList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Sua lista está vazia!\nAdicione novos itens acima para não esquecer nada no mercado.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(itemsList, key = { it.id }) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { toggleItem(item.id) },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (item.isChecked)
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                else
                                    MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = item.isChecked,
                                    onCheckedChange = { toggleItem(item.id) }
                                )

                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = if (item.isChecked) FontWeight.Normal else FontWeight.Medium,
                                    textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                    color = if (item.isChecked)
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    else
                                        MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 6.dp)
                                )

                                IconButton(
                                    onClick = { deleteItem(item.id) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Apagar item",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    MercadinhoTheme {
        ShoppingListScreen(onBackClick = {})
    }
}
