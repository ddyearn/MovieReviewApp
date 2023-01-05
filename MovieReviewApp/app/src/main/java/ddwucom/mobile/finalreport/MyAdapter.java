package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Movie> movieList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<Movie> movieList) {
        this.context = context;
        this.layout = layout;
        this.movieList = movieList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return movieList.size(); }

    @Override
    public Object getItem(int i) { return movieList.get(i); }

    @Override
    public long getItemId(int i) { return movieList.get(i).get_id(); }

    @Override
    public View getView(int pos, View convertView, ViewGroup viewGroup) {
        final int position = pos;
        ViewHolder holder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
            holder.releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);
            holder.movieRate = (TextView) convertView.findViewById(R.id.movieRate);
            holder.moviePoster = (ImageView) convertView.findViewById(R.id.moviePoster);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.movieTitle.setText(movieList.get(position).getTitle());
        holder.releaseDate.setText(movieList.get(position).getReleaseDate());
        holder.movieRate.setText(movieList.get(position).getRate());

        switch (movieList.get(position).getTitle()) {
            case "탑건:매버릭":
                holder.moviePoster.setImageResource(R.mipmap.topgun);
                break;
            case "범죄도시2":
                holder.moviePoster.setImageResource(R.mipmap.crimecity);
                break;
            case "마녀 Part2. The Other One":
                holder.moviePoster.setImageResource(R.mipmap.witch);
                break;
            case "버즈 라이트이어":
                holder.moviePoster.setImageResource(R.mipmap.lightyear);
                break;
            case "쥬라기 월드:도미니언":
                holder.moviePoster.setImageResource(R.mipmap.jurassic);
                break;
            default:
                holder.moviePoster.setImageResource(R.mipmap.ic_launcher);
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        TextView movieTitle;
        TextView releaseDate;
        TextView movieRate;
        ImageView moviePoster;
    }
}
