package com.ping.connectedtomyktm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.ping.connectedtomyktm.R
import com.ping.connectedtomyktm.bluetooth.Communication
import com.ping.connectedtomyktm.entities.SendingObject

class FragmentTesting : Fragment() {
    private lateinit var editTextEta: EditText
    private lateinit var editTextDist2Target: EditText
    private lateinit var editTextTurnDist: EditText
    private lateinit var editTextTurnDistUnit: EditText
    private lateinit var editTextTurnInfo: EditText
    private lateinit var editTextTurnRoad: EditText
    private lateinit var spinnerTurnIcon: Spinner
    private lateinit var buttonTesting: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_testing, container, false)

        this.editTextEta = view.findViewById(R.id.editTextEta)
        this.editTextDist2Target = view.findViewById(R.id.editTextDist2Target)
        this.editTextTurnDist = view.findViewById(R.id.editTextTurnDist)
        this.editTextTurnDistUnit = view.findViewById(R.id.editTextTurnDistUnit)
        this.editTextTurnInfo = view.findViewById(R.id.editTextTurnInfo)
        this.editTextTurnRoad = view.findViewById(R.id.editTextTurnRoad)
        this.spinnerTurnIcon = view.findViewById(R.id.spinnerTurnIcon)
        this.buttonTesting = view.findViewById(R.id.buttonTesting)

        this.spinnerTurnIcon.adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                SendingObject.TurnIcon.values()
            )
        }

        this.buttonTesting.setOnClickListener {
            val obj = SendingObject()
            obj.eta = this.editTextEta.text.toString()
            obj.dist2Target = this.editTextDist2Target.text.toString()
            obj.turnDist = this.editTextTurnDist.text.toString()
            obj.turnDistUnit = this.editTextTurnDistUnit.text.toString()
            obj.turnInfo = this.editTextTurnInfo.text.toString()
            obj.turnRoad = this.editTextTurnRoad.text.toString()
            obj.turnIcon = this.spinnerTurnIcon.selectedItem.toString()
            obj.uiContext = "guidance"
            Communication.send(obj)
        }

        return view
    }
}