package magnalleexample.testRecipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import magnalleexample.testRecipes.App
import magnalleexample.testRecipes.R
import magnalleexample.testRecipes.app
import magnalleexample.testRecipes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.repo = app.repository

        viewModel.getRecipes()
        viewModel.JSONText.observe(this, Observer {
            binding.JSONText.setText(it)
        })
    }
}