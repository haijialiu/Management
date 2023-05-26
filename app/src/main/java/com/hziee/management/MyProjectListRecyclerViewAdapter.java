package com.hziee.management;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hziee.management.placeholder.PlaceholderContent.PlaceholderItem;
import com.hziee.management.databinding.FragmentProjectBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProjectListRecyclerViewAdapter extends RecyclerView.Adapter<MyProjectListRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyProjectListRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentProjectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.projectTitleView.setText(mValues.get(position).id);
        holder.viewTasksButton.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView projectTitleView;
        public final Button viewTasksButton;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentProjectBinding binding) {
            super(binding.getRoot());
            projectTitleView = binding.projectTitle;
            viewTasksButton = binding.viewTasks;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + viewTasksButton.getText() + "'";
        }
    }
}