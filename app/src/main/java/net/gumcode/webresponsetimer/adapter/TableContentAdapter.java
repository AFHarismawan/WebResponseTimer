package net.gumcode.webresponsetimer.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gumcode.webresponsetimer.model.Item;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;


/**
 * Created by A. Fauzi Harismawan on 6/19/2016.
 */
public class TableContentAdapter extends TableDataAdapter<Item> {

    public TableContentAdapter(Context context, List<Item> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Item item = getRowData(rowIndex);
        View renderedView = null;

        TextView textView = new TextView(getContext());
        textView.setPadding(0, 20, 0, 20);
        switch (columnIndex) {
            case 0:
                textView.setText(Integer.toString(rowIndex + 1));
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
            case 1:
                textView.setText(item.python);
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
            case 2:
                textView.setText(item.php);
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
            case 3:
                textView.setText(item.perl);
                textView.setGravity(Gravity.CENTER);
                renderedView = textView;
                break;
        }

        return renderedView;
    }
}
