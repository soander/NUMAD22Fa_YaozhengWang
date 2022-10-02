package edu.northeastern.numad22fa_yaozhengwang.linkCollector;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.northeastern.numad22fa_yaozhengwang.R;

public class LinkViewHolder extends RecyclerView.ViewHolder {
    public TextView linkName;
    public TextView linkUrl;

    public LinkViewHolder(@NonNull View itemView, final LinkViewAdapter.LinkClickListener linkClickListener) {
        super(itemView);
        linkName = itemView.findViewById(R.id.link_name);
        linkUrl = itemView.findViewById(R.id.link_url);

        itemView.setOnClickListener(l -> {
            int position = getLayoutPosition();
            if(position != RecyclerView.NO_POSITION) {
                linkClickListener.onLinkClick(position);
            }
        });
    }
}
