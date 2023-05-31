package com.hziee.management;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hziee.management.entity.Project;
import com.hziee.management.placeholder.PlaceholderContent.PlaceholderItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProjectListRecyclerViewAdapter extends RecyclerView.Adapter<ProjectListRecyclerViewAdapter.ProjectHolder> {

    private final List<Project> projects;
    private Activity activity;
    public ProjectListRecyclerViewAdapter(List<Project> items,Activity activity) {
        projects = items;
        this.activity = activity;
    }

    @Override
    public ProjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        return new ProjectHolder(inflater,parent);

    }

    @Override
    public void onBindViewHolder(final ProjectHolder holder, int position) {
        String count;
        Project project = projects.get(position);
        holder.project = project;
        holder.projectTitleView.setText(project.getName());
        count = project.getMembersCount()==null ? "0":project.getMembersCount().toString();
        holder.membersCountView.setText(count);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 kk:mm", Locale.CHINA);
        holder.startTimeView.setText(dateFormat.format(project.getStartTime()));
        holder.endTimeView.setText(dateFormat.format(project.getEndTime()));

        //holder.viewTasksButton.setText("查看任务");
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public class ProjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView projectTitleView;
        public final TextView startTimeView;
        public final TextView endTimeView;
        public final TextView membersCountView;

        public Project project;

        public ProjectHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_project,parent,false));

            projectTitleView = itemView.findViewById(R.id.project_title);
            startTimeView = itemView.findViewById(R.id.start_time);
            endTimeView = itemView.findViewById(R.id.end_time);
            membersCountView = itemView.findViewById(R.id.project_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + projectTitleView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(itemView.getContext(),
                    project.getName()+"被点击！",Toast.LENGTH_SHORT).show();
        }
    }
}