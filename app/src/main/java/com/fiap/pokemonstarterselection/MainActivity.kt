package com.fiap.pokemonstarterselection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiap.pokemonstarterselection.ui.theme.PokemonStarterSelectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonStarterSelectionTheme {
                // Modifier é um elemento utilizado para decorar ou aumentar uma função Composable
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokemonStarterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// composable é a função que estamos


// criamos uma classe de dados reutilizavel
// data class é uma classe especial usada para armazenar dados.
data class Pokemon(
    val name: String = "",
    val imageRes: Int = R.drawable.pokeball_unselected
)

val starters = listOf(
    Pokemon("Bulbasaur", R.drawable.bulbassaur),
    Pokemon("Charmander", R.drawable.charmander),
    Pokemon("Squirtle", R.drawable.squirtle)
)


@Composable
// representa a tela principal do app
fun PokemonStarterScreen(modifier: Modifier = Modifier) {

    // remender serve para lembrar o estado e como o tipo é
    // mutableStateOf, se o estado muda, renderiza a tela novamente

    // quando rotaciona a tela, o estado é destruido
    // para isso não acontecer use rememberSalvable

    // ele só funciona para tipos primitivos
    // preciso ensinar para o Kotlin como restaurar o estado
    var pokemonSelected by remember {
        mutableStateOf(starters.first())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // comum separar por responsabilidades, mesmo que alguns
        // composables não sejam reutilizados
        PokeHeader("Escolha seu Pokémon Inicial")

        PokemonCard(pokemonSelected)

        // it é um parametro do Kotlin que indica o retorno
        PokemonOptionsList(
            options = starters,
            pokemonSelected = pokemonSelected,
            onSelected = { pokemonSelected = it },
        )
    }
}

@Composable
fun PokeHeader(label: String) {
    // parametros nomeados
    Text(
        text = label,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun PokemonCard(pokemon: Pokemon) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = pokemon.imageRes),
            contentDescription = pokemon.name,
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = pokemon.name.uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// componente especifico para uma opção de pokemon
@Composable
fun PokemonOption(
    selected: Boolean,
    pokemon: Pokemon,
    onSelected: (Pokemon) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onSelected(pokemon) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = if (selected)
                painterResource(
                    id =
                        R.drawable.pokeball_selected
                ) else painterResource(
                id = R.drawable.pokeball_unselected
            ),
            contentDescription = pokemon.name,
            modifier = Modifier.size(40.dp),
            colorFilter = if (isSystemInDarkTheme() &&
                !selected
            ) ColorFilter.tint(Color.White) else null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = pokemon.name, fontSize = 18.sp)
    }
}

// representa minha lista de opções
@Composable
fun PokemonOptionsList(
    options: List<Pokemon>,
    pokemonSelected: Pokemon,
    onSelected: (Pokemon) -> Unit,
) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {
        options.forEach { pokemon ->
            PokemonOption(
                pokemon = pokemon,
                selected = pokemon == pokemonSelected,
                onSelected = onSelected
            )
        }
    }
}

