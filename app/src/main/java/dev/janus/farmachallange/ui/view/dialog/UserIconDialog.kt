package dev.janus.farmachallange.ui.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import dev.janus.farmachallange.databinding.DialogUserIconBinding
import dev.janus.farmachallange.ui.view.adapters.IconUserAdapter

class UserIconDialog(selectIcon: (String) -> Unit): DialogFragment() {

    private lateinit var binding: DialogUserIconBinding
    private val adapter = IconUserAdapter(
        selectIcon = selectIcon,
        dismiss = { dismiss() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUserIconBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvIconsUser.adapter = adapter
        binding.rvIconsUser.layoutManager = layoutManager
    }
}