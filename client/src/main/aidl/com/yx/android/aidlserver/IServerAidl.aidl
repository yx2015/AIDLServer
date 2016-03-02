// IServerAidl.aidl
package com.yx.android.aidlserver;
import com.yx.android.aidlserver.Person;

interface IServerAidl {
   int add(int a,int b);
   List<Person> addPerson(in Person person);
}
