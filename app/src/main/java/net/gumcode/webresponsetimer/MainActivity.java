package net.gumcode.webresponsetimer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
                start();
            }
        });
    }

    private void initTable() {
        tableView.setDataAdapter(new TableContentAdapter(this, items));
    }

    private void start() {
        ProgressDialog pg = ProgressDialog.show(this, "", "Loading...", true, false);

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

                InputStream response = HTTPHelper.sendGETRequest(Config.PHP_GET_ALL_URL);

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

                InputStream response = HTTPHelper.sendGETRequest(Config.PERL_GET_ALL_URL);

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

                InputStream response = HTTPHelper.sendGETRequest(Config.PYTHON_GET_ALL_URL);

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
                pg.dismiss();

                loop = false;
            }
        }
    }
}
