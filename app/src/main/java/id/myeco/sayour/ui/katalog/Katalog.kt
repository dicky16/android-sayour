package id.myeco.sayour.ui.katalog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.myeco.sayour.R
import id.myeco.sayour.adapter.KatalogAdapter
import id.myeco.sayour.model.KatalogResponse
import id.myeco.sayour.viewmodel.KatalogViewModel
import kotlinx.android.synthetic.main.activity_katalog.*

class Katalog : AppCompatActivity() {

    companion object {
        const val KEY_KATALOG = "key_katalog"
    }

    private val getKatalog: Observer<List<KatalogResponse.KatalogModel>> =
        object : Observer<List<KatalogResponse.KatalogModel>> {
            override fun onChanged(t: List<KatalogResponse.KatalogModel>?) {
                if (t != null) {
                    if(t.size != 0) {
                        showRecyclerView(t as ArrayList<KatalogResponse.KatalogModel>)
                        pbKatalog.visibility = View.GONE
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_katalog)

        pbKatalog.visibility = View.VISIBLE

        val katalogViewModel: KatalogViewModel = ViewModelProvider(this).get(KatalogViewModel::class.java)
        katalogViewModel.getListKatalog().observe(this, getKatalog)
        katalogViewModel.setListKatalog()

    }

    private fun showRecyclerView(list: ArrayList<KatalogResponse.KatalogModel>) {
        recyclerView?.layoutManager = LinearLayoutManager(this)
        val adapter = KatalogAdapter(list)
        adapter.notifyDataSetChanged()
        recyclerView?.adapter = adapter
        adapter.setOnItemClickCallBack(object : KatalogAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: KatalogResponse.KatalogModel) {
                val intent = Intent(this@Katalog, DetailKatalog::class.java)
                intent.putExtra(KEY_KATALOG, data)
                startActivity(intent)
            }
        })
    }
}