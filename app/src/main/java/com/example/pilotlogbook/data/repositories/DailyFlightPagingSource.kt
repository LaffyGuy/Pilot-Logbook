package com.example.pilotlogbook.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pilotlogbook.data.room.dao.DailyFlightDao
import com.example.pilotlogbook.data.room.dao.SortDailyFlightDao
import com.example.pilotlogbook.domain.entities.DailyFlight
import com.example.pilotlogbook.utils.SortType


class DailyFlightPagingSource(
    private val dailyFlightDao: DailyFlightDao,
    private val sortDailyFlightDao: SortDailyFlightDao,
    private val sortType: SortType,
    private val pageSize: Int,
    private val searchBy: String): PagingSource<Int, DailyFlight>() {


    override fun getRefreshKey(state: PagingState<Int, DailyFlight>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DailyFlight> {
        val pageIndex = params.key ?: 0

        return try {

            when(sortType){
                SortType.DEFAULT -> {
                   val dailyFlightsEntity = dailyFlightDao.getAllDailyFlight(params.loadSize, pageIndex * pageSize, searchBy)
                   val dailyFlight = dailyFlightsEntity.map { entity ->
                        entity.toDailyFlight()
                   }
                   resourceSource(pageIndex, dailyFlight.size, params.loadSize, pageSize, dailyFlight)

                }
                SortType.DATE_DESC -> {
                    val dailyFlightEntity = sortDailyFlightDao.getDailyFlightByDateDesc(params.loadSize, pageIndex * pageSize, searchBy)
                    val dailyFlight = dailyFlightEntity.map { entity ->
                        entity.toDailyFlight()
                    }
                    resourceSource(pageIndex, dailyFlight.size, params.loadSize, pageSize, dailyFlight)

                }
                SortType.DATE_ASC -> {
                    val dailyFlightEntity = sortDailyFlightDao.getDailyFlightByDateAsc(params.loadSize, pageIndex * pageSize, searchBy)
                    val dailyFlight = dailyFlightEntity.map { entity ->
                        entity.toDailyFlight()
                    }

                    resourceSource(pageIndex, dailyFlight.size, params.loadSize, pageSize, dailyFlight)
                }
                SortType.TTF_DESC -> {
                    val dailyFlightEntity = sortDailyFlightDao.getDailyFlightByTotalTimeOfFlightDesc(params.loadSize, pageIndex * pageSize, searchBy)
                    val dailyFlight = dailyFlightEntity.map { entity ->
                        entity.toDailyFlight()
                    }

                    resourceSource(pageIndex, dailyFlight.size, params.loadSize, pageSize, dailyFlight)
                }
                SortType.TTF_ASC -> {
                    val dailyFlightEntity = sortDailyFlightDao.getDailyFlightByTotalTimeOfFlightAsc(params.loadSize, pageIndex * pageSize, searchBy)
                    val dailyFlight = dailyFlightEntity.map { entity ->
                        entity.toDailyFlight()
                    }

                    resourceSource(pageIndex, dailyFlight.size, params.loadSize, pageSize, dailyFlight)
                }

            }
        }catch (e: Throwable){
            LoadResult.Error(e)
        }
    }

    private fun resourceSource(pageIndex: Int, size: Int, loadSize: Int, pageSize: Int, data: List<DailyFlight>): LoadResult.Page<Int, DailyFlight>{
       return LoadResult.Page(
           data = data,
           prevKey = if(pageIndex == 0) null else pageIndex - 1,
           nextKey = if(size == loadSize) pageIndex + (loadSize / pageSize) else null

       )
    }

}