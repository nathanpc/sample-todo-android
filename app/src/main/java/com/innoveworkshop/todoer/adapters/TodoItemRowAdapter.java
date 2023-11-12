package com.innoveworkshop.todoer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.innoveworkshop.todoer.R;
import com.innoveworkshop.todoer.models.TodoItem;

import java.util.ArrayList;

public class TodoItemRowAdapter extends RecyclerView.Adapter<TodoItemRowAdapter.ViewHolder> {
    private ArrayList<TodoItem> items;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public TodoItemRowAdapter(Context context, ArrayList<TodoItem> items) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        clickListener = null;
    }

    /**
     * Sets the event listener when a row gets clicked by the user.
     *
     * @param listener Event handler for the click.
     */
    public void setOnClickListener(ItemClickListener listener) {
        clickListener = listener;
    }

    /**
     * Inflates the layout of the row into the actual list.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return Instantiated row layout.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_todo_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data from each item in the list to a row in the list.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoItem item = items.get(position);

        holder.doneCheck.setChecked(item.isDone());
        holder.titleLabel.setText(item.getTitle());
        holder.notesLabel.setText(item.getNotes());
    }

    /**
     * The RecyclerView needs to know the size of our list, this just provides that.
     *
     * @return Size of our data.
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Class responsible for recycling the views as you scroll.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleLabel;
        public TextView notesLabel;
        public CheckBox doneCheck;

        /**
         * Sets up the view that was inflated.
         *
         * @param itemView Layout view that was inflated.
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Get the components in the view.
            titleLabel = itemView.findViewById(R.id.title_label);
            notesLabel = itemView.findViewById(R.id.notes_label);
            doneCheck = itemView.findViewById(R.id.done_check);

            // Set what happens when the view gets clicked.
            itemView.setOnClickListener(this);
        }

        /**
         * Handles the onclick event of the view.
         */
        @Override
        public void onClick(View view) {
            // Pass the event to our custom listener in the activity.
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    /**
     * Defines what to do when a list item gets clicked.
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
