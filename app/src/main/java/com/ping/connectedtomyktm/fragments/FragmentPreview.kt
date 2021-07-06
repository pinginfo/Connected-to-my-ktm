package com.ping.connectedtomyktm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ping.connectedtomyktm.R
import com.ping.connectedtomyktm.viewModel.DataModel
import androidx.fragment.app.activityViewModels

class FragmentPreview : Fragment() {
    // TODO: https://developer.android.com/topic/libraries/data-binding
    private lateinit var turnIcon: ImageView
    private lateinit var eta: TextView
    private lateinit var dist2Target: TextView
    private lateinit var turnDist: TextView
    private lateinit var turnUnit: TextView
    private lateinit var turnInfo: TextView
    private lateinit var turnRoad: TextView

    private val model: DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_preview, container, false)

        this.turnIcon = view.findViewById(R.id.imageViewTurnIcon)
        this.eta = view.findViewById(R.id.textViewEta)
        this.dist2Target = view.findViewById(R.id.textViewDist2Target)
        this.turnDist = view.findViewById(R.id.textViewTurnDist)
        this.turnUnit = view.findViewById(R.id.textViewTurnUnit)
        this.turnInfo = view.findViewById(R.id.textViewTurnInfo)
        this.turnRoad = view.findViewById(R.id.textViewTurnRoad)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.selected.observe(viewLifecycleOwner, { item ->
            this.eta.text = item.eta
            this.dist2Target.text = item.dist2Target
            this.turnDist.text = item.turnDist
            this.turnUnit.text = item.turnDistUnit
            this.turnInfo.text = item.turnInfo
            this.turnRoad.text = item.turnRoad
            if (item.turnIcon.equals("END")) {
               this.turnIcon.setImageResource(R.drawable.ic_end)
            } else if (item.turnIcon.equals("HEAVY_LEFT")) {
                this.turnIcon.setImageResource(R.drawable.ic_heavy_left)
            } else if (item.turnIcon.equals("HEAVY_RIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_heavy_right)
            } else if (item.turnIcon.equals("KEEP_LEFT")) {
                this.turnIcon.setImageResource(R.drawable.ic_keep_left)
            } else if (item.turnIcon.equals("KEEP_RIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_keep_right)
            } else if (item.turnIcon.equals("QUITE_LEFT")) {
                this.turnIcon.setImageResource(R.drawable.ic_quite_left)
            } else if (item.turnIcon.equals("QUITE_RIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_quite_right)
            } else if (item.turnIcon.equals("LIGHT_LEFT")) {
                this.turnIcon.setImageResource(R.drawable.ic_light_left)
            } else if (item.turnIcon.equals("LIGHT_RIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_light_right)
            } else if (item.turnIcon.equals("GO_STRAIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_go_straight)
            } else if (item.turnIcon.equals("UTURN_LEFT")) {
                this.turnIcon.setImageResource(R.drawable.ic_uturn_left)
            } else if (item.turnIcon.equals("UTURN_RIGHT")) {
                this.turnIcon.setImageResource(R.drawable.ic_uturn_right)
            } else if (item.turnIcon.equals("LEAVE_HIGHWAY_RIGHT_LANE")) {
                this.turnIcon.setImageResource(R.drawable.ic_leave_highway_right_lane)
            } else if (item.turnIcon.equals("LEAVE_HIGHWAY_LEFT_LANE")) {
                this.turnIcon.setImageResource(R.drawable.ic_leave_highway_left_lane)
            } else if (item.turnIcon.equals("FERRY")) {
                this.turnIcon.setImageResource(R.drawable.ic_ferry)
            } else if (item.turnIcon.equals("RAB_SECT_4_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_4_lh)
            } else if (item.turnIcon.equals("RAB_SECT_4_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_4_rh)
            } else if (item.turnIcon.equals("RAB_SECT_6_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_6_lh)
            } else if (item.turnIcon.equals("RAB_SECT_6_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_6_rh)
            } else if (item.turnIcon.equals("RAB_SECT_8_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_8_lh)
            } else if (item.turnIcon.equals("RAB_SECT_8_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_8_rh)
            } else if (item.turnIcon.equals("RAB_SECT_10_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_10_lh)
            } else if (item.turnIcon.equals("RAB_SECT_10_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_10_rh)
            } else if (item.turnIcon.equals("RAB_SECT_12_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_12_lh)
            } else if (item.turnIcon.equals("RAB_SECT_12_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_12_rh)
            } else if (item.turnIcon.equals("RAB_SECT_14_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_14_lh)
            } else if (item.turnIcon.equals("RAB_SECT_14_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_14_rh)
            } else if (item.turnIcon.equals("RAB_SECT_16_LH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_16_lh)
            } else if (item.turnIcon.equals("RAB_SECT_16_RH")) {
                this.turnIcon.setImageResource(R.drawable.ic_rab_sect_16_rh)
            }
        })
    }

}