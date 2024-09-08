package com.sherrif.churchapp.adapters




import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//import com.sherrif.churchapp.LabTestsActivity
import com.sherrif.churchapp.MainActivity
import com.sherrif.churchapp.R
//import com.sherrif.churchapp.models.Desk
//import com.sherrif.churchapp.models.LabTests
import com.google.android.material.textview.MaterialTextView
import com.sherrif.churchapp.models.Desk

// we provide context in below class to make it be an activity
class  DeskAdapter (var context: Context):
    RecyclerView.Adapter<DeskAdapter.ViewHolder>(){
    // name of our class is LabAdapter
    //Recycler view.adapter provided by android to work with recyclerview
    // Lab adapter.viewHolder:: it means adapter will work with view holder class named view holder
    // create a list and connect it with our model
    var itemList: List<Desk> = listOf()// its empty
    //var item lists means the  value can be changed
    // list of means there are no labs in the list initially
    //create a class which will hold our views in single_lab.xml
    //
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    //access single_lab.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //access/inflate the single_item.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_main ,parent, false)
        //pass the single lab to view holder
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // bind the data to viewss from the single_lab.xml
        //fid your three textviews in single_item
        val dektitle = holder.itemView.findViewById<MaterialTextView>(R.id.desk_title)
        val deskmesssge = holder.itemView.findViewById<MaterialTextView>(R.id.desk_message)
        // assume one lab, and bind data, it will loop all other labs
        val desk = itemList[position]
        dektitle.text = desk.desk_title
        deskmesssge.text =desk.message
        //when one lab is clicked move to lab test activity

        holder.itemView.setOnClickListener{
            // to navigate to each lab test on each lab click
            // we carry lab_id clicked with bundles(putextra)
//            var id = desk.desk_id
            //pass this id along with intent
//            val intent = Intent(context, LabTestsActivity::class.java)
//            // we use key 1 to save it
//            intent.putExtra("key1",id)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(intent)


        }


    }
    //count the number of items
    override fun getItemCount(): Int {
        return itemList.size//count how many items in each list
    }
    //function filter data
    fun filterList(filterlist:List<Desk>){
        itemList = filterlist
        notifyDataSetChanged()
    }

    //    Earlier we mention item list is empty
//we will get data from our api, the bring it to the below function
// the data you bring must follow the lab model
    fun setListItems(data: List<Desk>){
        itemList = data // link our data with the item list
        notifyDataSetChanged()
        // tell us the adapter class that our item list is loaded with data
    }
}


