package mikolajgrygiel.jedzmyrazem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyExpandableListAdapter extends BaseExpandableListAdapter
{
    private int ParentClickStatus=-1;
    private int ChildClickStatus=-1;

    private LayoutInflater inflater;
    private ArrayList<Parent> parents;
    private Context context;

    public MyExpandableListAdapter(AppCompatActivity activity, ArrayList<Parent> parentsList, Context contextParrent)
    {
        // Create Layout Inflator
        context = contextParrent;
        inflater = LayoutInflater.from(activity);
        parents = parentsList;
    }


    // This Function used to inflate parent rows view

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parentView)
    {
        final Parent parent = parents.get(groupPosition);

        // Inflate parent.xml file for parent rows
        convertView = inflater.inflate(R.layout.parent, parentView, false);

        // Get parent.xml file elements and set values
        ((TextView) convertView.findViewById(R.id.textViewStartPlace)).setText(parent.getStartPlace());
        ((TextView) convertView.findViewById(R.id.textViewFinishPlace)).setText(parent.getFinishPlace());
        ((TextView) convertView.findViewById(R.id.textViewStartTime)).setText(parent.getStartTime());
        ((TextView) convertView.findViewById(R.id.textViewFinishTime)).setText(parent.getFinishTime());
        ((TextView) convertView.findViewById(R.id.textViewPassesCount)).setText(parent.getPasses());

        boolean a = isExpanded;
        return convertView;
    }


    // This Function used to inflate child rows view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parentView)
    {
        final Parent parent = parents.get(groupPosition);
        final Child child = parent.getChildren().get(childPosition);

        // Inflate child.xml file for child rows
        convertView = inflater.inflate(R.layout.child, parentView, false);

        // Get child.xml file elements and set values
        ((TextView) convertView.findViewById(R.id.textViewStartPlace)).setText(child.getStartPlace());
        ((TextView) convertView.findViewById(R.id.textViewFinishPlace)).setText(child.getFinishPlace());
        ((TextView) convertView.findViewById(R.id.textViewStartTime)).setText(child.getStartTime());
        ((TextView) convertView.findViewById(R.id.textViewFinishTime)).setText(child.getFinishTime());
        ((TextView) convertView.findViewById(R.id.textViewDriverName)).setText(child.getDriver());
        ((TextView) convertView.findViewById(R.id.textViewSpaces)).setText(child.getSpaces());

        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        //Log.i("Childs", groupPosition+"=  getChild =="+childPosition);
        return parents.get(groupPosition).getChildren().get(childPosition);
    }

    //Call when child row clicked
    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        /****** When Child row clicked then this function call *******/

        //Log.i("Noise", "parent == "+groupPosition+"=  child : =="+childPosition);
        if( ChildClickStatus!=childPosition)
        {
            ChildClickStatus = childPosition;
        }

        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        int size=0;
        if(parents.get(groupPosition).getChildren()!=null)
            size = parents.get(groupPosition).getChildren().size();
        return size;
    }


    @Override
    public Object getGroup(int groupPosition)
    {
        Log.i("Parent", groupPosition+"=  getGroup ");

        return parents.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return parents.size();
    }

    //Call when parent row clicked
    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public void notifyDataSetChanged()
    {
        // Refresh List rows
        super.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty()
    {
        return ((parents == null) || parents.isEmpty());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

}
