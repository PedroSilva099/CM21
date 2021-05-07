package ipvc.estg.CM21

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.CM21.adapters.DES
import ipvc.estg.CM21.adapters.TUT
import ipvc.estg.CM21.api.EndPoints
import ipvc.estg.CM21.api.Report
import ipvc.estg.CM21.api.ServiceBuilder
import ipvc.estg.CM21.api.User
import ipvc.estg.CM21.report.AddReport
import ipvc.estg.CM21.report.ReportPreview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var users: List<User>
    private lateinit var iduser: String
    private lateinit var report: List<Report>


    private var click = false

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallBack: LocationCallback
    private lateinit var locationRequest: LocationRequest


    private  var newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        iduser = intent.getStringExtra("id").toString()




        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var position: LatLng
        sharedPreferences =
                getSharedPreferences(getString(R.string.spf), Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(R.string.id_sh.toString(), 0)
        val loc = Location("teste")
        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(
                    call: Call<List<Report>>,
                    response: Response<List<Report>>
            ) {
                if (response.isSuccessful) {
                    report = response.body()!!
                    for (rep in report) {

                        if (id == rep.user_id) {
                            position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                            mMap.addMarker(
                                    MarkerOptions().position(position)
                                            .title(rep.titulo + " - " + rep.descricao ).icon(
                                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                                            )
                            )
                        } else {
                            position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                            mMap.addMarker(
                                    MarkerOptions().position(position)
                                            .title(rep.titulo + " - " + rep.descricao).icon(
                                                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                                            )
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
              //  mMap.addMarker(MarkerOptions().position(loc).title("Marker"))
                // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                findViewById<TextView>(R.id.txtcoordenadas).setText("Lat: " + loc.latitude + " - Long: " + loc.longitude)

            }

        }

        //request creation
        createLocationRequest()



        val fabLocation = findViewById<FloatingActionButton>(R.id.fab2)
        fabLocation.visibility = View.GONE
        fabLocation.setOnClickListener {
            val intent = Intent(this@MapsActivity, AddReport::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
            finish()

        }


        val fabLista = findViewById<FloatingActionButton>(R.id.reportList)
        fabLista.visibility = View.GONE
        fabLista.setOnClickListener {
            val intent = Intent(this@MapsActivity, AddReport::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
            finish()

        }

        val fabNotas = findViewById<FloatingActionButton>(R.id.notasFab)
        fabNotas.visibility = View.GONE
        fabNotas.setOnClickListener {
            val intent = Intent(this@MapsActivity, MainActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)


        }

        val fabLogout = findViewById<FloatingActionButton>(R.id.logout)
        fabLogout.visibility = View.GONE
        fabLogout.setOnClickListener {
            val editor: SharedPreferences.Editor= sharedPreferences.edit()
            editor.clear()
            editor.commit()
            editor.apply()
            val intent = Intent(this@MapsActivity, MainLogin::class.java)
            startActivity(intent)
            finish()

        }

               val fab5 = findViewById<ExtendedFloatingActionButton>(R.id.add_fab)
               fab5.shrink()
               fab5.setOnClickListener {
                   if (!click){
                       fabLogout.visibility = View.VISIBLE
                       fabLocation.visibility = View.VISIBLE
                       fabLista.visibility = View.VISIBLE
                       fabNotas.visibility = View.VISIBLE

                       fab5.extend()
                       click = true
                   } else {

                       fabLogout.visibility = View.GONE
                       fabLocation.visibility = View.GONE
                       fabLista.visibility = View.GONE
                       fabNotas.visibility = View.GONE

                       fab5.shrink()
                       click = false
                   }

               }





    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
            )

            return
        } else {
            mMap.isMyLocationEnabled = true
        }

        mMap.setOnInfoWindowClickListener(this)

        setUpMap()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.all -> {
                mMap.clear()
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true

            }
            R.id.acident -> {
                mMap.clear()
                val id = sharedPreferences.getInt(R.string.spf.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 1) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }
            R.id.obras -> {
                mMap.clear()
                val id = sharedPreferences.getInt(R.string.spf.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 2) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }
            R.id.fire -> {
                mMap.clear()
                val id = sharedPreferences.getInt(R.string.spf.toString(), 0)
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.getReports()
                var position: LatLng
                call.enqueue(object : Callback<List<Report>> {
                    override fun onResponse(
                            call: Call<List<Report>>,
                            response: Response<List<Report>>
                    ) {
                        if (response.isSuccessful) {
                            report = response.body()!!
                            for (rep in report) {
                                if(rep.type_id == 3) {
                                    if (id == rep.user_id) {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                                    } else {
                                        position = LatLng(rep.latitude.toDouble(), rep.longitude.toDouble())
                                        mMap.addMarker(MarkerOptions().position(position).title(rep.titulo + " - " + rep.descricao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Report>>, t: Throwable) {
                        Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onInfoWindowClick(marker: Marker) {
        val intent = Intent(this, ImagePreview::class.java).apply {
            putExtra(TUT, marker.title)
            putExtra(DES, marker.snippet)
        }
        startActivity(intent)
        finish()
    }

    fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        } else {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastLocation = location

                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
                }
            }
        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }


}