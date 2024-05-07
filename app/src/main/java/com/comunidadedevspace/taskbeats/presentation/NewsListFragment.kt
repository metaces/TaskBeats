package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.News
import com.comunidadedevspace.taskbeats.data.remote.NewsDto
import com.comunidadedevspace.taskbeats.data.remote.NewsResponse
import com.comunidadedevspace.taskbeats.data.remote.RetrofitModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 * Use the [NewsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsListFragment : Fragment() {

    private val adapter = NewsListAdapter()

    private val viewModel by lazy {
        NewsListVIewModel.create()
    }

    private val retrofitModule = RetrofitModule
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNewsList = view.findViewById<RecyclerView>(R.id.rv_news)

        rvNewsList.adapter = adapter

        viewModel.newsListLiveData.observe(viewLifecycleOwner) { newsListDto ->
            val newsList = newsListDto.map { newsDto ->
                News(
                    title = newsDto.title,
                    imgUrl = newsDto.imageUrl
                )
            }
            adapter.submitList(newsList)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewsListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = NewsListFragment()
    }
}