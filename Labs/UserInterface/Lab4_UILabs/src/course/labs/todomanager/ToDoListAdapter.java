package course.labs.todomanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import course.labs.todomanager.ToDoItem.Status;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// TODO - Create a View for the ToDoItem at specified position
    // Remember to check whether convertView holds an already allocated View
    // before created a new View.
    // Consider using the ViewHolder pattern to make scrolling more efficient
    // See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	static class ViewHolder {
	    public TextView text;
	    public CheckBox status;
	    public TextView priority;
	    public TextView date;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = (ToDoItem) getItem(position);

		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml.
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.todo_item, parent, false);
			
			//convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.todo_item, parent);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) convertView.findViewById(R.id.titleView);
			viewHolder.status = (CheckBox) convertView.findViewById(R.id.statusCheckBox);
			viewHolder.priority = (TextView) convertView.findViewById(R.id.priorityView);
			viewHolder.date = (TextView) convertView.findViewById(R.id.dateView);		
			convertView.setTag(viewHolder);
		}
		
		// TODO - Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();

		// TODO - Display Title in TextView
		viewHolder.text.setText(toDoItem.getTitle());

		// TODO - Set up Status CheckBox
        final CheckBox statusView = viewHolder.status;
        switch (toDoItem.getStatus()) {
        case DONE:
        	statusView.setChecked(true);
        	break;
        case NOTDONE:
        default:	
        	statusView.setChecked(false);
        	break;
        }
        
        // TODO - Must also set up an OnCheckedChangeListener,
        // which is called when the user toggles the status checkbox

        statusView
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						Log.i(TAG,"Entered onCheckedChanged()");
						if (isChecked) {
							toDoItem.setStatus(Status.DONE);
						} 
						else {
							toDoItem.setStatus(Status.NOTDONE);
						}
						notifyDataSetChanged();
                        
					}
				});


		// TODO - Display Priority in a TextView
        switch (toDoItem.getPriority()) {
        case HIGH:
        	viewHolder.priority.setText("HIGH");
        	break;
        case LOW:
        	viewHolder.priority.setText("LOW");
        	break;
        case MED:
        default:
        	viewHolder.priority.setText("MED");
        	break;
        }

		// TODO - Display Time and Date
        viewHolder.date.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));

		// Return the View you just created
		return convertView;

	}
}
