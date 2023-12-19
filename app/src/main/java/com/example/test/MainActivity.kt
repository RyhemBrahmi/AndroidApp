@file:Suppress("KotlinConstantConditions")

package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test.ui.theme.TestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MyUI()
                    }
                }
            }
        }
    }
}

@Composable
fun GameRow(game: Game) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TeamScoreColumn(teamName = game.homeTeam, teamAbbreviation = game.homeTeamAbbreviation)
                TeamDateColumn(score = game.score,
                               gameDate = game.gameDate,
                               gameTime = game.gameTime
                                  )
                TeamScoreColumn(teamName = game.awayTeam, teamAbbreviation = game.awayTeamAbbreviation)

            }



        }
    }
}

@Composable
fun TeamScoreColumn(teamName: String, teamAbbreviation: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          //  .background(Color.Red)
            .width(140.dp)


    ) {
        Text(text = teamName, color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center
        )
        Text(text = teamAbbreviation, color = Color.Gray)
        // If you want to add score here in team column, you can do it
    }
}
@Composable
fun TeamDateColumn(score:String,gameDate: String, gameTime:String ){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            // .padding(vertical = 4.dp, horizontal = 8.dp)
            // .background(Color.Gray)
            .width(100.dp)) {
        Text(text = gameDate, color = Color.Gray)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(15.dp)
                .background(Color(0xFF4CAF50))
                .padding(vertical = 4.dp, horizontal = 8.dp)
            //.padding(vertical = 4.dp, horizontal = 8.dp);

        ) {
            Text(
                text = score,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )


        }
        Text(text = gameTime, color = Color.Gray)
        // If you want to add score here in team column, you can do it
    }
}



data class Game(
    val homeTeam: String,
    val awayTeam: String,
    val score: String,
    val gameDate: String,
    val gameTime: String,
    val homeTeamAbbreviation: String,
    val awayTeamAbbreviation: String
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyUI() {
    val tabItems = listOf(
        TabItem(title = "Games"),
        TabItem(title = "Teams"),
    )

    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }

    // pager state
    var pagerState = rememberPagerState {
        tabItems.size
    }


    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(text = "Football App", color= Color(0xFFFFFFFF))  },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EE)
        )

    )
    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .background(Color(0xFFEEEEEE))

    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = (index == selectedTabIndex),
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = selectedTabIndex)
                        }
                    },
                    text = {
                        Text(text = item.title, color= Color(0xFFFFFFFF) )
                    },
                    modifier = Modifier.background(Color(0xFF6200EE))
                    ,
                )
            }
        }
// Sample data for teams
        val teams = listOf(
            Team("Cleveland Cavaliers", R.drawable.clevelandcavaliers),
            Team("Oklahoma City Thunder", R.drawable.oklahomacitythunder),
            Team("Sacramento Kings", R.drawable.sacramentokings),
            Team("Golden State Warriors", R.drawable.goldenstatewarriors),
            Team("Toronto Raptors", R.drawable.torontoraptors),
            Team("Portland Trail Blazers", R.drawable.portlandtrailblazers),
            Team("Dallas Marvericks", R.drawable.dallasmarvericks),
            Team("Denver Nuggets", R.drawable.denvernuggets),
            Team("Memphis Grizzlies", R.drawable.memphisgrizzlies),
            Team("San Antonio Spurs", R.drawable.sanantoniospurs),
            Team("Houston Rockets", R.drawable.houstonrockets),
            Team("New York Knicks", R.drawable.newyorkknicks),
            Team("Phoenix Suns", R.drawable.phoenixsuns),
            Team("Miami Heat", R.drawable.miamiheat)
            // Add more teams and their corresponding drawable flags here
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            when (index) {
                0 -> GamesList()
                1 -> TeamsPage(teams)
            }
        }

        LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
            if (!pagerState.isScrollInProgress) {
                selectedTabIndex = pagerState.currentPage
            }
        }
    }
}

@Composable
fun GamesList() {
    val games = listOf(
        Game(
            homeTeam = "Cleveland Cavaliers",
            awayTeam = "Oklahoma City Thunder",
            score = "0-0",
            gameDate = "04-01-2020",
            gameTime = "7:30",
            homeTeamAbbreviation = "CLE",
            awayTeamAbbreviation = "OKC"
        ),
        Game(
            homeTeam = "Sacramento Kings",
            awayTeam = "Golden State Warriors",
            score = "0-0",
            gameDate = "06-01-2020",
            gameTime = "10:00",
            homeTeamAbbreviation = "SAC",
            awayTeamAbbreviation = "GSW"
        ),
        Game(
            homeTeam = "Toronto Raptors",
            awayTeam = "Portland Trail Blazers",
            score = "0-0",
            gameDate = "07-01-2020",
            gameTime = "7:00",
            homeTeamAbbreviation = "TOR",
            awayTeamAbbreviation = "POR"
        ),
        Game(
            homeTeam = "Dallas Marvericks",
            awayTeam = "Denver Nuggets",
            score = "0-0",
            gameDate = "08-01-2020",
            gameTime = "7:30",
            homeTeamAbbreviation = "MEM",
            awayTeamAbbreviation = "SAS"
        ),
        Game(
            homeTeam = "Memphis Grizzlies",
            awayTeam = "San Antonio Spurs",
            score = "0-0",
            gameDate = "10-01-2020",
            gameTime = "8:00",
            homeTeamAbbreviation = "MEM",
            awayTeamAbbreviation = "SAS"
        ),
        Game(
            homeTeam = "Houston Rockets",
            awayTeam = "Portland Trail Blazers",
            score = "0-0",
            gameDate = "15-01-2020",
            gameTime = "9:30",
            homeTeamAbbreviation = "HOU",
            awayTeamAbbreviation = "POR"
        ),
        Game(
            homeTeam = "New York Knicks",
            awayTeam = "Phoenix Suns",
            score = "0-0",
            gameDate = "16-01-2020",
            gameTime = "7:30",
            homeTeamAbbreviation = "NYK",
            awayTeamAbbreviation = "PHX"
        ),
        Game(
            homeTeam = "Oklahoma City Thunder",
            awayTeam = "Miami Heat",
            score = "0-0",
            gameDate = "17-01-2020",
            gameTime = "8:00",
            homeTeamAbbreviation = "OKC",
            awayTeamAbbreviation = "MIA"
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()

    ) {
        items(games) { game ->
            GameRow(game)
        }
    }
}
@Composable
fun TeamsPage(teams: List<Team>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier
            //.padding(horizontal = 8.dp)

            .fillMaxWidth()
            .padding(4.dp)


                 ) {
        items(teams) { team ->
            TeamRow(team)
        }
    }
}
@Composable
fun TeamRow(team: Team) {
    Card (modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
        elevation = 10.dp) {
        Row(
            modifier = Modifier

                .fillMaxWidth()
               .padding(4.dp),
                //.background(Color.White)
            //    .padding(10.dp),


            //horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically


        ) {
            Image(
                painter = painterResource(id = team.flagDrawableResId),
                contentDescription = "${team.name} flag",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(text = team.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black)
        }
    }

}




data class TabItem(
    val title: String
)
data class Team(
    val name: String,
    val flagDrawableResId: Int
)
@Preview(showBackground = true)
@Composable
fun GameRowPreview() {
    GameRow(
        game = Game(
            homeTeam = "Cleveland Cavaliers",
            awayTeam = "Oklahoma City Thunder",
            score = "0-0",
            gameDate = "04-01-2020",
            gameTime = "7:30",
            homeTeamAbbreviation = "CLE",
            awayTeamAbbreviation = "OKC"
        )
    )
}
@Preview(showBackground = true)
@Composable
fun TeamRowPreview() {
   TeamRow(team =
   Team("Cleveland Cavaliers", R.drawable.clevelandcavaliers),

       )
}
@Preview(showBackground = true)
@Composable
fun MyUIPreview() {
    MyUI()
}
