package id.mailstudio.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.mailstudio.favorite.databinding.FoodFavoriteListItemBinding
import id.mailstudio.foodcatalogue.R
import id.mailstudio.foodcatalogue.domain.FoodUIModel


class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.FavoriteVH>() {

    private var listItem = listOf<FoodUIModel>()
    var onItemClick: ((FoodUIModel) -> Unit)? = null

    class FavoriteVH(val viewBinding: FoodFavoriteListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteVH {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val viewBinding = FoodFavoriteListItemBinding.inflate(layoutInflater, parent, false)
        return FavoriteVH(viewBinding)
    }

    override fun onBindViewHolder(holder: FavoriteVH, position: Int) {
        val data = listItem[position]
        holder.viewBinding.tvFoodTitle.text = data.foodName
        holder.viewBinding.tvFoodArea.text = data.foodArea
        if (data.isFavorite) {
            holder.viewBinding.tvHomeIsFavorite.setBackgroundResource(R.drawable.ic_favorite_fill)
        } else {
            holder.viewBinding.tvHomeIsFavorite.setBackgroundResource(R.drawable.ic_favorite_non_fill)
        }
        Glide.with(holder.itemView.context)
            .load(data.foodImage)
            .into(holder.viewBinding.ivFoodThumbnail)

        holder.viewBinding.foodItemLayout.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(items: List<FoodUIModel>?) {
        if (items == null) return
        listItem = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}