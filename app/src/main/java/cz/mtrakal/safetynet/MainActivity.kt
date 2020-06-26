package cz.mtrakal.safetynet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        runSafetynet()
    }

    private fun runSafetynet() {
        progressVisible(true)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                SafetyNetHelper.checkSafetyNet(this@MainActivity, "R2Rra24fVm5xa2Mg")
                withContext(Dispatchers.Main) {
                    progressVisible(false)
                }
            }
        }
    }

    private fun progressVisible(showProgress: Boolean) {
        vProgress.isVisible = showProgress
        vText.isVisible = !showProgress
    }
}