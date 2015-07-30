package co.adrianblan.cheddar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Adrian on 2015-07-30.
 */
public class CommentAdapter extends BaseAdapter {

    private ArrayList<Comment> comments;
    private ArrayList<Color> colors;

    public CommentAdapter() {
        comments = new ArrayList<Comment>();
        colors = new ArrayList<Color>();
        //colors.add(Color.parseColor())
    }

    public void add (Comment c){
        comments.add(c);
    }

    public void add (int i, Comment c){
        comments.add(i, c);
    }

    public void clear() {
        comments.clear();
    }

    public int getPosition(Comment c) {

        for(int i = 0; i < getCount(); i++) {
            if(comments.get(i).equals(c)){
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Comment com = comments.get(position);

        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        View comment_view = inflater.inflate(R.layout.comment, parent, false);

        TextView title = (TextView) comment_view.findViewById(R.id.comment_title);
        title.setText(com.getTitle());

        if(com.getBody() != null) {
            TextView body = (TextView) comment_view.findViewById(R.id.comment_body);

            // We can't show the text as is, but have to parse it as html
            body.setText(Html.fromHtml(com.getBody()));
        }

        TextView time = (TextView) comment_view.findViewById(R.id.comment_time);
        time.setText(com.getTime());

        // Adds padding based on hierarchy, and adds indicator
        if(com.getHierarchy() > 0){
            LinearLayout container = (LinearLayout) comment_view.findViewById(R.id.comment);
            container.setPadding((int) dpToPixels(5, parent.getContext()) * (com.getHierarchy() - 1), 0, 0, 0);

            LinearLayout indicator = (LinearLayout) comment_view.findViewById(R.id.comment_indicator);
            indicator.setVisibility(View.VISIBLE);
        }

        return comment_view;
    }

    public static float dpToPixels(float dp, Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}