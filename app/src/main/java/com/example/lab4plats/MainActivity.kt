package com.example.lab4plats


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.painterResource
import com.example.lab4plats.ui.theme.Lab4PlatsTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab4PlatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MainScreen()
                }
            }
        }
    }
}

data class ItemCard(val nombre:String, val url: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var recipe by rememberSaveable { mutableStateOf(" ") }
    var url by rememberSaveable { mutableStateOf(" ") }
    val listaRecetas = remember { mutableStateListOf<String>() }
    val listaImagenes = remember { mutableStateListOf<String>() }
    var card: ItemCard


    //Título
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        Text(
            text = "Recetario",
            modifier = Modifier.wrapContentHeight(),
            fontSize = 50.sp,
            color = Color.Blue,
            fontWeight= FontWeight.Bold
        )
    }

    //Textfield para el nombre de la receta
    Column(
        modifier = Modifier
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        TextField(
            value = recipe,
            onValueChange = { recipe = it },
            label = { Text("Ingrese el nombre de la receta:")})
    }

    //Textfield para el url de la imagen
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 170.dp)
    ){
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Ingrese el link de la imagen:")}
        )
    }

    //Boton de agregar
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 235.dp)
    ){
        Button(onClick = {
            if (recipe.isNotBlank() && url.isNotBlank()) {
                listaRecetas.add(recipe)
                listaImagenes.add(url)
                recipe=""
                url=""
            }
        }) {
            Text("Agregar receta")
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            itemsIndexed(listaRecetas) { index, nombre ->
                val link = listaImagenes[index]
                card = ItemCard(nombre, link)
                AddCard(card=card)
            }
        }
    }
}


@Composable
fun AddCard(card: ItemCard) {
    val nombre = card.nombre
    val url = card.url

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier.padding(6.dp)
        ){
            Image(
                painter = painterResource(android.R.drawable.ic_dialog_alert), //prueba con placeholder
                //painter = rememberAsyncImagePainter(url), //prueba con el url del usuario
                //painter = rememberAsyncImagePainter("https://assets.unileversolutions.com/recipes-v2/216635.jpg"), //prueba con un url ya dado

                contentDescription = null,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .padding(3.dp)
                    .width(20.dp)
                    .height(20.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(text = nombre)

        }
    }

}