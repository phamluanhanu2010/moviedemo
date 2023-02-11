package luanpv.example.movieapp.ui.activities.search

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.pgbank.personal.bases.BaseActivity
import luanpv.example.movieapp.databinding.ActivitySearchByNameBinding
import luanpv.example.movieapp.ui.activities.search.adapters.MovieItemAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : BaseActivity() {
    override val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchByNameBinding
    var outOfData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByNameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.apply {
            errorLiveData.observe(this@SearchActivity, Observer {
                if (viewModel.page == 1) {
                    binding.items.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.tvNoData.text = it
                } else {
                    outOfData = true
                }
                hideLoading()
            })

            movieItemsLiveData.observe(this@SearchActivity, Observer {
                hideLoading()
                if (viewModel.page == 1) {
                    if (it.isEmpty()) {
                        binding.items.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.items.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.GONE
                        binding.items.adapter = MovieItemAdapter(it) {
                            Toast.makeText(this@SearchActivity, "========" + it.Year, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    if (!it.isEmpty()) {
                        (binding.items.adapter as MovieItemAdapter).addData(it)
                    }
                }
            })
        }
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                onSearch()
                handled = true
            }
            handled
        })

        binding.items.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !outOfData) {
                    showLoading()
                    viewModel.searchByName(binding.edtSearch.text.toString())
                }
            }
        })

        binding.btnSearch.setOnClickListener {
            hideKeyboard(binding.edtSearch)
            onSearch()
        }
    }

    private fun onSearch() {
        outOfData = false
        showLoading()
        viewModel.page = 0
        viewModel.searchByName(binding.edtSearch.text.toString())
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}