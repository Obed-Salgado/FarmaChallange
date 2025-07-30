package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.data.model.Progress
import dev.janus.farmachallange.databinding.FragmentProgressBinding
import dev.janus.farmachallange.ui.view.adapters.ProgressAdapter
import dev.janus.farmachallange.ui.viewmodel.ProgressViewModel

@AndroidEntryPoint
class ProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProgressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()

        viewModel.getLevelsProgress()

        viewModel.progress.observe(viewLifecycleOwner){
            setUpRecyclerView(it)
        }
    }

    private fun setUpRecyclerView(progress: List<Progress>){
        binding.rvProgress.adapter = ProgressAdapter(progress)
        binding.rvProgress.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }
}