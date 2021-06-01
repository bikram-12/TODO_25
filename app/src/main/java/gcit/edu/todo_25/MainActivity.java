package gcit.edu.todo_25;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mFname, surname, mMark, id;
    Button mAdd, mView, mUpdate, mDelete;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFname = findViewById(R.id.editFname);
        surname = findViewById(R.id.editLname);
        mMark = findViewById(R.id.editMark);
        id = findViewById(R.id.id);
        mAdd = findViewById(R.id.Add);
        mView = findViewById(R.id.View);
        mUpdate = findViewById(R.id.Update);
        mDelete = findViewById(R.id.Delete);
        myDb = new DatabaseHelper(this);
    }

    public void adddata(View view) {
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(id.getText().toString(),
                        mFname.getText().toString(),
                        surname.getText().toString(),
                        mMark.getText().toString());
                if(isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainActivity.this, "Data  not Inserted", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void view(View view) {
        Cursor res = myDb.getAllData();
        if(res.getCount()==0){
            showMessage("Error", "Nothing found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Student id:"+ res.getString(0)+ "\n");
            buffer.append("First name:"+ res.getString(1)+ "\n");
            buffer.append("Last nmae:"+ res.getString(2)+ "\n");
            buffer.append("Marks:"+ res.getString(3)+ "\n");

            showMessage("List of Students", buffer.toString());

        }
    }

    private void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void update(View view) {
        boolean isUpdate = myDb.updateData(id.getText().toString(), mFname.getText().toString(), surname.getText().toString(),
                mMark.getText().toString());
        if(isUpdate == true)
            Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
    }

    public void delete(View view) {
        Integer deleteRows = myDb.deleteData(id.getText().toString());
        if(deleteRows > 0){
            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();

        }else
            Toast.makeText(MainActivity.this, "Data is not Deleted", Toast.LENGTH_SHORT).show();

    }
}