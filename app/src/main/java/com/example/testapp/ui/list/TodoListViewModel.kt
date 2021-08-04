package com.example.testapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.db.TodoDataBaseRepository
import com.example.testapp.model.TodoListData
import com.example.testapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListRepository: TodoListRepository,
    private val dataBaseRepository: TodoDataBaseRepository
) : ViewModel() {

    private val _todoListData = MutableLiveData<Resource<List<TodoListData>>>()

    val todoListData: LiveData<Resource<List<TodoListData>>>
        get() = _todoListData

    private val _todoListDataFromDB = MutableLiveData<List<TodoListData>>()

    val todoListDataFromDB: LiveData<List<TodoListData>>
        get() = _todoListDataFromDB


    fun getTodoLists() {
        viewModelScope.launch {
            _todoListData.postValue(Resource.loading(null))
            try {
                val response = todoListRepository.getTodosList()
                dataBaseRepository.insertAll(response.body()?.todoListData)
                _todoListData.postValue(Resource.success(response.body()?.todoListData))
            } catch (e: Exception) {
                _todoListData.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getTodoListsFromDb() = viewModelScope.launch {
        dataBaseRepository.getAll().let {
            _todoListDataFromDB.postValue(it)
        }
    }
}