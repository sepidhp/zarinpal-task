package com.zarinpal.modules.repositories.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinpal.RepositoriesQuery
import com.zarinpal.adapter.RepositoryAdapter
import com.zarinpal.data.server.apiDialog
import com.zarinpal.databinding.FragmentRepositoriesBinding
import com.zarinpal.extension.toPx
import com.zarinpal.fragment.RepositoryFragment
import com.zarinpal.modules.repositories.viewModel.RepositoriesViewModel
import com.zarinpal.utils.EndlessRecyclerViewScrollListener
import com.zarinpal.utils.LinearDividerDecoration
import com.zarinpal.utils.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoriesViewModel by activityViewModels()
    private val repositoryAdapter by lazy { RepositoryAdapter() }
    private lateinit var repositoryInfo: RepositoriesQuery.Repositories
    private lateinit var repositories: List<RepositoryFragment>

    private var cursor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
            setupView()
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRepositoriesApi(cursor)
        observe()
    }

    private fun setupView() {

        // setup repository list
        binding.rcvRepositories.apply {

            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                LinearDividerDecoration(
                    LinearDividerDecoration.VERTICAL,
                    16.toPx(),
                    true
                )
            )
            adapter = repositoryAdapter
            addOnScrollListener(scrollListener)
        }
    }

    private fun observe() {

        viewModel.isApiCalling.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.apiException.observe(viewLifecycleOwner) {

            apiDialog(requireActivity(), it) {

                onRetryClicked {
                    it.dismiss()
                    viewModel.getRepositoriesApi(cursor)
                }

                onExitClicked {
                    it.dismiss()
                    requireActivity().finish()
                }
            }.show()
        }

        viewModel.repositoryInfo.observe(viewLifecycleOwner) {
            repositoryInfo = it
            repositories = viewModel.getRepositories(it)
            cursor = it.pageInfo.endCursor
            setData()
        }
    }

    private fun setData() {

        if (repositories.isNotEmpty()) {

            if (repositoryInfo.totalCount == repositories.size)
                repositoryAdapter.list = repositories.toMutableList()
            else
                repositoryAdapter.list.addAll(repositories)
            repositoryAdapter.notifyDataSetChanged()
        }
    }

    private val scrollListener by lazy {
        EndlessRecyclerViewScrollListener(binding.rcvRepositories.layoutManager as LinearLayoutManager) { page, totalItemsCount, view ->

            if (repositoryInfo.pageInfo.hasNextPage)
                viewModel.getRepositoriesApi(cursor)
        }
    }
}