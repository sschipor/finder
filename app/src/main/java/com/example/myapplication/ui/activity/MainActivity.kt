package com.example.myapplication.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainLayoutBinding
import com.example.myapplication.ui.adapter.PetListAdapter
import com.example.myapplication.ui.alert.AlertDialogUtil
import com.example.myapplication.ui.fragment.LoginDialogFragment
import com.example.myapplication.ui.fragment.PetDetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainLayoutBinding? = null
    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLayoutBinding.inflate(layoutInflater)
        binding?.viewModel = viewModel
        binding?.petList?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PetListAdapter(viewModel)
        }
        setContentView(binding?.root)
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            openLoginScreen.observeEvent(this@MainActivity) {
                if (it) {
                    LoginDialogFragment.newInstance()
                        .show(supportFragmentManager, LoginDialogFragment.TAG)
                }
            }
            onError.observeEvent(this@MainActivity) {
                AlertDialogUtil.showAlertDialog(
                    context = this@MainActivity,
                    title = getString(R.string.login_error_title),
                    body = getString(R.string.error_general_body),
                    okButton = getString(R.string.ok_button)
                )
            }
            openDetailsScreen.observeEvent(this@MainActivity) {
                PetDetailsFragment.newInstance(it).show(
                    supportFragmentManager, PetDetailsFragment.TAG
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
