package id.herdroid.ecommercemandiri.view.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.herdroid.ecommercemandiri.domain.model.Product
import id.herdroid.ecommercemandiri.domain.usecase.ProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productUseCase: ProductUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    fun loadProducts() {
        viewModelScope.launch {
            try {
                _products.value = productUseCase.getAllProducts()
                _categories.value = productUseCase.getCategories()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "loadProducts: ${e.localizedMessage}")
            }
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            try {
                _products.value = if (category.equals("All", true)) {
                    productUseCase.getAllProducts()
                } else {
                    productUseCase.getProductsByCategory(category)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "categoryError: ${e.localizedMessage}")
            }
        }
    }

}
