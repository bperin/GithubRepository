package com.brianperin.githubrepository.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.adapter.UserModule
import com.brianperin.githubrepository.network.Result
import com.brianperin.githubrepository.util.DebouncingQueryTextListener
import com.brianperin.githubrepository.viewmodel.UsersViewModel
import com.idanatz.oneadapter.OneAdapter
import kotlinx.android.synthetic.main.fragment_users.*

/**
 * Our main fragment for displaying users,
 * this is the glue that hooks our view, and business logic together
 * The view model is currently tied to the parent activity in a real world
 * app we would probably want to share some information back and forth
 * ie a master detail flow
 * Observers live data from view model and adjusts the states accordingly
 */
class UsersFragment : Fragment() {

    private lateinit var oneAdapter: OneAdapter
    private val usersViewModel: UsersViewModel by activityViewModels()


    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerUsers.layoutManager = LinearLayoutManager(context)

        oneAdapter = OneAdapter(recyclerUsers) {
            itemModules += UserModule(requireContext())
        }


        usersViewModel.usersLiveData.observe(viewLifecycleOwner, { result ->
            progressLoading.visibility = View.INVISIBLE
            when (result.status) {
                Result.Status.SUCCESS -> {
                    oneAdapter.add(result.data!!)
                }
                Result.Status.LOADING -> {
                    progressLoading.visibility = View.VISIBLE
                }
                Result.Status.ERROR -> {
                    progressLoading.visibility = View.VISIBLE
                }
            }
        })

        searchUsers.setOnQueryTextListener(
            DebouncingQueryTextListener(viewLifecycleOwner.lifecycle) { newText ->
                newText?.let {
                    oneAdapter.clear()
                    if (it.isEmpty()) {
                        usersViewModel.clear()
                    } else {
                        usersViewModel.search(it)
                    }
                }
            }
        )
    }
}