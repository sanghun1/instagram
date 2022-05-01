package com.cos.instagram.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.instagram.R;
import com.cos.instagram.model.Post;

import java.util.List;

public class PostContentAdapter extends RecyclerView.Adapter<PostContentAdapter.PostContentHolder> {

    private List<Post> datas;

    public PostContentAdapter(List<Post> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public PostContentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostContentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostContentHolder holder, int position) {
        Post data = datas.get(position);
        // 홀더 쓰기
        holder.id.setText(data.getPostId());
        holder.contentId.setText(data.getPostId());
        holder.content.setText(data.getContents());
        holder.like.setText(data.getLike());
        holder.reply.setText(data.getReply());
        holder.date.setText(data.getPostDate().toString());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostContentHolder extends RecyclerView.ViewHolder {

        private TextView id, contentId, content, like, reply, date;

        public PostContentHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.post_item_id);
            contentId = itemView.findViewById(R.id.post_item_content_id);
            content = itemView.findViewById(R.id.post_item_content);
            like = itemView.findViewById(R.id.post_item_like_count);
            reply = itemView.findViewById(R.id.post_item_reply_count);
            date = itemView.findViewById(R.id.post_item_ago);
        }
    }
}
