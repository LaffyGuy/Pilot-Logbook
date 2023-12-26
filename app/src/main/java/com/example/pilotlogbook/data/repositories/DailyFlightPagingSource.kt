package com.example.pilotlogbook.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pilotlogbook.data.room.dao.DailyFlightDao
import com.example.pilotlogbook.domain.entities.DailyFlight
import kotlinx.coroutines.delay


class DailyFlightPagingSource(private val dailyFlightDao: DailyFlightDao, private val pageSize: Int, private val searchBy: String): PagingSource<Int, DailyFlight>() {


    override fun getRefreshKey(state: PagingState<Int, DailyFlight>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DailyFlight> {
        delay(2000)
        val pageIndex = params.key ?: 0
        return try {
            val dailyFlightsEntity = dailyFlightDao.getAllDailyFlight(params.loadSize, pageIndex * pageSize, searchBy)
            val dailyFlight = dailyFlightsEntity.map { entity ->
                entity.toDailyFlight()
            }

            return LoadResult.Page(
                data = dailyFlight,
                prevKey = if(pageIndex == 0) null else pageIndex -1,
                nextKey = if(dailyFlight.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        }catch(e: Exception){
            LoadResult.Error(
                throwable = e
            )
        }
    }
}