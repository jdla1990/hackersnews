package com.reigndesign.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.reigndesign.app.R;
import com.reigndesign.app.database.HackersFeedContract;
import com.reigndesign.app.database.HackersFeedDbHelper;
import com.reigndesign.app.listeners.SwipeDismissTouchListener;
import com.reigndesign.app.models.New;
import com.reigndesign.app.utils.DateHelper;

import java.util.ArrayList;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final List<New> newList;
    private HackersFeedDbHelper mDbHelper;
    private Context context;
    private String TAG = "NewsAdapter";


    public NewsAdapter(Context context) {
        this.mDbHelper = new HackersFeedDbHelper(context);
        this.context = context;
        this.newList = new ArrayList<>();
        this.loadFromStorage();
    }

    private void loadFromStorage() {
        List<New> newsFromDb = HackersFeedContract.fromDb(mDbHelper.getReadableDatabase());
        if (newsFromDb.size() > 0) {
            newList.addAll(0, newsFromDb);
            notifyDataSetChanged();
        }
    }

    public void addNews(List<New> mNew) {
        List<New> newFromDb = HackersFeedContract.toDb(mDbHelper, mNew);
        if (newFromDb.size() > 0) {
            newList.addAll(0, newFromDb);
            notifyDataSetChanged();
            Toast.makeText(context, String.format("%s news added", newFromDb.size()), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Nothing to show!", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeNewFromDb(New newToHide) {
        HackersFeedContract.removeNew(newToHide.getIdDb(), mDbHelper.getReadableDatabase());
        this.newList.remove(newToHide);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_new, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        New mNew = newList.get(position);

        holder.titleStory.setText(mNew.getTitle());
        holder.contentStoryTextView.setText(mNew.getComment());
        holder.leyendByWho.setText(String.format("By %s, %s", mNew.getAuthor(),
                DateHelper.format(mNew.getCreatedAt())));
        holder.idDB = mNew.getIdDb();

        holder.cardView.setOnTouchListener(new SwipeDismissTouchListener(holder.cardView, mNew,
                new SwipeDismissTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(Object token) {
                        return true;//siempre se podra borrar una noticia
                    }

                    @Override
                    public void onDismiss(View view, Object token) {
                        New newDismissed = (New) token;
                        removeNewFromDb(newDismissed);
                    }
                }));
    }

    @Override
    public int getItemCount() {
        return newList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleStory;
        TextView contentStoryTextView;
        TextView leyendByWho;
        CardView cardView;
        int idDB;

        ViewHolder(View itemView) {
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.titleStory = (TextView) itemView.findViewById(R.id.titleStoryTextView);
            this.contentStoryTextView = (TextView) itemView.findViewById(R.id.contentStoryTextView);
            this.leyendByWho = (TextView) itemView.findViewById(R.id.leyendByWho);
        }
    }
}
