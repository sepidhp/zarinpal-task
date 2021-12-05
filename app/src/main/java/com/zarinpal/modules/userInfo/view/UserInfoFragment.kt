package com.zarinpal.modules.userInfo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zarinpal.R
import com.zarinpal.data.server.apiDialog
import com.zarinpal.databinding.FragmentUserInfoBinding
import com.zarinpal.modules.userInfo.viewModel.UserInfoViewModel
import com.zarinpal.utils.ProgressDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoFragment : Fragment() {

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserInfoViewModel by activityViewModels()
    private val requestOptions =
        RequestOptions()
            .placeholder(R.drawable.ic_user_placeholder)
            .override(128, 128)
            .dontAnimate()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserInfo()
        observe()
    }

    private fun observe() {

        viewModel.isApiCalling.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.apiException.observe(viewLifecycleOwner) {

            apiDialog(requireActivity(), it) {

                onRetryClicked {
                    it.dismiss()
                    viewModel.getUserInfo()
                }

                onExitClicked {
                    it.dismiss()
                    requireActivity().finish()
                }
            }.show()
        }

        viewModel.userInfo.observe(viewLifecycleOwner) {
            setData()
        }
    }

    private fun setData() {

        val userInfo = viewModel.userInfo.value!!

        binding.txtName.text = userInfo.name
        binding.txtRepositoryCount.text =
            getString(R.string.repository_count_wth_value, userInfo.repositories.totalCount)
        binding.txtCompany.text = userInfo.company
        binding.txtFollowerAndFollowing.text =
            getString(
                R.string.follower_following_count_with_value,
                userInfo.followers.totalCount,
                userInfo.following.totalCount
            )
        binding.txtStarCount.text = userInfo.starredRepositories.totalCount.toString()
        binding.txtEmail.text = userInfo.email
        binding.txtLocation.text = userInfo.location
        Glide.with(binding.root).load(userInfo.avatarUrl).apply(requestOptions)
            .into(binding.imgUser)

        binding.root.visibility = View.VISIBLE
    }
}