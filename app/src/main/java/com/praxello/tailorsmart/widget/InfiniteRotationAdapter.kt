package com.praxello.tailorsmart.widget

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.praxello.tailorsmart.MainActivity
import com.praxello.tailorsmart.ProductListActivity
import com.praxello.tailorsmart.R
import com.praxello.tailorsmart.model.Offer

class InfiniteRotationAdapter(private val mContext: Context, itemList: List<Offer>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list: List<Offer> = listOf(itemList.last()) + itemList + listOf(itemList.first())
//    private val list: List<Offer> = itemList

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.let {
            val obj = list[position]
            Glide.with(mContext).load("http://103.127.146.25/~tailors/Tailorsmart/mobileimages/category/"
                    + obj.id + ".jpg").thumbnail(0.1f).into(holder.ivOffer!!)
            holder.itemView.setOnClickListener { view ->
                mContext.startActivity(Intent(mContext, ProductListActivity::class.java)
                        .putExtra("categoryId", obj.id)
                        .putExtra("data", (mContext as MainActivity).data))
            }
            if (!TextUtils.isEmpty(obj.title)) {
                holder.tvOffer?.text = obj.title
                holder.tvOffer?.setVisibility(View.VISIBLE)
            } else {
                holder.tvOffer?.setVisibility(View.GONE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_offer, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = list.size

    internal class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        @BindView(R.id.ivOffer)
        var ivOffer: ImageView? = null
//        @BindView(R.id.tvOffer)
        var tvOffer: TextView? = null

        init {
//            ButterKnife.bind(this, view)
            ivOffer = view.findViewById<ImageView>(R.id.ivOffer)
            tvOffer = view.findViewById<TextView>(R.id.tvOffer)
        }
    }
}