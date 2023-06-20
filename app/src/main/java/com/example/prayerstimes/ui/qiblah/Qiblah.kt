package com.example.prayerstimes.ui.qiblah

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.prayerstimes.R
import com.example.prayerstimes.databinding.FragmentQiblahBinding
import com.example.prayerstimes.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Qiblah : BaseFragment<FragmentQiblahBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment = R.layout.fragment_qiblah
    override val viewModel: QiblahViewModel by viewModels()

    override fun setup() {

    }
}