package id.mailstudio.search.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.mailstudio.foodcatalogue.R
import id.mailstudio.foodcatalogue.databinding.FoodListItemBinding
import id.mailstudio.foodcatalogue.domain.FoodUIModel


class FoodSearchAdapter : RecyclerView.Adapter<FoodSearchAdapter.FoodListVH>() {

    private var listItem = listOf<FoodUIModel>()
    var onItemClick: ((FoodUIModel) -> Unit)? = null

    class FoodListVH(val viewBinding: FoodListItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListVH {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val viewBinding = FoodListItemBinding.inflate(layoutInflater, parent, false)
        return FoodListVH(viewBinding)
    }

    override fun onBindViewHolder(holder: FoodListVH, position: Int) {
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