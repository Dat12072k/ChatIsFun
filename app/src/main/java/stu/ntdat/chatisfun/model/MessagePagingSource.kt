package stu.ntdat.chatisfun.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.flow.collect
import stu.ntdat.chatisfun.model.database.AppDatabase
import stu.ntdat.chatisfun.util.trace
import javax.inject.Inject

class MessagePagingSource @Inject constructor(private val database: AppDatabase,val convId: String) :
    PagingSource<Int, ChatMessage>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
        const val PAGE_SIZE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, ChatMessage>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChatMessage> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val message = database.chatMessageDao.getListMessByConvId(convId)
        return try {
            LoadResult.Page(
                data = message,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (message.isNullOrEmpty()) null else position + (params.loadSize/ PAGE_SIZE)
            )
        } catch (e: Exception) {
            trace(e)
            LoadResult.Error(e)
        }
    }
}