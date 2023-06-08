package com.hziee.management;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hziee.management.entity.Task;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskHolder> {

    private final List<Task> mTasks;
    private Callbacks callbacks;
    public TaskListRecyclerViewAdapter(List<Task> items,Callbacks callbacks) {
        mTasks = items;
        this.callbacks = callbacks;
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TaskHolder(inflater,parent);
    }

    @Override
    public void onBindViewHolder(final TaskHolder holder, int position) {
        //TODO
        Task task = mTasks.get(position);
        holder.task = task;
        holder.taskTitleView.setText(task.getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 kk:mm", Locale.CHINA);
        holder.startTimeView.setText(dateFormat.format(task.getStartTime()));
        holder.endTimeView.setText(dateFormat.format(task.getEndTime()));
        holder.taskHeaderView.setText(task.getHeader());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView taskTitleView;
        public final TextView startTimeView;
        public final TextView endTimeView;
        public final TextView taskHeaderView;
        public Task task;


        public TaskHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_task,parent,false));
            taskTitleView = itemView.findViewById(R.id.list_task_title);
            startTimeView = itemView.findViewById(R.id.list_task_start_time);
            endTimeView = itemView.findViewById(R.id.list_task_end_time);
            taskHeaderView = itemView.findViewById(R.id.list_task_header);
            itemView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskTitleView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            callbacks.onItemSelected(task.getId());
        }
    }
}