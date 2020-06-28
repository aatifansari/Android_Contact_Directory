
package com.example.contact1;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;

        import androidx.appcompat.app.AppCompatActivity;

        import com.google.android.material.floatingactionbutton.FloatingActionButton;

        import java.util.ArrayList;
        import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Dbhandler db = new Dbhandler(this);
        ArrayList<HashMap<String, String>> userlist = db.getUsers();
        ListView lv = (ListView) findViewById(R.id.list);
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, userlist, R.layout.list_row, new String[]{"name", "pnum"},
                new int[]{R.id.name, R.id.pnum});
        lv.setAdapter(adapter);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.back_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}