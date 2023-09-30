package dev.janus.farmachallange.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.janus.farmachallange.R
import dev.janus.farmachallange.data.model.Nivel
import dev.janus.farmachallange.databinding.FragmentMenuBinding
import dev.janus.farmachallange.ui.view.adapters.ListLevelAdapter
import dev.janus.farmachallange.ui.viewmodel.MenuViewModel
import kotlinx.coroutines.selects.select

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var adapter:ListLevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    /*    binding.btnSingle.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_singleGameFragment)
        }*/
        inintRecyclerView()

    }

    fun inintRecyclerView(){
        menuViewModel.nameLevel.observe(viewLifecycleOwner, Observer { name->
            adapter = ListLevelAdapter(name, selectNivel ={changueFragment(it)})
            binding.rvLevel.adapter = adapter
            binding.rvLevel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

        })
    }
    fun changueFragment(idNivel:String){
        findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToRondasFragment(idNivel))
    }


}