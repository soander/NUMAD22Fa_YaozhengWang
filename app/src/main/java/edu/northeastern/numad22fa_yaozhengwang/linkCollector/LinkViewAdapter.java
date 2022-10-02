package edu.northeastern.numad22fa_yaozhengwang.linkCollector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class LinkViewAdapter extends RecyclerView.Adapter<LinkViewHolder> {
    private final ArrayList<Link> links;
    private LinkClickListener linkClickListener;

    public LinkViewAdapter(ArrayList<Link> links) {
        this.links = links;
    }

    public void setOnLinkClickListener(LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_link, parent, false);
        return new LinkViewHolder(view, linkClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = links.get(position);
        holder.linkName.setText(link.getLinkName());
        holder.linkUrl.setText(link.getLinkUrl());
    }

    @Override
    public int getItemCount() {
        return links.size();
    }

    public interface LinkClickListener {
        void onLinkClick(int position);
    }
}
