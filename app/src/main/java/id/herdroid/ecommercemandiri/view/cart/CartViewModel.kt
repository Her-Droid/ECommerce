package id.herdroid.ecommercemandiri.view.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommercemandiri.data.db.CartEntity
import id.herdroid.ecommercemandiri.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {

    private val _cart = MutableStateFlow<List<CartEntity>>(emptyList())
    val cart: StateFlow<List<CartEntity>> = _cart

    init {
        viewModelScope.launch {
            repository.getCartItems().collectLatest {
                _cart.value = it
            }
        }
    }

    fun increaseQuantity(item: CartEntity) {
        viewModelScope.launch {
            val updatedItem = item.copy(quantity = item.quantity + 1)
            repository.updateCartItem(updatedItem)
        }
    }

    fun decreaseQuantity(item: CartEntity) {
        if (item.quantity > 1) {
            viewModelScope.launch {
                val updatedItem = item.copy(quantity = item.quantity - 1)
                repository.updateCartItem(updatedItem)
            }
        }
    }



    fun removeFromCart(item: CartEntity) {
        viewModelScope.launch {
            repository.removeItem(item)
        }
    }



    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

}