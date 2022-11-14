package com.example.inventory

import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class InventoryViewModel(private val itemDao: ItemDao): ViewModel() {
    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    private fun insertItem(item: Item){
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }
    /**
     * Returns an instance of the [Item] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item{
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    /**
     * Inserts the new Item into database.
     */
    fun addNewItem(itemName: String, itemPrice: String, itemCount: String){
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }
}

class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("UCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}