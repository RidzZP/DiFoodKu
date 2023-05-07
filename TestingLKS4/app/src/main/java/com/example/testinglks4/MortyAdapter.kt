import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testinglks4.Fragment.HomeFragment
import com.example.testinglks4.R
import com.example.testinglks4.ResultsItem

class MortyAdapter(private val characters: List<ResultsItem>) :
    RecyclerView.Adapter<MortyAdapter.MortyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MortyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return MortyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MortyViewHolder, position: Int) {
        val character = characters.get(position)
        holder.bind(character)
    }


    override fun getItemCount(): Int {
        return characters.size
    }

    class MortyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgCharacter: ImageView = itemView.findViewById(R.id.imageView)
        private val txtNama: TextView = itemView.findViewById(R.id.txtNama)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val txtHarga: TextView = itemView.findViewById(R.id.txtHarga)
        private val btnTambah: ImageView = itemView.findViewById(R.id.btnTambah)
        private val btnKurang: ImageView = itemView.findViewById(R.id.btnKurang)

        fun bind(character: ResultsItem) {
            txtNama.text = character.name
            Glide.with(itemView.context)
                .load(character.image)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imgCharacter)

            btnTambah.setOnClickListener {
                val quantity = txtQuantity.text.toString().toInt()
                txtQuantity.text = (quantity + 1).toString()
            }

            btnKurang.setOnClickListener {
                val quantity = txtQuantity.text.toString().toInt()
                if (quantity > 0) {
                    txtQuantity.text = (quantity - 1).toString()
                }

            }
        }
    }
}
