package luanpv.example.movieapp.ui.activities.search

import androidx.lifecycle.MutableLiveData
import com.pgbank.personal.bases.BaseViewModel
import com.pgbank.personal.data.impl.SearchRepo
import luanpv.example.movieapp.data.entities.response.MovieItemResponse

class SearchViewModel(val repo: SearchRepo) : BaseViewModel() {

    val movieItemsLiveData = MutableLiveData<ArrayList<MovieItemResponse>>()
    val errorLiveData = MutableLiveData<String?>()
    var page = 0

    fun searchByName(name: String) = launchSilent {
        page++
        val res = repo.searchByName(name, page)
        if (res.Response.equals("True")) {
            movieItemsLiveData.postValue(ArrayList(res.Search))
        } else {
            errorLiveData.postValue(res.Error)
        }
    }


}
