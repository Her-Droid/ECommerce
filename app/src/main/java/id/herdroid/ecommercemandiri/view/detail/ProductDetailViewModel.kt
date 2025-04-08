package id.herdroid.ecommercemandiri.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommercemandiri.data.db.CartEntity
import id.herdroid.ecommercemandiri.data.repository.CartRepository
import id.herdroid.ecommercemandiri.domain.model.Product
import id.herdroid.ecommercemandiri.domain.usecase.ProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    fun loadProductDetail(id: Int) {
        viewModelScope.launch {
            try {
                val detail = productUseCase.getProductDetail(id)
                _product.value = detail
            } catch (e: Exception) {

            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val item = CartEntity(
                id = product.id,
                title = product.title,
                price = product.price,
                image = product.image
            )
            cartRepository.addItem(item)
        }
    }
}
