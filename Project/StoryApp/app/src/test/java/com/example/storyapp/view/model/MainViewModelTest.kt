package com.example.storyapp.view.model

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.storyapp.view.adapter.DetailAdapter
import com.example.storyapp.view.data.*
import com.example.storyapp.view.network.DetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private lateinit var sharedPreferences: SharedPreferences

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var detailRepository: DetailRepository


    @Test
    fun `when getDetail should not null and return data`() = runTest {
        val dummyData = DataDummy.generateDummyDetail()
        val data: PagingData<DetailResponse> = DetailPagingSource.snapshot(dummyData)
        val exepected = MutableLiveData<PagingData<DetailResponse>>()
        exepected.value = data

        sharedPreferences = Mockito.mock(SharedPreferences::class.java)

        Mockito.`when`(detailRepository.getDetail()).thenReturn(exepected)
        val mainViewModel = MainViewModel(sharedPreferences,detailRepository)
        val actualDetile: PagingData<DetailResponse> = mainViewModel.detail.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = DetailAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualDetile)

        assertNotNull(differ.snapshot())
        assertEquals(dummyData.size, differ.snapshot().size)
        assertEquals(dummyData[0], differ.snapshot()[0])
    }

    @Test
    fun `when empty should return no Data`() = runTest {
        val data : PagingData<DetailResponse> = PagingData.from(emptyList())
        val exepected = MutableLiveData<PagingData<DetailResponse>>()
        exepected.value = data
        Mockito.`when`(detailRepository.getDetail()).thenReturn(exepected)

        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        val mainViewModel = MainViewModel(sharedPreferences,detailRepository)
        val actualDetile: PagingData<DetailResponse> = mainViewModel.detail.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = DetailAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualDetile)

        assertEquals(0, differ.snapshot().size)
    }


    val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    class DetailPagingSource : PagingSource<Int, LiveData<List<DetailResponse>>>() {
        companion object {
            fun snapshot(items: List<DetailResponse>): PagingData<DetailResponse> {
                return PagingData.from(items)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, LiveData<List<DetailResponse>>>): Int {
            return 0
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<DetailResponse>>> {
            return LoadResult.Page(emptyList(), 0, 1)
        }
    }
}