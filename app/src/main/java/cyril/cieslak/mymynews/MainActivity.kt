package cyril.cieslak.mymynews

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.widget.TableLayout
import android.widget.TextView
import cyril.cieslak.mymynews.Fragments.FragmentOne
import cyril.cieslak.mymynews.Fragments.FragmentThree
import cyril.cieslak.mymynews.Fragments.FragmentTwo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        // VIewPager Adapter
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentOne(), "One")
        adapter.addFragment(FragmentTwo(), "Two")
        adapter.addFragment(FragmentThree(), "Three")
        viewPager.adapter = adapter
      //  tabs.setupWithViewPager(viewPager)
       // var tabs  = findViewById<TableLayout>(R.id.tabs)
      //  var tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
      //  tabs.setTabMode(TabLayout.MODE_FIXED)



    }

    class MyViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title : String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
