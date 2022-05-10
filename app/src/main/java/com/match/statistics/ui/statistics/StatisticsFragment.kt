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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.match.statistics.databinding.FragmentStatisticsBinding
import com.match.statistics.domain.model.lol.Analysis
import com.match.statistics.domain.model.lol.Summoner
import com.match.statistics.ui.custom.profile.LeagueAdapter
import com.match.statistics.util.PagingLoadStateAdapter
import com.match.statistics.util.autoCleared
import com.match.statistics.util.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 메인 뷰.
 */
@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var binding: FragmentStatisticsBinding by autoCleared()
    private val viewModel: StatisticsViewModel by viewModels()
    private val matchHistoryAdapter by lazy { MatchHistoryAdapter() }

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

            layoutProfile.binding.rcRankRecord.apply {
                setHasFixedSize(true)
                adapter = LeagueAdapter().apply {
                    setPostInterface { league, binding ->
                        binding.root.setOnClickListener {
                            Snackbar.make(binding.root, league.tier, Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            rcMatchHistory.apply {
                setHasFixedSize(true)
                with(matchHistoryAdapter) {
                    adapter = this.withLoadStateFooter(
                        footer = PagingLoadStateAdapter(this)
                    )
                }

                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() > 0) {
                            if(!binding.fabScrollTop.isShown) {
                                binding.fabScrollTop.show()
                            }
                        } else  {
                            if(binding.fabScrollTop.isShown) {
                                binding.fabScrollTop.hide()
                            }
                        }
                    }
                })
            }

            layoutProfile.binding.btnRefresh.setOnClickListener {
                with(this@StatisticsFragment.viewModel) {
                    getUserProfile(curSummonerName)
                }
            }

            fabScrollTop.setOnClickListener {
                rcMatchHistory.scrollToPosition(0)
                appBarLayout.setExpanded(true, true)
            }
        }

        initStates()
    }

    /**
     * State 구성
     */
    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {

                launch {
                    viewModel.summonerNameState.collect {
                        when (it) {
                            is Resource.Success -> {
                                viewModel.curSummonerName = it.data
                                viewModel.getUserProfile(it.data)
                                getMatchLists(it.data)
                            }
                            is Resource.Failure -> {
                                // TODO: Back to Login View.
                            }
                        }
                    }
                }

                launch {
                    viewModel.summonerProfileState.collect {
                        when (it) {
                            is Resource.Success -> {
                                setProfileUI(it.data.summoner)
                                it.data.analysis?.let { analysis ->
                                    setAnalysisUI(analysis)
                                }
                            }
                            is Resource.Loading -> {
                                (binding.layoutProfile.binding.rcRankRecord.adapter as LeagueAdapter).submitList(null)
                                matchHistoryAdapter.refresh()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getMatchLists(summonerName:String) {
         viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.getMatches(summonerName).collectLatest { pagingData ->
                        withContext(Dispatchers.Main) {
                            matchHistoryAdapter.submitData(pagingData)
                        }
                    }
                }
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
}