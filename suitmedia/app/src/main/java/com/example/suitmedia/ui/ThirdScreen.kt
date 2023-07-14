package com.example.suitmedia.ui


import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmedia.MainViewModelFactory
import com.example.suitmedia.R
import com.example.suitmedia.adapter.ListDataAdapter
import com.example.suitmedia.data.LoadingStateAdapter
import com.example.suitmedia.databinding.ActivityThirdScreenBinding
import com.example.suitmedia.model.ListDataModel
import com.example.suitmedia.utils.EXTRA_NAME_USER

@Suppress("DEPRECATION")
class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private lateinit var mainViewModel: ListDataModel
    private lateinit var adapter: ListDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(this)
        mainViewModel = ViewModelProvider(this, factory)[ListDataModel::class.java]

        adapter = ListDataAdapter()

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            resources.getDrawable(R.drawable.bg_action_bar)
        )

        val actionBarLayout = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val title = actionBarLayout.findViewById<TextView>(R.id.title_action_bar)
        val backButton = actionBarLayout.findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            onBackPressed()
        }

        title.text = getString(R.string.title_third_activity)

        val layoutParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )

        supportActionBar?.setCustomView(actionBarLayout, layoutParams)

        binding.rvData.layoutManager = LinearLayoutManager(this)

        getData()

    }

    private fun getData() {
        binding.rvData.adapter = adapter
        adapter.setOnItemClickCallback(object : ListDataAdapter.OnItemClickCallback {
            override fun onItemClicked(
                name: String,
                avatar: String,
                id: String
            ) {
                val intent = Intent(this@ThirdScreen, SecondActivity::class.java)
                intent.putExtra(EXTRA_NAME_USER, name)
                startActivity(intent)
            }
        })

        binding.rvData.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )

        mainViewModel.data.observe(
            this,
        ) { data ->
            adapter.submitData(lifecycle, data)
        }
    }


}