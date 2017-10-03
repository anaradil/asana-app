package com.example.asanaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Usser on 14.11.2016.
 */
public class ListAdapter extends ArrayAdapter<PoseObject> implements View.OnClickListener {

    ListView lv;
    static boolean flag = true;
    Context c;
    BackendlessUser user;
    ViewHolder holder = null;
    PoseObject[] poses;
    public ListAdapter(Context context, int resource, PoseObject[] p, ListView lv) {
        super(context, resource,p);
        c = context;
        this.lv = lv;
        this.poses = p;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final PoseObject pose = getItem(position);
        Backendless.Data.mapTableToClass("Poses", Poses.class);
        user = Backendless.UserService.CurrentUser();
        if (convertView == null) {
            holder = new ViewHolder(c);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_pose, parent, false);
            holder.detail = (Button) convertView.findViewById(R.id.button7);
            holder.canDo = (Button) convertView.findViewById(R.id.button8);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        if(pose.isKnown()) {
            holder.canDo.setPressed(true);
            holder.canDo.setBackgroundResource(R.drawable.button_pressed_fragment);
            holder.canDo.setEnabled(false);

        } else {
            holder.canDo.setPressed(false);
            holder.canDo.setBackgroundResource(R.drawable.button_fragment);
            holder.canDo.setEnabled(true);
        }
        holder.detail.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {

                                                 Intent i = new Intent(c, ShowDetail.class);
                                                 i.putExtra("Detail", pose.getDetail());
                                                 c.startActivity(i);
                                             }
                                         });
        ImageDownload id = new ImageDownload(holder.iv, c);
        id.execute(pose.getObjectId());
        holder.canDo.setOnClickListener(this);
        return convertView;
    }

    //if can do it button is pressed update list of known asanas in backendless and set known=true in corresponding poseObject
    @Override
    public void onClick(View view) {
        View parentRow = (View)view.getParent();
        ListView listView  = (ListView)parentRow.getParent();
        final int position = listView.getPositionForView(parentRow);
        int xp = 0;
        if (poses[position].getDiff() == 1)
            xp = 20;
        else if (poses[position].getDiff() == 2)
            xp = 50;
        else
            xp = 70;
        LoggedInActivity.appUser.addPose(poses[position]);
        LoggedInActivity.appUser.addBPoses(poses[position].getBackendlessPose());
        xp = (Integer) LoggedInActivity.user.getProperty("xp") + xp;
        user.setProperty("Pose",LoggedInActivity.appUser.getBackPosesArray());
        user.setProperty("xp",xp);
        LoggedInActivity.appUser.setXp(xp);
        Backendless.UserService.update( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser user )
            {
                Toast.makeText(c, "Congratulations! You learned one more asana! " ,
                        Toast.LENGTH_LONG).show();
                poses[position].setKnown();
            }

            public void handleFault( BackendlessFault fault )
            {
                // user update failed
            }
        });

    }
}

