package stu.ntdat.chatisfun.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class DataBindingListAdapter<MODEL, DATA_BINDING_CLASS: ViewDataBinding>(
    @LayoutRes val itemLayout: Int
): ListAdapter<MODEL, DataBindingViewHolder<DATA_BINDING_CLASS>>(BestBaseCallbackItem<MODEL>()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<DATA_BINDING_CLASS> {
        val holder = DataBindingViewHolder.from<DATA_BINDING_CLASS>(parent, itemLayout)
        initStateViewHolder(holder)
        return holder
    }

    /**
     * Function is called before return from onCreateViewHolder method
     *
     * @param holder: The view holder created by onCreateViewHolder
     */
    open fun initStateViewHolder(holder: DataBindingViewHolder<DATA_BINDING_CLASS>){}
}

open class DataBindingViewHolder<DATA_BINDING_CLASS: ViewDataBinding> private constructor(
    private val binding: DATA_BINDING_CLASS
): RecyclerView.ViewHolder(binding.root) {
    fun bind(block: (binding: DATA_BINDING_CLASS) -> Unit){
        block(binding)
    }
    companion object{
        fun <T: ViewDataBinding> from(container: ViewGroup, @LayoutRes layoutRes: Int): DataBindingViewHolder<T>{
            return DataBindingViewHolder(container.inflateBinding(layoutRes))
        }
    }
}
fun <T: ViewDataBinding> ViewGroup.inflateBinding(@LayoutRes layoutId: Int, attach: Boolean = false): T{
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, attach)
}

abstract class BaseCallbackItem<MODEL>: DiffUtil.ItemCallback<MODEL>(){
    override fun areItemsTheSame(oldItem: MODEL, newItem: MODEL) = oldItem === newItem
}

open class BestBaseCallbackItem<ITEM>: BaseCallbackItem<ITEM>() {
    override fun areContentsTheSame(oldItem: ITEM, newItem: ITEM) = oldItem.toString() == newItem.toString()
}