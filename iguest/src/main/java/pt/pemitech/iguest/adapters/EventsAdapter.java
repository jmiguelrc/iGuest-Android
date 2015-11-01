package pt.pemitech.iguest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.azcltd.fluffycommons.adapters.ItemsAdapter;
import com.azcltd.fluffycommons.texts.SpannableBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import pt.pemitech.iguest.R;
import pt.pemitech.iguest.activities.MainActivity;
import pt.pemitech.iguest.api.RestClient;
import pt.pemitech.iguest.model.Event;

public class EventsAdapter extends ItemsAdapter<Event> implements View.OnClickListener {

    public EventsAdapter(Context context, List<Event> events) {
        super(context);
        if(events != null)
            setItemsList(events);
    }

    @Override
    protected View createView(Event item, int pos, ViewGroup parent, LayoutInflater inflater) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder();
        vh.image = (ImageView) view.findViewById(R.id.list_item_image);
        vh.image.setOnClickListener(this);
        vh.title = (TextView) view.findViewById(R.id.list_item_title);
        view.setTag(vh);

        return view;
    }

    @Override
    protected void bindView(Event item, int pos, View convertView) {
        ViewHolder vh = (ViewHolder) convertView.getTag();

        vh.image.setTag(item);
        Picasso.with(convertView.getContext())
                .load(RestClient.SERVER + "/" +item.getImage())
                .resize(700,280)
                //.fit()
                .centerCrop()
                .into(vh.image);

        SpannableBuilder builder = new SpannableBuilder(convertView.getContext());
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append( item.getClubName() ).append("  ")
                .clearStyle()
                .append(item.getTitle());
        vh.title.setText(builder.build());
    }

    @Override
    public void onClick(View view) {
        if (view.getContext() instanceof MainActivity) {
            MainActivity activity = (MainActivity) view.getContext();
            activity.openDetails(view, (Event) view.getTag());
        }
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

}
