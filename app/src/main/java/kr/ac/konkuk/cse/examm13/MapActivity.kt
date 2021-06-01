package kr.ac.konkuk.cse.examm13

import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kr.ac.konkuk.cse.R
import kr.ac.konkuk.cse.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var binding: ActivityMapBinding
    lateinit var googleMap: GoogleMap
    var loc = LatLng(37.554752, 126.970631)
    var jejuLoc = LatLng(33.507271, 126.493104)
    private val arrLoc = ArrayList<LatLng>()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    var startupdate = false


    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult?) {
                super.onLocationResult(location)

                if (location == null || location.locations.size == 0) return

                loc = LatLng(
                    location.locations[location.locations.size - 1].latitude,
                    location.locations[location.locations.size - 1].longitude
                )

                setCurrentLocation(loc)
                Log.i("location", "locationCallback()")
            }
        }
    }

    private fun setCurrentLocation(location: LatLng) {
        val option = MarkerOptions()
        option.position(loc)
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        googleMap.addMarker(option)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16.0f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()

//        init항Location()

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(jejuLoc, 16.0f))
            googleMap.setMinZoomPreference(10.0f)
            googleMap.setMaxZoomPreference(18.0f)


            val jejuMarker = MarkerOptions()
            jejuMarker.position(jejuLoc)
            jejuMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            jejuMarker.title("제주도")
            jejuMarker.snippet("제주국제공항")

            googleMap.addMarker(jejuMarker).showInfoWindow()
//
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f))
//            googleMap.setMinZoomPreference(10.0f)
//            googleMap.setMaxZoomPreference(18.0f)
//
//            val option = MarkerOptions()
//            option.position(loc)
//            option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//            option.title("역")
//            option.snippet("서울역")
//
//            val marker = googleMap.addMarker(option)
//            marker.showInfoWindow()
//
//            googleMap.setOnMapClickListener {
//                arrLoc.add(it)
//                googleMap.clear()
//
//                val option = MarkerOptions()
//                option.position(it)
//                option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//
//                googleMap.addMarker(option)
//
//                val option2 = PolylineOptions().color(Color.GREEN).addAll(arrLoc)
//                googleMap.addPolyline(option2)
//                val option3 =
//                    PolygonOptions().fillColor(Color.argb(100, 255, 255, 0))
//                        .strokeColor(Color.GREEN).addAll(arrLoc)
//                googleMap.addPolygon(option3)
//            }
        }
    }

    private fun initSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            ArrayList<String>()
        )
        adapter.add("Hybrid")
        adapter.add("Normal")
        adapter.add("Satellite")
        adapter.add("Terrian")

        binding.apply {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
                        }
                        1 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
                        }
                        2 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
                        }
                        3 -> {
                            googleMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        startLocationUpdate()
    }

    override fun onPause() {
        super.onPause()
//        stopLocationUpdate()
    }

    private fun stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        startupdate = false
        Log.i("location", "stopLocationUpdate()")
    }

    private fun startLocationUpdate() {
        if (checkLocationPermission() && checkLocationServiceStatus()) {
            startupdate = true
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest, locationCallback, Looper.getMainLooper()
            )
            Log.i("location", "startLocationUpdates()")
        }
    }

    private fun checkLocationServiceStatus(request: Boolean = true): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val result = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!result && request) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("위치 서비스 비활성화됨")
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다\n위치 설정을 허용하겠습니까?")
            builder.setPositiveButton("설정") { dialog, id ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, 1000)
            }
            builder.setNegativeButton("취소") { dialog, id ->
                dialog.dismiss()
                setCurrentLocation(loc)
            }
            builder.create().show()
        }

        return result
    }

    private fun checkLocationPermission(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!result) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }

        return result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1000 -> {
                if (checkLocationServiceStatus(false)) {
                    Toast.makeText(this, "위치 서비스 활성화됨", Toast.LENGTH_SHORT).show()
                    startLocationUpdate()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            100 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    startLocationUpdate()
                }
            }
        }
    }
}