package jp.techacademy.kenji.apiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import java.lang.NullPointerException

class ApiAdapter (private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<Shop>()
    private val itemsF = FavoriteShop()

    var onClickAddFavorite: ((Shop) -> Unit)? = null
    var onClickDeleteFavorite:((Shop) -> Unit)? = null
//    var onClickItem: ((String) -> Unit)? = null
    var onClickItem:((FavoriteShop) -> Unit)? = null


    fun refresh(list: List<Shop>){
        update(list, false)
    }

    fun add(list: List<Shop>){
        update(list, true)
    }


    fun update(list: List<Shop>, isAdd: Boolean) {
        items.apply {
            if(!isAdd) {
                clear()
            }
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, ViewType: Int): RecyclerView.ViewHolder{
        return ApiItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite, parent, false))
    }

    class ApiItemViewHolder(view: View): RecyclerView.ViewHolder(view){

        // レイアウトファイルからidがrootViewのConstraintLayoutオブジェクトを取得し、代入
        val rootView : ConstraintLayout = view.findViewById(R.id.rootView)
        // レイアウトファイルからidがnameTextViewのCTextViewオブジェクトを取得し、代入
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        // レイアウトファイルからidがimageViewのImageViewオブジェクトを取得し、代入
        val imageView: ImageView = view.findViewById(R.id.imageView)
        // レイアウトファイルからidがfavoriteImageViewのImageViewオブジェクトを取得し、代入
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)

        val addressTextView: TextView = view.findViewById(R.id.addressTextView)

    }

    override fun getItemCount():Int{
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,position: Int){
        if(holder is ApiItemViewHolder){
            updateApiItemViewHolder(holder,position)
        }
    }


    

    private fun updateApiItemViewHolder(holder: ApiItemViewHolder, position: Int){
        val data = items[position]
        val isFavorite = FavoriteShop.findBy(data.id) != null




        holder.apply{
            rootView.apply{
                setBackgroundColor(ContextCompat.getColor(context,
                if (position % 2 == 0) android.R.color.white else android.R.color.darker_gray))



                setOnClickListener{

                    itemsF.apply{
                        itemsF.id = data.id
                        itemsF.imageUrl = data.logoImage
                        itemsF.name = data.name
                        itemsF.url = if (data.couponUrls.sp.isNotEmpty()) data.couponUrls.sp else data.couponUrls.pc
                        itemsF.address = data.address
                        itemsF.couponUrlsSP = data.couponUrls.sp
                        itemsF.couponUrlsPC = data.couponUrls.pc
                    }

//                    onClickItem?.invoke(if (data.couponUrls.sp.isNotEmpty()) data.couponUrls.sp else data.couponUrls.pc)
                      onClickItem?.invoke(itemsF)
                }
            }

            nameTextView.text = data.name

            addressTextView.text = data.address

            Picasso.get().load(data.logoImage).into(imageView)

            favoriteImageView.apply{
                setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
                setOnClickListener{
                    if (isFavorite) {
                        onClickDeleteFavorite?.invoke(data)
                    }else{
                        onClickAddFavorite?.invoke(data)
                    }
                    notifyItemChanged(position)
                }
            }
        }
    }
}