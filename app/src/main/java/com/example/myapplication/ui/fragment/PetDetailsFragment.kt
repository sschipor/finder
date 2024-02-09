package com.example.myapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.myapplication.data.model.AnimalData
import com.example.myapplication.databinding.FragmentPetDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val ARG_ANIMAL = "animal"

class PetDetailsFragment : BottomSheetDialogFragment() {

    private var animalData: AnimalData? = null
    private var binding: FragmentPetDetailsBinding? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animalData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_ANIMAL, AnimalData::class.java)
            } else {
                it.getParcelable(ARG_ANIMAL)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        binding?.model = animalData
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val TAG = PetDetailsFragment::class.java.simpleName
        //could pass just ID and fetch the animalData details from remote before display
        @JvmStatic
        fun newInstance(animalData: AnimalData) =
            PetDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_ANIMAL, animalData)
                }
            }
    }
}