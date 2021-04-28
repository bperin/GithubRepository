package com.brianperin.githubrepository.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.brianperin.githubrepository.R
import com.brianperin.githubrepository.viewmodel.UsersViewModel

class UsersFragment : Fragment() {

    private val usersViewModel: UsersViewModel by activityViewModels()
//    private val employeesAdapter: EmployeesAdapter = EmployeesAdapter()

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }
}