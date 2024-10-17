package com.example.myapplication.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.YuGiOhRepository
import com.example.myapplication.ui.model.ItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class YuGiOhViewModel : ViewModel() {
    private val yuGiOhRepository: YuGiOhRepository by lazy { YuGiOhRepository() }
    private val _card: Flow<List<ItemUi>>
        get() = yuGiOhRepository.selectAll().map { yuGiOhObjects ->
            yuGiOhObjects.groupBy { yuGiOhObject ->
                yuGiOhObject.type
            }.flatMap {
                buildList {
                    add(
                        ItemUi.Header(
                            type = it.key,
                        )
                    )
                    addAll(it.value)
                    add(
                        ItemUi.Footer(
                            numberOfElements = it.value.size,
                        )
                    )
                }
            }
        }
    val card = _card
    fun insertNewCard() {
        viewModelScope.launch(Dispatchers.IO) {
            yuGiOhRepository.fetchData()
        }
    }
    fun deleteAllCards() {
        viewModelScope.launch(Dispatchers.IO) {
            yuGiOhRepository.deleteAll()
        }
    }
}
