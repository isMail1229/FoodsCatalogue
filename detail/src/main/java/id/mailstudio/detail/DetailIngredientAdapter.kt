package id.mailstudio.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.mailstudio.detail.databinding.FoodIngredientItemBinding
import id.mailstudio.foodcatalogue.domain.FoodIngredientUIModel

class DetailIngredientAdapter : RecyclerView.Adapter<DetailIngredientAdapter.IngredientVH>() {

    private var listItem = listOf<FoodIngredientUIModel>()
    class IngredientVH(val viewBinding: FoodIngredientItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientVH {
        val layoutInflater =
            LayoutInflater.from(parent.context)
        val viewBinding = FoodIngredientItemBinding.inflate(layoutInflater, parent, false)
        return IngredientVH(viewBinding)
    }

    override fun onBindViewHolder(holder: IngredientVH, position: Int) {
        val data = listItem[position]
        holder.viewBinding.tvIngredient.text = data.foodIngredient
        holder.viewBinding.tvMeasurement.text = data.foodMeasure
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItem(items: List<FoodIngredientUIModel>?) {
        if (items == null) return
        listItem = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}