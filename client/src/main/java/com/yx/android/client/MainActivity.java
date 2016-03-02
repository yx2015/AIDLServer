package com.yx.android.client;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yx.android.aidlserver.IServerAidl;
import com.yx.android.aidlserver.Person;

import java.util.List;


public class MainActivity extends Activity {
    private EditText mNum1, mNum2;
    private TextView mSum;
    private Button mSubmit;
    IServerAidl aidl;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl = IServerAidl.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidl = null;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNum1 = (EditText) findViewById(R.id.et_num1);
        mNum2 = (EditText) findViewById(R.id.et_num2);
        mSubmit = (Button) findViewById(R.id.btn_submit);
        mSum = (TextView) findViewById(R.id.tv_sum);
        bindService();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1 = mNum1.getText().toString();
                String num2 = mNum2.getText().toString();
                try {
                    String sum = aidl.add(Integer.parseInt(num1), Integer.parseInt(num2)) + "";
                    mSum.setText(sum);
                    List<Person> persons = aidl.addPerson(new Person("yangxin", 24));
                    Log.e("persons", persons.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    mSum.setText("调用失败");
                }
            }
        });

    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.yx.android.aidlserver", "com.yx.android.aidlserver.AidlService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
