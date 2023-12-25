package com.example.pilotlogbook.data.repositories

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pilotlogbook.data.room.dao.DailyFlightDao
import com.example.pilotlogbook.domain.entities.DailyFlight


class DailyFlightPagingSource(private val dailyFlightDao: DailyFlightDao, private val pageSize: Int, private val searchBy: String): PagingSource<Int, DailyFlight>() {


    override fun getRefreshKey(state: PagingState<Int, DailyFlight>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DailyFlight> {
        val pageIndex = params.key ?: 0
        Log.d("MyTag2", "Page index - $pageIndex")
        return try {
            val dailyFlightsEntity = dailyFlightDao.getAllDailyFlight(params.loadSize, pageIndex * pageSize, searchBy)
            Log.d("MyTag2", "Load size - ${params.loadSize}")
            val dailyFlight = dailyFlightsEntity.map { entity ->
                entity.toDailyFlight()
            }
            Log.d("MyTag2", "Load result entity - $dailyFlightsEntity")
            Log.d("MyTag2", "Load result - $dailyFlight")

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