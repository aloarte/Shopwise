package com.aloarte.shopwise.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aloarte.shopwise.domain.ShopwiseProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ShopwiseProductsRepository) : ViewModel() {

    fun fetchItems() {
        viewModelScope.launch {
            val data = repository.fetchProducts()
        }
    }
}