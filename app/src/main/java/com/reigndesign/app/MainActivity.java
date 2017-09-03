package com.reigndesign.app;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.reigndesign.app.adapters.NewsAdapter;
import com.reigndesign.app.models.New;
import com.reigndesign.app.network.News;
import com.reigndesign.app.network.ServiceGenerator;
import com.reigndesign.app.network.mappers.NewMapper;
import com.reigndesign.app.network.models.NewsEnvelope;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout swiperefresh;

    private Callback<NewsEnvelope> newsEnvelopeCallback = new Callback<NewsEnvelope>() {

        @Override
        public void onResponse(Call<NewsEnvelope> call, Response<NewsEnvelope> response) {
            NewsEnvelope newsEnvelope = response.body();
            List<New> news = NewMapper.map(newsEnvelope.getHits());
            swiperefresh.setRefreshing(false);
            mAdapter.addNews(news);
        }

        @Override
        public void onFailure(Call<NewsEnvelope> call, Throwable t) {
            swiperefresh.setRefreshing(false);
            Toast.makeText(MainActivity.this, "No se ha podido conectar al servicio de noticias, intente mas tarde", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        this.swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        this.mRecyclerView.setHasFixedSize(true);

        this.mLayoutManager = new LinearLayoutManager(this);
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mAdapter = new NewsAdapter(this);
        this.mRecyclerView.setAdapter(this.mAdapter);
        //trae noticias

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ServiceGenerator.createService(News.class).getNewsByDate().enqueue(newsEnvelopeCallback);
            }
        });
        swiperefresh.setRefreshing(true);
        ServiceGenerator.createService(News.class).getNewsByDate().enqueue(newsEnvelopeCallback);

    }
}
