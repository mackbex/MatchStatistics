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
import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Match
import com.match.statistics.domain.model.lol.Summoner
import com.match.statistics.ui.custom.profile.ProfileAdapter
import com.match.statistics.util.wrapper.Resource
import com.match.statistics.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.takeWhile
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

            layoutProfile.binding.rcRankRecord.adapter = ProfileAdapter().apply {
                setPostInterface { league, binding ->
                    binding.root.setOnClickListener {
                        Snackbar.make(binding.root, league.tier, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }

            rcMatchHistory.adapter = MatchHistoryAdapter()

            layoutProfile.binding.btnRefresh.setOnClickListener {
                with(this@StatisticsFragment.viewModel) {
                    getUserProfile(curSummonerName)
//                    getMatches(curSummonerName)
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
                    viewModel.summonerNameState.collect{
                        when(it) {
                            is Resource.Success -> {
                                viewModel.curSummonerName = it.data
                                viewModel.getUserProfile(it.data)
                                viewModel.getMatches(it.data).collectLatest {
                                    (binding.rcMatchHistory.adapter as MatchHistoryAdapter).submitData(it)
                                }
//                                viewModel.getMatches(it.data)
                            }
                            is Resource.Failure -> {
                                // TODO: Back to Login View.
                            }
                        }
                    }
                }

                launch {
                    viewModel.summonerProfileState.collect {
                        when(it) {
                            is Resource.Success -> {
                                setProfileUI(it.data.summoner)
                                it.data.analysis?.let { analysis ->
                                    setAnalysisUI(analysis)
                                }
                            }
                        }
                    }
                }
//                launch {
//                    viewModel.summonerMatchesState.collect {
//                        when(it) {
//                            is Resource.Success -> {
//                                setMatchHistoryUI(it.data)
//                            }
//                        }
//                    }
//                }
            }
        }
    }

    private fun setProfileUI(summoner: Summoner) {
        with(binding.layoutProfile) {
            profileImageUrl = summoner.profileImageUrl
            name = summoner.name
            level = summoner.level
            leagues = summoner.leagues
        }
    }

    private fun setAnalysisUI(analysis: Analysis) {
        with(binding.layoutAnalysis) {
            games = analysis.games
            wins = analysis.wins
            losses = analysis.losses
            kills = analysis.kills
            deaths = analysis.deaths
            assists = analysis.assists
            kdaRatio = analysis.kda
            killContrib = analysis.killContrib
            mostChampions = analysis.mostChampions
            mostPosition = analysis.mostPosition
            positionWinRate = analysis.positionWinRate
        }
    }

//    private fun setMatchHistoryUI(list:List<Match>) {
//        val adapter = binding.rcMatchHistory.adapter as MatchHistoryAdapter
//        adapter.submitList(list)
//    }
}