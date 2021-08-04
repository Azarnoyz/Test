package com.example.testapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.testapp.db.TodoDataBaseRepository
import com.example.testapp.model.TodoListData
import com.example.testapp.model.TodoModelResponse
import com.example.testapp.ui.list.TodoListRepository
import com.example.testapp.ui.list.TodoListViewModel
import com.example.testapp.utils.Resource
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TodoListViewModelTest {

    companion object {
        const val ERROR_MESSAGE = "error"
    }

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: TodoListViewModel

    @Mock
    private lateinit var dataBaseRepository: TodoDataBaseRepository

    @Mock
    private lateinit var todoListRepository: TodoListRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<TodoListData>>>

    @Before
    fun setUp() {
        viewModel = TodoListViewModel(
            todoListRepository,
            dataBaseRepository
        )
    }

    @Test
    fun getTodoList_Success() {
        testCoroutineRule.runBlockingTest {
            given(todoListRepository.getTodosList()).willReturn(
                Response.success(
                    TodoModelResponse(
                        emptyList()
                    )
                )
            )

            viewModel.getTodoLists()
            viewModel.todoListData.observeForever(apiUsersObserver)
            verify(todoListRepository).getTodosList()
            verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.todoListData.removeObserver(apiUsersObserver)

        }

    }

    @Test
    fun getTodoList_Error() {
        testCoroutineRule.runBlockingTest {

            doThrow(RuntimeException(ERROR_MESSAGE))
                .`when`(todoListRepository)
                .getTodosList()

            viewModel.getTodoLists()
            viewModel.todoListData.observeForever(apiUsersObserver)
            verify(todoListRepository).getTodosList()
            verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(ERROR_MESSAGE).toString(),
                    null
                )
            )
            viewModel.todoListData.removeObserver(apiUsersObserver)

        }

    }

}