package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.NewsAdapter
import com.example.myapplication.databinding.FragmentArticleBinding
import com.example.myapplication.databinding.FragmentSavedNewsBinding
import com.example.myapplication.ui.NewsActivity
import com.example.myapplication.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedNewsFragment:Fragment(R.layout.fragment_saved_news) {
    private var _binding:FragmentSavedNewsBinding?=null
    private val binding get()=_binding!!
    private lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter:NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentSavedNewsBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel
        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }

            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment2,
                bundle
            )

        }
        val itemTouchHelperCallBack= object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
              return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position= viewHolder.adapterPosition
                    val article = newsAdapter.differ.currentList[position]
                    viewModel.deleteArticle(article)
                    Snackbar.make(view,"Successfully deleted the article",Snackbar.LENGTH_LONG).apply {
                        setAction("undo"){
                            viewModel.saveArticle(article)
                        }
                        show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles->

            newsAdapter.differ.submitList(articles)

        })

    }


    private fun setUpRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
        }

        }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    }


