package com.epam.awards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.epam.awards.common.ComponentDependenciesProvider
import com.epam.awards.common.HasComponentDependencies
import com.epam.awards.di.main.DaggerMainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        performInject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun performInject() {
        DaggerMainComponent.factory().create(context = this.applicationContext)
            .inject(this)
    }
}
