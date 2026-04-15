package com.anotasmart.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.anotasmart.model.CategoryType
import com.anotasmart.model.ItemType
import com.anotasmart.model.UnitType
import com.anotasmart.model.SaleStatus
import com.anotasmart.model.InstallmentStatus
import com.anotasmart.model.PaymentMethod

@Entity(tableName = "Category")
data class Category(
    @PrimaryKey val id: String,
    val nome: String,
    val tipo: CategoryType
)

@Entity(
    tableName = "Product",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class Product(
    @PrimaryKey val id: String,
    val categoryId: String?,
    val nome: String,
    val precoCusto: Double,
    val precoVenda: Double,
    val unidadeMedida: UnitType,
    val tipoItem: ItemType,
    val quantidadeEstoque: Double,
    val imagePath: String?
)

@Entity(tableName = "Client")
data class Client(
    @PrimaryKey val id: String,
    val nome: String,
    val telefone: String,
    val endereco: String?,
    val imagePath: String?
)

@Entity(
    tableName = "Sale",
    foreignKeys = [
        ForeignKey(
            entity = Client::class,
            parentColumns = ["id"],
            childColumns = ["clientId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index(value = ["clientId"])]
)
data class Sale(
    @PrimaryKey val id: String,
    val clientId: String?,
    val dataVenda: Long,
    val status: SaleStatus,
    val valorTotal: Double
)

@Entity(
    tableName = "SaleItem",
    foreignKeys = [
        ForeignKey(
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["saleId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["saleId"]),
        Index(value = ["productId"])
    ]
)
data class SaleItem(
    @PrimaryKey val id: String,
    val saleId: String,
    val productId: String?,
    val nomeCustomizado: String?,
    val quantidade: Double,
    val custoUnitarioNoAto: Double,
    val precoVendaNoAto: Double
)

@Entity(
    tableName = "Installment",
    foreignKeys = [
        ForeignKey(
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["saleId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["saleId"])]
)
data class Installment(
    @PrimaryKey val id: String,
    val saleId: String,
    val numeroParcela: Int,
    val valor: Double,
    val dataVencimento: Long,
    val dataPagamento: Long?,
    val statusParcela: InstallmentStatus,
    val metodoPagamento: PaymentMethod?
)
