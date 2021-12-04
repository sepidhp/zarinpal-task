package com.zarinpal.modules.repositories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zarinpal.adapter.RepositoryAdapter
import com.zarinpal.data.server.apiDialog
import com.zarinpal.databinding.FragmentRepositoriesBinding
import com.zarinpal.extension.toPx
import com.zarinpal.modules.repositories.viewModel.RepositoriesViewModel
import com.zarinpal.utils.LinearDividerDecoration
import com.zarinpal.utils.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {

    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepositoriesViewModel by activityViewModels()
    private val progressDialog by lazy { ProgressDialog(requireActivity(), false) }
    private val repositoryAdapter by lazy { RepositoryAdapter() }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRepositories()
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
        }
    }

    private fun observe() {

        viewModel.isApiCalling.observe(viewLifecycleOwner) {

            if (it)
                progressDialog.show()
            else
                progressDialog.dismiss()
        }

        viewModel.apiException.observe(viewLifecycleOwner) {

            apiDialog(requireActivity(), it) {

                onRetryClicked {
                    it.dismiss()
                    viewModel.getRepositories()
                }

                onExitClicked {
                    it.dismiss()
                    requireActivity().finish()
                }
            }.show()
        }

        viewModel.repositories.observe(viewLifecycleOwner) {
            setData()
        }
    }

    private fun setData() {

        // set list
        repositoryAdapter.list = viewModel.repositories.value!!.toMutableList()
        repositoryAdapter.notifyDataSetChanged()
    }
}