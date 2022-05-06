package com.match.statistics.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.match.statistics.databinding.FragmentStatisticsBinding
import com.match.statistics.ui.statistics.profile.ProfileAdapter
import com.match.statistics.util.Resource
import com.match.statistics.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var binding: FragmentStatisticsBinding by autoCleared()
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            viewModel = this@StatisticsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner

            binding.rcRankRecord.adapter = ProfileAdapter().apply {
                setPostInterface { league, binding ->
                    binding.root.setOnClickListener {
                        Snackbar.make(binding.root, league.tierRank.name, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        initStates()
    }

    /**
     * State 구성
     */
    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.userProfileState.collect {
                        when(it) {
                            is Resource.Success -> {

                            }
                        }
                    }
                }
            }
        }
    }
}