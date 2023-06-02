package com.hziee.management;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hziee.management.placeholder.PlaceholderContent.PlaceholderItem;
import com.hziee.management.databinding.FragmentTaskBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public TaskListRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.taskTitleView.setText(mValues.get(position).id);
        holder.taskCommitView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView taskTitleView;
        public final TextView taskCommitView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentTaskBinding binding) {
            super(binding.getRoot());
            taskTitleView = binding.taskTitle;
            taskCommitView = binding.taskCommit;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskCommitView.getText() + "'";
        }
    }
}