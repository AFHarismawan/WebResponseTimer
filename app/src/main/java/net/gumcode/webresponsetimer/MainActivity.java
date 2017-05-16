package net.gumcode.webresponsetimer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.gumcode.webresponsetimer.adapter.ColumnHeaderAdapter;
import net.gumcode.webresponsetimer.adapter.TableContentAdapter;
import net.gumcode.webresponsetimer.config.Config;
import net.gumcode.webresponsetimer.model.Item;
import net.gumcode.webresponsetimer.utilities.HTTPHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;


public class MainActivity extends AppCompatActivity {

    private Thread t1, t2, t3;

    private SortableTableView tableView;
    private List<Item> items = new ArrayList<>();
    private long python, php, perl;
    private boolean a, b, c;
    private String[] phps = {Config.PHP_GET_ALL_URL + "_500.php", Config.PHP_GET_ALL_URL + "_1000.php", Config.PHP_GET_ALL_URL + "_2000.php", Config.PHP_GET_ALL_URL + "_5000.php", Config.PHP_GET_ALL_URL + "_10000.php"};
    private String[] pythons = {Config.PYTHON_GET_ALL_URL + "_500.py", Config.PYTHON_GET_ALL_URL + "_1000.py", Config.PYTHON_GET_ALL_URL + "_2000.py", Config.PYTHON_GET_ALL_URL + "_5000.py", Config.PYTHON_GET_ALL_URL + "_10000.py"};
    private String[] perls = {Config.PERL_GET_ALL_URL + "_500.pl", Config.PERL_GET_ALL_URL + "_1000.pl", Config.PERL_GET_ALL_URL + "_2000.pl", Config.PERL_GET_ALL_URL + "_5000.pl", Config.PERL_GET_ALL_URL + "_10000.pl"};
    private AlertDialog dialog;
    private ProgressDialog pd;
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tableView = (SortableTableView) findViewById(R.id.table);
        tableView.setHeaderAdapter(new ColumnHeaderAdapter(this, getResources().getStringArray(R.array.table_header)));

        Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        View v = getLayoutInflater().inflate(R.layout.dialog_header, null);
        TextView title = (TextView) v.findViewById(R.id.title);
        title.setText("JUMLAH DATA");

        View content = getLayoutInflater().inflate(R.layout.dialog_content, null);
        ListView list = (ListView) content.findViewById(R.id.list);
        ArrayList<String> items = new ArrayList<>();
        items.add("500");
        items.add("1000");
        items.add("2000");
        items.add("5000");
        items.add("10000");
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.cancel();
                selected = i;
                start();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(v);
        builder.setView(content);
        dialog = builder.create();
        dialog.show();
    }

    private void initTable() {
        tableView.setDataAdapter(new TableContentAdapter(this, items));
    }

    private void start() {
        php = 0;
        python = 0;
        perl = 0;
        a = false;
        b = false;
        c = false;

        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //request start time
                long startTime = System.currentTimeMillis();

                InputStream response = HTTPHelper.sendGETRequest(phps[selected]);

                //request end time
                php = System.currentTimeMillis() - startTime;

                a = true;
            }
        });

        t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                //request start time
                long startTime = System.currentTimeMillis();

                InputStream response = HTTPHelper.sendGETRequest(perls[selected]);

                //request end time
                perl = System.currentTimeMillis() - startTime;

                b = true;
            }
        });

        t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                //request start time
                long startTime = System.currentTimeMillis();

                InputStream response = HTTPHelper.sendGETRequest(pythons[selected]);

                //request end time
                python = System.currentTimeMillis() - startTime;

                c = true;
            }
        });

        t1.start();
        t2.start();
        t3.start();

        boolean loop = true;
        while (loop) {
            if (a & b & c) {
                Item item = new Item();
                item.python = Long.toString(python);
                item.php = Long.toString(php);
                item.perl = Long.toString(perl);
                items.add(item);

                initTable();

                loop = false;
            }
        }

    }
}
