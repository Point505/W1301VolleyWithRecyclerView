package kr.ac.kumohs20170334.w1301volleywithrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kumohs20170334.w1301volleywithrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private val songAdapter = SongAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SongViewModel::class.java]

        binding.list.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = songAdapter
        }

        model.list.observe(this) {
            // 좀더 구체적인 이벤트를 사용하라고 warning 나와서 변경함
            //songAdapter.notifyDataSetChanged()
            //Log.i("size", "${model.list.value?.size ?: 0}")

            // Changed가 아니라 Inserted
            songAdapter.notifyItemRangeInserted(0,
                model.list.value?.size ?: 0)//받아온 20개 songs Arrat에 있는
                                                    //GetItemCount 함수에서 받아 오는 개수 만큼
                    // songAdapter.notifyItemRangeInserted(0,
            //                songAdapter.itemCount)
        }

        model.requestSong()
    }

    inner class SongAdapter: RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val txTitle: TextView = itemView.findViewById(android.R.id.text1)
            val txSinger: TextView = itemView.findViewById(android.R.id.text2)//image =1 text =5 all =6
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = layoutInflater.inflate(android.R.layout.simple_list_item_2,
                parent,
                false)//simple_list_
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.txTitle.text = model.list.value?.get(position)?.title ?: null
            holder.txSinger.text = model.list.value?.get(position)?.singer ?: null
        }//?: 상관없이 진행 시킬떄 사용하는 함수

        override fun getItemCount() = model.list.value?.size ?: 0
    }
}//안드로이드를 제외하고  Sim