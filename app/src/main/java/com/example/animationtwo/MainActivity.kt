package com.example.animationtwo

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.animationtwo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding-in başlatılması
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // indicatorsContainer-ın başlatılması
        indicatorsContainer = binding.indicatorsContainer

        // Kənar çubuqların nəzərə alınması (Edge-to-Edge)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setOnBoardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnBoardingItems() {
        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    title = "Bütün dərslərinizi bir mərkəzdən izləyin",
                    description = "Dərslərinizi asanlıqla idarə edin, elanlar və resurslarla aktual qalın.",
                    buttonText = "Növbəti"
                ),
                OnboardingItem(
                    title = "Davamiyyət izləmənizi sadələşdirin",
                    description = "İnteqrasiya edilmiş təqvimimizlə davamiyyətinizi asanlıqla izləyin və cədvəlinizdən xəbərdar olun.",
                    buttonText = "Növbəti"
                ),
                OnboardingItem(
                    title = "Yeniliklərdən vaxtında xəbərdar olun",
                    description = "Tapşırıqlar, testlər, elanlar və digər vacib yeniləmələr üçün dərhal bildirişlər alın.",
                    buttonText = "Başla"
                )
            ),
            binding.onBoardingViewPager // ViewPager2-ni adapterə göndərilir
        )
        binding.onBoardingViewPager.adapter = onboardingItemAdapter
        binding.onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        // OverScroll-nin deaktiv edilməsi
        (binding.onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParams: LayoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}
