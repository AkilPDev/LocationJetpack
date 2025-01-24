package com.example.mylocationtrackjetpack

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mylocationtrackjetpack.ui.theme.MyLocationTrackJetpackTheme
import java.security.Permission

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyLocationTrackJetpackTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ){
                    myApp()
                }



            }
        }
    }
}

@Composable
fun myApp(){
    var context = LocalContext.current
    var locationUtils = LocationUtils(context)
    locationScreen(context = context, locationUtils = locationUtils)
}

@Composable
fun locationScreen(context: Context, locationUtils: LocationUtils ){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        var requestPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult ={ permissions ->
                if(permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){
                    //Permission granded
                }else{
                    //Request the permission
                    val rationaleRequire = ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                            || ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)

                    if(rationaleRequire){
                        Toast.makeText(context, "Location permision require for this feature to work", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context, "Location permision require please enable in the setting", Toast.LENGTH_LONG).show()
                    }
                }

            }
        )

        Text(text = "Get Location")
        Button(onClick = {
            if(locationUtils.hasLocationPermission(context)){
                //Permission grand
            }else{
                //permission not grand
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }) {
            Text(text = "Get the location")
        }

    }
}

