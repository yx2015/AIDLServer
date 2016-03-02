package com.yx.android.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/1 0001.
 */
public class AidlService extends Service {
    private List<Person> persons;
    private IBinder iBinder = new IServerAidl.Stub() {

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.e("TAG", "传过来的数字是" + a + "和" + b);
            return a + b;
        }

        @Override
        public List<Person> addPerson(Person person) throws RemoteException {
            persons.add(person);
            return persons;
        }


    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TAG", "服务端启动了");
        persons = new ArrayList<>();
        return iBinder;
    }

}
