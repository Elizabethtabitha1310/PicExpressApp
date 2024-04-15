package com.example.picexpressapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPicExpressApp()
        }
    }
}

@Composable
fun JetPicExpressApp() {
    Surface(color = Color.White) {
        MainScreen()
    }
}

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val takePicture = rememberLauncherForActivityResult(
        contract = TakePictureContract()
    ) { result ->
        if (result) {
            Toast.makeText(context, "Image captured successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }
    val pickGalleryImage = rememberLauncherForActivityResult(
        contract = PickGalleryImageContract()
    ) { uri ->

        if (uri != null) {
            Toast.makeText(context, "Image selected from gallery: $uri", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to select image from gallery", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.equitylogo),
            contentDescription = "Placeholder Image",
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )
        Button(
            onClick = {pickGalleryImage.launch(Unit) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Select Image from Gallery")
        }
            Button(
                onClick = { takePicture.launch(Unit) },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Capture Image from Camera")
            }
        }
    }
class TakePictureContract : ActivityResultContract<Unit, Boolean>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}

class PickGalleryImageContract : ActivityResultContract<Unit, Uri?>() {
    override fun createIntent(context: Context, input: Unit): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return intent?.data
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewJetPicExpressApp() {
    JetPicExpressApp()
}