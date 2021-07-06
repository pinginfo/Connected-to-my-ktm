package com.ping.connectedtomyktm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.ping.connectedtomyktm.R
import com.ping.connectedtomyktm.bluetooth.Communication
import com.ping.connectedtomyktm.viewModel.IsConnectedModel

class FragmentHome : Fragment() {
    private lateinit var imageView: ImageView
    private lateinit var textIsConnected: TextView
    private var bikeIsConnected: Boolean = false
    private val connectedModel: IsConnectedModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

        this.textIsConnected = view.findViewById(R.id.textViewConnectionState)
        this.imageView = view.findViewById(R.id.imageViewBike)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.imageView.setOnClickListener {
            if (this.bikeIsConnected) {
               Communication.close()
            } else {
                this.textIsConnected.setText(R.string.attempt_connect)
                Communication.open()
            }
        }

        this.connectedModel.selected.observe(viewLifecycleOwner, { item ->
            this.bikeIsConnected = item
            if (item) {
                this.imageView.setImageResource(R.drawable.ic_motorbike_orange)
                this.textIsConnected.setText(R.string.motorcycle_connected)
            } else {
                this.imageView.setImageResource(R.drawable.ic_motorbike)
                this.textIsConnected.setText(R.string.motorcycle_disconnected)
            }
        })

    }
}