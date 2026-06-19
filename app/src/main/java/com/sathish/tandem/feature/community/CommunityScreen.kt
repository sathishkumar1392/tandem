package com.sathish.tandem.feature.community

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.sathish.tandem.domain.model.CommunityMember
import com.sathish.tandem.feature.CommunityViewModel
import com.sathish.tandem.feature.community.components.MemberCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun CommunityScreen(
    viewModel: CommunityViewModel = koinViewModel()
){
    val lazyPagingItems = viewModel.community.collectAsLazyPagingItems()
    val likedIds by viewModel.likedIds.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {

        if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Failed to load community")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    lazyPagingItems.retry()
                }) {
                    Text("Retry")
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Community",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
                )
            }

            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id },
            ){  index ->
                val member  = lazyPagingItems[index]
                member?.let {
                    MemberCard(
                        member = member.copy(isLiked = member.id.toString() in likedIds),
                        onLikeClick = viewModel::onLikedClick
                    )
                }
            }

            when (val appendState = lazyPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Failed to load more.")
                            Spacer(modifier = Modifier.height(4.dp))
                            Button(onClick = { lazyPagingItems.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                else -> {}
            }

        }

    }
}

object PreviewData {
    val fakeMember = CommunityMember(
        id = 1,
        firstName = "Jonathan",
        pictureUrl = "",
        nativeLanguage = "EN",
        learnsLanguage = "RU",
        referenceCnt = 12,
        topic = "I can help you learn English and Spanish.",
        isNew = false,
        isLiked = false
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
  MemberCard(
      member = PreviewData.fakeMember,
      onLikeClick = {}
  )
}
