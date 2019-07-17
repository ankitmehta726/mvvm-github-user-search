package com.gihub.users.ui.search.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.gihub.users.R
import com.gihub.users.ui.base.BaseFragment
import com.gihub.users.viewmodel.AppViewModelFactory
import kotlinx.android.synthetic.main.fragment_user_list.*
import javax.inject.Inject
import io.reactivex.subjects.PublishSubject
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gihub.users.data.model.SearchedUser
import com.gihub.users.ui.search.MainActivity
import com.gihub.users.widgets.NetworkUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment<SearchViewModel>(), SearchListAdapter.OnUserClickListener {

    private lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory

    private lateinit var searchListAdapter: SearchListAdapter
    private lateinit var layoutManager1: LinearLayoutManager // Layout manager for a linear list

    private var pageNumber = 1
    private var loading = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private val VISIBLE_THRESHOLD = 1

    private var currentQuery = ""
    private var lastQuery = ""

    private lateinit var searchedUsersList: ArrayList<SearchedUser>

    companion object {
        const val SEARCHED_USER = "searched_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Initializing the list of users
        searchedUsersList = ArrayList()
    }

    override fun getViewModel(): SearchViewModel {
        searchViewModel = ViewModelProviders.of(this@SearchFragment, appViewModelFactory)
            .get(SearchViewModel::class.java)
        return searchViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoading()
        observeMessage()
        observeSearching()
        observeSearchedUserList()

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchListAdapter = SearchListAdapter(activity as MainActivity, searchedUsersList)
        searchListAdapter.setOnUserClickListener(this)

        layoutManager1 = LinearLayoutManager(activity as MainActivity)
        rvUserList.apply {
            layoutManager = layoutManager1
            adapter = searchListAdapter
        }

        //Applying pagination to the recycler view. Whenever the focus
        // will move to the last element of the  list in the page, new page
        // will be loaded and elements will be added to the list.
        setUpLoadMoreListener()
    }

    private fun observeMessage() {
        //observing any error/success message
        searchViewModel.message.observe(this, Observer {
            (activity as MainActivity).showInfoToast(it!!)
        })
    }

    private fun observeLoading() {
        //observing the progress state
        searchViewModel.loading.observe(this, Observer {
            (activity as MainActivity).setProgressVisibility(it!!)
        })
    }

    private fun observeSearchedUserList() {
        //Observing the fetched list of  user search
        searchViewModel.searchedUsersList.observe(this, Observer {

            //If the query is same and new page loaded then the new
            // elements would be added to the existing list.
            if (currentQuery == lastQuery) {
                searchedUsersList.addAll(it!!)
            } else {
                //If query changes, the list would be cleared and again
                // new data will be populate to the list.
                searchedUsersList.clear()
                searchedUsersList.addAll(it!!)
            }

            //notify the  recycler view in case of new data arrival.
            searchListAdapter.notifyDataSetChanged()
            loading = false
            lastQuery = currentQuery
        })
    }


    //Observing the data stream of the SearchView. when appropriate
    // data found, the search get performed.
    private fun observeSearching() {
        val disposable = fromView(searchView)
            .debounce(1200, TimeUnit.MILLISECONDS)
            .filter { v ->
                v.isNotEmpty() && v.length >= 3
            }.map { text ->
                text.toLowerCase().trim()
            }.distinctUntilChanged().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Timber.i("Entered string is: %s", it)
                currentQuery = it
                if (it.length >= 3)
                    if (NetworkUtils.isNetworkConnected(activity as MainActivity))
                        searchViewModel.searchUsers(it, pageNumber)
            }
    }

    //this method observing the data changes in the search view widget.
    // If the appropriate data comes through the stream, the search  operation
    // would be performed.
    private fun fromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                searchView.clearFocus() //if you want to close keyboard
                (activity as MainActivity).hideKeyboard()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length >= 3) {
                    //reset the page number when query changes
                    pageNumber = 1
                    subject.onNext(newText)
                } else {
                    //clear the list if the length is less than 3 characters
                    currentQuery = ""
                    searchedUsersList.clear()
                    searchListAdapter.notifyDataSetChanged()
                }
                return false
            }
        })

        return subject
    }

    /**
     * setting listener to get callback for load more
     */
    private fun setUpLoadMoreListener() {
        rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = layoutManager1.itemCount
                lastVisibleItem = layoutManager1.findLastVisibleItemPosition()

                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    pageNumber++
                    if (currentQuery.length >= 3) {
                        if (NetworkUtils.isNetworkConnected(activity as MainActivity))
                            searchViewModel.searchUsers(currentQuery, pageNumber)
                    } else {
                        //reset page to 0 if query is less than 3 chracters
                        pageNumber = 1
                    }
                    loading = true
                }
            }
        })
    }

    //whenever the user card get clicked, this method would be invoked
    // and the user would be navigated to the profile page.
    override fun onUserClick(searchedUser: SearchedUser) {
        val bundle = Bundle()
        bundle.putParcelable(SEARCHED_USER, searchedUser)
        Navigation.findNavController(view!!).navigate(R.id.action_userListFragment_to_userProfileFragment, bundle)
    }
}