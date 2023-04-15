package com.example.memorybox.presentation.views

import android.graphics.*
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.memorybox.R
import com.example.memorybox.data.api.APIService
import com.example.memorybox.data.api.RetrofitHelper
import com.example.memorybox.data.models.Memory
import com.example.memorybox.data.repositories.Repository
import com.example.memorybox.databinding.FragmentMapBinding
import com.example.memorybox.presentation.viewmodel.MapViewModel
import com.example.memorybox.presentation.viewmodel.MapViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() , GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    private var currentLocation:Location? = null
    private var locationReady:Boolean = false
    private lateinit var mapViewModel: MapViewModel
    private var memories:List<Memory>? =null
    private var mapReady:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = RetrofitHelper.getInstance().create(APIService::class.java)
        val repository = Repository(apiService)
        mapViewModel = ViewModelProvider(this, MapViewModelFactory(repository)).get(
            MapViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,
            com.example.memorybox.R.layout.fragment_map, container, false)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        mapViewModel.getMemories()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync{
            googleMap = it
            googleMap.setMyLocationEnabled(true);
            mapReady = true


            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            updateMap()

        }

        binding.addMemoryFab.setOnClickListener {

            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                currentLocation = it
                locationReady = true
                Log.d("location", currentLocation.toString())
                val bundle = Bundle()
                bundle.putString("lat", currentLocation!!.latitude.toString())
                bundle.putString("lng", currentLocation!!.longitude.toString())
                Navigation.findNavController(view!!).navigate(com.example.memorybox.R.id.action_mapFragment3_to_addMemoryFragment,bundle)

            }

        }

        mapViewModel.getMemoryLiveData().observe(this.requireActivity(),{
            memories=it
            Log.d("map","update map from live data")
            updateMap()

        })

        return binding.root
    }




    fun updateMap(){
        Log.d("map","update map")

        if(mapReady == true && memories != null){
            memories!!.forEach {memory->
                Log.d("map","${memory.lat!!.toDouble()} ${memory.lng!!.toDouble()}")

                val marker = LatLng(memory.lat!!.toDouble(),memory.lng!!.toDouble())

                Glide.with(this).asBitmap()
                    .load(memory.creatorImg)
                    .into(object : SimpleTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            @Nullable transition: Transition<in Bitmap?>?
                        ) {
                            googleMap.addMarker(
                                MarkerOptions().position(marker).title("Created by ${memory.creatorName}")
                                    .icon(BitmapDescriptorFactory.fromBitmap(getCroppedBitmap(resource)))



                            )!!.setTag(memory)

                        }
                    })



                    googleMap.setOnMarkerClickListener(this)





                //googleMap.addMarker(MarkerOptions().position(marker).title("Created by ${memory.creatorName}"))
            }
        }
    }


    fun getCroppedBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(
            (bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(),
            (bitmap.width / 2).toFloat(), paint
        )
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        val memory:Memory = marker.tag as Memory

        val bundle = Bundle()
        bundle.putString("desc", memory.desc)
        bundle.putString("imgLink", memory.imgLink)
        bundle.putString("creatorName", memory.creatorName)
        Navigation.findNavController(view!!).navigate(R.id.action_mapFragment3_to_descBottomSheetFragment,bundle)


        return true
    }


}


